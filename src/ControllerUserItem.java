import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerUserItem {

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label phone;

    private String email;

    private String balance;

    private int user_id;


    @FXML
    private void loadUserDetails() {
        ControllerUsersView ctrlUserView = (ControllerUsersView) UtilsViews.getController("users_view");
        ctrlUserView.loadUserDetails(name.getText(), surname.getText(), phone.getText(), email, balance, user_id);
    }

    public String getName() {
        return name.getText();
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public String getSurname() {
        return surname.getText();
    }

    public void setSurname(String surname) {
        this.surname.setText(surname);
    }

    public String getPhone() {
        return phone.getText();
    }

    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return balance;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

}
