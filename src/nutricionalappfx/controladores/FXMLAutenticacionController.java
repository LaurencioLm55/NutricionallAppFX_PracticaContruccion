/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package nutricionalappfx.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nutricionalappfx.dominio.pojo.Autenticacion;
import nutricionalappfx.dominio.pojo.Colaborador;
import nutricionalappfx.nutricionalappFx;
import nutricionalappfx.dominio.pojo.AutenticacionDM;
import nutricionalappfx.modelo.dao.AutenticacionDAO;
import nutricionalappfx.utilidades.Utilidades;


public class FXMLAutenticacionController implements Initializable {

    @FXML
    private VBox vBoxImagen;
    @FXML
    private ImageView imagenFondo;
    @FXML
    private TextField tfUsuario;
    @FXML
    private Label txtErrorUsuario;
    @FXML
    private PasswordField tfContraseña;
    @FXML
    private Label txtErrorContraseña;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenFondo.fitWidthProperty().bind(vBoxImagen.widthProperty());
        imagenFondo.fitHeightProperty().bind(vBoxImagen.heightProperty());
    }

    private boolean sonCamposValidos() {
        boolean esValido = true;
        txtErrorUsuario.setVisible(false);
        txtErrorContraseña.setVisible(false);
        if (tfUsuario.getText().trim().isEmpty()) {
            txtErrorUsuario.setText("El número de personal es obligatorio");
            txtErrorUsuario.setVisible(true);
            esValido = false;
        }
        if (tfContraseña.getText().isEmpty()) {
            txtErrorContraseña.setText("La contraseña es obligatoria");
            txtErrorContraseña.setVisible(true);
            esValido = false;
        }
        return esValido;
    }

    private void validarCredenciales(String noPersonal, String password) {
        AutenticacionDM autenticacionDM = new AutenticacionDM(new AutenticacionDAO());
        try {
            Autenticacion respuesta = autenticacionDM.verificarCredencialesColaborador(noPersonal, password);
            if (respuesta.isCredencialesCorrectas() && respuesta.isActivo()) {
                Utilidades.mostrarAlertaSimple("Credenciales correctas",
                        respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                irPantallaPrincipal(respuesta.getColaborador());
            } else {
                Utilidades.mostrarAlertaSimple("Lo sentimos",
                        respuesta.getMensaje(), Alert.AlertType.WARNING);
            }
        } catch (RuntimeException e) {
            Utilidades.mostrarAlertaSimple("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void irPantallaPrincipal(Colaborador colaborador) {
        try {
            Stage escenario = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    nutricionalappFx.class.getResource("vista/FXMLPrincipal.fxml"));
            Parent vista = loader.load();
            FXMLPrincipalController controlador = loader.getController();
            controlador.inicializarInformacion(colaborador);
            escenario.setScene(new Scene(vista));
            escenario.setTitle("Control Nutricional");
            escenario.show();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error",
                    "No se pudo cargar la pantalla principal.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnIniciarSesion(MouseEvent event) {
        if (sonCamposValidos()) {
            validarCredenciales(tfUsuario.getText().trim(), tfContraseña.getText());
        }
    }

}
