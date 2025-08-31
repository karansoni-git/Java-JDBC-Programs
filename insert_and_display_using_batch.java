import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class insert_and_display_using_batch {

    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            while (true) {

                System.out.print("Enter Name : ");
                String name = sc.nextLine().toUpperCase();

                System.out.print("Enter Class : ");
                String className = sc.nextLine().toUpperCase();

                System.out.print("Enter Division : ");
                String division = sc.nextLine().toUpperCase();

                String sql = String.format("INSERT INTO students(name,class,division) VALUES('%s','%s','%s')", name, className, division);
                stmt.addBatch(sql);

                System.out.print("\nDo you want to add more records(Y/N) : ");
                String answer = sc.nextLine();

                if (answer.equalsIgnoreCase("n")) {
                    break;
                }
            }

            stmt.executeBatch();

            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            System.out.println("\nAll Records : ");
            while (rs.next()) {
                System.out.println("RollNo : " + rs.getInt("rollno"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("Class : " + rs.getString("class"));
                System.out.println("Division : " + rs.getString("division"));
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
