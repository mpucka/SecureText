import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener{
	//username
	JLabel userLogin       = new JLabel ("Username: ");
	JTextField  userTF     = new JTextField();
	
	//password
	JLabel password        = new JLabel ("Password: ");
	JPasswordField  passTF = new JPasswordField();
	
	//the panels
	JPanel loginPanel      = new JPanel(new GridLayout(3,2));
	JPanel panel           = new JPanel();
	
	//the buttons
	JButton login          = new JButton("Login");
	JButton register       = new JButton("Register");
	
	CardLayout card;
	
	Login(){
		setLayout(new CardLayout());
		loginPanel.add(userLogin);
		loginPanel.add(userTF);
		loginPanel.add(password);
		loginPanel.add(passTF);
		
		login.addActionListener(this);
		register.addActionListener(this);
		
		loginPanel.add(login);
		loginPanel.add(register);
		
		panel.add(loginPanel);
		add(panel, "login");
		
		card = (CardLayout)getLayout();
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==login){
			try {
				BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
				String myPassword = null;
				String line     = input.readLine();
				
				while(line !=null){
					StringTokenizer token = new StringTokenizer(line);
					
					//if users match, the next token will be the passwords we need to confirm
					if(userTF.getText().equals(token.nextToken()))
						myPassword = token.nextToken();
					line = input.readLine();
				}
				input.close();
				
				//Create an encrypted password
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				digest.update(new String (passTF.getPassword()).getBytes());
				
				byte byteData []     = digest.digest();
				StringBuffer sBuffer = new StringBuffer(); //will hold the encrypted password
				
				for (int i = 0; i < byteData.length;i++)
					//Encrypting the password by running the hash
					sBuffer.append(Integer.toString((byteData[i] & 0xFF)+ 0x100,16).substring(1));
				
				//password from the file is == as the encrypted password
				if(myPassword.equals(sBuffer.toString())){
					add(new FileBrowser(userTF.getText()),"fb");
					card.show(this, "fb");
				}
				
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
		if(e.getSource()==register){
			add(new Register(), "register");
			card.show(this, "register");
		}
		
	}
	
	public static void main (String [] args){
		JFrame frame = new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		Login login = new Login();
		frame.add(login);
		frame.setVisible(true);
		
		
	}

}
