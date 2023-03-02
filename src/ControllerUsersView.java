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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ControllerUsersView implements Initializable {

    @FXML
    private VBox usersVBox;

    @FXML
    private VBox transactionVBox;

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

    @FXML
    private AnchorPane userDetail;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        JSONObject obj = new JSONObject("{}");
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/get_profiles", obj.toString(),
                (response) -> {
                    loadUserInfoCallback(response);
                });
    }

    private void loadUserInfoCallback(String response) {
        JSONObject objResponse = new JSONObject(response);
        System.out.println(objResponse.toString());
        if (objResponse.getString("status") != "KO") {
            JSONObject objResult = objResponse.getJSONObject("result");
            JSONArray JSONlist = objResult.getJSONArray("profiles");
            URL resource = this.getClass().getResource("./assets/viewUserItem.fxml");
    
            for (int i = 0; i < JSONlist.length(); i++) {
                JSONObject user = JSONlist.getJSONObject(i);
    
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
    }

    public void loadUserDetails(String name, String surname, String phone, String email, String balance) {
        this.name.setText(name);
        this.surname.setText(surname);
        this.phone.setText(phone);
        this.email.setText(email);
        this.balance.setText(balance);

        JSONObject obj = new JSONObject("{}");
        obj.put("phone", phone.toString());
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/get_transactions", obj.toString(),
                (response) -> {
                    loadTransactionsCallBack(response, phone);
                });

        this.userDetail.setVisible(true);
    }

    private void loadTransactionsCallBack(String response, String phone) {
        JSONObject objResponse = new JSONObject(response);
        JSONObject objResult = objResponse.getJSONObject("result");
        JSONArray JSONlist = objResult.getJSONArray("transactions");
        URL resource = this.getClass().getResource("./assets/viewTransactionItem.fxml");

        transactionVBox.getChildren().clear();

        for (int i = 0; i < JSONlist.length(); i++) {
            JSONObject transaction = JSONlist.getJSONObject(i);
            try {
                FXMLLoader loader = new FXMLLoader(resource);
                Parent itemTemplate = loader.load();
                TransactionUserItem itemController = loader.getController();

                if (transaction.getString("destination").equals(phone)) {
                    itemController.setName(transaction.getString("destinationName"));
                    itemController.setAmount(" +" + transaction.getString("amount"));
                    itemController.getAmount().setTextFill(Color.GREEN);
                    itemController.setId(transaction.getString("destination"));
                    if (!transaction.get("timeAccepted").equals(null)) {
                        itemController.setDate(transaction.get("timeAccepted").toString());
                    } else { itemController.setDate(""); }

                } 
                if (transaction.getString("origin").equals(phone)) {
                    itemController.setName(transaction.getString("destinationName"));
                    itemController.setAmount(" -" + transaction.getString("amount"));
                    itemController.getAmount().setTextFill(Color.RED);
                    itemController.setId(transaction.getString("destination"));
                    if (!transaction.get("timeAccepted").equals(null)) {
                        itemController.setDate(transaction.get("timeAccepted").toString());
                    } else { itemController.setDate(""); }
                }

                transactionVBox.getChildren().add(itemTemplate);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
