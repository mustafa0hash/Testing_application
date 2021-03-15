package sample;

import javafx.collections.ObservableList;

import java.sql.*;


public class model {

    Connection connection;
    private ObservableList<ObservableList> data;

    public model() {
        try {
            this.connection = dbconnection.getconnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (this.connection == null) {
            System.exit(1);
        }
    }

    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }


    }
    public void query() throws SQLException {
        String sql="select result , name,test ,age from info,paiten where info.person_ID=paiten.person_ID";
        String sql2="select result ,name , test, age from info, paiten where info.person_ID=paiten.person_ID and name like \"mus%\"";

        Statement stmt  = connection.createStatement();
        ResultSet rs    = stmt.executeQuery(sql2);
       /* Statement stmt  = connection.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);
*/
            while (rs.next()) {
                System.out.println(rs.getString("name")+"\t"
                        +rs.getString("age")+"\t"+
                        rs.getString("test")+"\t"+
                        rs.getString("result")+"\t" );
            }


    }

    public void insert(String name, String age, String test , String result,String gender,String date,String doctor) throws SQLException {
        String sql = "INSERT INTO info(name,age,gender, doctor,date) VALUES(?,?,?,?,?)" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, age);
        pstmt.setString(3, gender);
        pstmt.setString(4, doctor);
        pstmt.setString(5, date);

        pstmt.executeUpdate();

        /**************************/
            int x=0;
        Statement s  = connection.createStatement();
        ResultSet r    = s.executeQuery("select person_ID from info");
        while (r.next())
        x= r.getInt("person_ID");

        /*************************/

        String sql2="INSERT INTO paiten(test,result,person_ID) VALUES(?,?,?);";
        PreparedStatement pstmt2 = connection.prepareStatement(sql2);

            pstmt2.setString(1, test);
            pstmt2.setString(2, result);
            pstmt2.setInt(3, x);
            pstmt2.executeUpdate();


}

    public void update(int id, String name, double capacity) throws SQLException {
        String sql = "UPDATE warehouses SET name = ? , "
                + "capacity = ? "
                + "WHERE id = ?";

             PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.setInt(3, id);
            // update
            pstmt.executeUpdate();

        }

}
