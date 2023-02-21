import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerDades {

    @FXML
    private Label nombreConsola;

    @FXML
    private Label fechaDeSalida;

    @FXML
    private Label CPU;

    @FXML
    private Label velocidad;

    @FXML
    private Label marca;

    @FXML
    private Label color;

    @FXML
    private Label ventas;

    @FXML
    private ImageView imagen;

    @FXML
    private ProgressIndicator progressIndicator;

    private void showLoading() {
        progressIndicator.setVisible(true);
    }

    private void hideLoading() {
        progressIndicator.setVisible(false);
    }

    @FXML
    private void backButton(){
        UtilsViews.setViewAnimating("ViewConsoles");
    }

    public void loadDetails(String consola) {
        JSONObject obj = new JSONObject("{}");
        obj.put("type", "detalls");
        obj.put("name", consola);
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + ":" + Main.port + "/dades", obj.toString(),
                (response) -> {
                    loadDetailsCallback(response);
                });
    }

    public void loadDetailsCallback(String response) {
        showLoading();
        JSONObject objResponse = new JSONObject(response);
        if (objResponse.getString("status").equals("OK")) {
            JSONArray array = objResponse.getJSONArray("result");
            JSONObject consola = array.getJSONObject(0);
            setNombreConsola((String) consola.get("name"));
            setCPU((String) consola.get("processor"));
            // setColor((String) consola.get("color"));
            setFechaDeSalida((String) consola.get("date"));
            setMarca((String) consola.get("brand"));
            setVelocidad((BigDecimal) consola.get("speed"));
            setVentas((Integer) consola.get("sold"));
            try {
                Image image = new Image(
                        Main.protocol + "://" + Main.host + ":" + Main.port + "/" + consola.getString("image"));
                setImagen(image);
            } catch (Exception e) {

            }
        }
        hideLoading();
    }

    public Label getNombreConsola() {
        return nombreConsola;
    }

    public void setNombreConsola(String nombreConsola) {
        this.nombreConsola.setText(nombreConsola);
    }

    public Label getFechaDeSalida() {
        return fechaDeSalida;
    }

    public void setFechaDeSalida(String fechaDeSalida) {
        this.fechaDeSalida.setText(fechaDeSalida);
    }

    public Label getCPU() {
        return CPU;
    }

    public void setCPU(String cPU) {
        CPU.setText(cPU);
    }

    public Label getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(BigDecimal velocidad) {
        this.velocidad.setText(String.valueOf(velocidad));
    }

    public Label getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca.setText(marca);
    }

    public Label getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color.setText(color);
    }

    public Label getVentas() {
        return ventas;
    }

    public void setVentas(Integer ventas) {
        this.ventas.setText(String.valueOf(ventas));
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen.setImage(imagen);
        this.imagen.setFitWidth(200);
        this.imagen.setPreserveRatio(true);
    }

}
