/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.bean;

/**
 *
 * @author kreshan
 */
public class SessionUserBean {

   
    private String username;
    private int UserProfileId;
    private String name;
    private int adStatus;
    private int status;
    private String userType;
    private String logFilePath;
    private String currentSessionId;
    
    private String qrEncMsg;

    public String getQrEncMsg() {
        return qrEncMsg;
    }

    public void setQrEncMsg(String qrEncMsg) {
        this.qrEncMsg = qrEncMsg;
    }
    
    
    public int getStatus() {
        return status;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    public int getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(int adStatus) {
        this.adStatus = adStatus;
    }

    public String getUsername() {
        return username;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserProfileId() {
        return UserProfileId;
    }

    public void setUserProfileId(int UserProfileId) {
        this.UserProfileId = UserProfileId;
    }



    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getCurrentSessionId() {
        return currentSessionId;
    }

    public void setCurrentSessionId(String currentSessionId) {
        this.currentSessionId = currentSessionId;
    }
 
}
