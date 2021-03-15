package design1;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {
    public JFXTextField txt;
    public TreeTableView treetable;
    public TreeTableColumn col_id2;
    public TreeTableColumn col_name2;
    public TreeTableColumn col_age2;
    public TreeTableColumn col_test2;
    public TreeTableColumn col_res2;
    public TreeTableColumn f_key2;
    public TreeTableColumn col_norm2;
    @FXML
    public StackPane stack;
    @FXML
    public AnchorPane anchor;
    @FXML
    public Button button;
    public Button button2;


    Statement stmt;
    ObservableList<TreeTableColumn> t = FXCollections.observableArrayList();
    ResultSet rs;
    public String SQL = "select info.person_ID, name,age , paiten.test ,result, ID,normal from info,paiten,tests where tests.test=paiten.test and info.person_ID=paiten.person_ID";
    model m = new model();
    Connection c = m.connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void click(MouseEvent mouseEvent) {
    }

    public void next(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("smp3.fxml"));
        Scene scene=button.getScene();
        root.translateYProperty().set(scene.getHeight());
        stack.getChildren().add(root);
        Timeline timeline=new Timeline();
        KeyValue keyValue=new KeyValue(root.translateYProperty(),0, Interpolator.EASE_OUT);
        KeyFrame keyFrame=new KeyFrame(Duration.seconds(2),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event -> {
            stack.getChildren().remove(anchor);
        });
        timeline.play();


    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("smp1.fxml"));
        Scene scene=button2.getScene();

        root.translateYProperty().set(scene.getHeight());
       // stack.getChildren().add(root);
        StackPane  stackPane=(StackPane) scene.getRoot();
        stackPane.getChildren().add(root);
        Timeline timeline=new Timeline();
        KeyValue keyValue=new KeyValue(root.translateYProperty(),0, Interpolator.EASE_OUT);
        KeyFrame keyFrame=new KeyFrame(Duration.seconds(2),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event -> {
            stack.getChildren().remove(anchor);
        });
        timeline.play();
    }
}