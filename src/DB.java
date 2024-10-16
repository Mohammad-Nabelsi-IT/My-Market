import javax.sql.ConnectionEventListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private Statement statement;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public DB(String dbName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3306/" + dbName;
        this.connection = DriverManager.getConnection(dbURL, "root", "Bmwe602114106");
        this.statement = connection.createStatement();
    }

    public Statement getStatement() {
        return statement;
    }

    public void close() throws SQLException {
        this.statement.close();
        this.connection.close();
    }
}
