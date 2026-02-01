import controllers.ParcelController;
import interfaces.IParcelController;
import data.IDB;
import data.PostgresDB;
import repository.ClientRepository;
import repository.ParcelRepository;
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
        ParcelService service = new ParcelService(repo, clientRepo);
        IParcelController controller = new ParcelController(service);

        MyApplication app = new MyApplication(controller);
        app.start();

        db.close();
    }
}