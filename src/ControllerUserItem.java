import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class ControllerUserItem {

    @FXML
    private Label name;

    @FXML 
    private Circle accountStatus;
    
    @FXML
    private Label phone;

    private String email;

    private String balance;

    private int user_id;


    @FXML
    private void loadUserDetails() {
        ControllerUsersView ctrlUserView = (ControllerUsersView) UtilsViews.getController("users_view");
        ctrlUserView.loadUserDetails(name.getText(), phone.getText(), email, balance, user_id);
    }

    public String getName() {
        return name.getText();
    }

    public void setName(String name, String surname) {
        this.name.setText(name + " " + surname);
    }

    public void setStatusColor(String color) {
        if (color.equals("NO_VERFICAT")) { 
            this.accountStatus.setStroke(Paint.valueOf("#2A52BE"));
            this.accountStatus.setFill(Paint.valueOf("#2A52BE"));
        }
        if (color.equals("PER_VERIFICAR")) {
            this.accountStatus.setStroke(Paint.valueOf("#FF6400")); 
            this.accountStatus.setFill(Paint.valueOf("#FF6400")); 
        }
        if (color.equals("ACCEPTAT")) {
            this.accountStatus.setStroke(Paint.valueOf("#008000")); 
            this.accountStatus.setFill(Paint.valueOf("#008000")); 
        }
        if (color.equals("REFUSAT")) {
            this.accountStatus.setStroke(Paint.valueOf("#ff0000")); 
            this.accountStatus.setFill(Paint.valueOf("#ff0000")); 
        }
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
