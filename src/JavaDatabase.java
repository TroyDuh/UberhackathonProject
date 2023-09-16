/*
 * João Vítor Chaves Avila, September 15th, 2023
 * Connect DB and the rest of the project
 */

//Imports

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JavaDatabase {
  private String dbName;
  private Connection dbConn;
  private ArrayList<ArrayList<String>> data;

  public JavaDatabase() {
    dbName = "";
    dbConn = null;
    data = null;
  }

  public JavaDatabase(String dbName) {
    setDbName(dbName);
    setDbConn();
    data = null;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public Connection getDbConn() {
    return dbConn;
  }

  public void setDbConn() {
    String connectionURL = "jdbc:mysql://localhost:3306/" + this.dbName;
    this.dbConn = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      this.dbConn = DriverManager.getConnection(connectionURL, "root", "mysql1");
      // this.dbConn = DriverManager.getConnection(connectionURL);
    } catch (ClassNotFoundException ex) {
      System.out.println("Driver not found, check Library");
    } catch (SQLException se) {
      System.out.println("SQL Connection error!");
    }
  }

  public void closeDbConn() {
    try {
      this.dbConn.close();
    } catch (Exception err) {
      System.out.println("DB closing error.");
    }
  }

  public ArrayList<ArrayList<String>> getData(String tableName,
      String[] tableHeaders) {
    int columnCount = tableHeaders.length;
    Statement s = null;
    ResultSet rs = null;
    String dbQuery = "SELECT * FROM " + tableName;
    this.data = new ArrayList<>();
    // read the data
    try {
      // send the query and receive data
      s = this.dbConn.createStatement();
      rs = s.executeQuery(dbQuery);

      // read the data using rs and store in ArrayList data
      while (rs.next()) {
        // row object to hold one row data
        ArrayList<String> row = new ArrayList<>();
        // go through the row and read each cell
        for (int i = 0; i < columnCount; i++) {
          // read cell i
          // example: String cell = rs.getString("Name");
          // reads the cell in column Name
          // tableHeader={"Name", "Age", "Color"}
          String cell = rs.getString(tableHeaders[i]);
          // add the cell to the row
          // example row.add("Vinny");
          row.add(cell);
        }
        // add the row to the data
        // example: data.add "Vinny",15,"Pink"
        this.data.add(row);
      }
    } catch (SQLException se) {
      System.out.println("SQL Error: Not able to get data");
    }
    return data;
  }

  public void setData(ArrayList<ArrayList<String>> data) {
    this.data = data;
  }

  public void createDb(String newDbName) {
    setDbName(newDbName);
    Connection newConn;
    // create a db if not existing
    String connectionURL = "jdbc:mysql://localhost:3306/";
    String query = "CREATE DATABASE " + this.dbName;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      newConn = DriverManager.getConnection(connectionURL, "root", "mysql1");
      Statement s = newConn.createStatement();
      s.executeUpdate(query);
      System.out.println("New Database created!");
      newConn.close();
    } catch (ClassNotFoundException ex) {
      System.out.println("Driver not found, check library");
    } catch (SQLException se) {
      System.out.println("SQL Connection error, Db was not created!");
    }
  }

  public void createTable(String newTable, String dbName) {
    System.out.println(newTable);
    setDbName(dbName);
    setDbConn();
    Statement s;
    try {
      s = this.dbConn.createStatement();
      s.execute(newTable);
      System.out.println("New table created!");
      this.dbConn.close();
    } catch (SQLException se) {
      System.out.println("Error creating table " + newTable);
    }
  }

  // to conver a 2d arraylist to 2d array:
  public Object[][] to2dArray(ArrayList<ArrayList<String>> data) {
    if (data.size() == 0) // empty table
    {
      Object[][] dataList = new Object[0][0];
      return dataList;
    } else // table w existing data
    {
      int columnCount = data.get(0).size(); // number of columns
      Object[][] dataList = new Object[data.size()][columnCount];
      // read each cell of each row into array
      for (int r = 0; r < data.size(); r++) {
        ArrayList<String> row = data.get(r); // get the row
        for (int c = 0; c < columnCount; c++) {
          dataList[r][c] = row.get(c); // get the cell
        }
      }
      return dataList;
    }
  }

  public boolean checkTable(String tableName, String[] columnNames, String checkColumn, Object checkObject) {
    int checkColumnIndex = -1; 
    boolean objectExists = false;

    // find length of array
    int len = columnNames.length;
    int i = 0;

    // traverse in the array
    while (i < len) {

      // if the i-th element is t
      // then return the index
      if (columnNames[i] == checkColumn) {
        checkColumnIndex = i;
      } else {
        i = i + 1;
      }
    }

    // connect to db
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection myDbConn = objDb.getDbConn();

    ArrayList<ArrayList<String>> userTable = new ArrayList<ArrayList<String>>();
    userTable = objDb.getData(tableName, columnNames);

    // Looping through the DB Table, checking if accounts exist
    int outLoop = 0;
    do {
      ArrayList<String> row = new ArrayList<String>();
      row = userTable.get(outLoop);
      if (row.get(checkColumnIndex) == checkObject) {
        objectExists = true;
      }

      outLoop++;
    } while (userTable.size() > outLoop);

    return objectExists;
  }

  public boolean createAccount(String username, String password, String email, String phoneNumber, int type) {
    // db info
    String dbName = "Multuber";
    String tableName = "Users";
    String[] columnNames = { "username", "password", "email", "phoneNumber", "type" };
    boolean accountExists = false;

    // connect to db
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection myDbConn = objDb.getDbConn();

    accountExists = checkTable(tableName, columnNames, "username", username);

    if (accountExists == false) {
      String dbQuery = "INSERT INTO Users VALUES (?,?,?,?,?)";
      try {
        // prepare statement
        PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
        // enter data into query
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, email);
        ps.setString(4, phoneNumber);
        ps.setInt(5, type);
        // execute the query
        ps.executeUpdate();
        System.out.println("Data inserted successfully");
      } catch (SQLException se) {
        System.out.println("Error inserting data");
      }
      return true;
    } else {
      return false;
    }
  }

  public static void main(String[] args) {
    // db info
    String dbName = "Multuber";
    String tableName = "Users";
    String[] columnNames = { "username", "password", "email", "phoneNumber", "type" };
    String dbQuery = "INSERT INTO Users VALUES (?,?,?,?,?)";
    // 0 is regular user, 1 is driver, 2 is admin

    // connect to db
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection myDbConn = objDb.getDbConn();

    // data to be entered (to be replaced with textfields in GUI)
    String username = "James";
    String password = "7zip7";
    String email = "jvavila2007@gmail.com";
    String phoneNumber = "9798377648";
    int Type = 0;

    try {
      // prepare statement
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      // enter data into query
      ps.setString(1, username);
      ps.setString(2, password);
      ps.setString(3, email);
      ps.setString(4, phoneNumber);
      ps.setInt(5, Type);
      // execute the query
      ps.executeUpdate();
      System.out.println("Data inserted successfully");
    } catch (SQLException se) {
      System.out.println("Error inserting data");
    }
    // read the data from database
    ArrayList<ArrayList<String>> myData = objDb.getData(tableName, columnNames);
    System.out.println(myData);

    
  }
}
