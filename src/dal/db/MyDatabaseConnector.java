package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class MyDatabaseConnector {

    private SQLServerDataSource dataSource;

    /**
     * Database connector constructoren, her skriver vi alle vores login data til databasen.
     */
    public MyDatabaseConnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("MYTUNES2021GR7");
        dataSource.setUser("CSe21A_19");
        dataSource.setPassword("CSe21A_19");
        dataSource.setPortNumber(1433);
    }

    /**
     * Her bruges vores database login data til at connecte, ved brug af getConnection();
     * @return
     * @throws SQLServerException
     */
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}
