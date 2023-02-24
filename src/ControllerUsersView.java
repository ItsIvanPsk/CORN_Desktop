import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class ControllerUsersView implements Initializable {

    @FXML
    private VBox usersVBox;

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
        URL resource = this.getClass().getResource("./assets/viewUserItem.fxml");

        for (int i = 0; i < JSONlist.length(); i++) {
            JSONObject user = JSONlist.getJSONObject(i);
            System.out.println("Email: " + user.getString("email"));
            System.out.println("Name: " + user.getString("name"));
            System.out.println("Surname: " + user.getString("surname"));
            System.out.println("Phone: " + user.getString("phone"));
            System.out.println("Password: " + user.getString("password"));
            System.out.println("Balance: " + user.getFloat("balance"));

            try {
                FXMLLoader loader = new FXMLLoader(resource);
                Parent itemTemplate = loader.load();
                ControllerUserItem itemController = loader.getController();

                itemController.setName(user.getString("name"));
                itemController.setSurname(user.getString("surname"));
                itemController.setPhone(user.getString("phone"));
                itemController.setEmail(user.getString("email"));
                itemController.setBalance(String.valueOf(user.getFloat("balance")));

                usersVBox.getChildren().add(itemTemplate);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void loadUserDetails(String name, String surname, String phone, String email, String balance) {
        this.name.setText(name);
        this.surname.setText(surname);
        this.phone.setText(phone);
        this.email.setText(email);
        this.balance.setText(balance);
    }
}
