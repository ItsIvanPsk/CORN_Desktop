import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static UtilsWS socketClient;
    public static String protocolWS = "ws";

    public static String protocol = "http";
    public static String host = "localhost";
    public static String port = "3000";

    /*
     * public static String protocol = "https";
     * public static String host = "cornapi-production.up.railway.app";
     * public static String port = "7806";
     */

    public static String title = "CORN Desktop";

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        UtilsViews.addView(getClass(), "users_view", "./assets/users_view.fxml");

        Scene scene = new Scene(UtilsViews.parentContainer);

        stage.setScene(scene);
        stage.onCloseRequestProperty(); // Call close method when closing window
        stage.setTitle(title);
        stage.show();

    }

    @Override
    public void stop() throws Exception {
        socketClient.close();
        System.exit(1);
    }

}
