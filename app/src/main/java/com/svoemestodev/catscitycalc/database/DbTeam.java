package com.svoemestodev.catscitycalc.database;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.Map;



public class DbTeam {
    private String teamID;
    private String teamName;
    private Date timestamp;

    public DbTeam() {
    }

    public DbTeam(DocumentSnapshot documentSnapshot) {

        String key;
        Map<String, Object> map = documentSnapshot.getData();

        key = "teamID"; if (map.containsKey(key)) this.teamID = map.get(key).toString();
        key = "teamName"; if (map.containsKey(key)) this.teamName = map.get(key).toString();
        key = "timestamp"; if (map.containsKey(key)) {
            if (documentSnapshot.getTimestamp(key) == null) {
                this.timestamp = null;
            } else {
                this.timestamp = documentSnapshot.getTimestamp(key).toDate();
            }
        }

    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
