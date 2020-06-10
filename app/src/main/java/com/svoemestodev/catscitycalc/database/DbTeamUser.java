package com.svoemestodev.catscitycalc.database;

public class DbTeamUser {

    private String teamUserID;
    private String teamID;
    private String userID;
    private String userRole;
    private String userNIC;

    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }

    public String getTeamUserID() {
        return teamUserID;
    }

    public void setTeamUserID(String teamUserID) {
        this.teamUserID = teamUserID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
