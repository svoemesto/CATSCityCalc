package com.svoemestodev.catscitycalc;
import android.content.Context;
import android.graphics.Bitmap;
import com.googlecode.tesseract.android.TessBaseAPI;

public class TessOCR {
    private static TessBaseAPI mTess = null;

    public TessOCR(Context context, String language) {
        if (mTess == null) {
            mTess = new TessBaseAPI();
            String datapath = GameActivity.pathToCATScalcFolder + "/";
            mTess.init(datapath, language);
        }
    }

    public String getOCRResult(Bitmap bitmap) {
        mTess.setImage(bitmap);
        return mTess.getUTF8Text();
    }

    public void onDestroy() {
        if (mTess != null) mTess.end();
    }
}