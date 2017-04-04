package program2;

//package app;

import java.sql.*;
import java.util.*;
//import dbtLab3.*;

/**
 * Database is a class that specifies the interface to the movie database. Uses
 * JDBC.
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

	/**
	 * Close the connection to the database.
	 */
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
	/**
	 * Gets the information about a specific pallet.
	 * 
	 * @param id
	 * @return list of all info.
	 */
	public ArrayList<Integer> getId(String cookie, String date) {
		ArrayList<Integer> pallets = new ArrayList<Integer>();
		PreparedStatement ps = null;

		try {
			String sql = "SELECT id FROM pallets where cookie_name = ? and production_date = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cookie);
			ps.setString(2, date);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pallets.add(rs.getInt("id"));
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
		return pallets;
	}

	/**
	 * Gets blocked cookies from database.
	 * 
	 * @return list of blocked cookies
	 */
	public ArrayList<String> getBlockedCookies() {
		try {
			String sql = "SELECT * from Cookie where isBlocked = true";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			ArrayList<String> list = new ArrayList<>();

			while (rs.next()) {
				list.add(rs.getString("name"));
			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Get blocked pallets of a specific cookie.
	 * 
	 * @param cookie
	 * @return list of blocked pallets.
	 */
	public ArrayList<String> getBlockedPallets(String cookie) {
		try {
			String sql = "SELECT * from Cookie natural join Pallet where Cookie.isBlocked = true and Pallet.cookieName = ? and Cookie.name = Pallet.cookieName";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cookie);
			ResultSet rs = ps.executeQuery();

			ArrayList<String> list = new ArrayList<>();

			while (rs.next()) {
				list.add(Integer.toString(rs.getInt("ID")));
			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getPalletLocation(int id) {
		String location = "test";
		PreparedStatement ps = null;

		try {
			String sql = "SELECT location FROM pallets where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			location = rs.getString("location");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return location;
	}
	 public ArrayList<Integer> getPalletID(String cookie, String date) {
	        ArrayList<Integer> pallets = new ArrayList<Integer>();
	        PreparedStatement ps = null;

	        try {
	            String sql = "SELECT id FROM pallets WHERE cookie_name = ? and production_date = ?";
	            ps = conn.prepareStatement(sql);
	            ps.setString(1, cookie);
	            ps.setString(2, date);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                pallets.add(rs.getInt("id"));
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
	        return pallets;
	    }


	

	/**
	 * 
	 * @return cookieNames
	 */
	public ArrayList<String> getCookieNames() {
		ArrayList<String> cookies = new ArrayList<String>();
		PreparedStatement ps = null;

		try {
			String sql = "SELECT name FROM cookies";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cookies.add(rs.getString("name"));
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
		return cookies;
	}

	public ArrayList<String> getCreatedDates(String cookie) {
        ArrayList<String> dates = new ArrayList<String>();
        PreparedStatement ps = null;

        try {
            String sql = "SELECT DISTINCT production_date FROM pallets WHERE cookie_name = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cookie);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dates.add(rs.getString("production_dates"));
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
        return dates;
    }
	
	public String getCreatedDate(int id) {
        PreparedStatement ps = null;
        String date = null;
        try {
            String sql = "SELECT production_date FROM pallets WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            date = rs.getString("production_date");
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

	public boolean getPalletifDelivered(int id) {
		boolean isDelivered = false;
        PreparedStatement ps = null;

        try {
            String sql = "SELECT isDelivered FROM pallets WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            isDelivered = rs.getBoolean("isDelivered");
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return isDelivered;
	}

	public String getPalletdDate(int id) {
        String dDate = "test";
        PreparedStatement ps = null;

        try {
            String sql = "SELECT delivery_date FROM pallets WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            dDate = rs.getString("delivery_date");
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }


        return dDate;
    }

	public int getPalletOrderID(int id) {
		int orderID = 0;
        PreparedStatement ps = null;

        try {
            String sql = "SELECT orderID FROM pallets WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            orderID = rs.getInt("orderID");
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return orderID;
	}

	public Boolean ifBlocked(String cookie) {
        PreparedStatement ps = null;
        Boolean block = false;
        try {
            String sql = "SELECT isBlocked FROM cookies WHERE name = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cookie);
            ResultSet rs = ps.executeQuery();
            rs.next();
            block = rs.getBoolean("isBlocked");
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return block;
    }
	/**
	 * Booleans i sql är integer 1 eller 0
	 * @param cookie
	 */
	public void blockCookie(String cookie) {
        PreparedStatement ps = null;

        try {
            String sql =  "UPDATE cookies SET isBlocked = 1 where name = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cookie);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

	public void unBlockCookie(String cookie) {
        PreparedStatement ps = null;

        try {
            String sql =  "UPDATE cookies SET isBlocked = 0 WHERE name = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cookie);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	
}