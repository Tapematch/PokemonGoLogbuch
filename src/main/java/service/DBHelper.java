package service;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by philt on 06.09.2016.
 */
class DBHelper {

    static Connection getConnection() {

        final String hostname = "localhost";
        final String port = "3306";
        final String dbname = "pokemongolog";
        final String user = "pokemongolog";
        final String password = "pokemongopass";

        Connection connection = null;
        try {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname;
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
