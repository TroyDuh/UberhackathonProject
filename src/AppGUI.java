/*
 * João Vítor Chaves Avila, September 16th, 2023
 * Main Profile Picture
 */

//Imports

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public final class AppGUI extends JFrame implements ActionListener

{
    
    //Colors
    
    Color Teal = new Color(54,117,136);
    Color Cyan = new Color(173,216,230);
    Color Grey = new Color(43,45,47);
    
    //Panels and Buttons
    
    private JButton displayButton;
    private JButton closeButton;
    private JPanel closePanel;
    private JPanel  PanelForButtons;
    
    //Data in the frame
    
    private ArrayList<ArrayList<String>> data;
    private JTable dbTable;
    private JTableHeader header;
    private Object[][] dataList;
    private JScrollPane scrollTable;
    private TableColumn column;
    
    public AppGUI(String dbName, String tableName, String[] tableHeaders)
            
    {
        
        //Frame things
        super("Profile Information");
        this.setBounds(200, 200, 600, 600);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //ActionListener in the buttons
        this.displayButton = new JButton("Reload");
        displayButton.addActionListener(this);
        this.PanelForButtons = new JPanel();
        this.closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        
        //Add to the Panel
        PanelForButtons.add(displayButton);
        this.closePanel = new JPanel();
        closePanel.add(closeButton);
        
        //get object from JavaDbAccess
        JavaDatabase dbObj = new JavaDatabase(dbName);
        //get data of the database
        data = dbObj.getData(tableName, tableHeaders);
        //convert data to a 2D array
        dataList = dbObj.to2dArray(data);
        //close connection
        dbObj.closeDbConn();

        // construct Jtable
        dbTable = new JTable(dataList, tableHeaders);

        // format table
        dbTable.setGridColor(Color.BLACK);
        dbTable.setBackground(Teal);
        dbTable.setFont(new Font("Arial", Font.BOLD, 18));
        
        // format header
        header = dbTable.getTableHeader();
        header.setBackground(Cyan);
        header.setForeground(Color.BLACK);
        closePanel.setBackground(Grey);
        header.setFont(new Font("Arial", Font.BOLD, 25));
        
        //Color of the panel
        PanelForButtons.setBackground(Grey);

        // format rows
        dbTable.setRowHeight(25);

        // format columns
        column = dbTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(50);
        column = dbTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(50);
        column = dbTable.getColumnModel().getColumn(2);
        column.setPreferredWidth(50);
        
        // put the Table into Scroll Panel
        scrollTable = new JScrollPane();
        scrollTable.getViewport().add(dbTable);
        dbTable.setFillsViewportHeight(true);


        //Add buttons and Table to frame
        this.add(PanelForButtons, BorderLayout.NORTH);
        this.add(scrollTable, BorderLayout.CENTER);
        this.add(closePanel, BorderLayout.SOUTH);
        
        //GUI Visible
        this.setVisible(true);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent Cm) 
    
    {
      // db info
      String dbName = "Multuber";
      String tableName = "Users";
      String[] columnNames = {"username","password", "email", "phoneNumber", "type"};
        
        String command = Cm.getActionCommand();
        
        if (command.equals("Reload"))
            
        {
            
            this.dispose();
            
            new AppGUI(dbName, tableName, columnNames); 

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
        new AppGUI(dbName, tableName, columnNames);
        
    }

}
