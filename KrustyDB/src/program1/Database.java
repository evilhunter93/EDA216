package program1;

import java.sql.*;
import java.util.ArrayList;

/**
 * Database is a class that specifies the interface to the Krusty Database. Uses
 * JBD
 */
public class Database {

	/**
	 * The database connection.
	 */
	private Connection conn;
	private PreparedStatement login;

	/**
	 * Create the database interface object. Connection to the database is
	 * performed later.
	 */
	public Database() {
		conn = null;
	}

	/**
	 * Open a connection to the database, using the specified user name and
	 * password.
	 */
	public boolean openConnection(String filename) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if the connection to the database has been established
	 * 
	 * @return true if the connection has been established
	 */
	public boolean isConnected() {
		return conn != null;
	}

	/* --- insert own code here --- */
	public void loginUser(String userId) {
		PreparedStatement ps = null;

		try {
			String sql = "SELECT username FROM users WHERE username = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				userId = rs.getString("username");
				CurrentUser.instance().loginAs(userId);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// TODO
	// Börja koda eget här
	public ArrayList<String> getIngredient(String name) {
		return null;

	}


	public String getRecipeString(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean containsCookie(String recipe) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addRecipe(String recipe, ArrayList<String> recipeIngredients) {
		// TODO Auto-generated method stub
		
	}

}