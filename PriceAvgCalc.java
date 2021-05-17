import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PriceAvgCalc extends GatherBook  {
	
	InsertDataDb connFromInsert = new InsertDataDb();
	
	public double selectPrice(String par1) {

		//HERE WE EXECUTE the command to calculate the average of the prices (only those above 0) contained inside the "AUCTION_SOLD_AT" column.
		try (Connection conn = connFromInsert.connect();
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(par1)) {
			System.out.println(rs.getDouble(1));					//We have to select by column (there is only 1 column from the resultSet) because with the name "AUCTION_SOLD_AT" it doesn't finds
																	//anything.
			d1 = rs.getDouble(1);									//"d1" represents the double value provided by the SQLite command of calculating the average (represented by the param. "par1").
																	//"d1" is the result of the average calculation, in practice. It will be converted to string in the "GridBagLayoutTest" class.
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return d1;
	}
}