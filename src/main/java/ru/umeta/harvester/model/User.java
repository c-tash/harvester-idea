package ru.umeta.harvester.model;

import java.io.Serializable;

/**
 * Created by k.kosolapov on 23.03.2015.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 3611320607474857328L;
    private final String user;
    private final String password;

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
