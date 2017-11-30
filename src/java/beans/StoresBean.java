/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import utilities.ConnectionFactory;

/**
 *
 * @author milooyaguez karlsson
 */
@Stateless
public class StoresBean {

    public JsonArray getStores() {
        Connection connection;
        try {
            connection = ConnectionFactory.getConnection();
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM stores";
            ResultSet data = stmt.executeQuery(sql);

            JsonArrayBuilder stores = Json.createArrayBuilder();

            while (data.next()) {
                int id = data.getInt("id");
                String street_address = data.getString("street_address");
                String city = data.getString("city");
                String postalCode = data.getString("postal_code");

                JsonObject store = Json.createObjectBuilder()
                        .add("id", id)
                        .add("street_address", street_address)
                        .add("city", city)
                        .add("postal_code", postalCode).build();
                stores.add(store);
            }
            connection.close();
            return stores.build();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public JsonObject getStore(int id) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM stores WHERE id = " + id;
            ResultSet data = stmt.executeQuery(sql);
            if (data.next()) {
                JsonObjectBuilder store = Json.createObjectBuilder()
                        .add("id", data.getInt("id"))
                        .add("street_address", data.getString("street_address"))
                        .add("city", data.getString("city"))
                        .add("postal_code", data.getString("postal_code"));
                connection.close();
                return store.build();
            } else {
                JsonObjectBuilder store = Json.createObjectBuilder()
                        .add("ERROR", 400);
                connection.close();
                return store.build();
            }

        } catch (SQLException ex) {
            Logger.getLogger(StoresBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StoresBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JsonArray searchStoresByCity(String query) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement stmt = (Statement) connection.createStatement();
            String sql = "SELECT * FROM stores WHERE city LIKE '%" + query + "%'";
            ResultSet data = stmt.executeQuery(sql);
            
            JsonArrayBuilder stores = Json.createArrayBuilder();
            
            while(data.next()){
                JsonObject store = Json.createObjectBuilder()
                        .add("id", data.getInt("id"))
                        .add("street_address", data.getString("street_address"))
                        .add("city", data.getString("city"))
                        .add("postal_code", data.getString("postal_code")).build();
                stores.add(store);
            }
            connection.close();
            return stores.build();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
