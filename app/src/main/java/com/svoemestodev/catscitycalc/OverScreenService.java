package com.svoemestodev.catscitycalc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.svoemestodev.catscitycalc.activities.GameActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Timer;

public class OverScreenService extends Service implements View.OnTouchListener {

    private final IBinder mBinder = new LocalBinder();
    private ImageView ivTakeScreenshot;
    private ImageView ivClose;
    public RelativeLayout overlayLayout;
    private View topLeftView;
    private WindowManager windowManager;

    private float offsetX;
    private float offsetY;
    private int originalXPos;
    private int originalYPos;
    private boolean moving;

    private CapturedImageTransmogrifier capturedImageTransmogrifier;
    private MediaProjection mediaProjection;
    private VirtualDisplay virtualDisplay;
    private MediaProjectionManager mediaProjectionManager;
    private Handler handler;
    final private HandlerThread handlerThread = new HandlerThread(getClass().getSimpleName(), android.os.Process.THREAD_PRIORITY_BACKGROUND);
    public static final int VIRT_DISPLAY_FLAGS = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY | DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;
    public static final String EXTRA_RESULT_CODE = "resultCode";
    public static final String EXTRA_RESULT_INTENT = "resultIntent";

    private static final int NOTIFY_ID = 9906;
    private static final String CHANNEL_SCREENSHOT = "channel_screenshot";

    private int resultCode;
    private Intent resultData;
    private boolean waitingScreenshot;

    private boolean isLongPressed;


    private Timer timer;

    WindowManager getWindowManager() {
        return (windowManager);
    }

    Handler getHandler() {
        return (handler);
    }

    private void stopCapture() {
        if (mediaProjection != null) {
            mediaProjection.stop();
            virtualDisplay.release();
            mediaProjection = null;
        }
    }

    private void startCapture() {

        overlayLayout.setVisibility(View.INVISIBLE);


        waitingScreenshot = true;

        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, resultData);
        capturedImageTransmogrifier = new CapturedImageTransmogrifier(this);

        MediaProjection.Callback cb = new MediaProjection.Callback() {
            @Override
            public void onStop() {
                virtualDisplay.release();
            }
        };

        virtualDisplay = mediaProjection.createVirtualDisplay("catscitycalc",
                capturedImageTransmogrifier.getWidth(),
                capturedImageTransmogrifier.getHeight(),
                getResources().getDisplayMetrics().densityDpi,
                VIRT_DISPLAY_FLAGS,
                capturedImageTransmogrifier.getSurface(),
                null,
                handler);

        mediaProjection.registerCallback(cb, handler);

    }

    void processImage(final byte[] png) {

        if (waitingScreenshot) {

            waitingScreenshot = false;
            new Thread() {
                @Override
                public void run() {
                    File output = new File(getExternalFilesDir(null), "RealtimeScreenshot.png");

                    try {
                        FileOutputStream fos = new FileOutputStream(output);

                        fos.write(png);
                        fos.flush();
                        fos.getFD().sync();
                        fos.close();

                        MediaScannerConnection.scanFile(OverScreenService.this, new String[]{output.getAbsolutePath()}, new String[]{"image/png"}, null);
                        showToast("Скриншот сохранен: " + output.getAbsolutePath());
                        Log.e(getClass().getSimpleName(), "File: " + output.getAbsolutePath());

                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "Exception writing out screenshot", e);
                    }

                    Handler handler = new Handler(getBaseContext().getMainLooper());
                    handler.post( new Runnable() {
                        @Override
                        public void run() {
                            overlayLayout.setVisibility(View.VISIBLE);
                        }
                    } );

                    GameActivity.mainGameActivity.doRealtimeScreenshot();
                }
            }.start();

            stopCapture();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getRawX();
            float y = event.getRawY();

            moving = false;

            int[] location = new int[2];
            overlayLayout.getLocationOnScreen(location);

            originalXPos = location[0];
            originalYPos = location[1];

            offsetX = originalXPos - x;
            offsetY = originalYPos - y;

        } else if (event.getAction() == MotionEvent.ACTION_MOVE && isLongPressed) {
            int[] topLeftLocationOnScreen = new int[2];
            topLeftView.getLocationOnScreen(topLeftLocationOnScreen);

            float x = event.getRawX();
            float y = event.getRawY();

            WindowManager.LayoutParams params = (WindowManager.LayoutParams) overlayLayout.getLayoutParams();

            int newX = (int) (offsetX + x);
            int newY = (int) (offsetY + y);

            if (Math.abs(newX - originalXPos) < 1 && Math.abs(newY - originalYPos) < 1 && !moving) {
                return false;
            }

            params.x = newX - (topLeftLocationOnScreen[0]);
            params.y = newY - (topLeftLocationOnScreen[1]);

            windowManager.updateViewLayout(overlayLayout, params);
            moving = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isLongPressed = false;
            if (moving) {
                return true;
            }
        }

        return false;
    }

    public class LocalBinder extends Binder {
        public OverScreenService getService() {
            return OverScreenService.this;
        }
    }

    public void showToast(String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OverScreenService.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showOverlayLayout() {
        overlayLayout.setVisibility(View.VISIBLE);
    }

    public void hideOverlayLayout() {
        overlayLayout.setVisibility(View.INVISIBLE);
    }

    public void destroyOverlayLayout() {
        overlayLayout.setVisibility(View.INVISIBLE);
        overlayLayout = null;
        topLeftView = null;
        ivTakeScreenshot = null;
        ivClose = null;
    }

    public void createOverlayLayout() {

        overlayLayout = new RelativeLayout(this);

        RelativeLayout.LayoutParams imageViewParamTakeScreenshot = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ivTakeScreenshot = new ImageView(this);
        ivTakeScreenshot.setId(R.id.ls_iv_take_screenshot);
        ivTakeScreenshot.setLayoutParams(imageViewParamTakeScreenshot);
        ivTakeScreenshot.setImageResource(R.drawable.ic_photo_camera_red_24);
        ivTakeScreenshot.setMinimumWidth(200);
        ivTakeScreenshot.setMinimumHeight(200);
        imageViewParamTakeScreenshot.addRule(RelativeLayout.ALIGN_TOP);
        imageViewParamTakeScreenshot.addRule(RelativeLayout.ALIGN_LEFT);

        overlayLayout.addView(ivTakeScreenshot);

        RelativeLayout.LayoutParams imageViewParamClose = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ivClose = new ImageView(this);
        ivClose.setId(R.id.ls_iv_close);
        ivClose.setLayoutParams(imageViewParamClose);
        ivClose.setImageResource(R.drawable.ic_round_remove_circle_24);
        imageViewParamClose.addRule(RelativeLayout.END_OF, R.id.ls_iv_take_screenshot);

        overlayLayout.addView(ivClose);

        int layoutParamsType;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            layoutParamsType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        else {
            layoutParamsType = WindowManager.LayoutParams.TYPE_PHONE;
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutParamsType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 0;

        overlayLayout.setLayoutParams(params);


        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        overlayLayout.setOnTouchListener(this);

        windowManager.addView(overlayLayout, params);

        topLeftView = new View(this);

        topLeftView.setVisibility(View.VISIBLE);

        WindowManager.LayoutParams topLeftParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutParamsType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        topLeftParams.gravity = Gravity.LEFT | Gravity.TOP;
        topLeftParams.x = 0;
        topLeftParams.y = 0;
        topLeftParams.width = 0;
        topLeftParams.height = 0;
        windowManager.addView(topLeftView, topLeftParams);

        ivTakeScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("OverlayShowingService","ivTakeScreenshot onClick TakeScreenshot");
                showToast("Click Take Screenshot");

                startCapture();

            }
        });

        ivTakeScreenshot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isLongPressed = true;
                return false;
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("OverlayShowingService","onClick Close");

                showToast("Click Close");
                hideOverlayLayout();

            }
        });

        ivTakeScreenshot.setOnTouchListener(this);
        ivClose.setOnTouchListener(this);

        hideOverlayLayout();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("OverScreenService", "onBind()");

        resultCode = intent.getIntExtra(EXTRA_RESULT_CODE, 1337);
        resultData = intent.getParcelableExtra(EXTRA_RESULT_INTENT);

        NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mgr.getNotificationChannel(CHANNEL_SCREENSHOT) == null) {
            mgr.createNotificationChannel(new NotificationChannel(CHANNEL_SCREENSHOT, "Screenshot", NotificationManager.IMPORTANCE_DEFAULT));
        }

        NotificationCompat.Builder b = new NotificationCompat.Builder(this, CHANNEL_SCREENSHOT);

        b.setAutoCancel(true);
//                .setDefaults(Notification.DEFAULT_ALL);

        startForeground(NOTIFY_ID, b.build());

        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("OverScreenService", "onCreate()");
        mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        createOverlayLayout();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("OverScreenService", "onStartCommand()");
        resultData = intent.getParcelableExtra(EXTRA_RESULT_INTENT);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("OverScreenService", "onDestroy()");
        destroyOverlayLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("OverScreenService", "onConfigurationChanged()");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d("OverScreenService", "onLowMemory()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d("OverScreenService", "onTrimMemory()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("OverScreenService", "onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d("OverScreenService", "onRebind()");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d("OverScreenService", "onTaskRemoved()");
    }
}
