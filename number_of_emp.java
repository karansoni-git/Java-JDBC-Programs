import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class number_of_emp {

    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            //only return the one value which is the total count of employee in records
            ResultSet rs = st.executeQuery("select count(*) as total from details");

            while (rs.next()) {
                System.out.println("Total employee : " + rs.getInt("total"));
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
