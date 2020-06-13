package com.svoemestodev.catscitycalc.database;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class DbTeam {
    private String teamID;          // айди банды
    private String teamName;        // название банды
    private Date timestamp;         // таймстамп
    private boolean teamIsPublic;   // банда - публичная (видна в списке банд)
    private boolean teamIsOpened;   // в банду открыт прием игроков


    public DbTeam() {
    }

    public DbTeam(DocumentSnapshot documentSnapshot) {

        String key;
        Map<String, Object> map = documentSnapshot.getData();

        key = "teamID"; if (map.containsKey(key)) this.teamID = map.get(key).toString();
        key = "teamName"; if (map.containsKey(key)) this.teamName = map.get(key).toString();
        key = "teamIsPublic"; if (map.containsKey(key)) this.teamIsPublic = (boolean) map.get(key);
        key = "teamIsOpened"; if (map.containsKey(key)) this.teamIsOpened = (boolean) map.get(key);
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
        map.put("teamID", teamID);
        map.put("teamName", teamName);
        map.put("teamIsPublic", teamIsPublic);
        map.put("teamIsOpened", teamIsOpened);

        return map;
    }

    public boolean isTeamIsPublic() {
        return teamIsPublic;
    }

    public void setTeamIsPublic(boolean teamIsPublic) {
        this.teamIsPublic = teamIsPublic;
    }

    public boolean isTeamIsOpened() {
        return teamIsOpened;
    }

    public void setTeamIsOpened(boolean teamIsOpened) {
        this.teamIsOpened = teamIsOpened;
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
