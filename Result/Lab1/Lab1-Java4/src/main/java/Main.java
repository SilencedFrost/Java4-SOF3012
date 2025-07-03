import Managers.UserManager;
import config.ConfigLoader;

public class Main {
    public static void main(String[] args) {

        ConfigLoader.loadDatabaseConfig();

        UserManager userMng = new UserManager();
        userMng.findall();
    }
}
