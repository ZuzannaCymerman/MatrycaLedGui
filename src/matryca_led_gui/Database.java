package matryca_led_gui;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Database {
    public Connection conn;

    Database(){}

    public void setDB(){
        String url = "jdbc:postgresql://localhost:5432/arduino_gui";
        Properties props = new Properties();
        props.setProperty("user","arduino_db");
        props.setProperty("password","123456");
        try {
           conn = DriverManager.getConnection(url, props);
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
            ResultSet result = statement.executeQuery("select * from information_schema.tables where table_schema= 'views';");
            while (result.next()) {
                    String record = result.getString("table_name");
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

    public void delete(String table, String[] columns, String[] values) throws SQLException{
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("DELETE FROM networks WHERE "+columns[0]+" = '"+values[0]+"' AND "+columns[1]+" = '"+values[1]+"';");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void cleanTable(String table) throws SQLException{
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("TRUNCATE TABLE "+table+" RESTART IDENTITY;");
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
            statement.executeQuery("CREATE TABLE " + viewName + "(id serial primary key, led_number int, led_color varchar(20));");
        } catch (SQLException ex) {
            try (Statement statement = conn.createStatement()) {
                statement.executeQuery("TRUNCATE TABLE " + viewName + ";");
            } catch (SQLException e) {
            }
        }
    }

    void clearViews(){
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("DROP SCHEMA views CASCADE;");}catch(Exception ex){}
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("CREATE SCHEMA views;");}catch(Exception ex){}
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("TRUNCATE TABLE  viewtablenumbers;");}catch(Exception ex){}
    }

}
