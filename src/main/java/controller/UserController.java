package controller;

import service.UserService;
import utils.AppStarter;
import utils.Constants;
import view.*;

import java.util.Map;

public class UserController {

    UserService service;


    public UserController(UserService service) {
        this.service = service;
    }

    public void createUser() {
        UserCreateView view = new UserCreateView();
        Map<String, String> data = view.getData();
        String res = service.create(data);
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput(res);
            AppStarter.startApp();
        }
    }

    public void readUsers() {
        UserReadView view = new UserReadView();
        String res = service.read();
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput("\nUSERS:\n" + res);
            AppStarter.startApp();
        }
    }

    public void readUserById() {
        UserReadByIdView view = new UserReadByIdView();
        Map<String, String> data = view.getData();
        String res = service.readById(data);
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput("\nUSER BY ID:\n" + res);
            AppStarter.startApp();
        }
    }

    public void updateUser() {
        UserUpdateView view = new UserUpdateView();
        Map<String, String> data = view.getData();
        String res = service.update(data);
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput(res);
            AppStarter.startApp();
        }
    }

    public void deleteUser() {
        UserDeleteView view = new UserDeleteView();
        Map<String, String> data = view.getData();
        String res = service.delete(data);
        if (res.equals(Constants.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput(res);
            AppStarter.startApp();
        }
    }
}
