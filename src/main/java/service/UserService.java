package service;

import database.DBCheck;
import entity.User;
import entity.UserMap;
import exceptions.UserException;
import exceptions.DBException;
import repository.UserRepository;
import utils.Constants;
import utils.UserValidator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UserService {

    private UserRepository repository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    static {
        try {
            setupLogger();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setupLogger() throws IOException {
        try {
            FileHandler fileHandler = new FileHandler("MY_LOGS/mylogs.txt", true);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.WARNING);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public String create(Map<String, String> data) {
        if (DBCheck.isDBExists()) {
            logger.log(Level.SEVERE, Constants.DB_ABSENT_MSG);
            return Constants.DB_ABSENT_MSG;
        }
        Map<String, String> errors = new UserValidator().validateUserData(data);
        if (!errors.isEmpty()) {
            logger.log(Level.WARNING, Constants.LOG_DATA_INPTS_WRONG_MSG);
            return new UserException("Check inputs", errors).getErrors(errors);
        }
        String result = repository.create(new UserMap().mapData(data));
        logger.info("User creation result: " + result);
        return result;
    }

    public String read() {
        if (DBCheck.isDBExists()) {
            logger.log(Level.SEVERE, Constants.DB_ABSENT_MSG);
            return Constants.DB_ABSENT_MSG;
        }
        Optional<List<User>> optional = repository.read();
        if (optional.isPresent()) {
            List<User> list = optional.get();
            if (!list.isEmpty()) {
                AtomicInteger count = new AtomicInteger(0);
                StringBuilder sb = new StringBuilder();
                list.forEach((user) ->
                        sb.append(count.incrementAndGet())
                                .append(") ")
                                .append(user.toString())
                );
                logger.info("User data read: " + sb.toString());
                return sb.toString();
            } else {
                logger.warning(Constants.LOG_DATA_ABSENT_MSG);
                return Constants.DATA_ABSENT_MSG;
            }
        } else {
            logger.warning(Constants.LOG_DATA_ABSENT_MSG);
            return Constants.DATA_ABSENT_MSG;
        }
    }

    public String readById(Map<String, String> data) {
        if (DBCheck.isDBExists()) {
            logger.log(Level.SEVERE, Constants.DB_ABSENT_MSG);
            return Constants.DB_ABSENT_MSG;
        }
        Map<String, String> errors = new UserValidator().validateUserData(data);
        if (!errors.isEmpty()) {
            logger.log(Level.WARNING, Constants.LOG_DATA_INPTS_WRONG_MSG);
            return new UserException("Check inputs", errors).getErrors(errors);
        }
        Optional<User> optional = repository.readById(Long.parseLong(data.get("id")));
        if (optional.isPresent()) {
            User user = optional.get();
            logger.info("User data read by id: " + user.toString());
            return user.toString();
        } else {
            logger.warning(Constants.LOG_DATA_ABSENT_MSG);
            return Constants.DATA_ABSENT_MSG;
        }
    }

    public String update(Map<String, String> data) {
        if (DBCheck.isDBExists()) {
            logger.log(Level.SEVERE, Constants.DB_ABSENT_MSG);
            return Constants.DB_ABSENT_MSG;
        }
        Map<String, String> errors = new UserValidator().validateUserData(data);
        if (!errors.isEmpty()) {
            logger.log(Level.WARNING, Constants.LOG_DATA_INPTS_WRONG_MSG);
            return new UserException("Check inputs", errors).getErrors(errors);
        }
        User user = new UserMap().mapData(data);
        if (repository.readById(user.getId()).isEmpty()) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            String result = repository.update(user);
            logger.info("User update result: " + result);
            return result;
        }
    }

    public String delete(Map<String, String> data) {
        if (DBCheck.isDBExists()) {
            logger.log(Level.SEVERE, Constants.DB_ABSENT_MSG);
            return Constants.DB_ABSENT_MSG;
        }
        Map<String, String> errors = new UserValidator().validateUserData(data);
        if (!errors.isEmpty()) {
            logger.log(Level.WARNING, Constants.LOG_DATA_INPTS_WRONG_MSG);
            return new UserException("Check inputs", errors).getErrors(errors);
        }
        Long id = new UserMap().mapData(data).getId();
        if (!repository.isIdExists(id)) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            String result = repository.delete(id);
            logger.info("User deletion result: " + result);
            return result;
        }
    }
}
