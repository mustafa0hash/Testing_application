package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller3 implements Initializable {
    @FXML
    public TableView table2;
    public JFXTextField testf;
    public JFXTextField normalf;
    public TableColumn<Pv, String> col_t;
    public TableColumn<Pv, String> col_n;
    public AnchorPane contain;
    model m = new model();
    boolean foucs = false;
    Connection c = m.connection;
    ResultSet rs;
    String id;
    JFXTreeTableView<Pv> tree = new JFXTreeTableView<>();
    public ObservableList<ObservableList> data;
    ObservableList<TableColumn> t = FXCollections.observableArrayList();

    public void add_test(ActionEvent event) throws SQLException {
        String sql = "INSERT INTO tests(test,normal) VALUES(?,?)";
        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1, testf.getText());
        pstmt.setString(2, normalf.getText());
        pstmt.executeUpdate();
        init();
    }

    public void editt(ActionEvent event) throws SQLException {
        if (foucs) {
            String sql = "update tests set test=? ,normal=? where t_ID=?";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, testf.getText());
            pstmt.setString(2, normalf.getText());
            pstmt.setString(3, id);

            pstmt.executeUpdate();
        }
        init();
        foucs = false;
    }

    public void buildData(String enter) {

/*
        data = FXCollections.observableArrayList();
        try{
            c = dbconnection.getconnection();
            //SQL FOR SELECTING ALL OF CUSTOMER

            String SQL ="select test,normal from tests";

                    //ResultSet
                rs=c.createStatement().executeQuery(SQL);

            *//**********************************
         * TABLE COLUMN ADDED DYNAMICALLY *
         **********************************//*
            t.addAll(col_t,col_n);
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                //  TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                t.get(i).setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                // table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            *//********************************
         * Data added to ObservableList *
         ********************************//*
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));

                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            table2.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }*/
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
init();

    }

    void init() {
        Platform.runLater(() -> {
            JFXTreeTableColumn<Pv, String> ID = new JFXTreeTableColumn<>("ID");
            ID.setPrefWidth(160);
            ID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pv, String> param) -> {
                if (ID.validateValue(param)) return param.getValue().getValue().test;
                else return ID.getComputedValue(param);
            });
            JFXTreeTableColumn<Pv, String> test = new JFXTreeTableColumn<>("Test");
            test.setPrefWidth(160);
            test.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pv, String> param) -> {
                if (test.validateValue(param)) return param.getValue().getValue().test;
                else return test.getComputedValue(param);
            });

            JFXTreeTableColumn<Pv, String> normal = new JFXTreeTableColumn<>("Normal");
            normal.setPrefWidth(160);
            normal.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pv, String> param) -> {
                if (normal.validateValue(param)) return param.getValue().getValue().normal;
                else return normal.getComputedValue(param);
            });

            test.setCellFactory((TreeTableColumn<Pv, String> param) -> new GenericEditableTreeTableCell<Pv,
                    String>(new TextFieldEditorBuilder()));
            test.setOnEditCommit((TreeTableColumn.CellEditEvent<Pv, String> t) -> {
                ((Pv) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).test
                        .set(t.getNewValue());
            });

            normal.setCellFactory((TreeTableColumn<Pv, String> param) -> new GenericEditableTreeTableCell<Pv,
                    String>(new TextFieldEditorBuilder()));
            normal.setOnEditCommit((TreeTableColumn.CellEditEvent<Pv, String> t) -> {
                ((Pv) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                        .normal.set(t.getNewValue());
            });

            ObservableList<Pv> users = FXCollections.observableArrayList();

            String SQL = "select test, normal,t_ID  from tests";
            try {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
                while (rs.next()) {
                    users.add(new Pv(rs.getString("test"), rs.getString("normal"), rs.getString("t_ID")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            TreeItem<Pv> root = new RecursiveTreeItem<Pv>(users, RecursiveTreeObject::getChildren);

            tree = new JFXTreeTableView<Pv>(root);
            tree.setLayoutX(10);
            tree.setLayoutY(187);
            tree.setPrefWidth(350);
            tree.setPrefHeight(250);
            tree.setStyle(".table-column {\n" +
                    "  -fx-alignment: CENTER;\n" +
                    "}");
            tree.applyCss();
            contain.getChildren().add(tree);
            tree.setShowRoot(false);
            tree.setEditable(false);
            tree.getColumns().setAll(test, normal);

            tree.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (event.isSecondaryButtonDown() && event.getClickCount() == 1) {
                        foucs = tree.isFocused();
                        normalf.setText(tree.getSelectionModel().getSelectedItem().getValue().normal.getValue());
                        testf.setText(tree.getSelectionModel().getSelectedItem().getValue().test.getValue());
                        id = tree.getSelectionModel().getSelectedItem().getValue().ID.getValue();

                    }
                }
            });
        });

    }
}

class Pv extends RecursiveTreeObject<Pv>{

    StringProperty test;
    StringProperty normal;
    StringProperty ID;

    public Pv(String test, String normal,String ID) {
        this.test = new SimpleStringProperty(test);
        this.normal =new SimpleStringProperty(normal);
        this .ID=new SimpleStringProperty(ID);
    }

}

