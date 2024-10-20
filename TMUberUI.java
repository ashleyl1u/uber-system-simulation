import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TMUberUI
{
  public static void main(String[] args)
  {
    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    while (scanner.hasNextLine()){
      try{
        String action = scanner.nextLine();

        if (action == null || action.equals("")) 
        {
          System.out.print("\n>");
          continue;
        }
        // Quit the App
        else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
          return;
        // Print all the registered drivers
        else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
        {
          tmuber.listAllDrivers(); 
        }
        // Print all the registered users
        else if (action.equalsIgnoreCase("USERS"))  // List all users
        {
          tmuber.sortById(); //prints out users but in order of id since hashmaps has a random order
        }
        // Print all current ride requests or delivery requests
        else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
        {
          tmuber.listAllServiceRequests(); 
        }
        // Register a new driver
        else if (action.equalsIgnoreCase("REGDRIVER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String carModel = "";
          System.out.print("Car Model: ");
          if (scanner.hasNextLine())
          {
            carModel = scanner.nextLine();
          }
          String license = "";
          System.out.print("Car License: ");
          if (scanner.hasNextLine())
          {
            license = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if(scanner.hasNextLine()){
            address = scanner.nextLine();
          }

          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-15s", name, carModel, license, address);
        }

        // Register a new user
        else if (action.equalsIgnoreCase("REGUSER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          double wallet = 0.0;
          System.out.print("Wallet: ");
          if (scanner.hasNextDouble())
          {
            wallet = scanner.nextDouble();
            scanner.nextLine(); 
          }
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);

        }

        // Request a ride
        else if (action.equalsIgnoreCase("REQRIDE")) 
        {
          
          String accountId="";
          System.out.print("User Account Id: ");
          if(scanner.hasNextLine()){
            accountId = scanner.nextLine();
          }

          String from="";
          System.out.print("From Address: ");
          if(scanner.hasNextLine()){
            from = scanner.nextLine();
          }

          String to="";
          System.out.print("To Address: ");
          if(scanner.hasNextLine()){
            to = scanner.nextLine();
    
          }
          
          tmuber.requestRide(accountId,from, to);
          System.out.println();
          System.out.printf("RIDE for: %-15s From: %-15s To: %-15s", tmuber.getUser(accountId).getName(), from, to);
            
          
        }
        
        // Request a food delivery
        else if (action.equalsIgnoreCase("REQDLVY")) 
        {

          String accountId="";
          System.out.print("User Account Id: ");
          if(scanner.hasNextLine()){
            accountId = scanner.nextLine();
          }

          String from="";
          System.out.print("From Address: ");
          if(scanner.hasNextLine()){
            from = scanner.nextLine();
          }

          String to="";
          System.out.print("To Address: ");
          if(scanner.hasNextLine()){
            to = scanner.nextLine();
    
          }

          String restaurant = "";   
          System.out.print("Restaurant: ");
          if(scanner.hasNextLine()){
            restaurant = scanner.nextLine();
          }

          String foodOrderId="";
          System.out.print("Food Order #: ");
          if(scanner.hasNextLine()){
            foodOrderId=scanner.nextLine();
          }
          
          tmuber.requestDelivery(accountId,from, to, restaurant, foodOrderId );
          System.out.println();
          System.out.printf("DELIVERY for: %-15s From: %-15s To: %-15s", tmuber.getUser(accountId).getName(), from, to);
          
        }

        // Sort users by name
        else if (action.equalsIgnoreCase("SORTBYNAME")) 
        {
          tmuber.sortByUserName();
        }

        // Sort users by number of ride they have had
        else if (action.equalsIgnoreCase("SORTBYWALLET")) 
        {
          tmuber.sortByWallet();
        }
        
        // Cancel a current service (ride or delivery) request
        else if (action.equalsIgnoreCase("CANCELREQ")) 
        {
          int zone = -1;
          System.out.print("Zone: ");
          if (scanner.hasNextInt())
          {
            zone = scanner.nextInt();
            scanner.nextLine();
          } 
          
          int request = -1;
          System.out.print("Request #: ");
          if (scanner.hasNextInt())
          {
            request = scanner.nextInt();
            scanner.nextLine(); 
          } 
          
          
        
          tmuber.cancelServiceRequest(request, zone);
           
          System.out.println("Service request #" + request + " cancelled");
        }
        
        // Drop-off the user or the food delivery to the destination address
        else if (action.equalsIgnoreCase("DROPOFF")) 
        {

          String driverId="";
        
          System.out.print("Driver Id: ");
          if (scanner.hasNextLine())
          {
            driverId = scanner.nextLine();
          }
          tmuber.dropOff(driverId);
          System.out.println("Driver "+driverId+" Dropping off");
        }
        
        // Get the Current Total Revenues
        else if (action.equalsIgnoreCase("REVENUES")) 
        {
          System.out.println("Total Revenue: " + tmuber.totalRevenue);
        }
        // Unit Test of Valid City Address 
        else if (action.equalsIgnoreCase("ADDR")) 
        {
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          System.out.print(address);
          if (CityMap.validAddress(address))
            System.out.println("\nValid Address"); 
          else
            System.out.println("\nBad Address"); 
        }
        // Unit Test of CityMap Distance Method
        else if (action.equalsIgnoreCase("DIST")) 
        {
          String from = "";
          System.out.print("From: ");
          if (scanner.hasNextLine())
          {
            from = scanner.nextLine();
          }
          String to = "";
          System.out.print("To: ");
          if (scanner.hasNextLine())
          {
            to = scanner.nextLine();
          }
          System.out.print("\nFrom: " + from + " To: " + to);
          System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
        }
        
        else if (action.equalsIgnoreCase("PICKUP")) 
        {

          String driverId="";
          System.out.print("Driver Id: ");
          if (scanner.hasNextLine())
          {
            driverId = scanner.nextLine();
          }

          tmuber.pickup(driverId);
          System.out.println("Driver "+driverId+" Picking Up in Zone "+tmuber.getDriver(driverId).getZone());
          
          
          
          
        }
        else if (action.equalsIgnoreCase("LOADUSERS")) 
        {
          String filename="";
          try{
          
            System.out.print("User File: ");
            if (scanner.hasNextLine())
            {
              filename = scanner.nextLine();
            }
            
            ArrayList <User> users = new ArrayList<User>();
            users = TMUberRegistered.loadPreregisteredUsers(filename);
            tmuber.setUsers(users);
            System.out.println("Users Loaded");
          }
          catch(FileNotFoundException e){
            System.out.println("Users File: "+ filename+" not found");
          }
          catch(IOException e1){
            return;
          }

        }
        else if (action.equalsIgnoreCase("LOADDRIVERS")) 
        {
          String filename="";
          try{
            System.out.print("Drivers File: ");
            if (scanner.hasNextLine())
            {
              filename = scanner.nextLine();
            }
            ArrayList <Driver> drivers = new ArrayList<Driver>();
            drivers =TMUberRegistered.loadPreregisteredDrivers(filename);
            tmuber.setDrivers(drivers);
            System.out.println("Drivers Loaded");
          }
          catch(FileNotFoundException e){
            System.out.println("Drivers File: "+ filename+ " not found");
          }
          catch(IOException e1){
            return;
          }



        }
        else if (action.equalsIgnoreCase("DRIVETO")) 
        {
          String driverId="";
          System.out.print("Drivers Id: ");
          if (scanner.hasNextLine())
          {
            driverId = scanner.nextLine();
          }

          String address="";
          System.out.print("Address: ");
          if(scanner.hasNextLine()){
            address = scanner.nextLine();
          }

          tmuber.driveTo(driverId, address);
          System.out.println("Driver "+driverId +" Now in Zone "+ tmuber.getDriver(driverId).getZone());
        }

        
        

        System.out.print("\n>");
      }
      catch(DriverNotFoundException e1){
        System.out.print(e1.getMessage()+"\n\n>");
      }
      catch(DriverExistsException e2){
        System.out.print(e2.getMessage()+"\n\n>");
      }
      catch(InvalidNameException e3){
        System.out.print(e3.getMessage()+"\n\n>");
      }
      catch(InvalidCarModelException e4){
        System.out.print(e4.getMessage()+"\n\n>");
      }
      catch(InvalidLicencePlateException e5){
        System.out.print(e5.getMessage() +"\n\n>");
      }
      catch(InvalidAddressException e6){
        System.out.print(e6.getMessage() +"\n\n>");
      }
      catch (UserNotFoundException e7){
        System.out.print(e7.getMessage()+"\n\n>");
      }
      catch(InsufficientDistanceException e8){
        System.out.print(e8.getMessage()+"\n\n>");
      }
      catch(InsufficientFundsException e9){
        System.out.print(e9.getMessage()+"\n\n>");
      }
      catch(UserServiceExistsException e10){
        System.out.print(e10.getMessage()+"\n\n>");
      }
      catch(UserExistsException e11){
        System.out.print(e11.getMessage()+"\n\n>");
      }
      catch(ZoneNotFoundException e12){
        System.out.print(e12.getMessage()+"\n\n>");
      }
      catch (ServiceNotFoundException e13){
        System.out.print(e13.getMessage()+"\n\n>");
      }
      catch (DriverUnavailableException e14){
        System.out.print(e14.getMessage()+"\n\n>");
      }
      catch (InvalidRestaurantException e15){
        System.out.print(e15.getMessage()+"\n\n>");
      }
      catch (InvalidFoodOrderIdException e16){
        System.out.print(e16.getMessage()+"\n\n>");
      }
    }

    scanner.close();
  }
}

