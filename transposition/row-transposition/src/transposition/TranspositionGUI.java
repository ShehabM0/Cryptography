package transposition;

import java.awt.EventQueue;

import javax.swing.*;
import java.io.*;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Desktop;

public class TranspositionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	
	private JLabel lblPath = new JLabel("Path");
	JLabel lblNoFile = new JLabel("New label");
	JLabel lblNoKey = new JLabel("New label");
	JComboBox comboBox = new JComboBox();
	
	private final String RESLUT_PATH1 = "/home/shehab/Materials/cs402/lab/resultsE.txt";
	File resultedFile1 = new File(RESLUT_PATH1);
	
	private final String RESLUT_PATH2 = "/home/shehab/Materials/cs402/lab/resultsD.txt";
	File resultedFile2 = new File(RESLUT_PATH2);
	String path = null;
	String k = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TranspositionGUI frame = new TranspositionGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TranspositionGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Row Transposition");
		
		JLabel title = new JLabel("Playfair Encryption/Decryption Program");
		title.setFont(new Font("Dialog", Font.BOLD, 12));
		title.setBounds(100, 10, 250, 15);
		contentPane.add(title);
		
		JLabel lblK = new JLabel("Enter a key value:");
		lblK.setBounds(115, 118, 127, 15);
		contentPane.add(lblK);
		
		textField = new JTextField();
		textField.setBounds(255, 116, 94, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblFile = new JLabel("Select a file:");
		lblFile.setBounds(115, 55, 127, 15);
		contentPane.add(lblFile);
		
		
		lblPath.setBounds(42, 85, 366, 15);
		contentPane.add(lblPath);
		lblPath.setVisible(false);
		
		JButton btnFile = new JButton("Upload..");
		btnFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // Select File.
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setCurrentDirectory(new File("/home/shehab"));
		        int result = fileChooser.showOpenDialog(fileChooser);

		        if(result == JFileChooser.APPROVE_OPTION) {
		        	File selectedFile = fileChooser.getSelectedFile();
		            path = selectedFile.getAbsolutePath();
		            lblPath.setVisible(true);
		            if(!path.contains(".txt")) {
		            	lblPath.setText("Error: file extension should be .txt!!");
		            	lblPath.setForeground(Color.RED);
		            	path = null;
		            }
		            else {
		            	lblPath.setText(path);
		            }
		        }
			}
		});
		btnFile.setBounds(255, 52, 94, 20);
		contentPane.add(btnFile);
		
		JLabel lblBox = new JLabel("Pick an option:");
		lblBox.setBounds(115, 171, 105, 15);
		contentPane.add(lblBox);
		
		comboBox.setBounds(255, 166, 94, 24);
		contentPane.add(comboBox);
		comboBox.addItem("encryption");
		comboBox.addItem("decryption");
		
		JButton btnNewButton = new JButton("Results");
		btnNewButton.setBounds(171, 222, 117, 25);
		contentPane.add(btnNewButton);
		
		lblNoFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoFile.setVisible(false);
		lblNoFile.setBounds(100, 198, 274, 15);
		contentPane.add(lblNoFile);
		
		lblNoKey.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoKey.setVisible(false);
		lblNoKey.setBounds(80, 145, 310, 15);
		contentPane.add(lblNoKey);
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean flag = true;
				k = textField.getText();
				
				if(k.isEmpty()) {
					lblNoKey.setVisible(true);
					lblNoKey.setText("Error: key value should'nt be empty");
					lblNoKey.setForeground(Color.RED);
					flag = false;
				} else {
					lblNoKey.setVisible(false);
				}
				
				if(path == null) {
					lblNoFile.setVisible(true);
					lblNoFile.setText("Error: no provided file!!");
					lblNoFile.setForeground(Color.RED);
					flag = false;
				} else {
					lblNoFile.setVisible(false);
				}
				
				if(!flag) return;
				
				String box = (String)comboBox.getSelectedItem();
				if(box == "encryption") case1();
				else case2();
				
				try {
					Desktop desk = Desktop.getDesktop();
					if(box == "encryption") {						
						desk.open(resultedFile1);
					} else {
						desk.open(resultedFile2);
					}
				} catch(IOException ioe) {
					System.out.println("Error Opening file");
				}
			}
		});
		
	}
	
	private void case1() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(RESLUT_PATH1));
	        BufferedReader reader = new BufferedReader(new FileReader(path));
	        
	        String line = reader.readLine();
	        while(line != null) {
	        	Transposition pf = new Transposition(k);
	            String resStr = pf.encrypt(line);
	            writer.write(resStr + '\n');
	            line = reader.readLine();
	        }
	        reader.close();
	        writer.close();
        } catch (IOException ioe) {
            System.out.println("Error reading file!!\nYou may entered wrong directory path or file doesn't exit");
        }
	}
	
	private void case2() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(RESLUT_PATH2));
	        BufferedReader reader = new BufferedReader(new FileReader(path));
	        
	        String line = reader.readLine();
	        while(line != null) {
	        	Transposition pf = new Transposition(k);
	            String resStr = pf.decrypt(line);
	            writer.write(resStr + '\n');
	            line = reader.readLine();
	        }
	        reader.close();
	        writer.close();
        } catch (IOException ioe) {
            System.out.println("Error reading file!!\nYou may entered wrong directory path or file doesn't exit");
        }
	}
	
}
