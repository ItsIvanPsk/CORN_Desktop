import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ControllerUsersView implements Initializable {

    @FXML
    private ListView usersList;

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label balance;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        JSONObject obj = new JSONObject("{}");
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + ":" + Main.port + "/get_profiles", obj.toString(),
                (response) -> {
                    loadUserInfoCallback(response);
                });
    }

    private void loadUserInfoCallback(String response) {
        System.out.println(response);
        JSONObject objResponse = new JSONObject(response);
        JSONObject objResult = objResponse.getJSONObject("result");
        JSONArray JSONlist = objResult.getJSONArray("profiles");
        for (int i = 0; i < JSONlist.length(); i++) {
            JSONObject user = JSONlist.getJSONObject(i);
            System.out.println("Email: " + user.getString("email"));
            System.out.println("Name: " + user.getString("name"));
            System.out.println("Surname: " + user.getString("surname"));
            System.out.println("Phone: " + user.getString("phone"));
            System.out.println("Password: " + user.getString("password"));
            System.out.println("Balance: " + user.getFloat("balance"));
        }

    }

}
