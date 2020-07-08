package jdb;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.*; 
public class TakeAttendence implements ActionListener {  
	Container c1 ;
	CardLayout cd1; 
    JLabel StudentNameLabel;
    JPanel JPannel_For_ListJScrollPane;
	JButton BackButton;
	JButton AllPresentButton;
	JButton AllAbsentButton;
	JButton SubmitButton;
	JLabel bg;
	JButton PresentButton[]	= new JButton[100];
	JButton AbsentButton[]	= new JButton[100];
	int count;
	Connection con;
	Statement st;
	ResultSet rs;
	int r;
	LinkedHashSet<String> present = new LinkedHashSet<String>();
	LinkedHashSet<String> absent  = new LinkedHashSet<String>();
	String attends[] = new String[100];
	String absents[] = new String[100];
	String id[] 	 = new String[100];
	String dbUrl;
	
	TakeAttendence(Container c,CardLayout cd,String dbUrl) {  
		this.dbUrl = dbUrl;
        c1  = c;
		c1.setBackground(Color.white);
		c1.setSize(500, 500);  
        c1.setVisible(true);  
        
		cd1 = cd;  
        
        bg  = new JLabel();
        
        Font f = new Font("calibri",Font.BOLD,30);
        Border border = BorderFactory.createLineBorder(Color.black, 1);
        
        BackButton = new JButton("< Back");
        BackButton.setFont(f);
        BackButton.setBounds(20,610,150,40);
        BackButton.setBackground(Color.gray);
        BackButton.addActionListener(this);
        bg.add(BackButton);
        
        AllPresentButton = new JButton("all present");
        AllPresentButton.setFont(f);
        AllPresentButton.setBounds(250,610,200,40);
        AllPresentButton.setBackground(Color.gray);
        AllPresentButton.addActionListener(this);
     	bg.add(AllPresentButton);
     	
     	AllAbsentButton = new JButton("all absent");
     	AllAbsentButton.setFont(f);
     	AllAbsentButton.setBounds(500,610,200,40);
     	AllAbsentButton.setBackground(Color.gray);
     	AllAbsentButton.addActionListener(this);
     	bg.add(AllAbsentButton);
     	
     	SubmitButton = new JButton("submit");
     	SubmitButton.setFont(f);
     	SubmitButton.setBounds(1070,610,150,40);
     	SubmitButton.setBackground(Color.LIGHT_GRAY);
     	SubmitButton.addActionListener(this);
    	bg.add(SubmitButton);
    	
    	JPannel_For_ListJScrollPane = new JPanel( ) ;
    	JPannel_For_ListJScrollPane.setBackground(Color.white);

        Font f2 = new Font("calibri",Font.PLAIN,20);
        JPanel buttons;
        
        count = 1;
        try {
        	con=DriverManager.getConnection(dbUrl);
			st= con.createStatement();
			rs = st.executeQuery("SELECT * FROM register ORDER BY roll ASC");
			int i;
			while(rs.next())
			{	
			 	i=count;
				String roll = rs.getString("roll");
				String stu = rs.getString("name");
				attends[i] = rs.getString("attends");
				absents[i] = rs.getString("absents");
				id[i] = rs.getString("ID");
				buttons= new JPanel( ) ;
	            buttons.setLayout( new GridLayout( 1,2) ) ;
	            StudentNameLabel = new JLabel(roll.substring(0, roll.length() - 2)+"  "+stu);
	            StudentNameLabel.setFont(f2);
	            StudentNameLabel.setBorder(border);
	         	
	         	
	         	JPannel_For_ListJScrollPane.add(StudentNameLabel);
	         	PresentButton[i]=new JButton("present");
	         	PresentButton[i].setFont(f);
	         	PresentButton[i].setBackground(Color.gray);
	         	PresentButton[i].setBorder(border);
	        	
	        	buttons.add(PresentButton[i]);
	        	
	        	AbsentButton[i]=new JButton("absent");
	        	AbsentButton[i].setFont(f);
	        	AbsentButton[i].setBackground(Color.gray);
	        	AbsentButton[i].setBorder(border);
	        	
	        	buttons.add(AbsentButton[i]);
	        	
	        	PresentButton[i].addActionListener(this);
	        	AbsentButton[i].addActionListener(this);
	        	
	        	JPannel_For_ListJScrollPane.add(buttons);
	         	
	        	count = count+1;
				
			}
			
			st.close();
		    con.close();
        }catch(Exception ex) {
			System.out.println(ex);
		}
        
        JPannel_For_ListJScrollPane.setLayout( new GridLayout( count-1, 30) ) ;	
        JScrollPane ListJScrollPane = new JScrollPane(JPannel_For_ListJScrollPane);  
        ListJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        ListJScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        ListJScrollPane.setBounds(20,20,1200,580);
        bg.add(ListJScrollPane);
        
        c.add("TakeAttendence",bg);
    } 
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==BackButton)
		{
			cd1.show(c1, "Loginwindow");
		}
		
		if(ae.getSource()==AllPresentButton)
		{
			for(int i=1; i<count;i++) {
				
					PresentButton[i].setBackground(Color.green);
					AbsentButton[i].setBackground(Color.gray);
					present.add(String.valueOf(i));
					try{
						absent.remove(String.valueOf(i));
					}
					catch(Exception ex) {
						System.out.println(ex);
					}
				}
		}
		
		if(ae.getSource()==AllAbsentButton)
		{
			for(int i=1; i<count;i++) {
				AbsentButton[i].setBackground(Color.red);
				PresentButton[i].setBackground(Color.gray);
				absent.add(String.valueOf(i));
				
				try{
					present.remove(String.valueOf(i));
				}
				catch(Exception ex) {
					System.out.println(ex);
				}
			
				}
		}
		
		if(ae.getSource()==SubmitButton)
		{
			if((absent.size()+present.size())!=count-1) {
				return;
			}
			try {
				con=DriverManager.getConnection(dbUrl);
				 st= con.createStatement();
				 for(int j=0;j<count;j++) {
						 
						if(attends[j]==null) {
							attends[j]="0";
						}
						if(absents[j]==null) {
							absents[j]="0";
						}
						if(present.contains(String.valueOf(j))) {
							attends[j]=String.valueOf(Integer.parseInt(attends[j])+1);
							r=st.executeUpdate("update register set attends='"+attends[j]+"' where id="+String.valueOf(id[j]));
						}
						else {
							absents[j]=String.valueOf(Integer.parseInt(absents[j])+1);
							r=st.executeUpdate("update register set absents='"+absents[j]+"' where id="+String.valueOf(id[j]));
						}
					}
					st.close();
					con.close();
			}
				catch(Exception ex) {
				System.out.println(ex);
				}
			cd1.show(c1, "Loginwindow");
			}
		
		for(int i=0; i<count;i++) {
			if(ae.getSource()==PresentButton[i])
			{
				PresentButton[i].setBackground(Color.green);
				AbsentButton[i].setBackground(Color.gray);
				present.add(String.valueOf(i));
				try{
					absent.remove(String.valueOf(i));
				}
				catch(Exception ex) {
					System.out.println(ex);
				}
			}
			if(ae.getSource()==AbsentButton[i])
			{
				AbsentButton[i].setBackground(Color.red);
				PresentButton[i].setBackground(Color.gray);
				absent.add(String.valueOf(i));
				
				try{
					present.remove(String.valueOf(i));
				}
				catch(Exception ex) {
					System.out.println(ex);
				}
			}
			
		}
		
		

	}
   
}  