package org.mentalizr.cli.serviceObject;

//import jakarta.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SessionStatus {

    private boolean hasSession;
    private String userRole;
    private String sessionId;

    public SessionStatus() {

    }

    public SessionStatus(boolean hasSession, String userRole, String sessionId) {
        this.hasSession = hasSession;
        this.userRole = userRole;
        this.sessionId = sessionId;
    }

    public boolean isHasSession() {
        return hasSession;
    }

    public void setHasSession(boolean hasSession) {
        this.hasSession = hasSession;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
