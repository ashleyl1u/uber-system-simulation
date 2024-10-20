
public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;
    
  private TMUberService service; 
  private String address;
  private int zone;

  public Driver(String id, String name, String carModel, String licensePlate, String address)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";
    this.address = address;
    this.zone = CityMap.getCityZone(address);
    this.service =null;
   

  }

  // Prints Information about a driver
  public void printInfo()
  {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f", 
                      id, name, carModel, licensePlate, wallet);
    System.out.println();
    System.out.printf("Status: %-10s Address: %-15s Zone: %-2s", status, address, zone);

    if(this.status == Status.DRIVING){
      System.out.println();
      System.out.printf("From: %-15s To: %-15s", service.getFrom() , service.getTo());
    }
  }
  // Getters and Setters

  public void setZone (String address){
    this.zone = CityMap.getCityZone(address);
  }
  public void setAddress(String address){
    this.address = address;
    
  }

  public int getZone (){
    return zone;
  }

  public void setService(TMUberService service){
    if(this.status == Status.DRIVING){
      this.service = service;
    }
    else{
      this.service=null;
    }
  }

  public TMUberService getService(){
   
    return this.service;
    
  }

  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }

  
  public boolean equals(Object other)
  {
    Driver otherD = (Driver) other;
    if(otherD.name.equals(this.name)  && otherD.licensePlate.equals(this.licensePlate) ){
      return true;
    }
    return false;
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
}
