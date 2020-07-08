package jdb;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.*; 

public class ShowAttendence implements ActionListener {  
	Container c1 ;
	CardLayout cd1;
	JButton BackButton;
	JLabel bg;
	JLabel AttendsLabel[]	= new JLabel[100];
	JLabel AbsentsLabel[]	= new JLabel[100];
	JLabel PercentageLabel[]= new JLabel[100];
	JLabel StudentNameLabel;
	int count;
	Connection con;
	Statement st;
	ResultSet rs;
	int r;
	LinkedHashSet<String> present	= new LinkedHashSet<String>();
	LinkedHashSet<String> absent	= new LinkedHashSet<String>();
	String attends[] = new String[100];	
	String absents[] = new String[100];
	String dbUrl;
	
	ShowAttendence(Container c,CardLayout cd,String dbUrl) {  
		this.dbUrl = dbUrl;
		cd1	= cd;
        c1 	= c;
		c1.setBackground(Color.white);
        c1.setSize(500, 500);  
        c1.setVisible(true);  
        bg=new JLabel();
        Font f	= new Font("calibri",Font.BOLD,30);
        Font f2 = new Font("calibri",Font.PLAIN,20);
        Border border = BorderFactory.createLineBorder(Color.black, 1);
        BackButton = new JButton("< Back");
        BackButton.setFont(f);
        BackButton.setBounds(1070,610,150,40);
        BackButton.setBackground(Color.gray);
        BackButton.addActionListener(this);
        bg.add(BackButton);
        JPanel JPannel_For_ListJScrollPane = new JPanel( ) ;
        JPannel_For_ListJScrollPane.setBackground(Color.white);
        JPanel buttons;
        count = 1;
        try {
        	con=DriverManager.getConnection(dbUrl);
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM register ORDER BY roll ASC");
			int i;
			
			Font f1 = new Font("calibri",Font.BOLD,30);
			
			JPanel TOP = new JPanel( ) ;
			TOP.setLayout( new GridLayout( 1,3) ) ;
			
			JLabel DETAILS_HEADDING = new JLabel("ROLL  STUDENT NAME");
			DETAILS_HEADDING.setFont(f1);
			DETAILS_HEADDING.setBorder(border);
			JPannel_For_ListJScrollPane.add(DETAILS_HEADDING);
			
			DETAILS_HEADDING = new JLabel("ATTENDED");
			DETAILS_HEADDING.setFont(f1);
			DETAILS_HEADDING.setBorder(border);
			TOP.add(DETAILS_HEADDING);
			
			DETAILS_HEADDING = new JLabel("ABSENT");
			DETAILS_HEADDING.setFont(f1);
			DETAILS_HEADDING.setBorder(border);
			TOP.add(DETAILS_HEADDING);
			
			DETAILS_HEADDING = new JLabel("PERCENTAGE");
			DETAILS_HEADDING.setFont(f1);
			DETAILS_HEADDING.setBorder(border);
			TOP.add(DETAILS_HEADDING);
			
			JPannel_For_ListJScrollPane.add(TOP);
			
			while(rs.next())
			{	
			 	i = count;
				String roll	= rs.getString("roll");
				String stu 	= rs.getString("name");
				attends[i] 	= rs.getString("attends");
				absents[i] 	= rs.getString("absents");
				
				if(attends[i]==null) {
					attends[i] = "0";
				}
				if(absents[i]==null) {
					absents[i] = "0";
				}
				
				buttons= new JPanel( ) ;
	            buttons.setLayout( new GridLayout( 1,3) ) ;
	            
	            StudentNameLabel = new JLabel(roll.substring(0, roll.length() - 2)+"   "+stu);
	            StudentNameLabel.setFont(f2);
	            StudentNameLabel.setBorder(border);
	            JPannel_For_ListJScrollPane.add(StudentNameLabel);
	            
	         	AttendsLabel[i] = new JLabel(attends[i]);
	         	AttendsLabel[i].setFont(f);
	         	AttendsLabel[i].setBackground(Color.gray);
	         	AttendsLabel[i].setBorder(border);
	        	buttons.add(AttendsLabel[i]);
	        	
	        	AbsentsLabel[i] = new JLabel(absents[i]);
	        	AbsentsLabel[i].setFont(f);
	        	AbsentsLabel[i].setBackground(Color.gray);
	        	AbsentsLabel[i].setBorder(border);
	        	buttons.add(AbsentsLabel[i]);
	        	
	        	if((Integer.parseInt(attends[i])+Integer.parseInt(absents[i]))!=0){
	        		PercentageLabel[i] = new JLabel(String.valueOf(Integer.parseInt(attends[i])*100/
		        			((Integer.parseInt(attends[i])+Integer.parseInt(absents[i])))) + " %");
	        		}
	        	else {
	        		PercentageLabel[i] = new JLabel("0" + " %");
	        		}
	        	
	        	PercentageLabel[i].setFont(f);
	        	PercentageLabel[i].setBackground(Color.gray);
	        	PercentageLabel[i].setBorder(border);
	        	buttons.add(PercentageLabel[i]);
	        	JPannel_For_ListJScrollPane.add(buttons);
	        	
	        	count = count+1;
			}
			st.close();
			con.close();
        }catch(Exception ex) {
			System.out.println(ex);
		}
        
        JPannel_For_ListJScrollPane.setLayout( new GridLayout( count, 30) ) ;
        JScrollPane ListJScrollPane = new JScrollPane(JPannel_For_ListJScrollPane);    
        ListJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        ListJScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        ListJScrollPane.setBounds(20,20,1200,580);
        bg.add(ListJScrollPane);
        
        c.add("ShowAttendence",bg);
    }  
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==BackButton)
		{
			cd1.show(c1, "Loginwindow");
		}
	}
   
}  