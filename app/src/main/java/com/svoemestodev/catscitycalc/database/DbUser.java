package com.svoemestodev.catscitycalc.database;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DbUser {
    private String userID;
    private String userUID;
    private String userName;
    private String userEmail;
    private String userNIC;
    private String teamID;
    private String leaderUID;
    private Date timestamp;

    public DbUser() {
    }

    public DbUser(DocumentSnapshot documentSnapshot) {

        String key;
        Map<String, Object> map = documentSnapshot.getData();

        key = "userID"; if (map.containsKey(key)) this.userID = map.get(key).toString();
        key = "userUID"; if (map.containsKey(key)) this.userUID = map.get(key).toString();
        key = "userName"; if (map.containsKey(key)) this.userName = map.get(key).toString();
        key = "userEmail"; if (map.containsKey(key)) this.userEmail = map.get(key).toString();
        key = "userNIC"; if (map.containsKey(key)) this.userNIC = map.get(key).toString();

        key = "teamID"; if (map.containsKey(key)) {
            if (documentSnapshot.get(key) == null) {
                this.teamID = null;
            } else {
                this.teamID = documentSnapshot.get(key).toString();
            }
        }

        key = "leaderUID"; if (map.containsKey(key)) {
            if (documentSnapshot.get(key) == null) {
                this.leaderUID = null;
            } else {
                this.leaderUID = documentSnapshot.get(key).toString();
            }
        }


        key = "timestamp"; if (map.containsKey(key)) {
            if (documentSnapshot.getTimestamp(key) == null) {
                this.timestamp = null;
            } else {
                this.timestamp = documentSnapshot.getTimestamp(key).toDate();
            }
        }

    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("timestamp", FieldValue.serverTimestamp());
        map.put("userID", userID);
        map.put("userUID", userUID);
        map.put("userName", userName);
        map.put("userEmail", userEmail);
        map.put("userNIC", userNIC);
        map.put("teamID", teamID);
        map.put("leaderUID", leaderUID);

        return map;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getLeaderUID() {
        return leaderUID;
    }

    public void setLeaderUID(String leaderUID) {
        this.leaderUID = leaderUID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }




}
