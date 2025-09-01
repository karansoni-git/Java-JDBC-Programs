import java.sql.*;
import java.util.Scanner;

public class callable_with_parameters {
    private static final String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    private static final String user = "System";
    private static final String password = "kp1612";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            System.out.print("Enter EID : ");
            int eid = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter ENAME : ");
            String ename = sc.nextLine();
            System.out.print("Enter DEPTNO : ");
            int deptno = sc.nextInt();

            CallableStatement cstmt = con.prepareCall("{call insert_with_parameter(?,?,?)}");

            cstmt.setInt(1, eid);
            cstmt.setString(2, ename);
            cstmt.setInt(3, deptno);

            cstmt.execute();

            System.out.println("Record inserted successfully");

            ResultSet rs = stmt.executeQuery("SELECT * FROM cemp ORDER BY eid");
            int record_no = 0;

            while (rs.next()) {
                record_no++;
                System.out.println("Employee ID : " + rs.getInt("eid"));
                System.out.println("Employee Name : " + rs.getString("ename"));
                System.out.println("Department No : " + rs.getInt("deptno"));
                System.out.println("------------------------------");
            }

            System.out.println(record_no + " Records fetched");

            cstmt.close();
            con.close();
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
