import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class insert_record {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Inserting a details : ");

        System.out.print("Enter a Id : ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter a name : ");
        String name = sc.nextLine();

        System.out.print("Enter a designation : ");
        String designation = sc.nextLine();

        System.out.print("Enter a salary : ");
        int salary = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter a city : ");
        String city = sc.nextLine();

        System.out.print("Enter a department : ");
        String department = sc.nextLine();

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement psmt = con.prepareStatement("insert into details values(?,?,?,?,?,?)");
            psmt.setInt(1, id);
            psmt.setString(2, name);
            psmt.setString(3, designation);
            psmt.setInt(4, salary);
            psmt.setString(5, city);
            psmt.setString(6, department);
            int rows = psmt.executeUpdate();

            if (rows > 0) {
                System.out.println("record inserted successful");
                System.out.println(rows + "row(s) inserted" );
            } else {
                System.out.println("can't insert record");
            }

            psmt.close();
            con.close();
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
