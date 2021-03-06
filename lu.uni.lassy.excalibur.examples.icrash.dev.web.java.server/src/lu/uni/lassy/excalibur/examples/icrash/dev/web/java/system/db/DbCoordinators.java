/*******************************************************************************
 * Copyright (c) 2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Anton Nikonienkov - iCrash HTML5 API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

public class DbCoordinators extends DbAbstract {
	/**
	 * Creates a new Coordinator in the database using the data from the CtCoordinator passed.
	 *
	 * @param aCtCoordinator The CtCoordinator of which to use the data of to create the row in the database
	 */
	static public void insertCoordinator(CtCoordinator aCtCoordinator){
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
				
				String id = aCtCoordinator.id.value.getValue();
				String login =  aCtCoordinator.login.value.getValue();
				String pwd =  aCtCoordinator.pwd.value.getValue();
				String phone = aCtCoordinator.phoneNumber.value.getValue();
				
				log.debug("[DATABASE]-Insert coordinator");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".coordinators" +
											"(id,login,pwd,phone)" + 
											"VALUES("+"'"+id+"'"+",'"+login+"','"+pwd+"','"+phone+"')");
				
				log.debug(val + " row affected");
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}

	
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a coordinator details from the database, depending on the ID used and creates a CtCoordinator class to house the data.
	 *
	 * @param coordId The ID of which Coordinator to get the data of
	 * @return The coordinator that holds the data retrieved. It could be an empty class!
	 */
	static public CtCoordinator getCoordinator(String coordId){
		
		CtCoordinator aCtCoordinator = new CtCoordinator();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".coordinators WHERE id = " + coordId;
				

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) {				
					
					aCtCoordinator = new CtCoordinator();
					//coordinator's id
					DtCoordinatorID aId = new DtCoordinatorID(new PtString(res.getString("id")));
					//coordinator's login
					DtLogin aLogin = new DtLogin(new PtString(res.getString("login")));
					//coordinator's pwd
					DtPassword aPwd = new DtPassword(new PtString(res.getString("pwd")));
					DtPhoneNumber aPhone = new DtPhoneNumber(new PtString(res.getString("phone")));
					aCtCoordinator.init(aId, aLogin,aPwd, aPhone);
					
				}
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return aCtCoordinator;

	}
	
	/**
	 * Delete a coordinator from the database.
	 *
	 * @param aCtCoordinator The coordinator to delete from the database
	 */
	static public void deleteCoordinator(CtCoordinator aCtCoordinator){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Delete
			
			try{
				String sql = "DELETE FROM "+ dbName+ ".coordinators WHERE id = ?";
				String id = aCtCoordinator.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows+" row deleted");				
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}


			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Gets a list of all coordinators from the database. It's stored in a hashtable of the ID and the coordinator class
	// Returns a hashtable of coordinator ID's and their class
	static public Hashtable<String, CtCoordinator> getSystemCoordinators(){
		Hashtable<String, CtCoordinator> cmpSystemCtCoord = new Hashtable<String, CtCoordinator>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".coordinators ";

				PreparedStatement statement = conn.prepareStatement(sql);
				//statement.setString(1, alertId);
				ResultSet res = statement.executeQuery(sql);

				CtCoordinator aCtCoord = null;

				while (res.next()) {

					aCtCoord = new CtCoordinator();
					//alert's id
					DtCoordinatorID aId = new DtCoordinatorID(new PtString(
							res.getString("id")));
					DtLogin aLogin = new DtLogin(new PtString(res.getString("login")));
					DtPassword aPwd = new DtPassword(new PtString(res.getString("pwd")));
					DtPhoneNumber aPhoneNumber = new DtPhoneNumber(new PtString(
							res.getString("")));
					//init aCtAlert instance
					aCtCoord.init(aId, aLogin, aPwd, aPhoneNumber);
					
					//add instance to the hash
					cmpSystemCtCoord
							.put(aCtCoord.id.value.getValue(), aCtCoord);
				}
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			log.error("SQL connection problems ...");
			e.printStackTrace();
		}

		return cmpSystemCtCoord;
	}
}