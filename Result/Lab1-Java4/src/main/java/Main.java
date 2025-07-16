import entity.User;
import service.UserService;
import config.ConfigLoader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConfigLoader.loadDatabaseConfig();
        UserService userService = new UserService();
        List<User> users = userService.getAll();
        for(User user : users) {
            user.printInfo();
        }
    }
}
