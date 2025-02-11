package org.example.model;

import org.example.util.Utils;

public class User {

    private int id;
    private String name;

    public User(String name) {

        this.id = Utils.generateUserId();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', userId=" + id + "}";
    }

}
