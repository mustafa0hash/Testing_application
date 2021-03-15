package sample;


import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.utils.JFXUtilities;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller_2 implements Initializable {

    @FXML
    public AnchorPane anchor;
    public Button button2;
    public Button insert;
    public JFXTreeTableView<User> tree;
    public JFXTreeTableView<User> tree2;
    public JFXTextField stxt;
    public StackPane stack;
    public Label txt;
    ObservableList<String> list = FXCollections.observableArrayList("Lipid Profile Test", "Seminal Fluid Analysis", "General Stool Examination","Virology Tests","24 hr.Urine Collection","General Urine Examination");
    model model = new model();
    ObservableList<String> id;
    ObservableList<TableColumn> t = FXCollections.observableArrayList();
    ArrayList<String> x = new ArrayList<>();
    Connection c = model.connection;
    ResultSet rs;
    String pos,newv,dvalue;
    Thread t1 ;
    boolean p=true;
    JFXTreeTableColumn<User, String> name;

    ObservableList<Person> data2;
    @FXML
    private ComboBox<String> combo = new ComboBox<>(list);
    JFXTreeTableColumn<User, String> result;

    public void back(ActionEvent actionEvent) throws IOException, SQLException {
        Controller controller=new Controller();
        Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        Scene scene = button2.getScene();
        Main main=new Main();
       // Scene scene = main.scene;

//        root.translateYProperty().set(scene.getHeight());

        Parent stackPane = scene.getRoot();
        JFXDecorator decorator= (JFXDecorator) scene.getRoot();
        decorator.setContent(root);
      //  StackPane stackPane=controller.parent;
       // stackPane.getParent().add(root);
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event -> {
           // stackPane.getParent().remove(anchor);
        });
        timeline.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      combo.setItems(list);

Refresh();

//////////////////// search textfield
           /* stxt.textProperty().addListener((o, oldVal, newVal) -> {
                tree.setPredicate(user -> user.getValue().age.get().contains(newVal)
                        || user.getValue().name.get().contains(newVal)
                        || user.getValue().test.get().contains(newVal));
            });*/


            /*result.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                @Override
                public void handle(TableColumn.CellEditEvent event) {
                    TreeItem<User> editing= tree.getTreeItem(event.getTablePosition().getRow());
                    System.out.println(event.getNewValue().toString());
                }
            });*/
//        size.textProperty().bind(Bindings.createStringBinding(()->tree.getCurrentItemsCount()+"",
//                tree.currentItemsCountProperty()));

        Platform.runLater(() -> {

        tree.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.isSecondaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println("foucs " + tree.isFocused());

                    if(tree.getSelectionModel().getSelectedItem().isLeaf()){
                        System.out.println(tree.getSelectionModel().getSelectedItem().getValue().normal.getValue());
                    pos=tree.getSelectionModel().getSelectedItem().getValue().normal.getValue();
                    dvalue=tree.getSelectionModel().getSelectedItem().getValue().ID.getValue();

                    }

                }
            }
        });
        result.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<User, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<User, String> event) {

                //TreeItem<User> editing= tree.getTreeItem(event.);
                System.out.println(event.getNewValue().toString());
                newv=event.getNewValue().toString();
            }
        });

        });
    }

    public void Refresh(){

        Platform.runLater(() -> {
        //new Thread(() -> {
        JFXTreeTableColumn<User, String> ID = new JFXTreeTableColumn<>("ID");
        ID.setPrefWidth(50);
        ID.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (ID.validateValue(param)) return param.getValue().getValue().ID;
            else return ID.getComputedValue(param);
        });

        name = new JFXTreeTableColumn<>("Name");
        name.setPrefWidth(90);
        name.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (name.validateValue(param)) return param.getValue().getValue().name;
            else return name.getComputedValue(param);
        });

        JFXTreeTableColumn<User, String> ageColumn = new JFXTreeTableColumn<>("Age");
        ageColumn.setPrefWidth(75);
        ageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (ageColumn.validateValue(param)) return param.getValue().getValue().age;
            else return ageColumn.getComputedValue(param);
        });
        JFXTreeTableColumn<User, String> test = new JFXTreeTableColumn<>("Test");
        test.setPrefWidth(75);
        test.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (test.validateValue(param)) return param.getValue().getValue().test;
            else return test.getComputedValue(param);
        });
        result= new JFXTreeTableColumn<>("Result");
        result.setPrefWidth(75);
        result.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (result.validateValue(param)) return param.getValue().getValue().result;
            else return result.getComputedValue(param);
        });
        JFXTreeTableColumn<User, String> normal = new JFXTreeTableColumn<>("Normal");
        normal.setPrefWidth(60);
        normal.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
            if (normal.validateValue(param)) return param.getValue().getValue().normal;
            else return normal.getComputedValue(param);
        });
            JFXTreeTableColumn<User, String> date = new JFXTreeTableColumn<>("Date");
            date.setPrefWidth(100);
            date.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> {
                if (date.validateValue(param)) return param.getValue().getValue().date;
                else return date.getComputedValue(param);
            });


                ageColumn.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<User,
                        String>(new TextFieldEditorBuilder()));
                ageColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t) -> {
                    ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).age
                            .set(t.getNewValue());
                });

                name.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<User,
                        String>(new TextFieldEditorBuilder()));
                name.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t) -> {
                    ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                            .name.set(t.getNewValue());
                });

                test.setCellFactory((TreeTableColumn<User, String> param) ->
                        new GenericEditableTreeTableCell<User, String>(new TextFieldEditorBuilder()));
                test.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t) -> {
                    ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).test.
                            set(t.getNewValue());
                });
                result.setCellFactory((TreeTableColumn<User, String> param) ->
                        new GenericEditableTreeTableCell<User, String>(new TextFieldEditorBuilder()));
                result.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t) -> {
                    ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).result.
                            set(t.getNewValue());
                });
                ID.setCellFactory((TreeTableColumn<User, String> param) ->
                        new GenericEditableTreeTableCell<User, String>(new TextFieldEditorBuilder()));
                ID.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t) -> {
                    ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).ID.
                            set(t.getNewValue());
                });
                normal.setCellFactory((TreeTableColumn<User, String> param) ->
                        new GenericEditableTreeTableCell<User, String>(new TextFieldEditorBuilder()));
                normal.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t) -> {
                    ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).normal.
                            set(t.getNewValue());
                });
            date.setCellFactory((TreeTableColumn<User, String> param) ->
                    new GenericEditableTreeTableCell<User, String>(new TextFieldEditorBuilder()));
            date.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t) -> {
                ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).date.
                        set(t.getNewValue());
            });


// data
                ObservableList<User> users = FXCollections.observableArrayList();
                String SQL ="select person_ID, name,age , comTest ,comResult, IDcom,date from info,comtest where person_ID=comPerson_ID";
                try {
                    Statement stmt  = c.createStatement();
                    ResultSet rs    = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        users.add(new User(rs.getString("person_ID"),rs.getString("name"),rs.getString("age")
                                ,rs.getString("comTest"),rs.getString("comResult"),rs.getString("IDcom"),rs.getString("date")));
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }




// build tree

                TreeItem<User> root = new RecursiveTreeItem<User>(users, RecursiveTreeObject::getChildren);
//anchor.getChildren().removeAll(tree);
             tree= new JFXTreeTableView<User>(root);
                tree.setLayoutX(240);
                    tree.setLayoutY(28);
                    tree.setPrefWidth(525);
                    tree.setPrefHeight(471);

                   anchor.getChildren().add(tree);

         /*   if(p){
                tree.setRoot(root);
                System.out.println("root");
            }
            p=false;*/
                tree.setShowRoot(false);
                tree.setEditable(true);
                tree.getColumns().removeAll(ID,name,ageColumn,test,result,normal,date);
                tree.getColumns().setAll(ID, name, ageColumn,test,result,normal,date);

                        tree.group(date);


            System.out.println(tree.isEditable());

            //System.out.println(tree.notifyAccessibleAttributeChanged(););

        });

    }

    public void comboChanged(ActionEvent event) {

    }

    public void ins_test(ActionEvent event) {

        try {
            if (combo.getValue() != null) {
                System.out.println("enter test");
                if (combo.getValue().equals("Lipid Profile Test")) {
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view1.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Lipid Profile Test");
                    stage.setScene(new Scene(root));
                    stage.show();
                } else if (combo.getValue().equals("Seminal Fluid Analysis")) {
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view2.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Seminal Fluid Analysis");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                        else if (combo.getValue().equals("General Stool Examination")) {
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view3.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("General Stool Examination");
                    stage.setScene(new Scene(root));
                    stage.show();
                }   else if(combo.getValue().equals("Virology Tests")){
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view4.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Virology Tests");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                else if(combo.getValue().equals("24 hr.Urine Collection")){
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view5.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("24 hr.Urine Collection");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                else if(combo.getValue().equals("General Urine Examination")){
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/view6.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("General Urine Examination");
                    stage.setScene(new Scene(root));
                    stage.show();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Please Select test");

                alert.showAndWait();

            }





            // Hide this current window (if this is what you want)
            // ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    public void Edit(ActionEvent event) throws SQLException {
        System.out.println(tree.isFocused());


            if(newv!=null && pos!=null) {
                String Sql = "update comtest set comResult=? where IDcom=?";


                PreparedStatement pstmt = c.prepareStatement(Sql);


                // set the corresponding param
                pstmt.setString(1, newv);
                pstmt.setString(2, pos);


                // execute the delete statement
                pstmt.executeUpdate();
                System.out.println(pos + " updated ");
                pos=null; newv=null; dvalue=null;
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Please Select Row by Right Click");

                alert.showAndWait();
            }

            // System.out.println(tree.getEditingCell().getTreeItem().getValue().result.toString());
//        TreeTablePosition t;
//        t=tree.getEditingCell();
//        System.out.println(t.getColumn());
//        System.out.println(t.getTreeItem().getValue().toString());

Refresh();
            init();
    }

    public void delete(ActionEvent event) throws SQLException {
        if (dvalue!=null) {

            String Sql = "delete from info where person_ID =?";

            PreparedStatement pstmt = c.prepareStatement(Sql);

            // set the corresponding param
            pstmt.setString(1, dvalue);
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println(dvalue + " deleted ");

            Platform.runLater(() -> {
                // tree.unGroup(name);
                tree.reGroup();
                Refresh();
                init();
            });
            dvalue=null;
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please Select row by right click");
            alert.showAndWait();
        }
    }

    public void print(ActionEvent event) {
        if (dvalue!=null) {
        try {

                int P;
                JasperPrint jasperPrint;
                Connection connection;
                Class.forName("org.sqlite.JDBC");
                // connection= DriverManager.getConnection("jdbc:sqlite:test2.db");
                connection = dbconnection.getconnection();
                Statement stmt = connection.createStatement();
                String query = "select Pcase from info where person_ID=" + dvalue;
                rs = stmt.executeQuery(query);

                P = rs.getInt("Pcase");


                HashMap pram = new HashMap();
                //   pram.put("xxx","select info.person_ID, name,age , paiten.test ,result, ID,normal, date,doctor from info join paiten on info.person_ID=paiten.person_ID left join tests on paiten.test=tests.test where info.person_ID='33'");
                pram.put("x", dvalue);

                if (P == 2 || P == 4) {
                    jasperPrint = JasperFillManager.fillReport("general_s2.jasper", pram, connection);   // general2 ,virology4 , without nornal
                    JasperPrintManager.printReport(jasperPrint, false);
                   // JasperViewer.viewReport(jasperPrint);
                }
                if (P == 5 || P == 1) {
                    jasperPrint = JasperFillManager.fillReport("Blankt.jasper", pram, connection);   // 24 urine coll5 ,libid1 ,
                    JasperPrintManager.printReport(jasperPrint, false);
                    //JasperViewer.viewReport(jasperPrint);
                }
                if (P == 3) {
                    jasperPrint = JasperFillManager.fillReport("seminal.jasper", pram, connection);    // seminal3
                    JasperPrintManager.printReport(jasperPrint, false);
                   // JasperViewer.viewReport(jasperPrint);
                }
            if (P == 6) {
                jasperPrint = JasperFillManager.fillReport("general_s.jasper", pram, connection);    // general urine
                JasperPrintManager.printReport(jasperPrint, false);
                //JasperViewer.viewReport(jasperPrint);
            }


dvalue=null;
            //JasperViewer.viewReport(jasperPrint);
        }catch (Exception e){
            e.printStackTrace();
        }
        }else{
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("Information");
            alert3.setHeaderText(null);
            alert3.setContentText("Please Select row by right click");
         alert3.showAndWait();
        }
    }

    public void reload(ActionEvent event) {
        Refresh();
        init();

    }
    void init(){
        Platform.runLater(() -> {

            tree.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (event.isSecondaryButtonDown() && event.getClickCount() == 2) {
                        if(tree.getSelectionModel().getSelectedItem().isLeaf()) {
                            System.out.println("foucs " + tree.isFocused());
                            System.out.println(tree.getSelectionModel().getSelectedItem().getValue().normal.getValue());
                            pos = tree.getSelectionModel().getSelectedItem().getValue().normal.getValue();
                            dvalue = tree.getSelectionModel().getSelectedItem().getValue().ID.getValue();
                        }

                    }
                }
            });
            result.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<User, String>>() {
                @Override
                public void handle(TreeTableColumn.CellEditEvent<User, String> event) {

                    //TreeItem<User> editing= tree.getTreeItem(event.);
                    System.out.println(event.getNewValue().toString());
                    newv=event.getNewValue().toString();
                }
            });

        });
    }

    public void group(ActionEvent event) {
        tree.reGroup();
    }

    public void U_G(ActionEvent event) {

    }
}


class User extends RecursiveTreeObject<User>{
    StringProperty name;
    StringProperty test;
    StringProperty age;
    StringProperty result;
    StringProperty normal;
    StringProperty ID;
    StringProperty date;

    public User(String ID,String name, String age, String test,String result,String normal,String date) {
        this.name = new SimpleStringProperty(name) ;
        this.test = new SimpleStringProperty(test);
        this.age = new SimpleStringProperty(age);
        this.result=new SimpleStringProperty(result);
        this.normal=new SimpleStringProperty(normal);
        this.ID=new SimpleStringProperty(ID);
        this.date=new SimpleStringProperty(date);
    }
}

