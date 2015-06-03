package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.MongoCollection;
import utils.DB;

import java.util.UUID;

/**
 * Created by pxajie on 6/3/2015.
 */
public class User {
    @JsonProperty("_id")
    String id;
    String username;
    String password;
    UUID auth;

    public User () {}

    public User (String username, String password) {
        this.username = username;
        this.password = password;
        this.auth = UUID.randomUUID();
    }

    private static MongoCollection coll() {
        return DB.jongo.getCollection("user");
    }

    public boolean insert() {
        try {
            coll().insert(this);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User findUser(String username) {
        return coll().findOne("{username: #}", username).as(User.class);
    }

    public String authenticateUser(String username, String password) {
        User user = findUser(username);
        if (user.password.equals(password)) {
            return user.auth.toString();
        } else {
            return null;
        }
    }
}
