package projects.hospital_system;

import java.sql.*;
import java.util.Scanner;

public class patient {
    private Connection con;
    private Scanner sc;

    public patient(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void AddPatient() {
        System.out.print("Enter Patient Name : ");
        String name = sc.next().toUpperCase();
        System.out.print("Enter Patient Age : ");
        int age = sc.nextInt();
        System.out.print("Enter Patient Gender(male/female) : ");
        String gender = sc.next().toUpperCase();
        if (IsPatientExists(name, age, gender)) {
            System.out.println("Patient Is Already Registered");
            return;
        }
        try {
            PreparedStatement psmt = con.prepareStatement("INSERT INTO patients (name,age,gender) VALUES (?,?,?)");
            psmt.setString(1, name);
            psmt.setInt(2, age);
            psmt.setString(3, gender);
            int affectedRows = psmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully");
            } else {
                System.out.println("Patient Not Added!!!");
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        System.out.println();
    }

    public boolean IsPatientExists(String name, int age, String gender) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM patients WHERE name = '%s' and age = %d and gender = '%s'", name, age, gender));
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

    public void ViewPatients() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");
            System.out.println("All Patients List : ");
            System.out.println("+------------+--------------------+-------------+----------------+");
            System.out.println("| PATIENT ID |    PATIENT NAME    | PATIENT AGE | PATIENT GENDER |");
            System.out.println("+------------+--------------------+-------------+----------------+");
            while (rs.next()) {
                System.out.printf("|%-12s|%-20s|%-13s|%-16s|\n", rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("gender"));
            }
            System.out.println("+------------+--------------------+-------------+----------------+\n");
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    public boolean CheckPatient(int id, String name) {
        try {
            PreparedStatement psmt = con.prepareStatement("SELECT * FROM patients WHERE id = ? AND name = ?");
            psmt.setInt(1, id);
            psmt.setString(2, name);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                System.out.println("Patient Is Available In The Record");
                System.out.println();
                return true;
            } else {
                System.out.println("Patient Is Not Available In Record!!!");
                System.out.println();
                return false;
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        return false;
    }
}
