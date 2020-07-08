package jdb;
import javax.swing.*;
import java.io.*;
import java.nio.file.StandardCopyOption;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.InputStream;
import java.nio.file.Files;
import javax.swing.border.*;

public class Loginwindow extends JFrame implements ActionListener {
	CardLayout cd;					//CreateContentpane();
	Container c;					//CreateContentpane();
	JButton StudentsRegistry_Button;//Set_StudentsRegistry_Button();
	JButton TakeAttendence_Button;	//Set_TakeAttendence_Button();
	JButton ShowAttendence_Button;	//Set_ShowAttendence_Button();
	JLabel bg;						//CreateBackGround();
	String dbUrl;					//CopingDataBaseFromSource()
	Font font;						//SetFontAndBorderVariables();
	Border bd;						//SetFontAndBorderVariables();
	File f;							//CreateDataBaseDirectory()
	String dbdir;					//CreateDataBaseDirectory()
	String dbName;					//CopingDataBaseFromSource()
	String dbPath;					//CopingDataBaseFromSource()
	File f2;						//CopingDataBaseFromSource()
	Connection con;					//CreateDataBaseConnection();
	Statement st;					//CreateDataBaseConnection();
	ResultSet rs;					//CreateDataBaseConnection();
	
	Loginwindow()
	{
		CreateContentpane();
		CreateBackGround();
		SetFontAndBorderVariables();
		Set_StudentsRegistry_Button();
		Set_TakeAttendence_Button();
		Set_ShowAttendence_Button();
		CreateDataBaseDirectory();
		CopingDataBaseFromSource();
		CreateDataBaseConnection();
		
		c.add("Loginwindow",bg);
	}
	
	public static void main(String args[])
	{
		Loginwindow t=new Loginwindow();
		t.setTitle("Attendance Card");
		t.setSize(1280,700);
		t.setVisible(true);
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	public void CreateContentpane() {
		c=getContentPane();
		cd=new CardLayout();
		c.setLayout(cd);
		c.setBackground(Color.white);
	}
	
	public void CreateBackGround() {
		bg=new JLabel();
		bg.setLayout(null);
	}
	
	public void SetFontAndBorderVariables() {
		font=new Font("calibri",Font.BOLD,20); 
		bd=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
	}
	
	public void Set_StudentsRegistry_Button() {
		StudentsRegistry_Button=new JButton("<html>Student Registry</html>");
		StudentsRegistry_Button.setFont(font);
		StudentsRegistry_Button.setBounds(125,250,250,60);
		StudentsRegistry_Button.setBorder(bd);
		StudentsRegistry_Button.setBackground(Color.black);
		StudentsRegistry_Button.setForeground(Color.white);
		StudentsRegistry_Button.addActionListener(this);
		bg.add(StudentsRegistry_Button);
	}
	
	public void Set_TakeAttendence_Button() {
		TakeAttendence_Button=new JButton("<html>Take attendance</html>");
		TakeAttendence_Button.setFont(font);
		TakeAttendence_Button.setBounds(520,250,250,60);
		TakeAttendence_Button.setBorder(bd);
		TakeAttendence_Button.setBackground(Color.black);
		TakeAttendence_Button.setForeground(Color.white);
		TakeAttendence_Button.addActionListener(this);
		bg.add(TakeAttendence_Button);
	}
	
	public void Set_ShowAttendence_Button() {
		ShowAttendence_Button=new JButton("<html>Show Attendance</html>");
		ShowAttendence_Button.setFont(font);
		ShowAttendence_Button.setBounds(895,250,250,60);
		ShowAttendence_Button.setBorder(bd);
		ShowAttendence_Button.setBackground(Color.black);
		ShowAttendence_Button.setForeground(Color.white);
		ShowAttendence_Button.addActionListener(this);
		bg.add(ShowAttendence_Button);
	}
	
	public void CreateDataBaseConnection() {
	   	try {
	   		con=DriverManager.getConnection(dbUrl);	
			st= con.createStatement();
			st.close();
			con.close();
        }catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void CopingDataBaseFromSource() {
	    dbName = "RawData.accdb";
	    //dbName = "data.accdb";
	    dbPath = dbdir+"/"+dbName;
	    f2 = new File(dbPath);
	    
	    if(!f2.exists()) {
	    	InputStream iStream =Loginwindow.class.getResourceAsStream("database/"+dbName);
	    	try {
	    		Files.copy(iStream ,f2.toPath(),StandardCopyOption.REPLACE_EXISTING);
	    		}
	    	catch(Exception ex) {
	    		System.out.println(ex);
	    		}	
	    	}
	    dbUrl = "jdbc:ucanaccess://"+dbPath;
	   		
	}
	
	public void CreateDataBaseDirectory() {
		dbdir = "c:/zJavaProjectDB";
		f = new File(dbdir);
	    if(!f.exists())
	    	f.mkdir();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		
		if(ae.getSource()==StudentsRegistry_Button)
		{
			new DetailsEdit(c,cd,dbUrl);
			cd.show(c,"DetailsEdit");
		}
		
		if(ae.getSource()==TakeAttendence_Button)
		{
			new TakeAttendence(c,cd,dbUrl);
			cd.show(c, "TakeAttendence");
		}
		
			if(ae.getSource()==ShowAttendence_Button)
			{
				new ShowAttendence(c,cd,dbUrl);
				cd.show(c, "ShowAttendence");
			}
		
	}
}