package program;

// imports
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Nicolas Lehmann
 * A simple program for using a database with Java and JDBC.
 */
public class Main{

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {

		// PART 1 - build a connection to a database
		// needed imports: DriverManager, Connection, SQLException

		// Database - Setup
		String dbServer = "localhost";
		String dbPort   = "3306";
		String dbName   = "myDB";
		String dbUser   = "myUSER";
		String password = "passwort";

		try {
			// loading the driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException cnfe) {
			System.out.println("The mysql driver could not be loaded.");

			// print stacktrace
			cnfe.printStackTrace();
			System.exit(0);
		}

		Connection conn = null;

		try{
			// connect to the database
			conn = DriverManager.getConnection("jdbc:mysql://" + dbServer + ":" + dbPort + "/" + dbName, dbUser , password);
		} catch (SQLException sqle) {
			System.out.println("The connection could not be established.");

			// print stacktrace
			sqle.printStackTrace();
			System.exit(0);
		}

		System.out.println("Connection to database " + dbName + "@" + dbServer + ":" + dbPort + " successfully established.");
		System.out.println();
		System.out.println("Output:");

		// PART 2 - query the database
		// needed imports: Statement, PreparedStatement, ResultSet

		// PART 2.1 - STATEMENTS
		Statement stmt = conn.createStatement();
		ResultSet rset1 = stmt.executeQuery("SELECT * FROM ausgabe;");

		// PART 2.2 - PREPARED STATEMENTS
		PreparedStatement prepstmt = conn.prepareStatement("SELECT anzahl,text FROM ausgabe WHERE anzahl = ?;");
		prepstmt.setInt(1, 2);
		ResultSet rset2 = prepstmt.executeQuery();

		// PART 3 - RESULTSETS

		// PART 3.1 - STATEMENTS
		while (rset1.next()){
			System.out.print(rset1.getInt(1) + " -> ");
			System.out.println(rset1.getString(2) + " (Statement)");
		}

		// PART 3.2 - PREPARED STATEMENTS
		while (rset2.next()){
			System.out.print(rset2.getInt("anzahl") + " -> ");
			System.out.println(rset2.getString("text") + " (Prepared Statement)");
		}

		// close all connection-objects
		if (rset1 != null) {
			rset1.close();
		}
		if (rset2 != null) {
			rset2.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (prepstmt != null) {
			prepstmt.close();
		}
		if (conn != null) {
			conn.close();
		}
		
	} // eom

} // eoc