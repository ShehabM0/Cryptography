package hill;

import java.awt.EventQueue;

import javax.swing.*;
import java.io.*;

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

public class HillGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel lblPath = new JLabel("Path");
	JLabel lblNoFile = new JLabel("New label");
	JLabel lblNoKey = new JLabel("New label");
	JComboBox comboBox = new JComboBox();
	int[][] key = new int[3][3];
	
	private final String RESLUT_PATH1 = "/home/shehab/Materials/cs402/lab/resultsE.txt";
	File resultedFile1 = new File(RESLUT_PATH1);
	
	private final String RESLUT_PATH2 = "/home/shehab/Materials/cs402/lab/resultsD.txt";
	File resultedFile2 = new File(RESLUT_PATH2);
	
	String path = null;

	private JTextField textField_0;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HillGUI frame = new HillGUI();
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
	public HillGUI() {
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				key[i][j] = Integer.MIN_VALUE;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Ceaser");
		
		JLabel title = new JLabel("File Encryption/Decryption Program");
		title.setFont(new Font("Dialog", Font.BOLD, 12));
		title.setBounds(100, 10, 250, 15);
		contentPane.add(title);
		
		JLabel lblK = new JLabel("Enter a key matrix:");
		lblK.setBounds(115, 118, 133, 15);
		contentPane.add(lblK);
		
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
		lblBox.setBounds(115, 289, 105, 15);
		contentPane.add(lblBox);
		
		comboBox.setBounds(256, 284, 94, 24);
		contentPane.add(comboBox);
		comboBox.addItem("encryption");
		comboBox.addItem("decryption");
		
		JButton btnNewButton = new JButton("Results");
		btnNewButton.setBounds(173, 364, 117, 25);
		contentPane.add(btnNewButton);
		
		lblNoFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoFile.setVisible(false);
		lblNoFile.setBounds(100, 337, 274, 15);
		contentPane.add(lblNoFile);
		
		lblNoKey.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoKey.setVisible(false);
		lblNoKey.setBounds(80, 262, 310, 15);
		contentPane.add(lblNoKey);
		
		textField_0 = new JTextField();
		textField_0.setBounds(125, 145, 45, 19);
		contentPane.add(textField_0);
		textField_0.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(192, 145, 45, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(260, 145, 45, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(125, 176, 45, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(191, 176, 45, 19);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(260, 176, 45, 19);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(125, 213, 45, 19);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBounds(191, 213, 45, 19);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		textField_8 = new JTextField();
		textField_8.setBounds(260, 213, 45, 19);
		contentPane.add(textField_8);
		textField_8.setColumns(10);
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean flag = true;
				String kStr0 = textField_0.getText();
				String kStr1 = textField_1.getText();
				String kStr2 = textField_2.getText();
				
				String kStr3 = textField_3.getText();
				String kStr4 = textField_4.getText();
				String kStr5 = textField_5.getText();
				
				String kStr6 = textField_6.getText();
				String kStr7 = textField_7.getText();
				String kStr8 = textField_8.getText();
				
				if(!kStr0.isEmpty()) key[0][0] = Integer.parseInt(kStr0);
				if(!kStr1.isEmpty()) key[0][1] = Integer.parseInt(kStr1);
				if(!kStr2.isEmpty()) key[0][2] = Integer.parseInt(kStr2);
				
				if(!kStr3.isEmpty()) key[1][0] = Integer.parseInt(kStr3);
				if(!kStr4.isEmpty()) key[1][1] = Integer.parseInt(kStr4);
				if(!kStr5.isEmpty()) key[1][2] = Integer.parseInt(kStr5);

				if(!kStr6.isEmpty()) key[2][0] = Integer.parseInt(kStr6);
				if(!kStr7.isEmpty()) key[2][1] = Integer.parseInt(kStr7);
				if(!kStr8.isEmpty()) key[2][2] = Integer.parseInt(kStr8);
				
				
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						System.out.print(key[i][j]);
						if(key[i][j] == Integer.MIN_VALUE || key[i][j] < 0 || key[i][j] > 25)
							flag = false;
					}
					System.out.println();
				}
				
				
				if(path == null) {
					lblNoFile.setVisible(true);
					lblNoFile.setText("Error: no provided file!!");
					lblNoFile.setForeground(Color.RED);
					flag = false;
				} else {
					lblNoFile.setVisible(false);
				}
				if(!flag) {
					lblNoKey.setVisible(true);
					lblNoKey.setText("Error: key value should between 0 and 25!!");
					lblNoKey.setForeground(Color.RED);
				} else {
					lblNoKey.setVisible(false);
				}
				
				Hill hill = new Hill(key);
				flag = flag && hill.isInvertible(key);
				if(!flag) {
					lblNoKey.setVisible(true);
					lblNoKey.setText("Error: Matrix Key should be Invertible!!");
					lblNoKey.setForeground(Color.RED);
					return;
				}
				
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
	        	Hill hill = new Hill(key);
	            String resStr = hill.encrypt(line);
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
	        	Hill hill = new Hill(key);
	            String resStr = hill.decrypt(line); //rrlmwbkaspdh rfqhrg
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
