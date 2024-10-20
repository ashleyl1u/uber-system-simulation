import java.util.Arrays;
import java.util.Scanner;


public class CityMap
{
  // Checks for string consisting of all digits
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
   
    if (address.equals(null) || address.length() == 0 || address.equals(" ")|| address.equals(""))
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }

    sc.close();
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    String parts [] = getParts(address);
    boolean flag1=false;
    boolean flag2=false;
    boolean flag3=false;

    //checks if parts have 3 parts 
    if(parts.length != 3){
      return false;
    }

    else{
   
      //checks if the first part are all digits and has a length of 2
      if(allDigits(parts[0])==true && parts[0].length()==2){

        flag1=true;
      }
      
      //checks if the second part is valid
      if(Character.isDigit(parts[1].charAt(0)) == true && parts[1].length() == 3){
        
        String s1 =parts[1].substring(0,1);
        String s2 = parts[1].substring(1,3);

        if(s1.equals("1") && s2.equals("st")){
          flag2= true;
        }
        else if(s1.equals("2") && s2.equals("nd")){
          flag2= true;
        }
        else if(s1.equals("3") && s2.equals("rd")){
          flag2=true;
        }
        else if((s1.equals("4") || s1.equals("5") || s1.equals("6")|| s1.equals("7")|| s1.equals("8")|| s1.equals("9")) && s2.equals("th")){
          flag2=true;
        }
      }

      //checks if 3rd part is valid
      if(parts[2].equalsIgnoreCase("Avenue") || parts[2].equalsIgnoreCase("Street")){
        flag3=true;
      }
    }
    
    return flag1 && flag2 && flag3;
  }


  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};
    String [] parts = getParts(address);

    if (parts[2].equalsIgnoreCase("Street")){
      block[1]= Integer.parseInt(parts[1].substring(0,1));
      block[0] = Integer.parseInt(parts[0].substring(0,1));
    }
    else if(parts[2].equalsIgnoreCase("Avenue")){
      block[1]=Integer.parseInt(parts[0].substring(0,1));
      block[0]= Integer.parseInt(parts[1].substring(0,1));
    }  

    return block;
  }
  

  public static int getDistance(String from, String to)
  {
    int [] aFrom = getCityBlock(from);
    int [] aTo = getCityBlock(to);

    int avenue = Math.abs(aFrom[0]-aTo[0]);
    int street = Math.abs(aFrom[1]- aTo[1]);
    return avenue+street;
  }



  public static int getCityZone(String address){
    if(validAddress(address) == false){
      return -1;
    }

    String [] parts = getParts(address);
    String streetAddr ="";
    String avenueAddr = "";

    if(parts[2].equalsIgnoreCase("Street")){
      streetAddr = parts[1].substring(0,1);
      avenueAddr = parts[0].substring(0,1);
    }
    else if(parts[2].equalsIgnoreCase("Avenue")){
      avenueAddr = parts[1].substring(0,1);
      streetAddr = parts[0].substring(0,1);
    }
    else{}

    if( (streetAddr.equals("1") || streetAddr.equals("2") || streetAddr.equals("3") || streetAddr.equals("4") || streetAddr.equals("5")) && (avenueAddr.equals("1") || avenueAddr.equals("2") || avenueAddr.equals("3") || avenueAddr.equals("4") || avenueAddr.equals("5"))){
      return 3;
    }
    else if ( (streetAddr.equals("1") || streetAddr.equals("2") || streetAddr.equals("3") || streetAddr.equals("4") || streetAddr.equals("5")) && (avenueAddr.equals("6") || avenueAddr.equals("7") || avenueAddr.equals("8") || avenueAddr.equals("9"))){
      return 2;
    }
    else if( (streetAddr.equals("6") || streetAddr.equals("7") || streetAddr.equals("8") || streetAddr.equals("9")) && (avenueAddr.equals("6") || avenueAddr.equals("7") || avenueAddr.equals("8") || avenueAddr.equals("9") )){
      return 1;
    }
    else if( (streetAddr.equals("6") || streetAddr.equals("7") || streetAddr.equals("8") || streetAddr.equals("9")) && (avenueAddr.equals("1") || avenueAddr.equals("2") || avenueAddr.equals("3") || avenueAddr.equals("4") || avenueAddr.equals("5"))){
      return 0;
    }

    else{
      return -1;
    }

    
  }
}
