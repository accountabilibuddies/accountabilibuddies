package com.accountabilibuddies.accountabilibuddies.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Scoreboard")
public class Scoreboard extends ParseObject {

    /**
     Scoreboard {
        ParseUser user;
        int points;
        float money;
     }
     */

    // Default Constructor
    public Scoreboard() {
        super();
    }

    public Scoreboard(ParseUser user) {
        super();
        setUser(user);
        setPoints(0);
        setMoney(0.0);
    }

    public ParseUser getUser() {
        return (ParseUser) get("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public int getPoints() {
        return (int) get("points");
    }

    public void setPoints(int points) {
        put("points", points);
    }

    public Double getMoney() {
        return (Double) get("money");
    }

    public void setMoney(Double money) {
        put("money", money);
    }

    public void rewardPoints(int points) {
        setPoints(getPoints() + points);
    }
}
