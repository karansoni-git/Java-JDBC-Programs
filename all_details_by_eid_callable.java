import java.sql.*;
import java.util.Scanner;

public class all_details_by_eid_callable {
    private static final String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    private static final String user = "System";
    private static final String password = "kp1612";


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int eid = 0 ;
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            CallableStatement cstmt = con.prepareCall("{call get_employees_by_eid(?,?,?)}");
            System.out.print("Enter EID : ");
            eid = sc.nextInt();
            cstmt.setInt(1, eid);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.execute();
            String name = cstmt.getString(2);
            int deptno = cstmt.getInt(3);

            System.out.println("Employee Id : " + eid);
            System.out.println("Employee Name : " + name);
            System.out.println("Employee Deptno : " + deptno);


        } catch (SQLException s) {
            System.out.println("rocord not found for employee id ".toUpperCase() + eid);
        }
    }
}
