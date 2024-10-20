import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;




public class TMUberSystemManager
{
  private  Map<String,User> users;
  private  ArrayList <User> userList;
  private  ArrayList<Driver> drivers;

  

  private Queue<TMUberService>[] requests;
  

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;

  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    users   = new HashMap <String,User>();
    drivers = new ArrayList<Driver>();
    requests = new Queue [4];
    for(int i = 0; i< requests.length ; i++){
      requests [i] = new LinkedList<TMUberService>();
    }
    
    userList = new ArrayList<User>(users.values());
    
    totalRevenue = 0;
  }

  public  ArrayList <User> getUserList(){
    userList = new ArrayList<User>(users.values());
    return userList;
  }


  public  ArrayList <Driver> getDriverList(){
    return drivers;
  }


 


  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    if(users.get(accountId) != null){
      return users.get(accountId);
    }
    else{
      return null;
    }
  }

  //returns driver object based on a given driver id; return null if not found 
  public Driver getDriver (String driverId){
    for(int i = 0; i<drivers.size(); i++){
      if(drivers.get(i).getId().equals(driverId)){
        return drivers.get(i);
      }
    }
    return null;
  }
  
  // Check for duplicate users
  private void userExists(User user)
  {
    boolean flag = false;
    for(String Id : users.keySet()){
      User u = users.get(Id);
      if(u.equals(user)){
        flag=true;
      }
    }

    if(flag == true){
      throw new UserExistsException("User Already Exists in System");
    }
 
  }
  
 // Check for duplicate driver
 private void driverExists(Driver driver){
  boolean flag = false;
  for(int i=0; i<drivers.size();i++){
    if(drivers.get(i).equals(driver)){
      flag=true;
    
    }
  }
  if(flag == true){
    throw new DriverExistsException("Driver Already Exists in System");
  }
 }
  

  // Given a user, check if user ride/delivery request already exists in service requests
  private void existingRequest(TMUberService req)
  {
    boolean flag = false;
    int zone = CityMap.getCityZone(req.getFrom());
    for(TMUberService element : requests[zone]){
      if(element.equals(req)){
        flag=true;
      }
    }
    if(flag == true){
      throw new UserServiceExistsException("User Already Has Ride Request");
    }
  }

  // Calculate the cost of a ride or a delivery based on distance 
  private double getDeliveryCost(int distance){
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance){
    return distance * RIDERATE;
  }

  

  // Print Information (printInfo()) about all registered users in the system (for arraylists)
  public void listAllUsers(){
    
    System.out.println();
    int count =1;
    for(int i =0; i<userList.size();i++){
      System.out.printf("%-2s. ", count);
      userList.get(i).printInfo();
      System.out.println();
      count++;
    }
  }

 

  // Print Information (printInfo()) about all registered drivers in the system 
  public void listAllDrivers()
  {
    
    System.out.println();
    for(int i =0; i<drivers.size();i++){
      int listNum=i+1;
      System.out.printf("%-2s. ", listNum);
      drivers.get(i).printInfo();
      System.out.println("\n");
      

    }
  }

  // Print Information (printInfo()) about all current service requests 
  public void listAllServiceRequests()
  {
    
    for(int i =0; i<requests.length ;i++){
      System.out.println();
      System.out.println("ZONE "+i);
      System.out.println("======");
      int listNum =1;
      for(TMUberService s: requests[i]){
        System.out.printf("%-2s. %-60s", listNum,"------------------------------------------------------------");
        s.printInfo();
        listNum++;
        System.out.println();
      }
      
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {

    //making an array list of users 
    ArrayList <User> u = new ArrayList <User>();
      for(String key : users.keySet()){
        u.add(users.get(key));
      }
    
    userExists(new User (TMUberRegistered.generateUserAccountId(u), name, address, wallet));
      

    if((name.equals(" ") || name.equals(""))){
      throw new InvalidNameException("Invalid User Name");
    }
    
    if(CityMap.validAddress(address) == false || address.equals(" ") || address.equals("")){
      throw new InvalidAddressException("Invalid User Address");
    }
    
    if(wallet <0 ){ 
      throw new InsufficientFundsException("Invalid Money in Wallet");
    }
    
    users.put(TMUberRegistered.generateUserAccountId(u),new User (TMUberRegistered.generateUserAccountId(u), name, address, wallet));
    
    
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    driverExists((new Driver (TMUberRegistered.generateDriverId(drivers),name, carModel, carLicencePlate, address)));
      
    if((name.equals(" ") || name.equals(""))  ){
      throw new InvalidNameException("Invalid Driver Name");
    }
    if((carModel.equals(" ") || carModel.equals(""))){
      throw new InvalidCarModelException("Invalid Car Model");
    }

    if(carLicencePlate.equals(" ") || carLicencePlate.equals("")){
      throw new InvalidLicencePlateException("Invalid Car Licence Plate");
    }

    if(CityMap.validAddress(address)==false){
      throw new InvalidAddressException("Invalid Address");
    }

    
    drivers.add(new Driver( TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate, address));
    
   
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {

    boolean userFlag=false;

    for(String id : users.keySet()){
      if(id.equals(accountId)){
        userFlag=true;
      }
    }


    if(userFlag==false){
      throw new UserNotFoundException("User Account Not Found");
    }

     if(CityMap.validAddress(from)==false){
      throw new InvalidAddressException("Invalid From Address");
    }

     if(CityMap.validAddress(to)==false){
      throw new InvalidAddressException("Invalid To Address");
    }

    int distance = CityMap.getDistance(from,to);
    if(distance<1){
    throw new InsufficientDistanceException("Insufficient Travel Distance");
    }

    double cost = getRideCost(distance);
    if(cost > getUser(accountId).getWallet()){
      throw new InsufficientFundsException("Insufficient Funds");
    }
    
    existingRequest(new TMUberRide(from,to,getUser(accountId),distance,getRideCost(distance)));
     
    

    int zone = CityMap.getCityZone(from);
    requests[zone].add(new TMUberRide(from,to,getUser(accountId),distance,getRideCost(distance)));
    getUser(accountId).addRide();

    
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    boolean userFlag=false;
    for(String id : users.keySet()){
      if(id.equals(accountId)){
        userFlag=true;
      }
    }
    if(userFlag==false){
      throw new UserNotFoundException("User Account Not Found");
    }

    if(CityMap.validAddress(from)==false){
      throw new InvalidAddressException("Invalid From Address");
    }

    if(CityMap.validAddress(to)==false){
      throw new InvalidAddressException("Invalid To Address");
    }

    if(restaurant.equals(" ")|| restaurant.equals("")){
      throw new InvalidRestaurantException("Invalid Restaurant Name");
    }
    
    if(foodOrderId.equals(" ") || foodOrderId.equals("")){
      throw new InvalidFoodOrderIdException("Invalid Food Order Id");
    }

    int distance = CityMap.getDistance(from,to);
    if(distance<1){
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }

    double cost = getDeliveryCost(distance);
    if(cost > getUser(accountId).getWallet()){
      throw new InsufficientFundsException("Insufficient Funds");
    }
    
    existingRequest(new TMUberDelivery( from, to, getUser(accountId), distance, getDeliveryCost(distance), restaurant, foodOrderId));


    int zone = CityMap.getCityZone(from);
    requests[zone].add(new TMUberDelivery( from, to, getUser(accountId), distance, getDeliveryCost(distance), restaurant, foodOrderId));
    getUser(accountId).addDelivery();


  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int request, int zone)
  {

    if(zone>3 || zone<0){
      throw new ZoneNotFoundException( "Invalid Zone");
    }

    if(request>requests[zone].size() || request<=0){
      throw new ServiceNotFoundException("Invalid Request #");
    }
    
    TMUberService ser=null;
    int reqNum=1;
    for(TMUberService e : requests[zone]){
      if(reqNum==request){
        ser=e;
      }
      reqNum++;
    }
    
    User user_req = ser.getUser();

    //deducts the users delivery count if request type is delivery 
    if(ser.getType().equals("DELIVERY")){
      user_req.setDeliveries(user_req.getDeliveries()-1);
    }

    //deducts the users ride count if request type is ride 
    if(ser.getType().equals("RIDE")){
      user_req.setRide(user_req.getRides()-1);
    }

    //removes the request from the queue 
    Queue <TMUberService> hold = new LinkedList<>();
    while(!requests[zone].isEmpty()){
      if(!requests[zone].peek().equals(ser)){
        hold.add(requests[zone].poll());
      }
      else{
        break;
      }
    }

    requests[zone].remove();
    while(!requests[zone].isEmpty()){
      hold.add(requests[zone].poll());
      
    }

    while(!hold.isEmpty()){
      requests[zone].add(hold.poll());
      
    }
  }
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverId)
  {

    Driver d= getDriver(driverId);

    if(d==null ){
      throw new DriverNotFoundException("Invalid Driver Id");
    }

    if(d.getStatus() == Driver.Status.AVAILABLE){
      throw new DriverUnavailableException("Driver is not Servicing, cannot dropoff");
    }
    

    TMUberService ser = d.getService();

    totalRevenue += ser.getCost(); //get the cost for the service and adds to revenue

    double pay_driver = ser.getCost()*PAYRATE; //get the fee to pay the driver 

    d.pay(pay_driver); //pay the driver

    totalRevenue -= pay_driver;//substract fee from revenue bc we paid the driver

    ser.getUser().payForService(ser.getCost());//deduct cost of service from user 

    d.setStatus(Driver.Status.AVAILABLE); //changes driver status from driving --> avaliable 

    d.setService(null);//set the service of driver to null 

    d.setAddress(ser.getTo());//set the address of the driver to the to address 
    d.setZone(ser.getTo());//bc we changed the address we must also change the zone of the driver 
    
  }
  

  // Sort users by name
  // Then list all users

  public void sortByUserName()
  {
    userList = new ArrayList<User>(users.values());
    Collections.sort(userList, new NameComparator());
    
    listAllUsers();
    
  }
  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>{
    public int compare(User a, User b){
      
      return (a.getName().compareTo(b.getName()));
    }
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet()
  {
    userList = new ArrayList<User>(users.values());
    Collections.sort(userList, new UserWalletComparator());

    listAllUsers();
  }

  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    public int compare(User a, User b){
      if(a.getWallet() > b.getWallet()){
        return 1;
      }
      else if(a.getWallet() < b.getWallet()){
        return -1;
      }
      else{
        return 0;
      }
    }
  }

  //sortbyId
  public void sortById(){
    userList = new ArrayList<User>(users.values());
    Collections.sort(userList, new UserIdComparator());

    listAllUsers();
  }
  private class UserIdComparator implements Comparator<User>
  {
    public int compare(User a, User b){
      return Integer.parseInt(a.getAccountId())- Integer.parseInt((b.getAccountId()));
    }
  }
  

  public void pickup (String driverId) {
    Driver d = getDriver(driverId);
    if(d==null){
      throw new DriverNotFoundException("Invalid Driver Id");
    }

    if(d.getStatus() == Driver.Status.DRIVING){
      throw new DriverUnavailableException ("Driver not available");
    }

    int zone = d.getZone();
    TMUberService ser = requests[zone].poll();
    if(ser == null){
      throw new ServiceNotFoundException("No Service Request in Zone "+zone);
    }

      d.setStatus(Driver.Status.DRIVING);
      d.setService(ser);
      d.setAddress(ser.getFrom()); 
  }
  


  public void driveTo(String driverId, String address){
    Driver d = getDriver(driverId);
    if(d==null){
      throw new DriverNotFoundException("Invalid Driver Id");
    }

    if(CityMap.validAddress(address) == false){
      throw new InvalidAddressException("Invalid Address");
    }

    if(d.getStatus() == Driver.Status.DRIVING){
      throw new DriverUnavailableException ("Driver Not Available");
    }

    d.setAddress(address);
    d.setZone(address);
    
  }

  public void setUsers(ArrayList<User> userList){
    int size = users.size();
    if(size == 0){
      for(int i =0; i<userList.size();i++){
        users.put(userList.get(i).getAccountId(),userList.get(i));
      }
    }
    else{
      for(int i =0; i<userList.size();i++){
        String key = "900"+size;
        User value = userList.get(i);
        value.setAccountId(key);
        users.put(key,value);
        size++;
      }
    }
    
  }

  public void setDrivers(ArrayList<Driver> drivers){
    int size = this.drivers.size();
    if(size == 0){
      this.drivers = drivers ;
    }
    else{
      for(int i =0; i<drivers.size();i++){
        String id = "700"+size;
        drivers.get(i).setId(id);
        this.drivers.add(drivers.get(i));
        size++;
      }
      
    }
   
  }

  

}

//driver id not found 
class DriverNotFoundException extends RuntimeException{
  public DriverNotFoundException(){}
  public DriverNotFoundException(String message){
    super(message);
  }
}


class DriverExistsException extends RuntimeException{
  public DriverExistsException(){}
  public DriverExistsException(String message){
    super(message);
  }
}


class InvalidNameException extends RuntimeException{
  public InvalidNameException(){}
  public InvalidNameException(String message){
    super(message);
  }
}

class InvalidCarModelException extends RuntimeException{
  public InvalidCarModelException(){}
  public InvalidCarModelException(String message){
    super(message);
  }
}

class InvalidLicencePlateException extends RuntimeException{
  public InvalidLicencePlateException(){}
  public InvalidLicencePlateException(String message){
    super(message);
  }
}



class InvalidAddressException extends RuntimeException{
  public InvalidAddressException(){}
  public InvalidAddressException(String message){
    super(message);
  }
}
 class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(){}
  public UserNotFoundException(String message){
    super(message);
  }
 }

 class InsufficientDistanceException extends RuntimeException{
  public InsufficientDistanceException(){}
  public InsufficientDistanceException(String message){
    super(message);
  }
 }

 class InsufficientFundsException extends RuntimeException{
  public InsufficientFundsException(){}
  public InsufficientFundsException(String message){
    super(message);
  }
 }

 class UserServiceExistsException extends RuntimeException{
  public UserServiceExistsException(){}
  public UserServiceExistsException(String message){
    super(message);
  }
 }

 class UserExistsException extends RuntimeException{
  public UserExistsException(){}
  public UserExistsException(String message){
    super(message);
  }
}

class ZoneNotFoundException extends RuntimeException{
  public ZoneNotFoundException(){}
  public ZoneNotFoundException(String message){
    super(message);
  }
}

class ServiceNotFoundException extends RuntimeException{
  public ServiceNotFoundException(){}
  public ServiceNotFoundException(String message){
    super(message);
  }
}

class DriverUnavailableException extends RuntimeException{
  public DriverUnavailableException(){}
  public DriverUnavailableException (String message){
    super(message);
  }
}

class InvalidRestaurantException extends RuntimeException{
  public InvalidRestaurantException(){}
  public InvalidRestaurantException(String message){
    super(message);
  }
}

class InvalidFoodOrderIdException extends RuntimeException{
  public InvalidFoodOrderIdException(){}
  public InvalidFoodOrderIdException(String message){
    super(message);
  }
}



