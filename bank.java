import java.sql.*;
import java.util.Scanner;

public class bank {

    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    private static boolean isSufficient(Connection con, int account_number, double amount) {
        try {
            PreparedStatement psmt = con.prepareStatement("SELECT balance FROM bank WHERE account_number = ?");
            psmt.setInt(1, account_number);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                double current_balance = rs.getDouble("balance");
                if (current_balance < amount) {
                    return false;
                } else {
                    return true;
                }
            }
            rs.close();
            psmt.close();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            PreparedStatement debit_query = con.prepareStatement("UPDATE bank SET balance = balance - ? where account_number = ?");
            PreparedStatement credit_query = con.prepareStatement("UPDATE bank SET balance = balance + ? where account_number = ?");

            System.out.print("Enter Amount that you want to transact : ");
            double amount = sc.nextDouble();

            debit_query.setDouble(1, amount);
            debit_query.setInt(2, 1001);

            credit_query.setDouble(1, amount);
            credit_query.setInt(2, 1002);

            ResultSet data = stmt.executeQuery("SELECT account_holdername,balance FROM bank where account_number = 1001");
            String name = null;
            float balance = 0;
            if(data.next()){
            name = data.getString("account_holdername");
            balance = data.getFloat("balance");
            }

            ResultSet beforeTransaction = stmt.executeQuery("SELECT * FROM bank");

            if (isSufficient(con, 1001, amount)) {
                debit_query.addBatch();
                credit_query.addBatch();
                debit_query.executeBatch();
                credit_query.executeBatch();
                System.out.println("Transaction successfully done✅");
            } else {
                System.out.println("Insufficient Balance in " + name + "'s Account!!!");
                System.out.println("Current Balance of Account : " + balance);
                System.exit(0);
            }

            System.out.println("\nBefore Transaction : ");
            while (beforeTransaction.next()) {
                System.out.println("Account holder : " + beforeTransaction.getString("account_holdername") + " And balance : " + beforeTransaction.getDouble("balance"));
            }

            ResultSet afterTransaction = stmt.executeQuery("SELECT * FROM bank");
            System.out.println("\nAfter Transaction : ");
            while (afterTransaction.next()) {
                System.out.println("Account holder : " + afterTransaction.getString("account_holdername") + " And balance : " + afterTransaction.getDouble("balance"));
            }
            afterTransaction.close();
            beforeTransaction.close();
            data.close();
            credit_query.close();
            debit_query.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

