import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class find_details_by_dept {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter Department name : ");
            String deptno = sc.next();
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement psmt = con.prepareStatement("select * from details where department = ?");
            psmt.setString(1, deptno);
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
                System.out.println("id : " + rs.getInt("id"));
                System.out.println("name : " + rs.getString("name"));
                System.out.println("designation : " + rs.getString("designation"));
                System.out.println("salary : " + rs.getInt("salary"));
                System.out.println("-------------------------\n");
            }
            rs.close();
            psmt.close();
            con.close();
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
