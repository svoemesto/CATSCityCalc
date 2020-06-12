package com.svoemestodev.catscitycalc.database;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.svoemestodev.catscitycalc.activities.GameActivity;

public class Database {

    public static void deleteTeam(String teamID) {

        DocumentReference drTeam = GameActivity.fbDb.collection("teams").document(teamID);
        drTeam.delete();

    }

    public static void leaveTeam(String teamID, String userID, String teamUserID) {

        DocumentReference drUser = GameActivity.fbDb.collection("users").document(userID);

        drUser.update("teamID", null);

        DocumentReference drTeamUser = GameActivity.fbDb.collection("teams").document(teamID).collection("teamUsers").document(teamUserID);
        drTeamUser.delete();

    }



}
