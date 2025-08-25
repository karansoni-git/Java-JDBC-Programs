import java.sql.*;
import java.util.Scanner;

public class insert_and_display_records {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            System.out.print("Do you want to insert a record(Y/N) : ");
            String answer = sc.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                PreparedStatement psmt = con.prepareStatement("insert into details values(?,?,?,?,?,?)");
                System.out.println("Insert a details : ");

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

                psmt.setInt(1, id);
                psmt.setString(2, name);
                psmt.setString(3, designation);
                psmt.setInt(4, salary);
                psmt.setString(5, city);
                psmt.setString(6, department);

                int rows = psmt.executeUpdate();

                System.out.println(rows + " row(s) inserted successfully🚀" );
                psmt.close();
            } else {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from details");

                while (rs.next()) {

                    System.out.println("id : " + rs.getInt("id"));
                    System.out.println("name : " + rs.getString("name"));
                    System.out.println("designation : " + rs.getString("designation"));
                    System.out.println("salary : " + rs.getString("salary"));
                    System.out.println("city : " + rs.getString("city"));
                    System.out.println("department : " + rs.getString("department"));
                    System.out.println("-------------------------\n");

                }
                rs.close();
                stmt.close();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
