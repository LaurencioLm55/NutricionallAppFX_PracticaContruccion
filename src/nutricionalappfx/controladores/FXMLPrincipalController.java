/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package nutricionalappfx.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import nutricionalappfx.dominio.pojo.Colaborador;

/**
 * FXML Controller class
 *
 * @author lopez
 */
public class FXMLPrincipalController implements Initializable {

    private Colaborador colaborador;

    @FXML
    private Label lbNombre;
    @FXML
    private Label lbRol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarInformacion(Colaborador colaborador) {
        this.colaborador = colaborador;
        mostrarInformacionPantalla();
    }

    private void mostrarInformacionPantalla() {
        if (colaborador != null) {
            String nombreCompleto = colaborador.getNombre() + " "
                    + colaborador.getApellidoPaterno() + " "
                    + (colaborador.getApellidoMaterno() != null ? colaborador.getApellidoMaterno() : "");
            lbNombre.setText("Bienvenido(a) " + nombreCompleto.trim());
            lbRol.setText("Rol: " + colaborador.getRol());
        }
    }

}
