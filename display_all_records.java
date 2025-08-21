import java.sql.Connection; //interface
import java.sql.DriverManager; //this is a class
import java.sql.ResultSet; //interface
import java.sql.Statement; //interface

public class display_all_records {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from details");

            while (rs.next()) {
                //storing all the value into variables
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String designation = rs.getString("designation");
                int salary = rs.getInt("salary");

                //printing all the value of database by variables value
                System.out.println("id : " + id);
                System.out.println("name : " + name);
                System.out.println("designation : " + designation);
                System.out.println("salary : " + salary);
                System.out.println("-------------------------\n");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}