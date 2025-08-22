import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class highest_salary {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            //only return the one value which is the max or highest salary of employee from records
            ResultSet rs = st.executeQuery("select max(salary) as highest_salary from details");

            while (rs.next()) {
                System.out.println("highest salary : " + rs.getInt("highest_salary"));
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
