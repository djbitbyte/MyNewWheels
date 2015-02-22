import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
/**
 * This class allows the administrator to access the DB functions (search, add, update, delete).
 * 
 */

public class AdminFrame extends JFrame{
	CarTableModel ctm;
	JPanel mainPanel, tablePanel, infoPanel;
	DBCarConnector dbcc;
	JTable carTable;
	JTextField fieldBrand, fieldModel, fieldModelFullName, fieldTransmission, 
	           fieldFuelType, fieldPrice, fieldWebsite;
	JButton btnDelete, btnUpdate, btnAdd;
	
	public AdminFrame(String title, DBCarConnector dbcc){    // DB connection
		super(title);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		this.dbcc = dbcc;
		
		addMainPanel();
	
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void addMainPanel(){           // Frame layout 
		mainPanel = new JPanel(null);
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);
		addInfoPanel();
		addTablePanel();
		addButtonPanel();
	}
	
	private void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width, width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	public void addTable(){              //The contents of the table (Columns and rows)
		ctm = new CarTableModel();
		ctm.setColumnNamesAW(
				"Brand", 
				"Model", 
				"Model Full Name", 
				"Transmission", 
				"Fuel Type", 
				"Price",
				"Website");
		
		ctm.data = dbcc.getCarRecordsAdmin(this);
		ctm.fireTableStructureChanged();
		ctm.fireTableDataChanged();
		
		carTable = new JTable(ctm);
		carTable.setRowSorter(new TableRowSorter(ctm));
		carTable.setSize(new Dimension(1000, 607));
		carTable.setPreferredScrollableViewportSize(carTable.getSize());
		resizeColumnWidth(carTable);
		JScrollPane jsp = new JScrollPane(carTable);
		jsp.setSize(1000, 1000);
		tablePanel.add(jsp);
		
		carTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if (carTable.getSelectedRow() != -1) {
					String brand = carTable.getValueAt(
							carTable.getSelectedRow(), 0).toString();
					String model = carTable.getValueAt(
							carTable.getSelectedRow(), 1).toString();
					String fullname = carTable.getValueAt(
							carTable.getSelectedRow(), 2).toString();
					String transmission = carTable.getValueAt(
							carTable.getSelectedRow(), 3).toString();
					String fuelType = carTable.getValueAt(
							carTable.getSelectedRow(), 4).toString();
					String price = carTable.getValueAt(
							carTable.getSelectedRow(), 5).toString();
					String website = carTable.getValueAt(
							carTable.getSelectedRow(), 6).toString();
					fieldBrand.setText(brand);
					fieldModel.setText(model);
					fieldModelFullName.setText(fullname);
					fieldTransmission.setText(transmission);
					fieldFuelType.setText(fuelType);
					fieldPrice.setText(price);
					fieldWebsite.setText(website);
					btnAdd.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
				}
			}
		});
	}
	
	public void addTablePanel(){
		tablePanel = new JPanel();
		mainPanel.add(tablePanel, BorderLayout.EAST);
		addTable();
	}
	
	private JLabel createLabel(String labelTitle){
		JLabel label = new JLabel(labelTitle);
		label.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		label.setSize(new Dimension(400, 25));
		label.setMaximumSize(label.getSize());
		return label;
	}
	
	private JTextField createTextField(String title, int col){
		JTextField field = new JTextField();
		Dimension d = new Dimension();
		d.width = 150;
		d.height = 30;
		field.setPreferredSize(d);
		field.setMinimumSize(d);
		field.setMaximumSize(d);
		field.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		return field;
	}
	
	public void addInfoPanel(){
		infoPanel = new JPanel(null);
		
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		
		JLabel labelCarBrand = createLabel("Car Brand");
		fieldBrand = createTextField("brandField", 40);
		infoPanel.add(labelCarBrand);
		infoPanel.add(fieldBrand);
		
		JLabel labelCarModel = createLabel("Car Model");
		fieldModel = createTextField("modelField", 40);
		infoPanel.add(labelCarModel);
		infoPanel.add(fieldModel);
		
		JLabel labelModelFullName = createLabel("Model Full Name");
		fieldModelFullName = createTextField("modelFullNameField", 40);
		infoPanel.add(labelModelFullName);
		infoPanel.add(fieldModelFullName);
		
		JLabel labelTransmission = createLabel("Transmission");
		fieldTransmission = createTextField("transmissionField", 40);
		infoPanel.add(labelTransmission);
		infoPanel.add(fieldTransmission);
		
		JLabel labelFuelType = createLabel("Fuel Type");
		fieldFuelType = createTextField("fuelTypeField", 40);
		infoPanel.add(labelFuelType);
		infoPanel.add(fieldFuelType);
		
		JLabel labelPrice = createLabel("Price");
		fieldPrice = createTextField("priceField", 40);
		infoPanel.add(labelPrice);
		infoPanel.add(fieldPrice);
		
		JLabel labelWebsite = createLabel("Website");
		fieldWebsite = createTextField("websiteField", 40);
		infoPanel.add(labelWebsite);
		infoPanel.add(fieldWebsite);

		mainPanel.add(infoPanel, BorderLayout.WEST);
	}
	
	public JButton createButton(String title, String actionCommand){
		JButton button = new JButton(title);
		button.setSize(new Dimension(80, 25));
		button.setMaximumSize(button.getSize());
		AdminFrame local = this;
		button.setActionCommand(actionCommand);
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				if(ae.getActionCommand().equals("searchCar")){
					ctm.data = dbcc.getCarRecordsAdmin(local);
					ctm.fireTableDataChanged();
				}
				else if(ae.getActionCommand().equals("addNewCar")){
					if(dbcc.addNewCar(
							local.fieldBrand.getText(), 
							local.fieldModel.getText(),
							local.fieldModelFullName.getText(),
							local.fieldTransmission.getText(),
							local.fieldFuelType.getText(),
							local.fieldPrice.getText(),
							local.fieldWebsite.getText()))
						JOptionPane.showMessageDialog(local, 
								"New car record added successfully!");
					else
						JOptionPane.showMessageDialog(local, 
								"Invalid inputs!\n"
								+ "Please enter Car Brand, Car Model, Full Name and Price!");
					ctm.data = dbcc.getCarRecordsAdmin(local);
					ctm.fireTableDataChanged();
				}
				else if(ae.getActionCommand().equals("updateCarRecord")){
					String brand = carTable.getValueAt(carTable.getSelectedRow(), 0).toString();
					String model = carTable.getValueAt(carTable.getSelectedRow(), 1).toString();
					String fullname = carTable.getValueAt(carTable.getSelectedRow(), 2).toString(); 
					dbcc.deleteCarRecord(
							brand, 
							model,
							fullname);
					dbcc.addNewCar(
							local.fieldBrand.getText(), 
							local.fieldModel.getText(),
							local.fieldModelFullName.getText(),
							local.fieldTransmission.getText(),
							local.fieldFuelType.getText(),
							local.fieldPrice.getText(),
							local.fieldWebsite.getText());
					ctm.data = dbcc.getCarRecordsAdmin(local);
					ctm.fireTableDataChanged();
				}
				else if(ae.getActionCommand().equals("deleteCarRecord")){
					if(carTable.getSelectedRows().length != 0){
						if(JOptionPane.showConfirmDialog(local, 
								"Are you sure you want to make the deletion?", 
								"Confirm your decision!", 
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
							for(int i = 0; i < carTable.getSelectedRows().length; i++){
								String brand = carTable.getValueAt(carTable.getSelectedRows()[i], 0).toString();
								String model = carTable.getValueAt(carTable.getSelectedRows()[i], 1).toString();
								String fullname = carTable.getValueAt(carTable.getSelectedRows()[i], 2).toString();
								dbcc.deleteCarRecord(brand, model, fullname);
							}
						ctm.data = dbcc.getCarRecordsAdmin(local);
						ctm.fireTableDataChanged();
					}				
					else
						JOptionPane.showMessageDialog(local, 
								"Select car record that needs to be deleted!");
				}
				else if(ae.getActionCommand().equals("clearTextField")){
					fieldBrand.setText(null);
					fieldModel.setText(null);
					fieldModelFullName.setText(null);
					fieldTransmission.setText(null);
			        fieldFuelType.setText(null);
			        fieldPrice.setText(null);
			        fieldWebsite.setText(null);
			        btnAdd.setEnabled(true);
					btnUpdate.setEnabled(false);
					btnDelete.setEnabled(false);
					carTable.clearSelection();
					ctm.data = dbcc.getCarRecordsAdmin(local);
					ctm.fireTableDataChanged();
				}
				else if(ae.getActionCommand().equals("goBack")){
					dispose();
					AdminLoginFrame adm = new AdminLoginFrame();
					adm.setVisible(true);
				}
				
			}
		});
		
		return button;
	}
	
	public void addButtonPanel(){                                        // All of the buttons in this frame
		JPanel buttonPanel = new JPanel(null);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
	
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		JButton btnSearch = createButton("Search", "searchCar");
		buttonPanel.add(btnSearch);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		btnAdd = createButton("Add", "addNewCar");
		buttonPanel.add(btnAdd);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		btnUpdate = createButton("Update", "updateCarRecord");
		btnUpdate.setEnabled(false);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		btnDelete = createButton("Delete", "deleteCarRecord");
		btnDelete.setEnabled(false);
		buttonPanel.add(btnDelete);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		JButton btnClear = createButton("Clear", "clearTextField");
		buttonPanel.add(btnClear);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		JButton btnBack = createButton("Logout", "goBack");
		buttonPanel.add(btnBack);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
}
