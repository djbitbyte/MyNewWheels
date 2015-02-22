import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
/**
 * This is the frame that is for the users. It displays the search functions.
 */
public class UserFrame extends JFrame{
	JPanel mainPanel, filterPanel, tablePanel;
	JLabel mainLabel;
	JComboBox carBrand, carModel, startPrice, endPrice, t, ft;
	DBCarConnector dbcc;
	CarTableModel ctm;
	
	UserFrame(String title, DBCarConnector dbcc){
		super(title);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		this.dbcc = dbcc;
		
		addMainPanel();
		
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void addMainPanel(){
		mainPanel = new JPanel(null);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		setContentPane(mainPanel);
		addFilterPanel();
		addTablePanel();
	}
	
	public void addLabel(){
		JLabel mainLabel = new JLabel("Search your new car here!");
		mainLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		mainLabel.setSize(new Dimension(400, 25));
		mainLabel.setMaximumSize(mainLabel.getSize());
		mainLabel.setAlignmentY(JComponent.TOP_ALIGNMENT);
		filterPanel.add(mainLabel);
	}
	
	public void addCarBrand(){
		Vector<String> brand = new Vector<String>();
		brand.add("Brand (all)");
		brand.addAll(dbcc.getCarBrand());
		
		carBrand = new JComboBox(brand);
		carBrand.setSize(new Dimension(400, 25));
		carBrand.setMaximumSize(carBrand.getSize());
		carBrand.setAlignmentY(JComponent.TOP_ALIGNMENT);
		filterPanel.add(carBrand);
		carBrand.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				addCarModel();
			}
		});
	}
	
	public String getSelectedBrand(){
		String selectedBrand = carBrand.getSelectedItem().toString();
		return selectedBrand;
	}
	
	public void addCarModel(){
		Vector<String> model = dbcc.getCarModel(this, "Model (all)");
		if(carModel == null) {
			carModel = new JComboBox(model);
			carModel.setSize(new Dimension(400, 25));
			carModel.setMaximumSize(carModel.getSize());
			carModel.setAlignmentY(JComponent.TOP_ALIGNMENT);
			filterPanel.add(carModel);
		}
		else {
			carModel.removeAllItems();
			for(int i = 0; i < model.size(); i++)
				carModel.addItem(model.get(i));
		}
	}
	
	public void addStartPrice(){
		Vector<String> sp = new Vector<String>();
		sp.add("Start Price (kr)");
		for(int i = 1; i < 30; i++)
			sp.add(String.valueOf(50000*i));
		startPrice = new JComboBox(sp);
		startPrice.setSize(new Dimension(400, 25));
		startPrice.setMaximumSize(startPrice.getSize());
		filterPanel.add(startPrice);
	}
	
	public void addEndPrice(){
		Vector<String> ep = new Vector<String>();
		ep.add("End Price (kr)");
		for(int i = 1; i < 35; i++)
			ep.add(String.valueOf(50000*i));
		endPrice = new JComboBox(ep);
		endPrice.setSize(new Dimension(400, 25));
		endPrice.setMaximumSize(endPrice.getSize());
		filterPanel.add(endPrice);
	}
	
	public void addTransmission(){
		Vector<String> transmission = new Vector<String>();
		transmission.add("Transmission");
		transmission.add("Auto");
		transmission.add("Manual");
		t = new JComboBox(transmission);
		t.setSize(new Dimension(400, 25));
		t.setMaximumSize(t.getSize());
		filterPanel.add(t);
	}
	
	public void addFuelType(){
		Vector<String> fuelType = new Vector<String>();
		fuelType.add("Fuel Type");
		fuelType.add("Bensin");
		fuelType.add("Diesel");
		ft = new JComboBox(fuelType);
		ft.setSize(new Dimension(400, 25));
		ft.setMaximumSize(ft.getSize());
		ft.setAlignmentY(JComponent.TOP_ALIGNMENT);
		filterPanel.add(ft);
	}
	
	public void addButton(){
		JButton search = new JButton("Search");
		search.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		search.setSize(new Dimension(80, 25));
		search.setMaximumSize(search.getSize());
		filterPanel.add(search);
		UserFrame local = this;
		search.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){			
				ctm.data = dbcc.getCarRecords(local);
				ctm.fireTableDataChanged();
			}
		});
		
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JButton back = new JButton("Back");
		back.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		back.setSize(new Dimension(80, 25));
		back.setMaximumSize(back.getSize());
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){			
				dispose();
				StartFrame frmf = new StartFrame();
				frmf.setVisible(true);
			}
		});
		filterPanel.add(back);
	}
	
	public void addFilterPanel(){
		filterPanel = new JPanel(null);
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
		filterPanel.setBorder(new EmptyBorder(new Insets(0, 10, 0, 5)));
		filterPanel.setAlignmentY(JComponent.TOP_ALIGNMENT);
		mainPanel.add(filterPanel);
		addLabel();
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		addCarBrand();
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		addCarModel();
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		addStartPrice();
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		addEndPrice();
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		addTransmission();
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		addFuelType();
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		addButton();
	}
	
	public void addTable(){
		ctm = new CarTableModel();
		ctm.setColumnNamesMW("Brand", "Model", "Transmission", "Fuel Type", "Price");
		
		ctm.data = dbcc.getCarRecords(this);
		ctm.fireTableStructureChanged();
		ctm.fireTableDataChanged();
		
		JTable carTable = new JTable(ctm);
		carTable.setRowSorter(new TableRowSorter(ctm));
		carTable.setSize(new Dimension(1000, 607));
		carTable.setPreferredScrollableViewportSize(carTable.getSize());
		JScrollPane jsp = new JScrollPane(carTable);
		tablePanel.add(jsp);
	}
	
	public void addTablePanel(){
		tablePanel = new JPanel();
		mainPanel.add(tablePanel);
		addTable();
	}
}
