import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class city_records {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from details where city = 'rajkot' ");

            while (rs.next()) {
                System.out.println("id : " + rs.getInt("id"));
                System.out.println("name : " + rs.getString("name"));
                System.out.println("designation : " + rs.getString("designation"));
                System.out.println("salary : " + rs.getInt("salary"));
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
