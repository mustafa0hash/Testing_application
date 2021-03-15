package sample;

import com.jfoenix.utils.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class operation implements Initializable {
    public TextField t10;
    public TextField t9;
    public TextField t8;
    public TextField t7;
    public TextField t6;
    public TextField t5;
    public TextField t4;
    public TextField t3;
    public TextField t2;
    public TextField t1;
    public TextField name;
    public TextField age;
    public TextField doctor;
    public TextField t31;
    public TextField t32;
    public TextField t33;
    public TextField t34;
    public TextField t35;
    public TextField t36;
    public TextField t37;
    public TextField t38;
    public TextField t39;
    public TextField t310;
    public TextField t311;
    public TextField t312;
    public TextField t313;
    public TextField name3;
    public TextField age3;
    public TextField doctor3;
    public TextField t21;
    public TextField t22;
    public TextField t23;
    public TextField t24;
    public TextField t25;
    public TextField t26;
    public TextField t27;
    public TextField t28;
    public TextField t29;
    public TextField t210;
    public TextField t211;
    public TextField name2;
    public TextField age2;
    public TextField doctor2;
    public Button save;
    public Button save3;
    public Button save2;
    public TextField t41;
    public TextField t42;
    public TextField t43;
    public TextField t44;
    public TextField t45;
    public TextField t56;
    public TextField name4;
    public TextField age4;
    public TextField doctor4;
    public Button save4;
    public TextField t46;
    public TextField t51;
    public TextField t52;
    public TextField t53;
    public TextField t54;
    public TextField t55;
    public TextField name5;
    public TextField age5;
    public TextField doctor5;
    public Button save5;
    public TextField t212;
    public TextField t61;
    public TextField t62;
    public TextField t63;
    public TextField t64;
    public TextField t65;
    public TextField t66;
    public TextField t67;
    public TextField t68;
    public TextField t69;
    public TextField t70;
    public TextField t71;
    public TextField t72;
    public TextField t73;
    public TextField t74;
    public TextField t75;
    public Button save6;
    public TextField t76;
    public TextField t77;
    public TextField t78;
    public TextField t79;
    public TextField t80;
    public TextField t81;
    public TextField t82;
    public TextField name6;
    public TextField age6;
    public TextField doc6;
    ArrayList<TextField> result1=new ArrayList<>();
    ArrayList<TextField> result3=new ArrayList<>();
    ArrayList<TextField> result2=new ArrayList<>();
    ArrayList<TextField> result4=new ArrayList<>();
    ArrayList<TextField> result5=new ArrayList<>();
    ArrayList<TextField> result6=new ArrayList<>();
    String[] test1={"S. Glu","S. Urea","S. Creatinin","S. Cholertrol","S. Triglyceride","S. HDL Cholestrol","S.LDL Cholesterol","S. VIDI Cholestrol","LDL/HDL","TC/HD"};
    String[] test3={"Colour","Volume","Reaction","Lquification time","Total Sp. No.","MOTILITY","MOTILITY","MOTILITY","MORPHOLOGY","MORPHOLOGY","R.B.Cs","Pus Cells","Others"};
    String[] test2={"Colour","CONSIS","R. B. Cs","PUS CELLS","Ova","G.Lambila","E.Histolytica","MONILLIA","UN DIGESTED FOOD","OTHERS","PH","H-Pylori"};
    String[] test4={"H A V", "H B V","H C V","H bs Ag","H I V", "Blood Group"};
    String[] test5={"S.Total Protein","S.Albumin","S.Globulin","24 Hr Urine Protein", "24 Hr Urine Volume","VMA"};
    String[] test6={"Colour","Appearance","Sp. Gravity","Albumin","Reaction","Sugar","Ketone","Bile Pigments","Urobilinogen","Bilirubin","Nitrite","R.B.Cs","Pus cells","Epithelial Cells","Am. Urate","Am. Phosphate","Ca. Oxalate","Uric Acid","Casts","Mucus Threats","Bacteria","Others"};
    model m=new model();
    Controller_2 controller_2;
    Connection connection=m.connection;
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = myDateObj.format(myFormatObj);
    public void Save(ActionEvent event) throws SQLException {


        String sql = "INSERT INTO info(name,age,Pcase,date) VALUES(?,?,?,?)" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name.getText());
        pstmt.setString(2, age.getText());
        pstmt.setInt(3,1);
        pstmt.setString(4, formattedDate);
        pstmt.executeUpdate();
        /**************************/
        int x=0;
        Statement s  = connection.createStatement();
        ResultSet r    = s.executeQuery("select person_ID from info");
        while (r.next())
            x= r.getInt("person_ID");

        /*************************/
        for(int i=0;i<10;i++) {
            String sql2 = "INSERT INTO comtest(comTest,comResult,comPerson_ID) VALUES(?,?,?);";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);

             pstmt2.setString(1, test1[i]);
             pstmt2.setString(2, result1.get(i).getText());
            pstmt2.setInt(3, x);
            pstmt2.executeUpdate();
        }

       // controller_2.Refresh();
        save.setText("Saved");

    }



    public void Save2(ActionEvent event) throws SQLException {


        String sql = "INSERT INTO info(name,age,Pcase,date) VALUES(?,?,?,?)" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name2.getText());
        pstmt.setString(2, age2.getText());
        pstmt.setInt(3,2);
        pstmt.setString(4, formattedDate);
        pstmt.executeUpdate();
        /**************************/
        int x=0;
        Statement s  = connection.createStatement();
        ResultSet r    = s.executeQuery("select person_ID from info");
        while (r.next())
            x= r.getInt("person_ID");

        /*************************/
        for(int i=0;i<12;i++) {
            String sql2 = "INSERT INTO comtest(comTest,comResult,comPerson_ID) VALUES(?,?,?);";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);

            pstmt2.setString(1, test2[i]);
            pstmt2.setString(2, result2.get(i).getText());
            pstmt2.setInt(3, x);
            pstmt2.executeUpdate();
        }
       // controller_2.Refresh();
        save2.setText("Saved");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        result1.add(t1);
        result1.add(t2);
        result1.add(t3);
        result1.add(t4);
        result1.add(t5);
        result1.add(t6);
        result1.add(t7);
        result1.add(t8);
        result1.add(t9);
        result1.add(t10);
        result3.add(t31);
        result3.add(t32);
        result3.add(t33);
        result3.add(t34);
        result3.add(t35);
        result3.add(t36);
        result3.add(t37);
        result3.add(t38);
        result3.add(t39);
        result3.add(t310);
        result3.add(t311);
        result3.add(t312);
        result3.add(t313);
        result2.add(t21);
        result2.add(t22);
        result2.add(t23);
        result2.add(t24);
        result2.add(t25);
        result2.add(t26);
        result2.add(t27);
        result2.add(t28);
        result2.add(t29);
        result2.add(t210);
        result2.add(t211);
        result2.add(t212);
        result4.add(t41);
        result4.add(t42);
        result4.add(t43);
        result4.add(t44);
        result4.add(t45);
        result4.add(t46);
        result5.add(t51);
        result5.add(t52);
        result5.add(t53);
        result5.add(t54);
        result5.add(t55);
        result5.add(t56);
        result6.add(t61);
        result6.add(t62);
        result6.add(t63);
        result6.add(t64);
        result6.add(t65);
        result6.add(t66);
        result6.add(t67);
        result6.add(t68);
        result6.add(t69);
        result6.add(t70);
        result6.add(t71);
        result6.add(t72);
        result6.add(t73);
        result6.add(t74);
        result6.add(t75);
        result6.add(t76);
        result6.add(t77);
        result6.add(t78);
        result6.add(t79);
        result6.add(t80);
        result6.add(t81);
        result6.add(t82);





    }

    public void Save3(ActionEvent event) throws SQLException {


        String sql = "INSERT INTO info(name,age,Pcase,date) VALUES(?,?,?,?)" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name3.getText());
        pstmt.setString(2, age3.getText());
        pstmt.setInt(3,3);
        pstmt.setString(4, formattedDate);
        pstmt.executeUpdate();
        /**************************/
        int x=0;
        Statement s  = connection.createStatement();
        ResultSet r    = s.executeQuery("select person_ID from info");
        while (r.next())
            x= r.getInt("person_ID");

        /*************************/
        for(int i=0;i<13;i++) {
            String sql2 = "INSERT INTO comtest(comTest,comResult,comPerson_ID) VALUES(?,?,?);";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);

            pstmt2.setString(1, test3[i]);
            pstmt2.setString(2, result3.get(i).getText());
            pstmt2.setInt(3, x);
            pstmt2.executeUpdate();
        }
      //  controller_2.Refresh();
        save3.setText("Saved");
    }

    public void Save4(ActionEvent event) throws SQLException {

        String sql = "INSERT INTO info(name,age,Pcase,date) VALUES(?,?,?,?)" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name4.getText());
        pstmt.setString(2, age4.getText());
        pstmt.setInt(3,4);
        pstmt.setString(4, formattedDate);
        pstmt.executeUpdate();
        /**************************/
        int x=0;
        Statement s  = connection.createStatement();
        ResultSet r    = s.executeQuery("select person_ID from info");
        while (r.next())
            x= r.getInt("person_ID");

        /*************************/
        for(int i=0;i<6;i++) {
            String sql2 = "INSERT INTO comtest(comTest,comResult,comPerson_ID) VALUES(?,?,?);";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);

            pstmt2.setString(1, test4[i]);
            pstmt2.setString(2, result4.get(i).getText());
            pstmt2.setInt(3, x);
            pstmt2.executeUpdate();
        }
       // controller_2.Refresh();
        save4.setText("Saved");
    }

    public void Save5(ActionEvent event) throws SQLException {
        String sql = "INSERT INTO info(name,age,Pcase,date) VALUES(?,?,?,?)" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name5.getText());
        pstmt.setString(2, age5.getText());
        pstmt.setInt(3,5);
        pstmt.setString(4, formattedDate);
        pstmt.executeUpdate();
        /**************************/
        int x=0;
        Statement s  = connection.createStatement();
        ResultSet r    = s.executeQuery("select person_ID from info");
        while (r.next())
            x= r.getInt("person_ID");

        /*************************/
        for(int i=0;i<6;i++) {
            String sql2 = "INSERT INTO comtest(comTest,comResult,comPerson_ID) VALUES(?,?,?);";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);

            pstmt2.setString(1, test5[i]);
            pstmt2.setString(2, result5.get(i).getText());
            pstmt2.setInt(3, x);
            pstmt2.executeUpdate();
        }
       // JFXUtilities.runInFX(()->{

            //controller_2.Refresh();

       // });
        save5.setText("Saved");
    }

    public void Save6(ActionEvent event) throws SQLException {
        String sql = "INSERT INTO info(name,age,Pcase,date) VALUES(?,?,?,?)" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name6.getText());
        pstmt.setString(2, age6.getText());
        pstmt.setInt(3,6);
        pstmt.setString(4, formattedDate);
        pstmt.executeUpdate();
        /**************************/
        int x=0;
        Statement s  = connection.createStatement();
        ResultSet r    = s.executeQuery("select person_ID from info");
        while (r.next())
            x= r.getInt("person_ID");

        /*************************/
        for(int i=0;i<22;i++) {
            String sql2 = "INSERT INTO comtest(comTest,comResult,comPerson_ID) VALUES(?,?,?);";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);

            pstmt2.setString(1, test6[i]);
            pstmt2.setString(2, result6.get(i).getText());
            pstmt2.setInt(3, x);
            pstmt2.executeUpdate();
        }
        // JFXUtilities.runInFX(()->{

        //controller_2.Refresh();

        // });
        save6.setText("Saved");
    }
}
