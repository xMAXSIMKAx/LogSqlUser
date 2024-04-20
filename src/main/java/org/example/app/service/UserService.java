package org.example.app.service;


import org.example.app.database.DBCheck;
import org.example.app.entity.User;
import org.example.app.entity.UserMapper;
import org.example.app.exceptions.UserException;
import org.example.app.exceptions.DBException;
import org.example.app.repository.impl.UserRepository;
import org.example.app.utils.Users;
import org.example.app.utils.UserValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    UserRepository repository;
    private static final Logger LOGGER =
            Logger.getLogger(UserService.class.getName());

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public String create(Map<String, String> data) {
        if (DBCheck.isDBExists()) {
            try {
                throw new DBException(Users.DB_ABSENT_MSG);
            } catch (DBException e) {
                LOGGER.log(Level.SEVERE, Users.LOG_DB_ABSENT_MSG);
                return e.getMessage();
            }
        }
        Map<String, String> errors =
                new UserValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new UserException("Check inputs", errors);
            } catch (UserException e) {
                LOGGER.log(Level.WARNING, Users.LOG_DATA_INPTS_WRONG_MSG);
                return e.getErrors(errors);
            }
        }
        return repository.create(new UserMapper().mapContactData(data));
    }

    public String read() {
        if (DBCheck.isDBExists()) {
            try {
                throw new DBException(Users.DB_ABSENT_MSG);
            } catch (DBException e) {
                LOGGER.log(Level.SEVERE, Users.LOG_DB_ABSENT_MSG);
                return e.getMessage();
            }
        }
        Optional<List<User>> optional = repository.read();
        if (optional.isPresent()) {
            List<User> list = optional.get();
            if (!list.isEmpty()) {
                AtomicInteger count = new AtomicInteger(0);
                StringBuilder sb = new StringBuilder();
                list.forEach((contact) ->
                        sb.append(count.incrementAndGet())
                                .append(") ")
                                .append(contact.toString())
                );
                return sb.toString();
            } else {
                LOGGER.log(Level.WARNING, Users.LOG_DATA_ABSENT_MSG);
                return Users.DATA_ABSENT_MSG;
            }
        } else {
            LOGGER.log(Level.WARNING, Users.LOG_DATA_ABSENT_MSG);
            return Users.DATA_ABSENT_MSG;
        }
    }

    public String readById(Map<String, String> data) {
        if (DBCheck.isDBExists()) {
            try {
                throw new DBException(Users.DB_ABSENT_MSG);
            } catch (DBException e) {
                LOGGER.log(Level.SEVERE, Users.LOG_DB_ABSENT_MSG);
                return e.getMessage();
            }
        }
        Map<String, String> errors =
                new UserValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new UserException("Check inputs", errors);
            } catch (UserException e) {
                LOGGER.log(Level.WARNING, Users.LOG_DATA_INPTS_WRONG_MSG);
                return e.getErrors(errors);
            }
        }
        Optional<User> optional =
                repository.readById(Long.parseLong(data.get("id")));
        if (optional.isPresent()) {
            User contact = optional.get();
            return contact.toString();
        } else {
            LOGGER.log(Level.WARNING, Users.LOG_DATA_ABSENT_MSG);
            return Users.DATA_ABSENT_MSG;
        }
    }

    public String update(Map<String, String> data) {
        if (DBCheck.isDBExists()) {
            try {
                throw new DBException(Users.DB_ABSENT_MSG);
            } catch (DBException e) {
                LOGGER.log(Level.SEVERE, Users.LOG_DB_ABSENT_MSG);
                return e.getMessage();
            }
        }
        Map<String, String> errors =
                new UserValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new UserException("Check inputs", errors);
            } catch (UserException e) {
                LOGGER.log(Level.WARNING, Users.LOG_DATA_INPTS_WRONG_MSG);
                return e.getErrors(errors);
            }
        }
        User contact = new UserMapper().mapContactData(data);
        if (repository.readById(contact.getId()).isEmpty()) {
            return Users.DATA_ABSENT_MSG;
        } else {
            return repository.update(contact);
        }
    }

    public String delete(Map<String, String> data) {

        if (DBCheck.isDBExists()) {
            try {
                throw new DBException(Users.DB_ABSENT_MSG);
            } catch (DBException e) {
                LOGGER.log(Level.SEVERE, Users.LOG_DB_ABSENT_MSG);
                return e.getMessage();
            }
        }
        Map<String, String> errors =
                new UserValidator().validateContactData(data);
        if (!errors.isEmpty()) {
            try {
                throw new UserException("Check inputs", errors);
            } catch (UserException e) {
                LOGGER.log(Level.WARNING, Users.LOG_DATA_INPTS_WRONG_MSG);
                return e.getErrors(errors);
            }
        }

        Long id = new UserMapper().mapContactData(data).getId();
        if (!repository.isIdExists(id)) {
            return Users.DATA_ABSENT_MSG;
        } else {
            return repository.delete(id);
        }
    }
}
