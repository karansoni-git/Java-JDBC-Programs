import java.sql.*;

public class callable_stmt_default {
    private static final String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    private static final String user = "System";
    private static final String password = "kp1612";

    public static void main(String[] args) {

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            CallableStatement cstmt = con.prepareCall("{call insert_record()}");
            cstmt.execute();
            System.out.println("record inserted successfully");
            ResultSet rs = stmt.executeQuery("SELECT * FROM cemp");
            int record_no = 0;
            while(rs.next()){
                record_no ++;
                System.out.println("Employee ID : " + rs.getInt("eid"));
                System.out.println("Employee Name : " + rs.getString("ename"));
                System.out.println("Department No : " + rs.getInt("deptno"));
                System.out.println("------------------------------");
            }
            System.out.println(record_no + " Records fetched");
            rs.close();
            cstmt.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
