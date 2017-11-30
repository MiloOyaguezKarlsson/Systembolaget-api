/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import utilities.ConnectionFactory;

/**
 *
 * @author milooyaguez karlsson
 */
@Stateless
public class ArticlesBean {

    public JsonArray getArticles() {
        Connection connection;

        try {
            connection = ConnectionFactory.getConnection();
            Statement stmt = (Statement) connection.createStatement();
            String sql = "SELECT * FROM articles";
            ResultSet data = stmt.executeQuery(sql);

            JsonArrayBuilder articles = Json.createArrayBuilder();

            while (data.next()) {
                int id = data.getInt("id");
                String name = data.getString("name");
                String merchGroup = data.getString("merch_group");
                int price = data.getInt("price");
                int volume = data.getInt("volumeml");

                JsonObject article = Json.createObjectBuilder()
                        .add("id", id)
                        .add("name", name)
                        .add("merch_group", merchGroup)
                        .add("price", price)
                        .add("volumeml", volume).build();
                articles.add(article);
            }
            connection.close();
            return articles.build();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public JsonArray getArticlesInStore(int id) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement stmt = (Statement) connection.createStatement();
            String sql = String.format("SELECT * FROM articles WHERE id IN "
                    + "(SELECT articleid FROM storearticles WHERE storeid = %d)", id);
            ResultSet data = stmt.executeQuery(sql);

            JsonArrayBuilder articles = Json.createArrayBuilder();

            if (data.next()) {
                while (data.next()) {
                    int articleid = data.getInt("id");
                    String name = data.getString("name");
                    String merchGroup = data.getString("merch_group");
                    int price = data.getInt("price");
                    int volume = data.getInt("volumeml");

                    JsonObject article = Json.createObjectBuilder()
                            .add("id", articleid)
                            .add("name", name)
                            .add("merch_group", merchGroup)
                            .add("price", price)
                            .add("volumeml", volume).build();
                    articles.add(article);
                }
            } else {
                JsonObject article = Json.createObjectBuilder()
                        .add("ERROR", 400).build();
                articles.add(article);
            }
            connection.close();
            return articles.build();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public JsonArray searchArticles(String query) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement stmt = (Statement) connection.createStatement();
            String sql = "SELECT * FROM articles WHERE name LIKE '%" + query + "%' OR merch_group LIKE '% " + query + " %'";
            ResultSet data = stmt.executeQuery(sql);

            JsonArrayBuilder articles = Json.createArrayBuilder();

            while (data.next()) {
                JsonObject article = Json.createObjectBuilder()
                        .add("id", data.getInt("id"))
                        .add("name", data.getString("name"))
                        .add("merch_group", data.getString("merch_group"))
                        .add("price", data.getInt("price"))
                        .add("volumeml", data.getInt("volumeml")).build();
                articles.add(article);
            }
            connection.close();
            return articles.build();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public JsonArray searchArticlesInStore(String query, int id) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement stmt = (Statement) connection.createStatement();
            String sql = "SELECT * FROM articles WHERE name LIKE '%" + query
                    + "%' OR merch_group LIKE '% " + query + " %'"
                    + " AND id IN (SELECT articleid FROM storearticles WHERE storeid = " + id + ")";
            ResultSet data = stmt.executeQuery(sql);
            
            JsonArrayBuilder articles = Json.createArrayBuilder();
            
            while (data.next()) {
                JsonObject article = Json.createObjectBuilder()
                        .add("id", data.getInt("id"))
                        .add("name", data.getString("name"))
                        .add("merch_group", data.getString("merch_group"))
                        .add("price", data.getInt("price"))
                        .add("volumeml", data.getInt("volumeml")).build();
                articles.add(article);
            }
            connection.close();
            return articles.build();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
