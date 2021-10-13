package matryca_led_gui;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Database {
    public Connection conn;

    Database(){}

    public void setDB(){
        String url = "jdbc:sqlite:db/gui.db";
        Properties props = new Properties();
        try {
           conn = DriverManager.getConnection(url);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    public HashMap fetch(String table, String[] columns) throws SQLException{
        HashMap<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();

        for (String column: columns){
            data.put(column, new ArrayList<String>());
        }

        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM "+table+";");
                while (result.next()) {
                    for(String column: columns) {
                     String record = result.getString(column);
                     data.get(column).add(record);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return data;
    }

    public ArrayList<String> fetchViews() throws SQLException{
        ArrayList<String> data = new ArrayList<String>();
        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery("select * from sqlite_master where type = 'table' and name not like 'networks';");
            while (result.next()) {
                    String record = result.getString("name");
                    data.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return data;
    }

    public void insert(String table, String[] columns, String[] values) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String columns_string = columns[0];
            String values_string = "'"+values[0]+"'";
            for (int i = 1; i < columns.length; i++) {
                columns_string = columns_string+", "+columns[i];
                values_string = values_string+", '"+values[i]+"'";
            }
            statement.executeQuery("INSERT INTO "+table+"("+columns_string+") VALUES("+values_string+");");

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void closeConnection(){
        try (Statement statement = conn.createStatement()) {
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void createView(String viewName) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("CREATE TABLE " + viewName + "(id serial primary key, led_number int, led_color int);");
        } catch (SQLException ex) {
            try (Statement statement = conn.createStatement()) {
                statement.executeQuery("TRUNCATE TABLE " + viewName + " RESTART IDENTITY;");
            } catch (SQLException e) {
            }
        }
    }

   public void clearViews(){
        ArrayList<String> views  = new ArrayList<String>();
        try{views = fetchViews();}catch(Exception e){}
        views.forEach((view) -> {
           deleteView(view);
        });
    }

    public void deleteView(String viewName){
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("DROP TABLE "+viewName+";");}catch(Exception ex){}
    }

}
