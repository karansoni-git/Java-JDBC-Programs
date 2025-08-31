import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLOutput;
import java.util.Scanner;

public class update_using_switch {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            System.out.print("Enter RollNo : ");
            int rollno = sc.nextInt();
            sc.nextLine();

            System.out.print("What do you want to update in Rollno " + rollno + " : ");
            String answer = sc.nextLine();

            switch (answer.toUpperCase()) {
                case "NAME":
                    PreparedStatement psmt1 = con.prepareStatement("update students set name = ? where rollno = ?");
                    System.out.print("Enter new Name : ");
                    String name = sc.nextLine();
                    psmt1.setString(1, name);
                    psmt1.setInt(2, rollno);
                    psmt1.executeUpdate();
                    psmt1.close();
                    System.out.println("Successfully updated");
                    break;

                case "CLASS":
                    PreparedStatement psmt2 = con.prepareStatement("update students set class = ? where rollno = ?");
                    System.out.print("Enter new Class : ");
                    String className = sc.nextLine();
                    psmt2.setString(1, className.toUpperCase());
                    psmt2.setInt(2, rollno);
                    psmt2.executeUpdate();
                    psmt2.close();
                    System.out.println("Successfully updated");
                    break;

                case "DIVISION":
                    PreparedStatement psmt3 = con.prepareStatement("update students set division = ? where rollno = ?");
                    System.out.print("Enter new Division : ");
                    String division = sc.nextLine();
                    psmt3.setString(1, division.toUpperCase());
                    psmt3.setInt(2, rollno);
                    psmt3.executeUpdate();
                    psmt3.close();
                    System.out.println("Successfully updated");
                    break;

                case "ROLLNO":
                    PreparedStatement psmt4 = con.prepareStatement("update students set rollno = ? where rollno = ?");
                    System.out.print("Enter new Rollno : ");
                    String newRollno = sc.nextLine();
                    psmt4.setString(1, newRollno);
                    psmt4.setInt(2, rollno);
                    psmt4.executeUpdate();
                    psmt4.close();
                    System.out.println("Successfully updated");
                    break;

                default:
                    System.out.println("Enter valid field name and value to update record!!❌");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
