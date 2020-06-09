package com.svoemestodev.catscitycalc;

public class DbUser {
    String userID;
    String userUID;
    String userName;
    String userEmail;
    String userNIC;
    String teamID;
    String leaderUID;

    public DbUser() {
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
