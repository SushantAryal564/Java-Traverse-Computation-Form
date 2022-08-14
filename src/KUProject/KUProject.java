package KUProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.CardLayout;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;

public class KUProject {

	private JFrame frame;
	private JTextField distanceField;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTable tableUserData;
	private JTextField angleField;
	private int count = 0;
	private int stationNumber = 1000;
	private double degree;
	private double minutes;
	private double seconds;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KUProject window = new KUProject();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KUProject() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0,0,1400,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(230, 230, 250));
		panel.setBackground(new Color(245, 255, 250));
		panel.setBorder(new LineBorder(new Color(95, 158, 160), 5, true));
		panel.setBounds(0,0,1386,763);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel title_Panel = new JPanel();
		title_Panel.setBounds(10, 10, 1366, 77);
		title_Panel.setForeground(new Color(230, 230, 250));
		title_Panel.setBorder(new LineBorder(new Color(95, 158, 160), 5, true));
		title_Panel.setBackground(new Color(245, 255, 250));
		panel.add(title_Panel);
		title_Panel.setLayout(null);
		
		JLabel title_label = new JLabel("Traverse Computation Form");
		title_label.setFont(new Font("Rockwell", Font.BOLD, 50));
		title_label.setBounds(10, 10, 1346, 57);
		title_Panel.add(title_label);
		
		JPanel table_Panel_UserData = new JPanel();
		table_Panel_UserData.setForeground(new Color(230, 230, 250));
		table_Panel_UserData.setBorder(new LineBorder(new Color(95, 158, 160), 5, true));
		table_Panel_UserData.setBackground(new Color(245, 255, 250));
		table_Panel_UserData.setBounds(10, 97, 902, 498);
		panel.add(table_Panel_UserData);
		table_Panel_UserData.setLayout(null);
		
		JScrollPane scrollPaneUserData = new JScrollPane();
		scrollPaneUserData.setBounds(10, 10, 882, 478);
		table_Panel_UserData.add(scrollPaneUserData);
		
		
		
		tableUserData = new JTable();
		tableUserData.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"S.N", "Station", "Obs. Angle", "Distance", "Bearing", "\u2206E", "Easting Corr.(m)", "\u2206N", "Norting corr.", "Easting", "Norting"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, String.class, String.class, Object.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableUserData.getColumnModel().getColumn(0).setPreferredWidth(32);
		tableUserData.getColumnModel().getColumn(1).setPreferredWidth(43);
		tableUserData.getColumnModel().getColumn(3).setPreferredWidth(68);
		scrollPaneUserData.setViewportView(tableUserData);
		
		JPanel data_Panel = new JPanel();
		data_Panel.setForeground(new Color(230, 230, 250));
		data_Panel.setBorder(new TitledBorder(new LineBorder(new Color(95, 158, 160), 5), "Add Data To Table", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		data_Panel.setBackground(new Color(245, 255, 250));
		data_Panel.setBounds(10, 605, 913, 148);
		panel.add(data_Panel);
		data_Panel.setLayout(null);
		
		JLabel angle_label = new JLabel("Angle Observed:");
		angle_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		angle_label.setBounds(10, 20, 146, 23);
		data_Panel.add(angle_label);
		
		JLabel distance_label = new JLabel("Distance:");
		distance_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		distance_label.setBounds(10, 53, 100, 23);
		data_Panel.add(distance_label);
		
		distanceField = new JTextField();
		distanceField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		distanceField.setColumns(10);
		distanceField.setBounds(93, 53, 84, 19);
		data_Panel.add(distanceField);
		
		JButton RecordAddButton = new JButton("Add Record");
		RecordAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableUserData.getModel();
				model.addRow(new Object[] {
						increaseCount(),
						increaseStation(),
						decimalCoordinate(),
						distanceField.getText(),
//						getBearing(getPreviousIncludedAngle(),getPreviousBearingAngle()),
				});
				if(tableUserData.getSelectedRow() == -1) {
					if(tableUserData.getRowCount()==0) {
						JOptionPane.showMessageDialog(null, "Membership Update confirmed","Membership Management System",JOptionPane.OK_OPTION);
					}
				}
			}
		});
		RecordAddButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		RecordAddButton.setBounds(518, 20, 146, 35);
		data_Panel.add(RecordAddButton);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				angleField.setText("");
				distanceField.setText("");
			}
		});
		resetButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		resetButton.setBounds(722, 20, 146, 35);
		data_Panel.add(resetButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableUserData.getModel();
				if(tableUserData.getSelectedRow() == -1) {
					if(tableUserData.getRowCount()==0) {
						JOptionPane.showMessageDialog(null, "No data to delete","Traverse Computation Form",JOptionPane.OK_OPTION);
						
					}else {
						JOptionPane.showMessageDialog(null, "Select a row to delete","Traverse Computation Form",JOptionPane.OK_OPTION);
					}
				}else {
					model.removeRow(tableUserData.getSelectedRow());
				}
			}
		});
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		deleteButton.setBounds(518, 65, 146, 35);
		data_Panel.add(deleteButton);
		
		JButton printButton = new JButton("Print");
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tableUserData.print();
				}catch(java.awt.print.PrinterException er) {
					System.err.format("No Printer Found",er.getMessage());
				}
			}
		});
		printButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		printButton.setBounds(722, 65, 146, 35);
		data_Panel.add(printButton);
		
		angleField = new JTextField();
		angleField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		angleField.setColumns(10);
		angleField.setBounds(149, 20, 186, 19);
		data_Panel.add(angleField);
		
		JPanel userInfo_Panel = new JPanel();
		userInfo_Panel.setForeground(new Color(230, 230, 250));
		userInfo_Panel.setBorder(new LineBorder(new Color(95, 158, 160), 5, true));
		userInfo_Panel.setBackground(new Color(245, 255, 250));
		userInfo_Panel.setBounds(933, 605, 443, 148);
		panel.add(userInfo_Panel);
		userInfo_Panel.setLayout(null);
		
		JLabel computedBy_label = new JLabel("Computed By: ");
		computedBy_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		computedBy_label.setBounds(10, 10, 123, 23);
		userInfo_Panel.add(computedBy_label);
		
		JLabel date_label = new JLabel("Date:");
		date_label.setFont(new Font("Tahoma", Font.BOLD, 16));
		date_label.setBounds(10, 51, 59, 23);
		userInfo_Panel.add(date_label);
		
		JLabel group_Label = new JLabel("Group:");
		group_Label.setFont(new Font("Tahoma", Font.BOLD, 16));
		group_Label.setBounds(10, 87, 66, 23);
		userInfo_Panel.add(group_Label);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_6.setColumns(10);
		textField_6.setBounds(143, 10, 210, 31);
		userInfo_Panel.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_7.setColumns(10);
		textField_7.setBounds(143, 47, 210, 31);
		userInfo_Panel.add(textField_7);
		
		textField_8 = new JTextField();
		textField_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_8.setColumns(10);
		textField_8.setBounds(143, 83, 210, 31);
		userInfo_Panel.add(textField_8);
	}
	public int increaseCount() {
		count = count+1;
		return count;
	}
	public int increaseStation() {
		stationNumber = stationNumber+1;
		return stationNumber;
	}
	public void getDegree() {
		String input = angleField.getText();
		int degreePosition = input.indexOf("°");
		int minuteSymbol = input.indexOf("′");
		int secondSymbol = input.indexOf("″");
		degree =Double.parseDouble(input.substring(0, degreePosition));
		minutes = Double.parseDouble(input.substring(degreePosition+1, minuteSymbol));
		seconds = Double.parseDouble(input.substring(minuteSymbol+1, secondSymbol));
	}
	public double decimalCoordinate() {
		getDegree();
		 DecimalFormat df = new DecimalFormat("#.#####");
		 df.setRoundingMode(RoundingMode.CEILING);
		double decimal = (((minutes*60)+seconds)/(60*60));
		double answer = decimal+degree;
		return Double.parseDouble(df.format(answer));
	}
	public double getBearing(double includedAngle, double bearing) {
		double backBearing=0.0;
		if(bearing > 180) {
			backBearing = bearing-180;
		}
		else if (bearing < 180) {
			backBearing = bearing+180;
		}
		double linebearing = backBearing - includedAngle;
		return linebearing;
	}
}
