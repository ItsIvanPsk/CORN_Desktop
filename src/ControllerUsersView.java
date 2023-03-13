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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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

    @FXML
    private Button filter;

    @FXML
    private Button filterClean;

    @FXML
    private TextField filterBalanceStart;

    @FXML
    private TextField filterBalanceEnd;

    @FXML
    private TextField filterName;

    @FXML
    private Slider filterTransaction;

    @FXML
    private ComboBox<String> accountStatus;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        accountStatus.getItems().addAll("NO_VERIFICAT", "PER_VERIFICAR", "ACCEPTAT", "REFUSAT");
        accountStatus.getSelectionModel().select("NO_VERIFICAT");
        JSONObject obj = new JSONObject("{}");
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/get_profiles", obj.toString(),
                (response) -> {
                    loadUserInfoCallback(response);
                });
    }

    private void loadUserInfoCallback(String response) {
        JSONObject objResponse = new JSONObject(response);
        System.out.println(objResponse.toString());
        usersVBox.getChildren().clear();
        if (objResponse.get("status").toString() != "KO") {
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
                    itemController.setUserId(user.getInt("user_id"));
    
                    usersVBox.getChildren().add(itemTemplate);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public void loadUserDetails(String name, String surname, String phone, String email, String balance, int user_id) {
        this.name.setText(name);
        this.surname.setText(surname);
        this.phone.setText(phone);
        this.email.setText(email);
        this.balance.setText(balance);

        JSONObject obj = new JSONObject("{}");
        obj.put("user_id", user_id);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/get_transactions_phone", obj.toString(),
                (response) -> {
                    System.out.println(response);
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
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onFilterPressed() {
        int balanceStart = 0;
        int balanceEnd = 0;
        if (filterBalanceStart.getText() != "") {
            balanceStart = Integer.parseInt(filterBalanceStart.getText());
        } else if (filterBalanceEnd.getText() != "") {
            balanceEnd = Integer.parseInt(filterBalanceEnd.getText());
        }
        System.out.println("Pressed filter");
        if (!isNumeric(filterBalanceStart.getText())) {
            showAlert(AlertType.ERROR, "Balance Error", null, "El saldo d'inici no es un número");
        } else if (!isNumeric(filterBalanceEnd.getText())) {
            showAlert(AlertType.ERROR, "Balance Error", null, "El saldo fin no es un número");
        } else if (!filterBalanceStart.getText().isEmpty() && filterBalanceEnd.getText().isEmpty() || filterBalanceStart.getText().isEmpty() && !filterBalanceEnd.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Balance Error", null, "No pots filtrar sense ficar un minim sense un maxim i viceversa");
        } else if (balanceStart < balanceEnd) {
            showAlert(AlertType.ERROR, "Balance Error", null, "El saldo min no pot ser mes petit que el saldo maxim");
        } else {
            JSONObject json = new JSONObject();
            if (filterName == null || filterName.getText().equals("")) {
                json.put("account_name", "null");
            } else { 
                json.put("account_name", filterName.getText());
            }
            if (filterTransaction == null || filterName.getText().equals("")) {
                json.put("transaction_count", "null");
            } else { 
                json.put("transaction_count", (int) filterTransaction.getValue());
            }
            if (filterBalanceStart == null || filterBalanceStart.getText().equals("")) {
                json.put("account_balance_min", "null");
            } else { 
                json.put("account_balance_min", (Integer.parseInt(filterBalanceStart.getText())));
            }
            if (filterBalanceEnd == null || filterBalanceEnd.getText().equals("")) {
                json.put("account_balance_max", "null");
            } else { 
                json.put("account_balance_max", Integer.parseInt(filterBalanceEnd.getText()));
            }
            if (accountStatus == null || accountStatus.getValue().equals("")) {
                json.put("account_status", "null");
            } else { 
                json.put("account_status", accountStatus.getValue().toString());
            }

            System.out.println("JSON -> " + json);

            UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/user_filter", json.toString(),
                (response) -> {
                    System.out.println(response);
                    loadUserInfoCallback(response);
                });

        }
    }

    @FXML
    public void onClearFilterPressed() {
        System.out.println("Clean filter pressed");
        filterName.setText("");
        filterTransaction.setValue(filterTransaction.getMin());
        filterBalanceStart.setText("");
        filterBalanceEnd.setText("");
        showAlert(AlertType.INFORMATION, "Filtre","", "Se han resetejat els filtres.");

        JSONObject obj = new JSONObject("{}");
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + "/get_profiles", obj.toString(),
                (response) -> {
                    loadUserInfoCallback(response);
                });
        this.userDetail.setVisible(false);

    }

    @FXML
    public void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static boolean isNumeric(String cadena) {
        if (cadena.isEmpty()) {
            return true;
        } else {
            try {
                Integer.parseInt(cadena);
                return true;
            } catch (NumberFormatException excepcion) {
                return false;
            }
        }
    }

}
