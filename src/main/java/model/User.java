package model;


import javax.json.Json;
import javax.json.JsonObject;
import java.sql.Date;

/**
 * Created by Niklas on 06.09.2016.
 */
public class User {
    private int id;
    private String username;
    private Team team;
    private Date startdate;
    private String sessionId;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("username", username)
                .add("team", team.toString())
                .add("startdate", startdate.getTime())
                .add("sessionId", sessionId).build();
    }
}
