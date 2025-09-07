package projects.hotel_system;

import java.sql.*;
import java.util.Scanner;

public class hotel_management {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            while (true) {
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("----------------------------------------------------");
                System.out.println("1. Reserve a room");
                System.out.println("2. View reservation");
                System.out.println("3. Get a room number");
                System.out.println("4. Update reservation");
                System.out.println("5. Delete reservation");
                System.out.println("0. Save and exit from system");
                System.out.print("Choose an option : ");
                int answer = sc.nextInt();
                System.out.println();
                switch (answer) {
                    case 1:
                        ReserveRoom(sc, stmt);
                        break;
                    case 2:
                        ViewReservation(stmt);
                        break;
                    case 3:
                        GetRoomNumber(stmt, sc);
                        break;
                    case 4:
                        UpdateReservation(stmt, sc);
                        break;
                    case 5:
                        DeleteReservation(stmt, sc);
                        break;
                    case 0:
                        SaveExit(con, stmt, sc);
                    default:
                        System.out.println("Invalid input ");
                        System.out.println("Can't perform any operation");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void ReserveRoom(Scanner sc, Statement stmt) {
        System.out.print("Enter Guest name : ");
        String guest_name = sc.next();
        System.out.print("Enter Room no : ");
        int room_no = sc.nextInt();
        System.out.print("Enter Contact no : ");
        String contact_no = sc.next();
        try {
            String sql = String.format("INSERT INTO hotel_db (guest_name,room_number,contact_number) VALUES('%s',%d,'%s')", guest_name, room_no, contact_no);
            int affectedRows = stmt.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Successfully Reserved a Room\n");
            } else {
                System.out.println("Reservation Failed!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void ViewReservation(Statement stmt) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM hotel_db");
            System.out.println("*---------------------------------------------------------------------------------------------------------*");
            System.out.println("|   Reservation ID   |   Guest Name   |   Room Number   |   Contact Number   |   Reservation Name   |");
            System.out.println("*---------------------------------------------------------------------------------------------------------*");
            while (rs.next()) {
                System.out.println("           " + rs.getInt("reservation_id") + "               " + rs.getString("guest_name") + "            " + rs.getInt("room_number") + "            " + rs.getString("contact_number") + "           " + rs.getTimestamp("reservation_date"));
            }
            System.out.println("*---------------------------------------------------------------------------------------------------------*");
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    private static void GetRoomNumber(Statement stmt, Scanner sc) {
        System.out.print("Enter a Reservation Id : ");
        int reservation_id = sc.nextInt();
        System.out.print("Enter Guest Name : ");
        String guest_name = sc.next();
        try {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM hotel_db where reservation_id = '%d' and guest_name = '%s'", reservation_id, guest_name));
            if (rs.next()) {
                System.out.println(rs.getString("guest_name") + " is staying at room no : " + rs.getInt("room_number"));
                System.out.println("Thank you for using our services\n");
            } else {
                System.out.println("No reservation found for given id and guest name!\n");
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    private static void UpdateReservation(Statement stmt, Scanner sc) {
        System.out.print("Enter reservation id : ");
        int reservation_id = sc.nextInt();
        if (!reservationExist(stmt, reservation_id)) {
            System.out.println("Reservation not found for given id!");
            return;
        }
        try {
            while (true) {
                System.out.println("1. update Guest Name");
                System.out.println("2. Update Room No");
                System.out.println("3. Update Contact No");
                System.out.println("4. Exit");
                System.out.print("Enter Your Choice : ");
                int answer = sc.nextInt();
                switch (answer) {
                    case 1:
                        System.out.print("Enter New Guest Name : ");
                        String guestName = sc.next();
                        int affectedRows1 = stmt.executeUpdate(String.format("UPDATE hotel_db SET guest_name = '%s' WHERE reservation_id = %d", guestName, reservation_id));
                        if (affectedRows1 > 0) {
                            System.out.println("Guest name updated successfully");
                        } else {
                            System.out.println("Can't update guest name successfully!!!");
                        }
                        break;
                    case 2:
                        System.out.print("Enter New Room No : ");
                        int roomNo = sc.nextInt();
                        int affectedRows2 = stmt.executeUpdate(String.format("UPDATE hotel_db SET room_number = %d WHERE reservation_id = %d", roomNo, reservation_id));
                        if (affectedRows2 > 0) {
                            System.out.println("Room no updated successfully");
                        } else {
                            System.out.println("Can't update Room no successfully!!!");
                        }
                        break;
                    case 3:
                        System.out.print("Enter New Contact No : ");
                        int contactNo = sc.nextInt();
                        int affectedRows3 = stmt.executeUpdate(String.format("UPDATE hotel_db SET contact_number = %d WHERE reservation_id = %d", contactNo, reservation_id));
                        if (affectedRows3 > 0) {
                            System.out.println("Contact no updated successfully");
                        } else {
                            System.out.println("Can't update Contact no successfully!!!");
                        }
                        break;
                    case 4:
                        System.out.println("All Updatation Done");
                        return;
                    default:
                        System.out.println("Invalid input");
                        System.out.println("Can't perform any operation");
                }
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    private static void DeleteReservation(Statement stmt, Scanner sc) {
        System.out.print("Enter reservation id : ");
        int reservation_id = sc.nextInt();
        if (!reservationExist(stmt, reservation_id)) {
            System.out.println("Reservation not found for given id!");
            return;
        }
        try {
            int affectedRows = stmt.executeUpdate(String.format("DELETE FROM hotel_db WHERE reservation_id = %d", reservation_id));
            if (affectedRows > 0) {
                System.out.println("Reservation Deleted Successfully\n");
            } else {
                System.out.println("Reservation Delete process failed!!!\n");
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }

    }

    private static boolean reservationExist(Statement stmt, int reservation_id) {
        String sql = "SELECT reservation_id FROM hotel_db WHERE reservation_id = '" + reservation_id + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException s) {
            System.out.println(s.getMessage());
            return false;
        }
    }

    private static void SaveExit(Connection con, Statement stmt, Scanner sc) {
        try {
            sc.close();
            stmt.close();
            con.close();
            int s = 5;
            System.out.print("All data saved and exiting the System");
            for(int i = 1; i<=5; i++){
                System.out.print(".");
                Thread.sleep(300);
            }
            System.out.println("\nThank you for using our System👌");
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}