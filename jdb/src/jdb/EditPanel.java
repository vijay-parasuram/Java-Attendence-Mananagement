
package jdb;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
 

public class EditPanel implements ActionListener {  
	Container c1 ;
	CardLayout cd1;
	JButton BackButton;
	JButton UpdateButton;
	JButton submit;
	JLabel RepeatedRollCall;
	JLabel IncorrectRollCallLabel;
	JLabel bg;
	String id;
	JTextField RollNoTextField;
	JTextField StudentNameTextField;
	String rollno;
	String dbUrl;
	//String StudentNameTextFieldData;
	JLabel RollNoLabel;
	JLabel NameLabel;
	Connection con;
	Statement st;
	ResultSet rs;
	Font f;
	int r;
	
	EditPanel(Container c,CardLayout cd,String id,String dbUrl) {  
		this.id=id;
		this.dbUrl=dbUrl;
        c1 =c;
		cd1=cd;
		Set_ContentPane();
		Create_BackGroundJLabel();
		Set_Font_And_Border();
		Add_BackButton_To_BackGroundJLabbel();
		Add_UpdateButton_To_BackGroundJLabbel();
		Add_RepeatedRollCall_To_BackGroundJLabbel();
		Add_IncorrectRollCallLabel_To_BackGroundJLabbel();
		Add_RollNoLabel_To_BackGroundJLabbel();
		Add_RollNoTextField_To_BackGroundJLabbel();
		Add_NameLabel_To_BackGroundJLabbel();
		Add_StudentNameTextField_To_BackGroundJLabbel();
        SetText_TO_RollNoTextField_and_StudentNameTextField();
        c.add("EditPanel",bg);
    }  
	
	public void Set_ContentPane() {
		c1.setBackground(Color.white);
		c1.setSize(500, 500);  
	    c1.setVisible(true); 
	}
	public void Create_BackGroundJLabel() {
		bg=new JLabel();
        bg.setBackground(Color.white);
	}
	public void Set_Font_And_Border() {
		f = new Font("calibri",Font.BOLD,30);
	}
	public void Add_BackButton_To_BackGroundJLabbel() {
		BackButton=new JButton("< Back");
        BackButton.setFont(f);
        BackButton.setBounds(1070,610,150,40);
        BackButton.setBackground(Color.gray);
        BackButton.addActionListener(this);
        bg.add(BackButton);
	}
	public void Add_UpdateButton_To_BackGroundJLabbel() {
		UpdateButton=new JButton("update");
        UpdateButton.setFont(f);
        UpdateButton.setBounds(670,300,150,40);
        UpdateButton.setBackground(Color.gray);
        UpdateButton.addActionListener(this);
        bg.add(UpdateButton);
	}
	public void Add_RepeatedRollCall_To_BackGroundJLabbel() {
		RepeatedRollCall =new JLabel("repeted roll call");
        RepeatedRollCall.setFont(f);
        RepeatedRollCall.setVisible(false);
        RepeatedRollCall.setBounds(670,350,300,40);
        RepeatedRollCall.setForeground(Color.red);
       	bg.add(RepeatedRollCall);   
	}
	public void Add_IncorrectRollCallLabel_To_BackGroundJLabbel() {
		IncorrectRollCallLabel =new JLabel("incorrect roll call");
       	IncorrectRollCallLabel.setFont(f);
       	IncorrectRollCallLabel.setVisible(false);
       	IncorrectRollCallLabel.setBounds(670,350,300,40);
       	IncorrectRollCallLabel.setForeground(Color.red);
        bg.add(IncorrectRollCallLabel);
	}
	public void Add_RollNoLabel_To_BackGroundJLabbel() {
		RollNoLabel=new JLabel("ROLL NO");
        RollNoLabel.setFont(f);
        RollNoLabel.setBounds(100,100,300,40);
        bg.add(RollNoLabel);
	}
	public void Add_RollNoTextField_To_BackGroundJLabbel() {
		RollNoTextField = new JTextField(16); 
        RollNoTextField.setFont(f);
        RollNoTextField.setBounds(300,100,300,40);
        RollNoTextField.addActionListener(this);
    	bg.add(RollNoTextField);
	}
	public void Add_NameLabel_To_BackGroundJLabbel() {
		NameLabel=new JLabel("NAME");
    	NameLabel.setFont(f);
    	NameLabel.setBounds(100,200,300,40);
        bg.add(NameLabel);
	}
	public void Add_StudentNameTextField_To_BackGroundJLabbel() {
		 StudentNameTextField = new JTextField(16); 
	     StudentNameTextField.setFont(f);
	     StudentNameTextField.setBounds(300,200,500,40);
	     StudentNameTextField.addActionListener(this);
	     bg.add(StudentNameTextField);		
	}
	public void SetText_TO_RollNoTextField_and_StudentNameTextField() {
		try {
        	con=DriverManager.getConnection(dbUrl);
			st= con.createStatement();
			rs = st.executeQuery("select * from register Where id = "+id);
			if(rs.next()) {
				StudentNameTextField.setText(rs.getString("name"));
				rollno=rs.getString("roll").substring(0, rs.getString("roll").length() - 2);
				RollNoTextField.setText(rollno);
			}
			st.close();
			con.close();
        }catch(Exception ex) {
			System.out.println(ex);
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==BackButton)
		{
			cd1.show(c1, "DetailsEdit");
		}
		if(ae.getSource()==UpdateButton)
		{
			try {
				con=DriverManager.getConnection(dbUrl);
				st= con.createStatement();
				
				try {
					Integer.parseInt(RollNoTextField.getText());
				 }
				catch (NumberFormatException nfe) {
					IncorrectRollCallLabel.setVisible(true);
					RepeatedRollCall.setVisible(false);
				    return;
				   	}
				rs = st.executeQuery("select * from register Where roll = "+RollNoTextField.getText());
				
				if(rs.next()&&!rollno.equals(RollNoTextField.getText())) {
					RepeatedRollCall.setVisible(true);
					IncorrectRollCallLabel.setVisible(false);
					return;
				}
				r=st.executeUpdate("update register set name='"+StudentNameTextField.getText()+"' where id="+id);
				r=st.executeUpdate("update register set roll='"+RollNoTextField.getText()+"' where id="+id);
				st.close();
				con.close();
	        }catch(Exception ex) {
	        	System.out.println(ex);
			}
			new DetailsEdit(c1,cd1,dbUrl);
			cd1.show(c1, "DetailsEdit");
		}
	}
}  