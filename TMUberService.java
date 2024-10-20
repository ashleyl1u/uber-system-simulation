abstract public class TMUberService implements Comparable <TMUberService>
{
    
  private String from;
  private String to;
  private User user;
  private String type;      
  private int distance; 
  private double cost;  
  
  public TMUberService( String from, String to, User user, int distance, double cost, String type)
  {
    this.from = from;
    this.to = to;
    this.user = user;
    this.distance = distance;
    this.cost = cost;
    this.type = type;
    
  }

  abstract public String getServiceType();

  // Getters and Setters
  public String getFrom()
  {
    return from;
  }
  public void setFrom(String from)
  {
    this.from = from;
  }
  public String getTo()
  {
    return to;
  }
  public void setTo(String to)
  {
    this.to = to;
  }
  public User getUser()
  {
    return user;
  }
  public void setUser(User user)
  {
    this.user = user;
  }
  public int getDistance()
  {
    return distance;
  }
  public void setDistance(int distance)
  {
    this.distance = distance;
  }
  public double getCost()
  {
    return cost;
  }
  public void setCost(double cost)
  {
    this.cost = cost;
  }

  public String getType()
  {
    return type;
  }


  

public int compareTo(TMUberService otherS){
  if(this.distance> otherS.distance ){
    return 1;
  }
  else if (this.distance<otherS.distance){
    return -1;
  }
  else{
    return 0;
  }
}

  

  public boolean equals(Object other)
  {

    TMUberService otherS = (TMUberService) other;
      if(otherS.type.equals(this.type) && otherS.user.equals(this.user) ){
        return true;
      }
      else{
        return false;
      }
    
    
    
  }
  
  public void printInfo()
  {
    System.out.printf("\nType: %-9s From: %-15s To: %-15s", type, from, to);
    System.out.print("\nUser: ");
    user.printInfo();
    
  }
}