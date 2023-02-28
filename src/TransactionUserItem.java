import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionUserItem {

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label phone;

    private String email;

    private String balance;

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

}
