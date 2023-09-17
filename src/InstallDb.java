 /*
 * João Vítor Chaves Avila, September 15th 2023
 * Install database 
 */

public class InstallDb
{ 
  
  public static void main(String[] args)
  {
    
    String dbName = "Multuber";
    // Creating an object of DB class
    JavaDatabase objDb = new JavaDatabase();
    // creating a new database
    objDb.createDb(dbName);
    // creating a new table
    String newTable = "CREATE TABLE Users(username varchar (20), "
      + "password varchar(20), email varchar(50), phoneNumber varchar(15), type tinyint)";
    objDb.createTable(newTable, dbName); 
    
    // creating a second table
    String newTable2 = "CREATE TABLE Destination (ID int, placeName varchar(50), "
      + "xCord double, yCord double, district varchar(50),"
      + " locationType varchar(50) )";
    objDb.createTable(newTable2, dbName); 
    
    // creating a third table
    String NewTable3 = "CREATE TABLE Start (ID int, placeName varchar(50), "
      + "xCord double, yCord double, district varchar(50),"
      + " locationType varchar(50) )";
    objDb.createTable(NewTable3, dbName); 
        
  }
  
}

