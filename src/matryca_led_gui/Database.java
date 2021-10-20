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
        setDB();
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
        closeConnection();
        return data;
    }

    public ArrayList<String> fetchViews() throws SQLException{
        setDB();
        ArrayList<String> data = new ArrayList<String>();
        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery("select * from sqlite_master where type = 'table' and name not like 'sqlite_sequence';");
            while (result.next()) {
                    String record = result.getString("name");
                    data.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        closeConnection();
        return data;
    }

    public void insert(String table, String[] columns, int[] values) throws SQLException {
        setDB();
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
        closeConnection();
    }

    public void closeConnection(){
        try (Statement statement = conn.createStatement()) {
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void createView(String viewName) throws SQLException {
        setDB();
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("CREATE TABLE " + viewName + "(led_number int, led_color int, led_brightness int);");
        } catch (SQLException ex) {
            System.out.println(ex);
            try (Statement statement = conn.createStatement()) {
                statement.executeQuery("delete from "+viewName+";");
            } catch (Exception e){System.out.println(e);}

        }
        closeConnection();
    }

   public void clearViews(){
        setDB();
        ArrayList<String> views  = new ArrayList<String>();
        try{views = fetchViews();}catch(Exception e){}
        views.forEach((view) -> {
           deleteView(view);
        });
        closeConnection();
    }

    public void deleteView(String viewName){
        setDB();
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("DROP TABLE "+viewName+";");}catch(Exception ex){}
        closeConnection();
    }

}
