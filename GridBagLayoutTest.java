import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class GridBagLayoutTestCopy extends GatherBook implements ActionListener {
	
	//Creating the objects.
	
	InsertDataDb connFromInsert = new InsertDataDb();
	
	JFrameTest frame = new JFrameTest();
	JPanel panel = new JPanel(new GridBagLayout());	
	GridBagConstraints c = new GridBagConstraints();

	JLabel labelUpper = new JLabel("A super awesome software for searching antique books DBs!");
	
	
	JLabel labelOne = new JLabel("Update Abe's DB here:");
	JLabel labelTwo = new JLabel("Update Ebay's DB here:");
	JLabel labelThree = new JLabel("Get the average of prices about Abe DB:");
	JLabel labelFour = new JLabel("Get the average for Ebay's sold items price:");
	JLabel labelFive = new JLabel("Search by title - enter a term like \"Medicine\":");
	JLabel labelSix = new JLabel("Search by year:");
	JLabel labelSeven = new JLabel("Insert starting year, like \"1700\":");
	JLabel labelEight = new JLabel("Insert ending year, like \"1830\":");
	
	JButton buttonOne = new JButton("Update DB");
	JButton buttonBlank = new JButton("PROVA BLANK");
	JButton buttonTwo = new JButton("Update DB");
	JButton buttonThree = new JButton("Get average");
	JButton buttonFour = new JButton("Get average");
	JButton buttonSearchTerm = new JButton("Search");
	JButton buttonSearchYear = new JButton("Search");
	
	JTextField textOne = new JTextField(9);														//The number as argument means the number of "columns".	
	JTextField textTwoStart = new JTextField(5);
	JTextField textTwoEnd = new JTextField(5);
	
	JTextArea resultsField = new JTextArea(3, 43);												//The large empty field where we will display search results.
	
	//LOADING POPUP MODAL WITH ANIMATED GIF (INITIALIZATION)
	
	JFrame popupFrame = new JFrame("BorderLayoutDemo");											//We create the frame that will contains the dialog.
	JPanel popupPanel = new JPanel(new BorderLayout());
	JDialog loading = new JDialog(popupFrame);													//We specified the parent component of the new JDialog object as its argument ("popupFrame").
	ImageIcon loadingImg = new ImageIcon("ajax-loader.gif");									//We create a new image object (in this case an animated .gif).
	JLabel circleLoad = new JLabel("Performing update of DB... ", loadingImg, JLabel.CENTER);	//We create a new JLabel object with a text and the imageIcon object as parameters.
	
	
	
	//SQLITE COMMANDS
	String sqlAvgAbe = "SELECT ROUND(AVG(NULLIF(PRICE_TAG_NOT_AUCTION, 0))) FROM MAPS";
	String sqlAvgEbay = "SELECT ROUND(AVG(NULLIF(AUCTION_SOLD_AT, 0))) FROM MAPS";
	
	
	GridBagLayoutTestCopy() {
		
		//SETTING FONT SPECIFICS FOR THE LABELS
		
		labelUpper.setFont(new Font("", Font.PLAIN, 18));
		
		labelOne.setFont(new Font("", Font.PLAIN, 14));
		labelTwo.setFont(new Font("", Font.PLAIN, 14));
		labelThree.setFont(new Font("", Font.PLAIN, 14));
		labelFour.setFont(new Font("", Font.PLAIN, 14));
		labelFive.setFont(new Font("", Font.PLAIN, 14));
		labelSix.setFont(new Font("", Font.PLAIN, 14));
		labelSeven.setFont(new Font("", Font.PLAIN, 14));
		labelEight.setFont(new Font("", Font.PLAIN, 14));
		
		resultsField.setEditable(false);														//We have to make it NOT editable by the user because it have only to show data as output.		
		
		//ACTIVATE THE BUTTONS FOR THE actionPerformed METHOD
		
		buttonOne.addActionListener(this);
		buttonOne.setActionCommand("searchAbe");					//We set a specific command name "searchAbe" to use then in the actionPerformed method to bound the button to a specific action.
																								//So every button will have its own action.		
		buttonTwo.addActionListener(this);
		buttonTwo.setActionCommand("searchEbay");
		
		buttonThree.addActionListener(this);
		buttonThree.setActionCommand("calcAvgAbe");
		
		buttonFour.addActionListener(this);
		buttonFour.setActionCommand("calcAvgEbay");
		
		buttonSearchTerm.addActionListener(this);
		buttonSearchTerm.setActionCommand("searchTerm");
		
		buttonSearchYear.addActionListener(this);
		buttonSearchYear.setActionCommand("searchYear");
		
		
		//POSITIONING OF THE ELEMENTS INSIDE THE LAYOUT
															
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(20,0,20,0);					//Element padding
		panel.add(labelUpper, c);
		
		c.anchor = GridBagConstraints.LINE_START;			//Here we set the labels (the first one and the every one below as the var is then never reset) to be placed in the left part of the column.
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;									//Resetting the value to default for the below elements.
		c.weightx = 0.0;
		c.insets = new Insets(10,20,0,0);
		panel.add(labelOne, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 1;
		//c.weightx = 1.0;		
		c.insets = new Insets(10,75,0,20);
		panel.add(buttonOne, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,20,0,0);
		panel.add(labelTwo, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(10,75,0,20);
		panel.add(buttonTwo, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10,20,0,0);
		panel.add(labelThree, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(10,75,0,20);
		panel.add(buttonThree, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10,20,0,0);
		panel.add(labelFour, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(10,75,0,20);
		panel.add(buttonFour, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(10,20,0,0);
		panel.add(labelFive, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 5;
		c.insets = new Insets(10,75,0,20);
		panel.add(textOne, c);		
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 6;
		buttonSearchTerm.setPreferredSize(new Dimension(80, 20));		//"setPreferredSize()" is a method of the class "JComponent", inherited by the class "JPanel". It changes the dimension of an element
		c.insets = new Insets(10,75,0,20);								//(in this case a button).
		panel.add(buttonSearchTerm, c);		
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 7;
		c.insets = new Insets(10,20,0,0);
		panel.add(labelSix, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 8;
		c.insets = new Insets(10,20,0,0);
		panel.add(labelSeven, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 8;
		c.insets = new Insets(10,75,0,20);
		panel.add(textTwoStart, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 9;
		c.insets = new Insets(10,20,0,0);
		panel.add(labelEight, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 9;
		c.insets = new Insets(10,75,0,20);
		panel.add(textTwoEnd, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 10;
		buttonSearchYear.setPreferredSize(new Dimension(80, 20));
		c.insets = new Insets(10,75,0,20);
		panel.add(buttonSearchYear, c);
		
		c.gridx = 0;
		c.gridy = 11;
		c.ipady = 300;													//It makes this component tall (internal padding).		
		c.gridwidth = 2;
		c.insets = new Insets(20,20,30,20);
		
		resultsField.setLineWrap(true);									//Fundamental to break up too long lines in multi lines.
		
		JScrollPane scrollableTextArea = new JScrollPane(resultsField);
		
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		panel.add(scrollableTextArea, c);								//It adds the "scrollableTextArea" (which contains even the "resultsField" text area) in the panel and positions it based on data
																		//set with the insets ("c" - based on class "GridBagConstraints").
		frame.add(panel);
		
		frame.pack();
		frame.setLocationRelativeTo(null);								//It's used to let appear the window at the center of the screen.
		
		//LOADING POPUP MODAL WITH ANIMATED GIF (METHODS)
		
		loading.setUndecorated(true);
		circleLoad.setFont(new Font("", Font.PLAIN, 16));				//We keep the default type of font but we increase its size.
		popupPanel.add(circleLoad, BorderLayout.CENTER);
		loading.add(popupPanel);
	    loading.pack();
	    loading.setLocationRelativeTo(popupFrame);
	    loading.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    loading.setModal(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String str = e.getActionCommand();								//Here we get the action command from the buttons that used "setActionCommand()" method.
		switch(str) {													//It "switches" between cases and when it finds one corresponding to the "str" var. it will runs it.
			case "searchAbe":
				Boolean popupButton1 = false;
				GatherBook AbeWebClient = new AbeBooksSub();
				
				try {
					
					SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
					    @Override
					    protected Boolean doInBackground() throws Exception {
					    	
					    	AbeWebClient.getElements("https://www.abebooks.com/collections/sc/atlases/2dzei9jydsjGsGfJtSsP4H?cm_sp=ccbrowse-_-p0-_-collections");
					    	
					    	return true;
					    }
					    
					    protected void done() {
					  
					    	loading.dispose();
					    }
						
					};
					
					worker.execute();
					loading.setVisible(true);
					
				    try {
				        worker.get();
				        popupButton1 = true;
				    } catch (Exception e1) {
				        e1.printStackTrace();
				    }
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (popupButton1 == true) {
					JOptionPane.showMessageDialog(null, "Abe DB correctly updated");
				}
				
				break;
				
			case "searchEbay":												//Bound to "buttonTwo".

				Boolean popupButton2 = false;
				GatherBook EbaySoldMapWebClient = new EbaySoldMapSub();
				try {
					
					SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
					    @Override
					    protected Boolean doInBackground() throws Exception {
					    	
					    	EbaySoldMapWebClient.getElements("https://www.ebay.com/sch/29223/i.html?_from=R40&_nkw=map&LH_TitleDesc=0&_sop=12&_sadis=200&LH_Complete=1&LH_Complete=1&LH_Sold=1");
							
					    	return true;
					    }
					    
					    protected void done() {
					  
					    	loading.dispose();
					    }
						
					};
					
					worker.execute();
					loading.setVisible(true);
					
				    try {
				        worker.get();
				        popupButton2 = true;
				    } catch (Exception e1) {
				        e1.printStackTrace();
				    }
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (popupButton2 == true) {
					JOptionPane.showMessageDialog(null, "Ebay DB correctly updated");
				}
				
				break;
				
			case "calcAvgAbe":												//Bound to "buttonThree".

				Boolean popupButton3 = false;
				PriceAvgCalc avgCalcAbe = new PriceAvgCalc();
				try {
					avgCalcAbe.selectPrice(sqlAvgAbe);
					
					String avgConverted = String.valueOf(d1);				//Here is the same, but separating methods for building up the final string with text and var text.
					resultsField.setText("Average of Abe's prices is: $"+ avgConverted);
					
					popupButton3 = true;
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (popupButton3 == true) {
					JOptionPane.showMessageDialog(null, "Average calculation for Abe's prices done");
				}
				
				break;
				
			case "calcAvgEbay":												//Bound to "buttonThree".

				Boolean popupButton4 = false;
				PriceAvgCalc avgCalcEbay = new PriceAvgCalc();
				try {
					avgCalcEbay.selectPrice(sqlAvgEbay);
					
					String avgConverted = String.valueOf(d1);
					resultsField.setText("Average of Ebay's prices is: $"+ avgConverted);
					
					popupButton4 = true;
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (popupButton4 == true) {
					JOptionPane.showMessageDialog(null, "Average calculation for Ebays's prices done");
				}
				
				break;
				
			case "searchTerm":												//Bound to "buttonFour".

				Boolean popupButton5 = false;
				String searchTerm = textOne.getText();
				String searchTermCommand = "SELECT * FROM MAPS WHERE TITLE LIKE '%"+searchTerm+"%'";
				
				try (Connection conn = connFromInsert.connect();
			        Statement stmt  = conn.createStatement();
			        ResultSet rs    = stmt.executeQuery(searchTermCommand)) {
					System.out.println(rs.getString(1));					//We have to select by column (there is only 1 column from the resultSet) because with the name "AUCTION_SOLD_AT" it doesn't finds
																			//anything.					
					
					
					resultsField.setText("Results for term \""+ searchTerm +"\":\n\n");		//We print out of the while loop the starting string that have to be wrote once in the beginning.
																							//We use "\n" to create a new line so when below we use the "append" method, the first line will go in a new 
																							//line below; we then use again "\n" because we want to leave a blank line between title and results.
					
					while (rs.next()) {
		                System.out.println(rs.getString(1));
		                resultsField.append((rs.getString(1))+"\n");				//Here with ".append()" method instead, we append to the set resultsField JTextArea in each line the results,
		                															//that would be set in each new line thank to the "\n".
		            }
					
					popupButton5 = true;
				} catch(SQLException er) {
					System.out.println(er.getMessage());
					JOptionPane.showMessageDialog(null, "No results found for term \""+ searchTerm +"\"");
				}
				
				if (popupButton5 == true) {
					JOptionPane.showMessageDialog(null, "Search for term \""+ searchTerm +"\" done");
				}
				
				break;
				
			case "searchYear":													//Bound to "buttonFour".

				Boolean popupButton6 = false;
				String startingYearText = textTwoStart.getText();
				String endingYearText = textTwoEnd.getText();
				
				String searchYearRangeCommand = "SELECT * FROM MAPS WHERE YEAR BETWEEN "+startingYearText+" AND "+endingYearText+"";
				
				try (Connection conn = connFromInsert.connect();
			        Statement stmt  = conn.createStatement();
			        ResultSet rs    = stmt.executeQuery(searchYearRangeCommand)) {
					System.out.println(rs.getString(1));
					resultsField.setText("Results for range search \""+ startingYearText +"\"-"+endingYearText+"\":\n\n");
					
					while (rs.next()) {
		                System.out.println(rs.getString(1));
		                resultsField.append((rs.getString(1))+"\n");
		            }
					
					popupButton6 = true;
				} catch(SQLException er) {
					System.out.println(er.getMessage());
					JOptionPane.showMessageDialog(null, "No results found for years range \""+ startingYearText +"\"-\""+endingYearText+"\"");
				}
				
				if (popupButton6 == true) {
					JOptionPane.showMessageDialog(null, "Search for years range \""+ startingYearText +"\"-\""+endingYearText+"\" done");
				}
				
				break;
				
		}
		
	}
	
	public static void main(String[] args) {
		
		new GridBagLayoutTestCopy();
		
	}
}
