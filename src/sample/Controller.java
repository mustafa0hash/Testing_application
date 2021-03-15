package sample;

import com.jfoenix.controls.JFXAutoCompletePopup;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXDialog;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;


import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    public Button btn;
    @FXML
    public TableView table;
    public ObservableList<ObservableList> data;

    public TableColumn col_name;
    public TableColumn col_age;
    public TableColumn col_test;
    public TableColumn col_res;
    public TextField in_name;
    public TextField in_age;
    public TextField in_test;
    public TextField in_res;
    public TextField look;
    public TableColumn col_id;
    public TableColumn f_key;
    public TableColumn p_name;
    public TableColumn p_age;
    public TableColumn p_id;
    public TableView table2;
    public ComboBox combo;
    public TextField n_normal;
    public TextField n_test;
    public TableColumn col_norm;
    public StackPane parent;
    public AnchorPane anchor;
    public Button button;
    public ImageView img;
    public Label l;
    public TableColumn col_date;
    public TableColumn col_doctor;
    public TextField in_doctor;
    public TextField in_gender;
    public ImageView excel;
    // public SearchableComboBox combo=new SearchableComboBox(getUsername());
    model model=new model();
    ObservableList<String> id;
    ObservableList<TableColumn> t = FXCollections.observableArrayList();
    ArrayList<String> x=new ArrayList<>();
    Connection c=model.connection;
    String sql="select result , name,test ,age from info,paiten";
    ResultSet rs;

    public Controller() throws SQLException {
    }

    //CONNECTION DATABASE
    public void buildData(String enter) throws Exception{


        data = FXCollections.observableArrayList();
        try{
            c = dbconnection.getconnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String sql2="select info.person_ID ,name ,age , paiten.test, result, ID,normal from info, paiten,tests where tests.test=paiten.test and info.person_ID=paiten.person_ID and name like \"" +
                    enter +"%\"";

            String SQL ="select info.person_ID, name,age , paiten.test ,result, ID,normal, date,doctor from info join paiten on info.person_ID=paiten.person_ID\n" +
                    "left join tests on paiten.test=tests.test";
            //ResultSet
            if(enter!=" "){
                Statement stmt  = c.createStatement();
                 rs    = stmt.executeQuery(sql2);}
            else if(enter==" "){
                rs=c.createStatement().executeQuery(SQL);
            }

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            t.addAll(col_id,col_name,col_age,col_test,col_res,f_key,col_norm,col_date,col_doctor);
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
              //  TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                t.get(i).setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

               // table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    if(rs.getString(i)==null)
                        row.add(" ");
                    else
                   row.add(rs.getString(i));

                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            table.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void open(ActionEvent actionEvent) throws SQLException {
table.setEditable(true);
    //    buildData(" ");

        //model.insert(scanner.next(),scanner.nextInt(),scanner.next(),scanner.next());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final Font f;
        try {
            f = Font.loadFont(new FileInputStream(new File("chocoleta.ttf")), 29);
            l.setFont(f); // use this font with our label
            l.setStyle("-fx-text-fill : #34465d");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        new Thread(() -> {
            try {
                buildData(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        /*JFXDialog dialog = new JFXDialog();
        Label label=new Label("plaese select test");
        label.setStyle("-fx-text-fill   : #34495e;\n" +
                "-fx-min-height : 40; \n"+
                "-fx-min-width : 140; \n"+
                "-fx-alignment: center; \n"+
                "    -fx-font-weight : bolder;\n" +
                "    -fx-font-size   : 14px;\n" +
                "    -fx-border-radius: 100px;\n" +
                "        -fx-background-radius: 200px;");
        dialog.setContent(label);*/
       // dialog.show(parent);
        select();
      //  person();
      /*  ValidationSupport validationSupport=new ValidationSupport();
        validationSupport.registerValidator(in_test, Validator.createEmptyValidator(""));*/
       /* try {
            combo.setEditable(true);
            combo.setItems(getUsername());
            TextFields.bindAutoCompletion(in_test, getUsername());
            TextFields.bindAutoCompletion(combo.getEditor(), getUsername());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        img.setOnMouseClicked(event -> {
            try {
                buildData(" ");
                in_name.clear();in_age.clear();in_test.clear();in_res.clear();in_doctor.clear();in_gender.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        excel.setOnMouseClicked(event -> {
            if(event.getClickCount()==2){
                export();
            }
        });
    }

    public void btn_insert(ActionEvent actionEvent) throws SQLException {
        if(!in_test.getText().isEmpty()) {
            System.out.println("test"+in_test.getText());
            LocalDateTime myDateObj = LocalDateTime.now();
             DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = myDateObj.format(myFormatObj);
            model.insert(in_name.getText(), in_age.getText()
                    , in_test.getText(), in_res.getText(),in_gender.getText(),formattedDate,in_doctor.getText());
            try {
                buildData(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }
            in_name.clear();in_age.clear();in_test.clear();in_res.clear();in_doctor.clear();in_gender.clear();
        }else{
            JFXDialog dialog = new JFXDialog();
        Label label=new Label("plaese select test");
        label.setStyle("-fx-text-fill   : #34495e;\n" +
                "-fx-min-height : 40; \n"+
                "-fx-min-width : 140; \n"+
                "-fx-alignment: center; \n"+
                "    -fx-font-weight : bolder;\n" +
                "    -fx-font-size   : 14px;\n" +
                "    -fx-border-radius: 100px;\n" +
                "        -fx-background-radius: 200px;");
        dialog.setContent(label);
             dialog.show(parent);
        }
    }


    public void search(KeyEvent keyEvent) throws Exception {
        buildData(String.valueOf(look.getText()));
    }

    public void delete(ActionEvent actionEvent) throws SQLException {

        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();

//        System.out.println(table.getSelectionModel().getSelectedCells());
//        System.out.println(table.getSelectionModel().getSelectedItems());

        String Sql="delete from info where person_ID =?";
        String[] x;
     x=table.getSelectionModel().getSelectedItems().toString().split(",");
        String value=x[0].replace("[[","");

        PreparedStatement pstmt = c.prepareStatement(Sql);

            // set the corresponding param
            pstmt.setString(1, value);
            // execute the delete statement
            pstmt.executeUpdate();
        System.out.println(value+" deleted ");



    }
    public void select(){

        table.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    String[]update=table.getSelectionModel().getSelectedItems().toString().replace("[[","").replace("]]","").replace(" ","").split(",");
                    System.out.println( "select: "+Arrays.toString(update));
                    x.add(0,update[0]);
                    x.add(1,update[5]);
                }else if(event.isPrimaryButtonDown() && event.getClickCount() == 2){
                    String[]update=table.getSelectionModel().getSelectedItems().toString().replace("[[","").replace("]]","").replace(" ","").split(",");
                    System.out.println( "select: "+Arrays.toString(update));
                    x.add(0,update[0]);
                    x.add(1,update[5]);
                    in_name.setText(update[1]);
                    in_age.setText(update[2]);
                    in_test.setText(update[3]);
                    in_res.setText(update[4]);
                }
            }
        });


    }

    public void update(ActionEvent actionEvent) throws SQLException {

        if (!in_test.getText().isEmpty() & !in_res.getText().isEmpty()) {
            String Sql = "update info set name=? ,age=? where person_ID=?";
            String sql2 = "update paiten set test=?, result=? where ID=?";


            PreparedStatement pstmt = c.prepareStatement(Sql);
            PreparedStatement pstmt2 = c.prepareStatement(sql2);


            // set the corresponding param
            pstmt.setString(1, in_name.getText());
            pstmt.setString(2, in_age.getText());
            pstmt.setString(3, x.get(0));

            pstmt2.setString(2, in_res.getText());
            pstmt2.setString(1, in_test.getText());
            pstmt2.setString(3, x.get(1));
            System.out.println(x.get(0) + x.get(1));
            // execute the delete statement
            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            System.out.println(x + " updated ");
            try {
                buildData(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JFXDialog dialog = new JFXDialog();
            Label label = new Label("plaese select Row");
            label.setStyle("-fx-text-fill   : #34495e;\n" +
                    "-fx-min-height : 40; \n" +
                    "-fx-min-width : 140; \n" +
                    "-fx-alignment: center; \n" +
                    "    -fx-font-weight : bolder;\n" +
                    "    -fx-font-size   : 14px;\n" +
                    "    -fx-border-radius: 100px;\n" +
                    "        -fx-background-radius: 200px;");
            dialog.setContent(label);
            dialog.show(parent);
        }
    }

    public void ins_test(ActionEvent actionEvent) throws Exception {
        if (!in_test.getText().isEmpty()) {


            String[] x;
            x = table.getSelectionModel().getSelectedItems().toString().split(",");
            String value = x[0].replace("[[", "");

            String sql2 = "INSERT INTO paiten(test,result,person_ID) VALUES(?,?,?);";
            PreparedStatement pstmt2 = c.prepareStatement(sql2);

            pstmt2.setString(1, in_test.getText().toString());
            pstmt2.setString(2, in_res.getText().toString());
            pstmt2.setInt(3, Integer.parseInt(value));
            pstmt2.executeUpdate();
            buildData(" ");
            in_test.clear();
            in_res.clear();
            System.out.println("2 test: "+in_test+" "+ in_res +" "+value );

        }
        else {
            JFXDialog dialog = new JFXDialog();
            Label label=new Label("plaese select Row");
            label.setStyle("-fx-text-fill   : #34495e;\n" +
                    "-fx-min-height : 40; \n"+
                    "-fx-min-width : 140; \n"+
                    "-fx-alignment: center; \n"+
                    "    -fx-font-weight : bolder;\n" +
                    "    -fx-font-size   : 14px;\n" +
                    "    -fx-border-radius: 100px;\n" +
                    "        -fx-background-radius: 200px;");
            dialog.setContent(label);
            dialog.show(parent);

        }
    }

    public void person(){
         ObservableList<ObservableList> data2;
        data2 = FXCollections.observableArrayList();
        try{
            c = dbconnection.getconnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL ="select name,age ,person_ID from info";
            //ResultSet
                ResultSet resultSet;
                resultSet=c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            ObservableList<TableColumn> tab = FXCollections.observableArrayList();
            tab.addAll(p_name,p_age,p_id);
            for(int i=0 ; i<resultSet.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                //  TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                tab.get(i).setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(resultSet.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=resultSet.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(resultSet.getString(i));

                }
                data2.add(row);

            }

            //FINALLY ADDED TO TableView
            table2.setItems(data2);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");

        }    }
    // when initializing the window or in some other method
    boolean cas=true;
    void initialize() {
        if (cas) {
            cas=false;
            ObservableList observableList=FXCollections.observableArrayList();
            ObservableList observableList2=FXCollections.observableArrayList("male","female");
            JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
            JFXAutoCompletePopup<String> autoCompletePopup2 = new JFXAutoCompletePopup<>();
            String SQL = "select test from tests";
            try {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
                while (rs.next()) {
                    observableList.add(rs.getString("test"));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            autoCompletePopup.getSuggestions().addAll(observableList);
            autoCompletePopup2.getSuggestions().addAll(observableList2);

            autoCompletePopup.setSelectionHandler(event -> {
                in_test.setText(event.getObject());

                // you can do other actions here when text completed
            });
            autoCompletePopup2.setSelectionHandler(event -> {
                in_gender.setText(event.getObject());

                // you can do other actions here when text completed
            });

            // filtering options
            in_test.textProperty().addListener(observable -> {
                autoCompletePopup.filter(string -> string.toLowerCase().contains(in_test.getText().toLowerCase()));
                if (autoCompletePopup.getFilteredSuggestions().isEmpty() || in_test.getText().isEmpty()) {
                    autoCompletePopup.hide();
                    // if you remove textField.getText.isEmpty() when text field is empty it suggests all options
                    // so you can choose
                } else {
                    autoCompletePopup.show(in_test);
                }
            });
            in_gender.textProperty().addListener(observable -> {
                autoCompletePopup2.filter(string -> string.toLowerCase().contains(in_gender.getText().toLowerCase()));
                if (autoCompletePopup2.getFilteredSuggestions().isEmpty() || in_gender.getText().isEmpty()) {
                    autoCompletePopup2.hide();
                    // if you remove textField.getText.isEmpty() when text field is empty it suggests all options
                    // so you can choose
                } else {
                    autoCompletePopup2.show(in_gender);
                }
            });
        }
    }

    public void auto(KeyEvent keyEvent) throws SQLException {

        initialize();

    }

    public ObservableList<String> getUsername() throws SQLException {
        ObservableList<String> data3= FXCollections.observableArrayList();
        c = dbconnection.getconnection();
        //SQL FOR SELECTING ALL OF CUSTOMER
        String SQL ="select name from info ";
        //ResultSet
        ResultSet resultSet;
        resultSet=c.createStatement().executeQuery(SQL);
        while(resultSet.next()){
            //Iterate Row
            for(int i=1 ; i<=resultSet.getMetaData().getColumnCount(); i++) {
                //Iterate Column
                data3.add(resultSet.getString("name"));
            }
        }

        return data3;
    }

    public void add_test(ActionEvent actionEvent) throws SQLException {
        String sql = "INSERT INTO tests(test,normal) VALUES(?,?)" ;
        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1, n_test.getText());
        pstmt.setString(2, n_normal.getText());
        pstmt.executeUpdate();

    }

    public void createReport(ActionEvent actionEvent) throws JRException, ClassNotFoundException, SQLException {

/*
            JasperReport jasperReport= JasperCompileManager.compileReport("C:\\Users\\Mr. Mustafa\\IdeaProjects\\proj1\\printTable.jrxml");
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,c);
            JasperViewer.viewReport(jasperPrint);



            System.out.print("Done!");
            Statement stmt;
            String query="select * from info";
            stmt=c.createStatement();
            rs=stmt.executeQuery(query);
            JRResultSetDataSource rsdt=new JRResultSetDataSource(rs);

            JasperPrint jp;
            jp = JasperFillManager.fillReport("C:\\Users\\Mr. Mustafa\\IdeaProjects\\proj1\\printTable.jrxml", new HashMap(), rsdt);
            JasperViewer jv = new JasperViewer(jp);
            jv.setVisible(true);
            c.close();*/
        Connection connection;
        Class.forName("org.sqlite.JDBC");
        // connection= DriverManager.getConnection("jdbc:sqlite:test2.db");
        connection=dbconnection.getconnection();
        Statement stmt = connection.createStatement();
        String  query = "select * from info,paiten,tests";
        rs=stmt.executeQuery(query);

        HashMap pram=new HashMap();
            //   pram.put("xxx","select info.person_ID, name,age , paiten.test ,result, ID,normal, date,doctor from info join paiten on info.person_ID=paiten.person_ID left join tests on paiten.test=tests.test where info.person_ID='33'");
            pram.put("x","34");
//        pram.put("xx","33");

            JasperPrint jasperPrint= JasperFillManager.fillReport("zero.jasper",pram,connection);
            JasperPrintManager.printReport(jasperPrint,false);
            JasperViewer.viewReport(jasperPrint);

    }

    public void load_next(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("sample3.fxml"));

        Scene scene=button.getScene();
        root.translateYProperty().set(scene.getHeight());
        parent.getChildren().add(root);
        Timeline timeline=new Timeline();
        KeyValue keyValue=new KeyValue(root.translateYProperty(),0, Interpolator.EASE_IN);
        KeyFrame keyFrame=new KeyFrame(Duration.seconds(2),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event -> {
            parent.getChildren().remove(anchor);
        });
        timeline.play();
    }
    public void load_back() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("sample2.fxml"));

        root.translateYProperty().set(557);
        parent.getChildren().add(root);
        Timeline timeline=new Timeline();
        KeyValue keyValue=new KeyValue(root.translateYProperty(),0, Interpolator.EASE_IN);
        KeyFrame keyFrame=new KeyFrame(Duration.seconds(2),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event -> {
          //  parent.getChildren().remove(anchor);
        });
        timeline.play();
    }

    public void refresh(MouseEvent mouseEvent) {
    }

    public void note2(MouseEvent mouseEvent) throws IOException {

    }

    public void showp(ActionEvent event) throws IOException {
        Parent root2;
        root2 = FXMLLoader.load(getClass().getClassLoader().getResource("sample/ins.fxml"));
        Stage stage2 = new Stage();
        stage2.setTitle("Tests profile");
        JFXDecorator decorator = new JFXDecorator(stage2,root2);
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator);
        stage2.setScene(scene);
        stage2.show();
    }

    public void load(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("sample2.fxml"));

        root.translateYProperty().set(557);
        parent.getChildren().add(root);
        Timeline timeline=new Timeline();
        KeyValue keyValue=new KeyValue(root.translateYProperty(),0, Interpolator.EASE_IN);
        KeyFrame keyFrame=new KeyFrame(Duration.seconds(2),keyValue);
        timeline.getKeyFrames().add(keyFrame);
//        timeline.setOnFinished(event -> {
//            //  parent.getChildren().remove(anchor);
//        });
        timeline.play();
    }

    public void report(ActionEvent event) {

        try {
            if(x.get(0)!=null) {
                Connection connection;
                Class.forName("org.sqlite.JDBC");
                // connection= DriverManager.getConnection("jdbc:sqlite:test2.db");
                connection = dbconnection.getconnection();


                HashMap pram = new HashMap();
                //   pram.put("xxx","select info.person_ID, name,age , paiten.test ,result, ID,normal, date,doctor from info join paiten on info.person_ID=paiten.person_ID left join tests on paiten.test=tests.test where info.person_ID='33'");
                pram.put("x", x.get(0));
//        pram.put("xx","33");

                JasperPrint jasperPrint = JasperFillManager.fillReport("zero.jasper", pram, c);


                JasperPrintManager.printReport(jasperPrint, false);
                //JasperViewer.viewReport(jasperPrint);
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(e.toString());
        }
    }

    public void export(){
        try {

            PrintWriter pw= new PrintWriter(new File("Tests_table1.csv"));
            StringBuilder sb=new StringBuilder();



            ResultSet rs=null;

            String query="select info.person_ID, name,age , paiten.test ,result, ID,normal, date,doctor from info join paiten on info.person_ID=paiten.person_ID left join tests on paiten.test=tests.test";
            PreparedStatement ps=c.prepareStatement(query);
            rs=ps.executeQuery();

            while(rs.next()){
                sb.append(rs.getString("person_ID")+" ");
                sb.append(",");
                sb.append(rs.getString("name")+" ");
                sb.append(",");
                sb.append(rs.getString("age")+" ");
                sb.append(",");
                sb.append(rs.getString("test")+" ");
                sb.append(",");
                sb.append(rs.getString("result")+" ");
                sb.append(",");
                sb.append(rs.getString("normal")+" ");
                sb.append(",");
                sb.append(rs.getString("date")+" ");
                sb.append(",");
                sb.append(rs.getString("doctor")+" ");
                sb.append("\r\n");
            }

            pw.write(sb.toString());
            pw.close();
            System.out.println("finished");

        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
