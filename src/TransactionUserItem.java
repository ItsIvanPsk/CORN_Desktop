import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionUserItem {

    @FXML
    private Label name;
    
    @FXML
    private Label id;

    @FXML
    private Label amount;
    
    @FXML
    private Label date;

    public TransactionUserItem() {  }

    public TransactionUserItem(Label name, Label id, Label amount, Label date) {
        this.name = name;
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public Label getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public Label getId() {
        return id;
    }

    public void setId(String id) {
        this.id.setText(id);
    }

    public Label getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.setText(amount);
    }

    public Label getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

}
