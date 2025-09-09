package projects.banking_system;

import oracle.jdbc.proxy.annotation.Pre;

import java.lang.reflect.GenericArrayType;
import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private final Connection con;
    private final Scanner sc;

    public Accounts(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public long OpenAccount(String email) {
        if (!AccountExists(email)) {
            System.out.print("Enter Full Name : ");
            String full_name = sc.next();
            System.out.print("Enter Initial Amount : ");
            float initial_amount = sc.nextFloat();
            System.out.print("Enter Security pin : ");
            String security_pin = sc.next();
            try {
                long account_number = GenerateAccountNumber();
                PreparedStatement psmt = con.prepareStatement("INSERT INTO accounts(account_number,full_name,email,balance,security_pin) VALUES (?,?,?,?,?)");
                psmt.setLong(1, account_number);
                psmt.setString(2, full_name);
                psmt.setString(3, email);
                psmt.setDouble(4, initial_amount);
                psmt.setString(5, security_pin);
                int affectedRows = psmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("New Account Created successfully");
                    return account_number;
                } else {
                    throw new RuntimeException("Account Creation Failed!!!");
                }
            } catch (SQLException s) {
                System.out.println(s.getMessage());
            }
        }
        throw new RuntimeException("Account Already Exists");
    }

    public long GenerateAccountNumber() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1");
            if (rs.next()) {
                long account_number = rs.getLong("account_number");
                return account_number + 1;
            } else {
                return 1000100;
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        return 1000100;
    }

    public boolean AccountExists(String email) {
        try {
            PreparedStatement psmt = con.prepareStatement("SELECT account_number FROM accounts WHERE email = ?");
            psmt.setString(1, email);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        return false;
    }

    public long GetAccountNumber(String email) {
        try {
            PreparedStatement psmt = con.prepareStatement("SELECT account_number FROM accounts WHERE email = ?");
            psmt.setString(1, email);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("account_number");
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        throw new RuntimeException("Does Not Have Account Number For This Email!!!");
    }
}
