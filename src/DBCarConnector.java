import java.sql.*;
import java.util.Vector;
/**
 * This class contains the search connector (Group 11) and login connector (Group 12).
 */
public class DBCarConnector {
	Connection c;
	Statement stmt;
	PreparedStatement preparedstmt;
	
	public DBCarConnector(String server, String port, String db, String user, String pass){
		String driver = "com.mysql.jdbc.Driver";
		
		try{
			Class.forName(driver).newInstance();
			
			String s = String.format(
					"jdbc:mysql://%s:%s/%s?user=%s&password=%s",
					server, 
					port, 
					db, 
					user,
					pass);
			
			c = DriverManager.getConnection(s);
			
			stmt = c.createStatement();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Vector<String> getCarBrand(){                    
		Vector<String> brand = new Vector<String>();
		
		try{
			ResultSet rs = stmt.executeQuery("SELECT CarBrand FROM car_brand;");
			if(rs.next()){
				brand.add(rs.getString("CarBrand"));
				while(rs.next())
					brand.add(rs.getString("CarBrand"));
				return brand;
			}
		} catch(Exception e){
				e.printStackTrace();
			}
		return brand;
	}
	
	public Vector<String> getCarModel(UserFrame uw, String str){
		Vector<String> model = new Vector<String>();
		model.add(str);
		
		try{
			if(uw.carBrand.getSelectedIndex() != 0){
				String query = String.format(
						"SELECT Model FROM brand_model WHERE Brand = '%s'", 
						uw.getSelectedBrand());
				ResultSet rs = stmt.executeQuery(query);
				if(rs.next()){
					model.add(rs.getString("Model"));
					while(rs.next())
						model.add(rs.getString("Model"));
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	public Vector<Object> getCarRecords(UserFrame uw){
		Vector<Object> carTable = new Vector<Object>();
		
		String query = "SELECT Brand, ModelFullName, Transmission, FuelType, Price " +
	                   "FROM brand_model, model_detail " +
				       "WHERE brand_model.Model = model_detail.Model ";
		if(uw.carBrand.getSelectedIndex() != 0)
			query += " AND Brand = '" + uw.getSelectedBrand() + "'";
		if(uw.carModel.getSelectedIndex() != 0)
			query += " AND model_detail.Model = \"" + uw.carModel.getSelectedItem().toString() + "\"";
		if(uw.startPrice.getSelectedIndex() != 0)
			query += " AND Price >= " + uw.startPrice.getSelectedItem().toString();
		if(uw.endPrice.getSelectedIndex() != 0)
			query += " AND Price <= " + uw.endPrice.getSelectedItem().toString();
		if(uw.t.getSelectedIndex() != 0)
			query += " AND Transmission = '" + uw.t.getSelectedItem().toString() + "'";
		if(uw.ft.getSelectedIndex() != 0)
			query += " AND FuelType = '" + uw.ft.getSelectedItem().toString() +"'";
		
		try{
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()){
				carTable.add(rs.getString("Brand"));
				carTable.add(rs.getString("ModelFullName"));
				carTable.add(rs.getString("Transmission"));
				carTable.add(rs.getString("FuelType"));
				
				String priceStr = rs.getString("Price"); 
				Integer price = Integer.parseInt(priceStr);
				carTable.add(price);
				
				while(rs.next()){
					carTable.add(rs.getString(1));
					carTable.add(rs.getString(2));
					carTable.add(rs.getString(3));
					carTable.add(rs.getString(4));
					
					priceStr = rs.getString(5);
					price = Integer.parseInt(priceStr);
					carTable.add(price);
				}
			}
			return carTable;		
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Object> getCarRecordsAdmin(AdminFrame aw){
		String query = "SELECT car_brand.CarBrand, brand_model.Model, ModelFullName, Transmission, " +
				       "FuelType, Price, Website " +
	                   "FROM brand_model, model_detail, car_brand " +
				       "WHERE brand_model.Model = model_detail.Model " +
				       "AND car_brand.CarBrand = brand_model.Brand ";
		if(!aw.fieldBrand.getText().equals(""))
			query += " AND brand_model.Brand = '" + aw.fieldBrand.getText() + "'";
		if(!aw.fieldModel.getText().equals(""))
			query += " AND brand_model.Model = \"" + aw.fieldModel.getText() + "\"";
		if(!aw.fieldModelFullName.getText().equals(""))
			query += " AND model_detail.modelFullName = \"" + 
		              aw.fieldModelFullName.getText() + "\"";
		if(!aw.fieldTransmission.getText().equals(""))
			query += " AND Transmission = '" + aw.fieldTransmission.getText() + "'";
		if(!aw.fieldFuelType.getText().equals(""))
			query += " AND FuelType = '" + aw.fieldFuelType.getText() + "'";
		if(!aw.fieldPrice.getText().equals(""))
			query += " AND Price = '" + aw.fieldPrice.getText() + "'";
		if(!aw.fieldWebsite.getText().equals(""))
			query += " AND Website = '" + aw.fieldWebsite.getText() + "'";
		
		try{
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()){
				Vector<Object> carTableAdmin = new Vector<Object>();
				carTableAdmin.add(rs.getString("CarBrand"));
				carTableAdmin.add(rs.getString("Model"));
				carTableAdmin.add(rs.getString("ModelFullName"));
				carTableAdmin.add(rs.getString("Transmission"));
				carTableAdmin.add(rs.getString("FuelType"));
				
				String priceStr = rs.getString("Price");
				Integer price = Integer.parseInt(priceStr);
				carTableAdmin.add(price);
				
				carTableAdmin.add(rs.getString("Website"));
				while(rs.next()){
					carTableAdmin.add(rs.getString("CarBrand"));
					carTableAdmin.add(rs.getString("Model"));
					carTableAdmin.add(rs.getString("ModelFullName"));
					carTableAdmin.add(rs.getString("Transmission"));
					carTableAdmin.add(rs.getString("FuelType"));
					
					priceStr = rs.getString("Price");
					price = Integer.parseInt(priceStr);
					carTableAdmin.add(price);
					
					carTableAdmin.add(rs.getString("Website"));
				}
				return carTableAdmin;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean addCarBrand(String brand, String website) {
		try{
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM car_brand WHERE CarBrand = '" + 
			        brand + "'");
			if(!rs.next()){
				String columes = "(CarBrand";
				String values = "(?";			
				
				if(!website.equals("")) {
					columes += ", Website";
					values += ", ?";
				}
				
				columes +=")";
				values += ")";
				
				String query = String.format("INSERT INTO car_brand%s VALUES %s", 
						columes, values);
				
				preparedstmt = c.prepareStatement(query);
				preparedstmt.setString(1, brand);
				
				int index = 2;
				if(!website.equals("")) { 
					preparedstmt.setString(index, website);
					index++;
				}
				
				preparedstmt.execute();
			}
			else {
				boolean needUpdate = !website.equals("");
				
				if(needUpdate) {
					String query = "UPDATE car_brand SET CarBrand=?";
					
					if(!website.equals("")) {
						query += ", Website=?";
					}
			
					query += String.format(" where CarBrand = \"%s\"", brand);
					
					preparedstmt = c.prepareStatement(query);
					preparedstmt.setString(1, brand);
					
					int index = 2;
					if(!website.equals("")) {
						preparedstmt.setString(index, website);
						index++;
					}
					
					preparedstmt.execute();
				}
			}
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean addCarBrandModel(String brand, String model) {
		try{
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM brand_model WHERE brand_model.Model = \"" +
			        model + "\"");
			if(!rs.next()){
				assert !brand.equals("");
				String query = "INSERT INTO brand_model VALUES (?, ?)";
				preparedstmt = c.prepareStatement(query);
				preparedstmt.setString(1, model);
				preparedstmt.setString(2, brand);
				preparedstmt.execute();
			}
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean addCarModelDetail(
			String model, 
			String fullname, 
			String transmission, 
			String fuelType, 
			String price) {
		try{
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM model_detail WHERE ModelFullName = \"" +
			        fullname + "\"");
			
			if(!rs.next()){
				assert !model.equals("");
				
				String columes = "(ModelFullName, Model";
				String values = "(?, ?";
				
				if(!transmission.equals("")) {
					columes += ", Transmission";
					values += ", ?";
				}
				
				if(!fuelType.equals("")) {
					columes += ", FuelType";
					values += ", ?";
				}
				
				if(!price.equals("")) {
					columes += ", Price";
					values += ", ?";
				}
				
				columes += ")";
				values += ")";
				
				String query = String.format("INSERT INTO model_detail%s VALUES %s", 
						columes, values);
				
				preparedstmt = c.prepareStatement(query);
				
				preparedstmt.setString(1, fullname);
				
				preparedstmt.setString(2, model);
				
				int index = 3;
				if(!transmission.equals("")) {
					preparedstmt.setString(index, transmission);
					index++;
				}
				
				if(!fuelType.equals("")) {
					preparedstmt.setString(index, fuelType);
					index++;
				}
				
				if(!price.equals("")) {
					preparedstmt.setInt(index, Integer.parseInt(price));
					index++;
				}
				
				preparedstmt.execute();
			}
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean addNewCar(
			String brand, 
			String model, 
			String fullname, 
			String transmission,
			String fuelType,
			String price,
			String website) {
		if(brand.equals("") 
				|| model.equals("")
				|| fullname.equals(""))
			return false;
		
		try {
			Integer.parseInt(price);
		}
		catch(NumberFormatException e) {
			return false;
		}
		
		if(addCarBrand(brand, website)) {
		
			if(addCarBrandModel(brand, model)) {
				return addCarModelDetail(model, 
						fullname, 
						transmission,
						fuelType,
						price);
			}
		}
		
		return false;
	}
	
	public void deleteCarRecord(String brand, String model, String fullname){
		try{
			String query = String.format("DELETE FROM model_detail WHERE ModelFullName = \"%s\"", fullname);
			preparedstmt = c.prepareStatement(query);
			preparedstmt.execute();
			
			String queryCheckModel = String.format("SELECT * FROM model_detail WHERE Model = \"%s\"", model);
			ResultSet rsCheckModel = stmt.executeQuery(queryCheckModel);
			if(!rsCheckModel.next()){
				String queryDeleteModel = String.format("DELETE FROM brand_model WHERE Model = \"%s\"", model);
				preparedstmt = c.prepareStatement(queryDeleteModel);
				preparedstmt.execute();
			}
			
			String queryCheckBrand = String.format("SELECT * FROM brand_model WHERE Brand = \"%s\"", brand);
			ResultSet rsCheckBrand = stmt.executeQuery(queryCheckBrand);
			if(!rsCheckBrand.next()){
				String queryDeleteBrand = String.format("DELETE FROM car_brand WHERE CarBrand = \"%s\"", brand);
				preparedstmt = c.prepareStatement(queryDeleteBrand);
				preparedstmt.execute();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getUserPass(String userName) {         
		try{
			String queryCheckUser = String.format("SELECT * FROM user WHERE userName = \"%s\"", userName);
			ResultSet rsCheckUser = stmt.executeQuery(queryCheckUser);
			if(rsCheckUser.next()){
				return rsCheckUser.getString("userPass");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
