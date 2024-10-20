import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList <User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    
    public static ArrayList<User> loadPreregisteredUsers(String filename) throws IOException
    {
        ArrayList <User> users = new ArrayList<User>();

        Scanner in = new Scanner (new File (filename));

        while(in.hasNextLine()){
            String name =in.nextLine();
            String address = in.nextLine();
            double wallet = Double.parseDouble(in.nextLine());
            users.add(new User(generateUserAccountId(users),name,address, wallet));
        }
       
        
        in.close();
        return users;
        
    }

 
    public static ArrayList<Driver> loadPreregisteredDrivers( String filename) throws IOException
    {
        ArrayList <Driver> drivers = new ArrayList<Driver>();

        Scanner in = new Scanner (new File (filename));

        while(in.hasNextLine()){
            String name = in.nextLine();
            String carModel = in.nextLine();
            String licensePlate = in.nextLine();
            String address = in.nextLine();

            drivers.add(new Driver (generateDriverId(drivers),name, carModel, licensePlate, address));
        }

        in.close();

        return drivers;
    
    }
}

