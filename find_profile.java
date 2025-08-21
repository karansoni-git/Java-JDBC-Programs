import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class find_profile {

    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {

        try {

            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from details where designation='developer'");

            while (rs.next()) {
                System.out.println("id : " + rs.getInt("id"));
                System.out.println("name : " + rs.getString("name"));
                System.out.println("designation : " + rs.getString("designation"));
                System.out.println("-------------------------\n");
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
