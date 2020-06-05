package test;

import java.sql.*;

import org.json.JSONObject;

import dataBase.Database;

public class TestD {
	public JSONObject testDBSelect() {
		String nom = null;
		try {
			Connection con=Database.getMySQLConnection();
			String query="Select * from user";
			Statement st=con.createStatement();
			ResultSet rs= st.executeQuery(query);
			while (rs.next()) {
				nom=rs.getString("nom");
				System.out.println(nom);
			}
			
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
