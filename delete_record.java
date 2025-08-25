import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class delete_record {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter ID to delete that records : ");
        int id = sc.nextInt();

        try{
            Connection con = DriverManager.getConnection(url,user,password);
            PreparedStatement psmt = con.prepareStatement("delete from details where id = ?");
            psmt.setInt(1,id);
            int row = psmt.executeUpdate();

            System.out.println(row + " record deleted successfully!");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
