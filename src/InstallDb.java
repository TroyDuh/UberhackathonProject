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
    //String myNewTable = "CREATE TABLE Cars2 (Name2 varchar(50),"
      //+ "Price2 int, Color2 varchar(50) )";
    //objDb.createTable(myNewTable, dbName); 
        
  }
  
}

