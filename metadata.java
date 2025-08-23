import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class metadata {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        try{
            Connection con = DriverManager.getConnection(url,user,password);
            DatabaseMetaData dbmt = con.getMetaData();
            System.out.println("Driver name : " + dbmt.getDriverName());
            System.out.println("Driver version : " + dbmt.getDriverVersion());
            System.out.println("Database product name : " + dbmt.getDatabaseProductName());
            System.out.println("Database product version : " + dbmt.getDatabaseProductVersion());
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
}
