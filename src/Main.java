import controllers.BlacklistController;
import controllers.ParcelController;
import interfaces.IParcelController;
import data.IDB;
import data.PostgresDB;
import repository.BlacklistRepository;
import repository.ClientRepository;
import repository.ParcelRepository;
import repository.UserRepository;
import service.AuthService;
import service.BlacklistService;
import service.IBlacklistService;
import service.ParcelService;



public class Main {
    public static void main(String[] args) {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PWD");

        IDB db = new PostgresDB(
                url,
                user,
                password
        );

        ParcelRepository repo = new ParcelRepository(db);
        ClientRepository clientRepo = new ClientRepository(db);
        BlacklistRepository blacklistRepository = new BlacklistRepository(db);
        IBlacklistService blacklistService = new BlacklistService(blacklistRepository);
        ParcelService service = new ParcelService(repo, clientRepo, blacklistService);
        IParcelController controller = new ParcelController(service);
        BlacklistController blacklistController = new BlacklistController(blacklistService, clientRepo);

        UserRepository userRepository = new UserRepository(db);
        AuthService authService = new AuthService(userRepository);
        MyApplication app = new MyApplication(controller, blacklistController, authService);
        app.start();

        db.close();
    }
}

