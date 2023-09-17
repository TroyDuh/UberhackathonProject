
/*
* Group Members: João Vítor Chaves Avila, Troy Anderson The, Dimas Deffieux
* Project created on September 15th 2023
* Main file that will be what the user will run (login page)
* */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class login extends JFrame implements ActionListener
{
  //Color
    
  Color Teal = new Color(54,117,136);
  Color Grey = new Color(43,45,47);
    
  //Panels and Buttons
    
  private JLabel usernameLabel;
  private JTextField usernameField;
  private JLabel passwordLabel;
  private JTextField passwordField;
  private JButton loginButton;
  private JButton closeButton;
  private JPanel closePanel;
  private JPanel PropertiesPanel;
  private JComboBox<String> districtComboBox;
  ResultSet rs = null;
    
  public login()
            
  {
        
    //Frame
        
    super("Update the Database");
    this.setBounds(600, 200, 600, 200);
    this.getContentPane().setBackground(Color.LIGHT_GRAY);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Creating the comboBox of options for the user to choose
    String[] districsLocations = new String[]
    {
      "Kreuzberg",
      "Mitte",
      "Friedrichshain",
      "Prenzlauer Berg",
      "Neukölln",
      "Charlottenburg",
      "Friedrichshain-Kreuzberg",
      "Pankow",
      "Schöneberg",
    };
        
    //For the Action Listener
        
    this.usernameLabel = new JLabel("Usernane:");
    this.usernameField = new JTextField(15);
    this.passwordLabel = new JLabel("Password:");
    this.passwordField = new JTextField(15);
    this.loginButton = new JButton("Login");
    loginButton.addActionListener(this);
    districtComboBox = new JComboBox<>(districsLocations);
    this.closeButton = new JButton("Close");
    closeButton.addActionListener(this);
    this.PropertiesPanel = new JPanel();
        
    //Add to the Panel
    PropertiesPanel.add(usernameLabel);
    PropertiesPanel.add(usernameField);
    PropertiesPanel.add(passwordLabel);
    PropertiesPanel.add(passwordField);
    PropertiesPanel.add(loginButton);
    PropertiesPanel.add(new JLabel("District Location:"));
    PropertiesPanel.add(districtComboBox);
    this.closePanel = new JPanel();
    closePanel.add(closeButton);
        
    //Color 
    PropertiesPanel.setBackground(Teal);
    closePanel.setBackground(Grey);
        
    //BorderLayout
    this.add(PropertiesPanel, BorderLayout.CENTER);
    this.add(closePanel, BorderLayout.SOUTH);

    //GUI Visible
    this.setVisible(true);

  }


  @Override
  public void actionPerformed(ActionEvent Cm)
  {
    String command = Cm.getActionCommand();
        
    // db info
    String dbName = "Multuber";
    String tableName = "Users";
    String[] columnNames = {"username","password", "email", "phoneNumber", "type"};

    // insert a query, to supply values later
    String dbQuery = "SELECT * FROM Users WHERE username = ? AND password = ?"; 
    
    ResultSet Result = null;
    boolean AuthLog = false;
        
    // connect to db
    JavaDatabase objDb = new JavaDatabase(dbName);
    Connection myDbConn = objDb.getDbConn();

    // data to be entered
    String username;
    String password;
        
    if (command.equals("Login")) 
    {
      //Get and Set Text
      username = usernameField.getText();
      password = passwordField.getText();
      usernameField.setText("");
      passwordField.setText("");
      
      try
      {
        // prepare statement
        PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
        // enter data into query
        ps.setString(1, username);
        ps.setString(2, password);
        // execute the query     
        Result = ps.executeQuery();
        
        AuthLog = Result.next();
        
        if (AuthLog == true)
        {
          
          this.dispose();
          
          System.out.println("Data updated successfully");
        
          new AppGUI(dbName, tableName, columnNames);
        
        }
  
      }
      catch (SQLException se)
      {
        System.out.println("Error updating data");
      }

    }
        
    else if(command.equals("Close"))

    {

      this.dispose();

    }
    
  }
  
  public static void main(String[] args)
  {

    // db info
    String dbName = "Multuber";
    String tableName = "Users";
    String[] columnNames = {"username","password", "email", "phoneNumber", "type"};
    
    //create an Object form the class DbGui
    new login();

  }
  
}