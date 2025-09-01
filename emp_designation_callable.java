import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.Scanner;

public class emp_designation_callable {
    private static final String url = "jdbc:oracle:thin:@//localhost:1521/xe";
    private static final String user = "System";
    private static final String password = "kp1612";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            Connection con = DriverManager.getConnection(url,user,password);
            System.out.print("Enter EID : ");
            int id = sc.nextInt();

            CallableStatement cstmt = con.prepareCall("{call emp_details(?,?)}");
            cstmt.setInt(1,id);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.execute();
            String name = cstmt.getString(2);
            System.out.println("EID No : " + id + " is " + name + "'s Id");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
