package jdb;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class DetailsEdit implements ActionListener {  
	Container c1 ;
	CardLayout cd1;
	JButton BackButton;
	JButton AddButton;
	JLabel RepeatedRollCall;
	JLabel IncorrectRollCallLabel;
	JLabel RollNoLabel;
	JLabel AddStudentName;
	JLabel bg;
	JButton EditButton[]=new JButton[100];
	JButton RemoveButton[]=new JButton[100];
	JLabel StudentNameLabel[]=new JLabel[100];
	JPanel ButtonsPannel[]= new JPanel[100] ;
	JTextField RollNoTextField;
	JTextField NameTextField;
	String dbUrl;
	Font f2;
	Font f;
	Border border;
	int count;
	Connection con;
	Statement st;
	ResultSet rs;
	int r;
	String RollNoOfStudent_String;
	String NameOfStudent_String;
	String attends[] = new String[100];
	String absents[] = new String[100];
	String id[] = new String[100];
	JPanel JPannel_For_ListJScrollPane;
	
	DetailsEdit(Container c,CardLayout cd, String dbUrl) {  
		this.dbUrl=dbUrl;
        c1 =c;
        cd1=cd;
        
        Set_ContentPane();
        Create_BackGroundJLabel();
        Set_Font_And_Border();
        Add_BackButton_To_BackGroundJLabbel();
        Add_AddButton_To_BackGroundJLabbel();
        Add_RollNoTextField_To_BackGroundJLabbel();
        Add_NameTextField_To_BackGroundJLabbel();
        Add_RollNoLabel_To_BackGroundJLabbel();
        Add_AddStudentName_To_BackGroundJLabbel();
        Add_RepeatedRollCall_To_BackGroundJLabbel();
        Add_IncorrectRollCallLabel_To_BackGroundJLabbel();
        Create_JPannel_For_ListJScrollPane();
        Add_Student_Contents();
        
        Creating_ListJScrollPane_Using_JPannel_For_ListJScrollPane();
        c.add("DetailsEdit",bg);
    }  
	
	public void Set_ContentPane() {
		c1.setBackground(Color.white);
        c1.setSize(500, 500);  
        c1.setVisible(true);  
	}
	public void Create_BackGroundJLabel() {
		bg=new JLabel();
	}
	public void Set_Font_And_Border() {
		f = new Font("calibri",Font.BOLD,30);
        f2 = new Font("calibri",Font.PLAIN,20);
        border = BorderFactory.createLineBorder(Color.black, 1);
	}
	public void Add_BackButton_To_BackGroundJLabbel() {
		 BackButton=new JButton("< Back");
	     BackButton.setFont(f);
	     BackButton.setBounds(1070,610,150,40);
	     BackButton.setBackground(Color.gray);
	     BackButton.addActionListener(this);
	     bg.add(BackButton);
	}
	public void Add_AddButton_To_BackGroundJLabbel() {
		AddButton=new JButton("ADD");
        AddButton.setFont(f);
	   	AddButton.setBounds(750,550,150,50);
	   	AddButton.setBackground(Color.gray);
	   	AddButton.addActionListener(this);
	   	bg.add(AddButton);
	}
	public void Add_RollNoTextField_To_BackGroundJLabbel() {
    	RollNoTextField = new JTextField(16); 
    	RollNoTextField.setFont(f);
    	RollNoTextField.setBounds(30,550,200,50);
    	RollNoTextField.addActionListener(this);
    	bg.add(RollNoTextField);
    		
	}
	public void Add_NameTextField_To_BackGroundJLabbel() {
    	NameTextField = new JTextField(16); 
    	NameTextField.setFont(f);
    	NameTextField.setBounds(240,550,500,50);
    	NameTextField.addActionListener(this);
    	bg.add(NameTextField);	
	}
	public void Add_RollNoLabel_To_BackGroundJLabbel() {
		RollNoLabel=new JLabel("ROLL NO");
       	RollNoLabel.setFont(f);
       	RollNoLabel.setBounds(30,520,300,40);
        bg.add(RollNoLabel);
	}
	public void Add_AddStudentName_To_BackGroundJLabbel() {
        AddStudentName=new JLabel("NAME OF STUDENT");
        AddStudentName.setFont(f);
        AddStudentName.setBounds(240,520,300,40);
        bg.add(AddStudentName);
	}
	public void Add_RepeatedRollCall_To_BackGroundJLabbel() {
        RepeatedRollCall =new JLabel("repeted roll call");
        RepeatedRollCall.setFont(f);
        RepeatedRollCall.setVisible(false);
        RepeatedRollCall.setBounds(30,600,300,40);
        RepeatedRollCall.setForeground(Color.red);
        bg.add(RepeatedRollCall);				
	}
	public void Add_IncorrectRollCallLabel_To_BackGroundJLabbel(){
        IncorrectRollCallLabel =new JLabel("incorrect roll call");
        IncorrectRollCallLabel.setFont(f);
        IncorrectRollCallLabel.setVisible(false);
        IncorrectRollCallLabel.setBounds(30,600,300,40);
        IncorrectRollCallLabel.setForeground(Color.red);
    	bg.add(IncorrectRollCallLabel);		 
	 }
	public void Create_JPannel_For_ListJScrollPane(){
        JPannel_For_ListJScrollPane = new JPanel( ) ;
        JPannel_For_ListJScrollPane.setBackground(Color.white);
  
	}
	public void Add_Student_Contents() {
        count = 1;
        try {con=DriverManager.getConnection(dbUrl);
			st= con.createStatement();
			rs = st.executeQuery("SELECT * FROM register ORDER BY roll ASC");
			int i;
			while(rs.next())
			{	
				i=count;
				RollNoOfStudent_String = rs.getString("roll");
				NameOfStudent_String = rs.getString("name");
				attends[i] = rs.getString("attends");
				absents[i] = rs.getString("absents");
				id[i] = rs.getString("ID");
				
				StudentNameLabel[i] = new JLabel(RollNoOfStudent_String.substring(0, RollNoOfStudent_String.length() - 2)+"  "+NameOfStudent_String);
	            StudentNameLabel[i].setFont(f2);
	            StudentNameLabel[i].setBorder(border);
	            JPannel_For_ListJScrollPane.add(StudentNameLabel[i]);
	         	
				ButtonsPannel[i]= new JPanel( ) ;
				ButtonsPannel[i].setLayout( new GridLayout( 1,2) ) ;
				JPannel_For_ListJScrollPane.add(ButtonsPannel[i]);
	            
	         	EditButton[i]=new JButton("edit");
	        	EditButton[i].setFont(f);
	        	EditButton[i].setBackground(Color.gray);
	        	EditButton[i].setBorder(border);
	        	EditButton[i].addActionListener(this);
	        	ButtonsPannel[i].add(EditButton[i]);
	        	
	        	RemoveButton[i]=new JButton("remove");
	        	RemoveButton[i].setFont(f);
	        	RemoveButton[i].setBackground(Color.gray);
	        	RemoveButton[i].setBorder(border);
	        	RemoveButton[i].addActionListener(this);
	        	ButtonsPannel[i].add(RemoveButton[i]);

	        	count = count+1;
			}
			st.close();
		    con.close();
		    
        }catch(Exception ex) {
			System.out.println(ex);
		}
        JPannel_For_ListJScrollPane.setLayout( new GridLayout( count-1, 30) ) ;	
	}
	public void Creating_ListJScrollPane_Using_JPannel_For_ListJScrollPane() {
		JScrollPane ListJScrollPane = new JScrollPane(JPannel_For_ListJScrollPane);  
        ListJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        ListJScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        ListJScrollPane.setBounds(20,20,1200,480);
        bg.add(ListJScrollPane);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==BackButton)
		{
			cd1.show(c1, "Loginwindow");
		}
		
		if(ae.getSource()==AddButton)
		{
			try {
				con=DriverManager.getConnection(dbUrl);
				st= con.createStatement();
				String name=NameTextField.getText();
				String roll = RollNoTextField.getText();
				 try {
					 Integer.parseInt(roll);
				 }
				 catch (NumberFormatException nfe) {
					 IncorrectRollCallLabel.setVisible(true);
					 RepeatedRollCall.setVisible(false);
					 return;
				    }
				 rs = st.executeQuery("select * from register Where roll = "+roll);
				 if(rs.next()) {
					 RepeatedRollCall.setVisible(true);
					 IncorrectRollCallLabel.setVisible(false);
					 return;
				 }
				 if(name.length()==0||roll.length()==0) {
					 return;
				 }
				 r= st.executeUpdate("insert into register(roll,name) values('"+roll+"','"+name+"')");
				 st.close();
				 con.close();
			}
			catch(Exception ex) {
				System.out.println(ex);
			}	
			new DetailsEdit(c1,cd1,dbUrl);
			cd1.show(c1, "DetailsEdit");
		}
		
		for(int i=0; i<count;i++) {
			if(ae.getSource()==EditButton[i])
			{
				new EditPanel(c1,cd1,id[i],dbUrl);
				cd1.show(c1,"EditPanel");
			}
			if(ae.getSource()==RemoveButton[i])
			{
				
				try {
					con=DriverManager.getConnection(dbUrl);
					 st= con.createStatement();
					r=st.executeUpdate("DELETE FROM register WHERE id = "+String.valueOf(id[i]));
					st.close();
					con.close();
					
					new DetailsEdit(c1,cd1,dbUrl);
					cd1.show(c1,"DetailsEdit");
				}
				catch(Exception ex) {
					System.out.println(ex);
				}
			}	
		}
	}
}