package sample;




import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.*;
import java.util.HashMap;

public class report {

    private static ResultSet rs;
    private static Statement stmt ;
    private static Connection connection;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, JRException {


        
        Class.forName("org.sqlite.JDBC");
       // connection= DriverManager.getConnection("jdbc:sqlite:test2.db");
        connection=dbconnection.getconnection();


        stmt=connection.createStatement();
        String  query = "select * from info,paiten,tests";
        rs=stmt.executeQuery(query);



        //JRResultSetDataSource rsdt=new JRResultSetDataSource(rs);
//         JasperFillManager.fillReportToFile("C:\\Users\\Mr. Mustafa\\IdeaProjects\\proj1\\printTable.jrxml",
//                new HashMap(), rsdt);


        HashMap pram=new HashMap();
     //   pram.put("xxx","select info.person_ID, name,age , paiten.test ,result, ID,normal, date,doctor from info join paiten on info.person_ID=paiten.person_ID left join tests on paiten.test=tests.test where info.person_ID='33'");
        pram.put("x","33");
//        pram.put("xx","33");
       JasperPrint jasperPrint= JasperFillManager.fillReport("sample/zero.jasper",pram,connection);
       JasperPrintManager.printReport(jasperPrint,false);
        JasperViewer.viewReport(jasperPrint);

    }
}
