import javax.swing.table.AbstractTableModel;

import java.util.Vector;
/**
 * This class retrieves the information from the connector and displays it on the frame. 
 */
public class CarTableModel extends AbstractTableModel{
	public Vector<String> columnNames;
	
	public Vector<Object> data;
	
	public void setColumnNamesMW(String s0, String s1, String s2, String s3, String s4){
		 columnNames = new Vector<String>();
		 columnNames.add(s0);
		 columnNames.add(s1);
		 columnNames.add(s2);
		 columnNames.add(s3);
		 columnNames.add(s4);
	}
	
	public void setColumnNamesAW(
			String s0, 
			String s1, 
			String s2, 
			String s3, 
			String s4, 
			String s5,
			String s6){
		 columnNames = new Vector<String>();
		 columnNames.add(s0);
		 columnNames.add(s1);
		 columnNames.add(s2);
		 columnNames.add(s3);
		 columnNames.add(s4);
		 columnNames.add(s5);
		 columnNames.add(s6);
	}

	public int getColumnCount() {
		return columnNames == null ? 0 : columnNames.size();
	}

	public int getRowCount() {
		return data == null 
			|| columnNames == null 
			|| columnNames.size() == 0 ? 0 : data.size() / columnNames.size();
	}

	public String getColumnName(int col) {
		return columnNames == null ? "" : columnNames.get(col);
	}

	public Object getValueAt(int row, int col) {
		return data == null 
				|| columnNames == null 
				|| data.get(row*columnNames.size() + col) == null 
				? "" : data.get(row*columnNames.size() + col);
	}

	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
}


