package service;

import controller.UserController;
import exceptions.OptionException;
import repository.UserRepository;
import utils.AppStarter;
import utils.Constants;
import view.*;

public class AppService {

    UserRepository repository = new UserRepository();
    UserService service = new UserService(repository);
    UserController controller = new UserController(service);

    public void handleOption(int option) {
        switch (option) {
            case 1 -> controller.createUser();
            case 2 -> controller.readUsers();
            case 3 -> controller.updateUser();
            case 4 -> controller.deleteUser();
            case 5 -> controller.readUserById();
            case 0 -> new AppView().getOutput(Integer.toString(option));
            default -> {
                try {
                    throw new OptionException(Constants.INCORRECT_OPTION_MSG);
                } catch (OptionException e) {
                    new AppView().getOutput(e.getMessage());
                    AppStarter.startApp();
                }
            }
        }
    }
}
