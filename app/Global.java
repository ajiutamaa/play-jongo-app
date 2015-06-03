import com.mongodb.MongoClient;
import org.jongo.Jongo;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import utils.DB;

/**
 * Created by pxajie on 5/30/2015.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application application) {
        super.beforeStart(application);

        Logger.debug("** start app **");
        try {
            MongoClient mc = new MongoClient("127.0.0.1", 27017);
            DB.jongo = new Jongo(mc.getDB("jongo-db"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Logger.debug("** Jongo database connection created");
    }
}
