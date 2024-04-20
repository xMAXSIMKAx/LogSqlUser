package org.example.app.database;

import org.example.app.utils.Users;

import java.io.File;

public class DBCheck {

    public static boolean isDBExists() {
        return !new File(Users.DB_BASE_URL +
                Users.DB_NAME).exists();
    }
}
