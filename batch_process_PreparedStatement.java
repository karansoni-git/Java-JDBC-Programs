import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class batch_process_PreparedStatement {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement psmt = con.prepareStatement("INSERT INTO students(name,class,division) VALUES(?,?,?)");
            while (true) {
                System.out.print("Enter Name : ");
                String name = sc.nextLine();

                System.out.print("Enter Class : ");
                String className = sc.nextLine();

                System.out.print("Enter Division : ");
                String division = sc.nextLine();

                System.out.print("Do you want to add more record(Y/N) : ");
                String answer = sc.nextLine();

                psmt.setString(1,name.toUpperCase());
                psmt.setString(2,className.toUpperCase());
                psmt.setString(3,division.toUpperCase());
                psmt.addBatch();

                if (answer.equalsIgnoreCase("n")) {
                    break;
                }
            }

            int[] arr = psmt.executeBatch();
            int insertedSuccessfully = 0;
            for (int i = 1; i <= arr.length; i++) {
                if (i == 0) {
                    System.out.println("record no " + i + " is not inserted successfully!");
                } else {
                    insertedSuccessfully++;
                }
            }
            System.out.println(insertedSuccessfully + " records inserted successfully");
            psmt.clearBatch();
            psmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
