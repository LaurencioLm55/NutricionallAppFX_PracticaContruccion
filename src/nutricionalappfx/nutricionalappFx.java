
package nutricionalappfx;

import java.io.IOException;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class nutricionalappFx extends Application {
    
    @Override
   public void start(Stage stage){
        
        try {         
            Parent vista = FXMLLoader.load(getClass().getResource("vista/FXMLAutenticacion.fxml"));
            Scene scene = new Scene(vista, 700, 500);
            stage.setScene(scene);
            stage.setTitle("NutriPro - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
