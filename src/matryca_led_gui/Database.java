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

    public HashMap fetch(Connection conn, String table, String[] columns) throws SQLException{
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


    public void insert(Connection conn, String table, String[] columns, String[] values) throws SQLException {
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

    public void delete(Connection conn, String table, String[] columns, String[] values) throws SQLException{
        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("DELETE FROM networks WHERE "+columns[0]+" = '"+values[0]+"' AND "+columns[1]+" = '"+values[1]+"';");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void cleanTable(Connection conn, String table) throws SQLException{
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

    public void createTable(Connection conn,String tableName, String[] columns, String[] valueTypes) throws SQLException{

        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("DROP TABLE "+tableName+";");

        } catch (SQLException e) {
            try (Statement statement = conn.createStatement()) {
                statement.executeQuery("CREATE TABLE "+tableName+" ("+columns[0]+" "+valueTypes[0]+");");
            } catch (SQLException ex) {System.out.println(e.toString());}

            for(int i = 1;i< columns.length;i++){
                try(Statement statement = conn.createStatement()){
                    statement.executeQuery("ALTER TABLE "+tableName+" ADD "+columns[i]+" "+valueTypes[i]+";");
                } catch (SQLException ex1) {System.out.println(e.toString());}
            }
        }


    }

    void clearViewsDB(){
        HashMap<String, ArrayList<String>> viewtablenumbers = new HashMap<String, ArrayList<String>>();
        setDB();

        try {viewtablenumbers = fetch(conn, "viewtablenumbers",new String[]{"id"});
        }catch(Exception ex){}

        ArrayList<String> ids = viewtablenumbers.get("id");

        ids.forEach((id) ->{
            try (Statement statement = conn.createStatement()) {
                statement.executeQuery("DROP TABLE "+"view"+id+";");
            }catch(Exception ex){}
        });

        try (Statement statement = conn.createStatement()) {
            statement.executeQuery("DROP TABLE "+"viewtablenumbers"+";");}catch(Exception ex){}

        try{createTable(conn, "viewtablenumbers",
                new String[]{"id", "name"},
                new String[]{"SERIAL PRIMARY KEY","VARCHAR (20)"});}catch(Exception ex){};

        try{insert(conn, "viewtablenumbers", new String[]{"name"}, new String[]{"name"});}catch(Exception ex){}
        closeConnection();
    }

}
