/*
=> What is a Transaction in JDBC?
-> In database terms, a transaction is a sequence of one or more SQL operations executed as a single logical unit of work.
-> Either all statements succeed (commit)
-> Or none of them take effect (rollback)

=> Transactions follow the ACID properties:
1. Atomicity – All or nothing
2. Consistency – Database stays valid
3. Isolation – One transaction doesn’t interfere with another
4. Durability – Changes persist after commit

=> Auto-commit in JDBC
-> By default, JDBC connections run in auto-commit mode, meaning every SQL statement is committed immediately after execution.
-> To control transactions manually, you disable auto-commit.

=> Transaction Workflow in JDBC
The general steps are:
1. Disable auto-commit
    con.setAutoCommit(false);
2. Execute SQL statements
    (INSERT, UPDATE, DELETE, etc.)
3. Commit changes if all good
    con.commit();
4. Rollback changes if something fails
    con.rollback();
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class transaction_handling {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            //set auto commit to false.
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            System.out.print("Enter name : ");
            String name = sc.nextLine();
            System.out.print("Enter class : ");
            String classname = sc.nextLine();
            System.out.print("Enter division : ");
            String division = sc.nextLine();
            stmt.executeUpdate(String.format("INSERT INTO students(name,class,division) VALUES('%s','%s','%s')", name.toUpperCase(), classname.toUpperCase(), division.toUpperCase()));
            System.out.println("Enter your choice : ");
            System.out.println("1. Commit the records");
            System.out.println("2. Rollback Records");
            System.out.println("3. Exit");
            int answer = sc.nextInt();

            switch (answer) {
                case 1:
                    con.commit();
                    System.out.println("Record inserted successfully");
                    break;
                case 2:
                    con.rollback();
                    System.out.println("Record rollbacked");
                    System.out.println("not inseted as per your choice");
                    break;
                case 3:
                    System.out.println("Exit the program");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter valid input!!!");
            }

            stmt.close();
            con.close();
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
