
public class TMUberDelivery extends TMUberService 
{
  public static final String TYPENAME = "DELIVERY";
 
  private String restaurant; 
  private String foodOrderId;
   

  public TMUberDelivery( String from, String to, User user, int distance, double cost,String restaurant, String order)
  {
    super( from, to, user, distance, cost, "DELIVERY");
    this.restaurant = restaurant;
    this.foodOrderId = order;
  }
 
  
  public String getServiceType()
  {
    return TYPENAME;
  }
  public String getRestaurant()
  {
    return restaurant;
  }
  public void setRestaurant(String restaurant)
  {
    this.restaurant = restaurant;
  }
  public String getFoodOrderId()
  {
    return foodOrderId;
  }
  public void setFoodOrderId(String foodOrderId)
  {
    this.foodOrderId = foodOrderId;
  }
  

  public boolean equals(Object other)
  {
    TMUberService otherS = (TMUberService) other;
    if(otherS.getType() != "DELIVERY"){
      return false;
    }
    else{
      TMUberDelivery otherD = (TMUberDelivery) other;
      if(super.equals(otherD) && otherD.restaurant.equals(this.restaurant) && otherD.foodOrderId.equals(this.foodOrderId)){
        return true;
      }
      return false;
    }

  
  }
 

  public void printInfo()
  {
    super.printInfo();
    System.out.printf("\nRestaurant: %-9s Food Order #: %-3s", restaurant, foodOrderId); 
    
  }
}
