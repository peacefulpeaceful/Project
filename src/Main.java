import controllers.ParcelController;
import controllers.interfaces.IParcelController;
import data.IDB;
import data.PostgresDB;
import repository.ParcelRepository;
import service.ParcelService;


public class Main {
    public static void main(String[] args) {

        IDB db = new PostgresDB(
                "jdbc:postgresql://localhost:5432/DefinitelyNOTsomedb",
                "postgres",
                "0000"
        );

        ParcelRepository repo = new ParcelRepository(db);
        ParcelService service = new ParcelService(repo);
        IParcelController controller = new ParcelController(service);

        MyApplication app = new MyApplication(controller);
        app.start();

        db.close();
    }
}