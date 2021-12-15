package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class MyDatabaseConnector {

    private SQLServerDataSource dataSource;

    public MyDatabaseConnector() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("MYTUNES2021GR7");
        dataSource.setUser("CSe21A_19");
        dataSource.setPassword("CSe21A_19");
        dataSource.setPortNumber(1433);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}
