package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by philt on 06.09.2016.
 */
class DBHelper {

    static Connection getConnection() throws ReflectiveOperationException, SQLException {

        final String hostname = "localhost";
        final String port = "3306";
        final String dbname = "pokemongolog";
        final String user = "pokemongolog";
        final String password = "pokemongopass";

        Connection connection = null;

        try {
            Class.forName("org.gjt.mm.mysql.Driver").newInstance();
        } catch (ReflectiveOperationException e){
            throw e;
        }
        String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname;
        connection = DriverManager.getConnection(url, user, password);

        return connection;
    }
}
