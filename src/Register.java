import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JPanel implements ActionListener{
	
	JLabel    username = new JLabel("Choose a username");
	JTextField userTF  = new JTextField();
	
	JLabel    password    = new JLabel("Password");
	JPasswordField passTF = new JPasswordField();
	
	JLabel    passwordConfirm    = new JLabel("Confirm Password");
	JPasswordField passConfirmTF = new JPasswordField();
	
	JButton register = new JButton("Register");
	JButton cancel   = new JButton("Cancel");
	
	Register (){
		JPanel registerPanel = new JPanel();
		
		registerPanel.setLayout(new GridLayout(4,2));
		registerPanel.add(username);
		registerPanel.add(userTF);
		registerPanel.add(password);
		registerPanel.add(passTF);
		registerPanel.add(passwordConfirm);
		registerPanel.add(passConfirmTF);
		registerPanel.add(register);
		registerPanel.add(cancel);
		
		register.addActionListener(this);
		cancel.addActionListener(this);
		add(registerPanel);
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==register && passTF.getPassword().length > 0 && userTF.getText().length()>0){
			String myPassword   = new String (passTF.getPassword());
			String ConfPassword = new String (passConfirmTF.getPassword());
			if(myPassword.equals(ConfPassword)){
				try {
					BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
					String line          = input.readLine();
					while (line != null){
						
						//to get the username
						StringTokenizer token = new StringTokenizer(line);
						if(userTF.getText().equals(token.nextToken())){
							System.out.println("This user already exists");
							return;
						}
						line = input.readLine();
							
					}
					input.close();
					
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					digest.update(myPassword.getBytes());
					
					byte byteData []     = digest.digest();
					StringBuffer sBuffer = new StringBuffer();
					
					for (int i = 0; i < byteData.length;i++)
						//Encrypting the password
						sBuffer.append(Integer.toString((byteData[i] & 0xFF)+ 0x100,16).substring(1));
					
					BufferedWriter output = new BufferedWriter(new FileWriter("passwords.txt", true));
					
					output.write(userTF.getText()+" "+sBuffer.toString()+"\n");
					output.close();
					
					Login login = (Login) getParent();
					login.card.show(login, "login");
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		}
		if(e.getSource()==cancel){
			Login login = (Login) getParent();
			login.card.show(login, "login");
		}		
	}
}
