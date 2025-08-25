import java.sql.*;
import java.util.Scanner;

public class user_input_record {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter Id to check details of employee : ");
            int id = sc.nextInt();

            Connection con = DriverManager.getConnection(url, user, password);
            String sql = "select * from details where id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            System.out.println();
            if (rs.next()) {
                System.out.println("id : " + rs.getInt("id"));
                System.out.println("name : " + rs.getString("name"));
                System.out.println("designation : " + rs.getString("designation"));
                System.out.println("salary : " + rs.getInt("salary"));
                System.out.println("-------------------------\n");
            } else {
                System.out.print("there is no record of id " + id + "!!");
            }
            rs.close();
            pstmt.close();
            con.close();
            sc.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
