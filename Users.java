package projects.banking_system;

import oracle.jdbc.proxy.annotation.Pre;

import java.sql.*;
import java.util.Scanner;

public class Users {
    private Connection con;
    private Scanner sc;

    public Users(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void Register() {
        System.out.print("Enter User Name : ");
        String name = sc.next();
        System.out.print("Enter User Email : ");
        String email = sc.next();
        System.out.print("Enter User Password : ");
        String password = sc.next();
        if (UserExists(email)) {
            System.out.println("This email is already registered");
            return;
        }
        try {
            PreparedStatement psmt = con.prepareStatement("INSERT INTO user(full_name,email,password) VALUES (?,?,?)");
            psmt.setString(1, name);
            psmt.setString(2, email);
            psmt.setString(3, password);
            int affectedRows = psmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Your Registration Successfully Done");
            } else {
                System.out.println("Failed To Register New User!!!");
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public String Login() {
        System.out.print("Enter Email : ");
        String email = sc.next();
        System.out.print("Enter Password : ");
        String password = sc.next();
        try {
            PreparedStatement psmt = con.prepareStatement("SELECT * FROM user WHERE email = ? and password = ?");
            psmt.setString(1, email);
            psmt.setString(2, password);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return email;
            } else {
                return null;
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        return null;
    }


    public boolean UserExists(String email) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM user WHERE email = '%s'", email));
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
}
