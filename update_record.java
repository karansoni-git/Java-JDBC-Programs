import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class update_record {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the id : ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("enter new name : ");
        String newname = sc.nextLine();

        System.out.print("Enter new salary : ");
        int newsalary = sc.nextInt();

        try{
            Connection con = DriverManager.getConnection(url,user,password);
            PreparedStatement psmt = con.prepareStatement("update details set name = ? , salary = ? where id = ?");
            psmt.setString(1,newname);
            psmt.setInt(2,newsalary);
            psmt.setInt(3,id);

            int rows = psmt.executeUpdate();
            System.out.println(rows + " row(s) updated");

            psmt.close();
            con.close();
            sc.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
