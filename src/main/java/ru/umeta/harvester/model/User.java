package ru.umeta.harvester.model;

import java.io.Serializable;

/**
 * Created by k.kosolapov on 23.03.2015.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 3611320607474857328L;
    private final String user;
    private final String password;
    private final Integer id;

    public User(String user, String password, Integer id) {
        this.user = user;
        this.password = password;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Override public int hashCode() {
        int prime = 31;
        int result = 1;
        result = result*prime + ((user == null) ? 0 : user.hashCode());
        result = result*prime + ((password  == null) ? 0 : password.hashCode());
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        if (user == null && other.user != null) {
            return false;
        }
        if (user != null) {
            if (!user.equals(other.user)) {
                return false;
            }
        }
        if (password == null && other.password != null) {
            return false;
        }
        if (password != null) {
            if (!password.equals(other.password)) {
                return false;
            }
        }
        return true;
    }
}
