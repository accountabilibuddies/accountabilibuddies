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

    public Scoreboard(ParseUser user, double money) {
        super();
        setUser(user);
        setPoints(0);
        setMoney(money);
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

    public double getMoney() {
        return getDouble("money");
    }

    public void setMoney(double money) {
        put("money", money);
    }

    public void rewardPoints(int points) {
        setPoints(getPoints() + points);
    }
}
