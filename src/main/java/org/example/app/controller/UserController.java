package org.example.app.controller;

import org.example.app.service.UserService;
import org.example.app.utils.AppStarter;
import org.example.app.utils.Users;
import org.example.app.view.UserView;

import java.util.Map;

public class UserController {

    UserView view;
    UserService service;

    public UserController(UserService service, UserView view) {
        this.service = service;
        this.view = view;
    }

    public void createUser() {
        // Отримуємо вхідні дані
        Map<String, String> data = view.getCreateData();
        // Передаємо дані на обробку та отримуємо результат
        String res = service.create(data);
        // Перевіряємо результат щодо обробки даних.
        // Якщо БД відсутня, виводимо повідомлення про це
        // і закриваємо програму.
        // Інакше виводимо результат та перезапускаємо програму.
        if (res.equals(Users.DB_ABSENT_MSG)) {
            // Виводимо результат
            view.getOutput(res);
            // Закриваємо програму
            System.exit(0);
        } else {
            // Виводимо результат
            view.getOutput(res);
            // Перезапускаємо програму
            AppStarter.startApp();
        }
    }

    public void readUser() {

        String res = service.read();

        if (res.equals(Users.DB_ABSENT_MSG)) {

            view.getOutput(res);

            System.exit(0);
        } else {

            view.getOutput("\nUsers:\n" + res);

            AppStarter.startApp();
        }
    }

    public void readUserById() {
        Map<String, String> data = view.getByIDData();
        String res = service.readById(data);
        if (res.equals(Users.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput("\nCONTACT BY ID:\n" + res);
            AppStarter.startApp();
        }
    }

    public void updateUser() {
        Map<String, String> data = view.getUpdateData();
        String res = service.update(data);
        if (res.equals(Users.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput(res);
            AppStarter.startApp();
        }
    }

    public void deleteUser() {
        Map<String, String> data = view.getDeleteData();
        String res = service.delete(data);
        if (res.equals(Users.DB_ABSENT_MSG)) {
            view.getOutput(res);
            System.exit(0);
        } else {
            view.getOutput(res);
            AppStarter.startApp();
        }
    }
}
