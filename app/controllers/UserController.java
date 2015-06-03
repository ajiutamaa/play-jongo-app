package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Map;

/**
 * Created by lenovo on 6/3/2015.
 */
public class UserController extends Controller {
    public Result register() {
        Map<String, String []> data = Controller.request().body().asFormUrlEncoded();
        String username = data.get("username")[0];
        String password = data.get("password")[0];
        User user = new User(username, password);
        return user.insert()? ok("User registered!") : ok("Registration failed");
    }
    public Result login() {
        return ok();
    }
    public Result logout() {
        return ok();
    }
}
