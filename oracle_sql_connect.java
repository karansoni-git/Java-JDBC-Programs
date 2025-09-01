import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class oracle_sql_connect {
    private static final String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    private static final String user = "System";
    private static final String password = "kp1612";

    public static void main(String[] args) {
        try{
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM com");

            while(rs.next()){
                System.out.println("Department name : " + rs.getString("d_name"));
                System.out.println("Department city : " + rs.getString("d_city"));
                System.out.println("Department no : " + rs.getInt("deptno"));
                System.out.println("------------------------------");
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
