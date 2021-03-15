package sample;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
public Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        model m=new model();
        System.out.println(m.isDbConnected());
      // m.query();
      // m.insert("zx",20,"sugar","160");
        JFXDecorator decorator = new JFXDecorator(primaryStage,root);
        decorator.setCustomMaximize(true);
         scene= new Scene(decorator);
        //Scene scene=new Scene(root);
        Image image=new Image(getClass().getResourceAsStream("/sample/flask.png"));
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("file:Style.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
