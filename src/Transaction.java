public class Transaction {
    
    private String originName, destinationName, token, timeSetup, timeAccepted;
    private int origin, destination, amount, acepted;
    
    public Transaction() {  }

    public Transaction(String originName, String destinationName, String token, String timeSetup, String timeAccepted,
            int origin, int destination, int amount, int acepted) {
        this.originName = originName;
        this.destinationName = destinationName;
        this.token = token;
        this.timeSetup = timeSetup;
        this.timeAccepted = timeAccepted;
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
        this.acepted = acepted;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTimeSetup() {
        return timeSetup;
    }

    public void setTimeSetup(String timeSetup) {
        this.timeSetup = timeSetup;
    }

    public String getTimeAccepted() {
        return timeAccepted;
    }

    public void setTimeAccepted(String timeAccepted) {
        this.timeAccepted = timeAccepted;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAcepted() {
        return acepted;
    }

    public void setAcepted(int acepted) {
        this.acepted = acepted;
    }

    

}
