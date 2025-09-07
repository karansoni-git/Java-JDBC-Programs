package projects.hospital_system;

import java.sql.*;
import java.util.Scanner;

public class doctor {
    private Connection con;

    public doctor(Connection con, Scanner sc) {
        this.con = con;
    }

    public void ViewDoctors() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");
            System.out.println("All Doctors List : ");
            System.out.println("+-----------+-------------------+-----------------------+");
            System.out.println("| DOCTOR ID |    DOCTOR NAME    | DOCTOR SPECIALIZATION |");
            System.out.println("+-----------+-------------------+-----------------------+");
            while (rs.next()) {
                System.out.printf("|%-11s|%-19s|%-23s|\n",rs.getInt("id"),rs.getString("name"),rs.getString("specialization"));
            }
            System.out.println("+-----------+-------------------+-----------------------+\n");
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public boolean CheckDoctor(int id, String name) {
        try {
            PreparedStatement psmt = con.prepareStatement("SELECT * FROM doctors WHERE id = ? AND name = ?");
            psmt.setInt(1, id);
            psmt.setString(2, name);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                System.out.println("Doctor Is Available In The Record");
                System.out.println();
                return true;
            } else {
                System.out.println("Doctor Is Not Available In Record!!!");
                System.out.println();
                return false;
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        return false;
    }
}
