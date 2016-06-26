import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FileBrowser extends JPanel implements ActionListener {
	JLabel label         = new JLabel("File List");
	JButton newFile      = new JButton("New File");
	JButton open         = new JButton("Open");
	JTextField newFileTx = new JTextField(11);
	ButtonGroup group;
	File directory;
	
	public FileBrowser(String dir){
		directory = new File(dir);
		//make a new directory if directory doesn't exist
		directory.mkdir();
		JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length+3,1));
		fileList.add(label);
		group = new ButtonGroup();
		for (File file:directory.listFiles()){
			JRadioButton radio = new JRadioButton(file.getName());
			radio.setActionCommand(file.getName());
			group.add(radio);
			fileList.add(radio);
			
		}
		JPanel panel = new JPanel();
		panel.add(newFileTx);
		panel.add(newFile);
		
		newFile.addActionListener(this);
		open.addActionListener(this);
		
		fileList.add(open);
		fileList.add(panel);
		
		add(fileList);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Login login = (Login) getParent();
		if (e.getSource()==open){
			login.add(new Editor(directory.getName()+"\\"+group.getSelection().getActionCommand()),"editor"); 
			login.card.show(login,"editor");
		}
		if(e.getSource()==newFile){
			String file = directory.getName()+"\\"+newFileTx.getText()+".txt";
			//
			if(newFileTx.getText().length()> 0 && !(new File(file).exists())){
				login.add(new Editor(file),"editor");
				login.card.show(login, "editor");
				
			}
		}
		
		
		
	}
}
