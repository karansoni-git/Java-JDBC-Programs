package projects.hospital_system;

import javax.management.relation.RelationSupport;
import java.beans.PropertyEditorSupport;
import java.security.PrivateKey;
import java.sql.*;
import java.util.Scanner;

public class hospitalapp {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            patient p = new patient(con, sc);
            doctor d = new doctor(con, sc);
            while (true) {
                System.out.println("----> HOSPITAL MANAGEMENT SYSTEM <----");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. Check Patient");
                System.out.println("4. View Doctors");
                System.out.println("5. Check Doctor");
                System.out.println("6. Book New Appointment");
                System.out.println("7. Check Appointments");
                System.out.println("8. Exit");
                System.out.print("Enter Your Choice : ");
                int answer = sc.nextInt();
                switch (answer) {
                    case 1:
                        p.AddPatient();
                        break;
                    case 2:
                        p.ViewPatients();
                        break;
                    case 3:
                        System.out.print("Enter Patient Id : ");
                        int patient_id = sc.nextInt();
                        System.out.print("Enter Patient Name : ");
                        String patient_name = sc.next();
                        p.CheckPatient(patient_id, patient_name);
                        break;
                    case 4:
                        d.ViewDoctors();
                        break;
                    case 5:
                        System.out.print("Enter Doctor Id : ");
                        int doctor_id = sc.nextInt();
                        System.out.print("Enter Doctor Name : ");
                        String doctor_name = sc.next().toUpperCase();
                        d.CheckDoctor(doctor_id, doctor_name);
                        break;
                    case 6:
                        BookAppointment(con, sc, p, d);
                        break;
                    case 7:
                        CheckAppointments(con);
                        break;
                    case 8:
                        System.out.println("THANK YOU FOR USING OUR SERVICE");
                        System.out.print("EXITING SYSTEM");
                        for (int i = 1; i <= 5; i++) {
                            System.out.print(".");
                            Thread.sleep(400);
                        }
                        return;
                    default:
                        System.out.println("Enter Valid Choice!");
                }
            }
        } catch (Exception s) {
            System.out.println(s.getMessage());
        }
    }

    public static void BookAppointment(Connection con, Scanner sc, patient p, doctor d) throws InterruptedException {
        System.out.print("Enter Patient Id : ");
        int patient_id = sc.nextInt();
        System.out.print("Enter Patient Name : ");
        String patient_name = sc.next();
        System.out.print("Enter Doctor Id : ");
        int doctor_id = sc.nextInt();
        System.out.print("Enter Doctor Name : ");
        String doctor_name = sc.next();
        if (p.CheckPatient(patient_id, patient_name) && d.CheckDoctor(doctor_id, doctor_name)) {
            System.out.print("Enter Appointment Date(YYYY-MM-DD) : ");
            String appointment_date = sc.next();
            if (CheckDate(doctor_id, appointment_date, con)) {
                try {
                    PreparedStatement psmt = con.prepareStatement("INSERT INTO appointments (patient_id,doctor_id,appointment_date) VALUES(?,?,?)");
                    psmt.setInt(1, patient_id);
                    psmt.setInt(2, doctor_id);
                    psmt.setString(3, appointment_date);
                    int affectedRows = psmt.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Appointment Booked successfully");
                    } else {
                        System.out.println("Failed To Book Appointment");
                    }
                } catch (SQLException s) {
                    System.out.println(s.getMessage());
                }
            } else {
                System.out.println("This Date Already Booked For Other Patient");
            }
        } else {
            System.out.println("You Will Be Directed To Add Patient Section");
            Thread.sleep(1500);
            p.AddPatient();
            System.out.println("You Will Be Directed To Appointment Section");
            Thread.sleep(1500);
            BookAppointment(con, sc, p, d);
//            System.out.println("Register First And Then Try To Book The Appointment");
//            System.out.println("Patient or Doctor Not Available!!!\n");
        }
    }

    public static boolean CheckDate(int doctor_id, String appointment_date, Connection con) {
        try {
            PreparedStatement psmt = con.prepareStatement("SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?");
            psmt.setInt(1, doctor_id);
            psmt.setString(2, appointment_date);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
        return false;
    }

    public static void CheckAppointments(Connection con) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");
            System.out.println("+----------------+------------+-----------+----------------------+");
            System.out.println("| APPOINTMENT ID | PATIENT ID | DOCTOR ID |   APPOINTMENT DATE   |");
            System.out.println("+----------------+------------+-----------+----------------------+");
            while (rs.next()) {
                System.out.printf("|%-16s|%-12s|%-11s|%-22s|\n", rs.getInt("id"), rs.getInt("patient_id"), rs.getInt("doctor_id"), rs.getString("appointment_date"));
            }
            System.out.println("+----------------+------------+-----------+----------------------+");
        } catch (SQLException s) {
            System.out.println();
        }
    }
}
