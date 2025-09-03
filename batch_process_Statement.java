/*
=> What is Batch Processing in JDBC?
    -> In JDBC(Java Database Connectivity),batch processing means sending multiple SQL statements to the database in a single request rather than executing each
       statement individually.

=> some benefits :
    -> Performance boost — reduces network calls between Java app and DB.
    -> Better resource utilization — DB can optimize execution internally.
    -> Cleaner code — loop logic stays in Java, execution happens in bulk.

=> How Batch Processing Works in JDBC :
    1. Create a Statement or PreparedStatement
    2. Add SQL commands to the batch using addBatch()
    3. Execute the batch using executeBatch()
    4. (Optional) Clear batch with clearBatch() (best practice)


| Method                 | Description                                                                     |
| ---------------------- | ------------------------------------------------------------------------------- |
| `addBatch(String sql)` | For Statement object — adds a SQL command to batch                              |
| `addBatch()`           | For PreparedStatement — adds the current parameterized statement to batch        |
| `executeBatch()`       | Executes all commands in the batch; returns an `int[]` with affected row counts |
| `clearBatch()`         | Removes all commands from batch without executing                               |

=> Note : addBatch() method only allow to perform DML commands like INSERT , UPDATE , DELETE not allow to perform DQL or other commands because it does not return any result.
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class batch_process_Statement {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            while (true) {

                System.out.print("Enter name : ");
                String name = sc.nextLine();

                System.out.print("Enter class : ");
                String className = sc.nextLine();

                System.out.print("Enter division : ");
                String division = sc.nextLine();

                System.out.print("Do you want to add more records(Y/N) : ");
                String answer = sc.nextLine();

                String sql = String.format("INSERT INTO students(name,class,division) VALUES('%s','%s','%s')", name.toUpperCase(), className.toUpperCase(), division.toUpperCase());
                stmt.addBatch(sql);

                if (answer.equalsIgnoreCase("n")) {
                    break;
                }
            }

            //this excuteBatch() method return a integer type array which consists of value 0 and 1.
            //1 means statement inserted successfully and 0 means not inserted successfully
            int[] arr = stmt.executeBatch();
            int insertedSuccessfully = 0;
            for (int i = 1; i <= arr.length; i++) {
                //inform user which statement is not inserted successfully when array value return 0.
                if (i == 0) {
                    System.out.println("record no " + i + " is not inserted successfully!");
                } else {
                    insertedSuccessfully++;
                }
            }
            System.out.println(insertedSuccessfully + " records inserted successfully");
            //
            stmt.clearBatch();
            stmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
