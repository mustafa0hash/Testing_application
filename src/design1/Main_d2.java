package design1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.model;

public class Main_d2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("smp1.fxml"));
        primaryStage.setTitle("Hello World");
        design1.model m= new design1.model();
        System.out.println(m.isDbConnected());
     //  m.query();
      // m.insert("zx",20,"sugar","160");
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
      //  primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
