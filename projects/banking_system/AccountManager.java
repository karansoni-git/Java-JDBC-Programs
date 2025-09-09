package projects.banking_system;

import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection con;
    private Scanner sc;

    public AccountManager(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void CreditMoney(long account_number) throws SQLException {
        System.out.print("Enter Amount : ");
        double amount = sc.nextDouble();
        System.out.print("Enter Security pin : ");
        String security_pin = sc.next();
        try {
            con.setAutoCommit(false);
            if (account_number != 0) {
                PreparedStatement psmt = con.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
                psmt.setLong(1, account_number);
                psmt.setString(2, security_pin);
                ResultSet rs = psmt.executeQuery();
                if (rs.next()) {
                    PreparedStatement credit_query = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
                    credit_query.setDouble(1, amount);
                    credit_query.setLong(2, account_number);
                    int affectedRows = credit_query.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Rs:-" + amount + " Credit Successfully");
                        con.commit();
                        con.setAutoCommit(true);
                        return;
                    } else {
                        System.out.println("Transaction Failed!!!");
                        con.rollback();
                        con.setAutoCommit(true);
                    }
                } else {
                    System.out.println("Invalid Security Pin!!!");
                }
            } else {
                System.out.println("Invalid Account Number!!!");
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        con.setAutoCommit(true);
    }

    public void DebitMoney(long account_number) throws SQLException {
        System.out.print("Enter Amount : ");
        double amount = sc.nextDouble();
        System.out.print("Enter Security pin : ");
        String security_pin = sc.next();
        try {
            con.setAutoCommit(false);
            if (account_number != 0) {
                PreparedStatement psmt = con.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
                psmt.setLong(1, account_number);
                psmt.setString(2, security_pin);
                ResultSet rs = psmt.executeQuery();
                if (rs.next()) {
                    double current_balance = rs.getDouble("balance");
                    if (amount <= current_balance) {
                        PreparedStatement debit_query = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
                        debit_query.setDouble(1, amount);
                        debit_query.setLong(2, account_number);
                        int affectedRows = debit_query.executeUpdate();
                        if (affectedRows > 0) {
                            System.out.println("Rs:-" + amount + " Debited Successfully");
                            con.commit();
                            con.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction Failed!!!");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient Balance In Account");
                        System.out.println("Transaction Failed Due To Low Balance");
                    }
                } else {
                    System.out.println("Invalid Security Pin!!!");
                }
            } else {
                System.out.println("Invalid Account Number!!!");
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        con.setAutoCommit(true);
    }

    public void CheckBalance(long account_number) {
        System.out.print("Enter Security pin : ");
        String security_pin = sc.next();
        try {
            PreparedStatement psmt = con.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
            psmt.setLong(1, account_number);
            psmt.setString(2, security_pin);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                System.out.println("Balance : " + rs.getDouble("balance"));
            } else {
                System.out.println("Invalid Pin!!!");
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public void TransferMoney(long sender_account_number) throws SQLException {
        System.out.print("Enter Receiver Account Number : ");
        long receiver_account_number = sc.nextLong();
        System.out.print("Enter Amount : ");
        double amount = sc.nextDouble();
        System.out.print("Enter Security Pin : ");
        String security_pin = sc.next();
        try {
            con.setAutoCommit(false);
            if (sender_account_number != 0 && receiver_account_number != 0) {
                PreparedStatement transfer_Query = con.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
                transfer_Query.setLong(1, sender_account_number);
                transfer_Query.setString(2, security_pin);
                ResultSet rs = transfer_Query.executeQuery();
                if (rs.next()) {
                    double current_balance = rs.getDouble("balance");
                    if (current_balance >= amount) {
                        PreparedStatement debit_query = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_number = ?");
                        PreparedStatement credit_query = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_number = ?");
                        debit_query.setDouble(1, amount);
                        debit_query.setLong(2, sender_account_number);
                        credit_query.setDouble(1, amount);
                        credit_query.setLong(2, receiver_account_number);
                        int affectedRows1 = debit_query.executeUpdate();
                        int affectedRows2 = credit_query.executeUpdate();
                        if (affectedRows1 > 0 && affectedRows2 > 0) {
                            System.out.println("Transaction Successfully Done");
                            System.out.println("Rs:- " + amount + " Transferred Successfully");
                            con.commit();
                            con.setAutoCommit(true);
                        } else {
                            System.out.println("Transaction Failed!!!");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient Balance In Account");
                        System.out.println("Transaction Failed Due To Low Balance");
                    }
                } else {
                    System.out.println("Invalid Pin!!!");
                }
            } else {
                System.out.println("Invalid Account Number!!!");
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        con.setAutoCommit(true);
    }
}
