package allMethodsDВ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DB.ConnectionDB.connectionDB;

public class UtilsDB {
    public static int getLastValueIntOfColumn(String tableName, String columnName){
        String result = null;
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM "+tableName+" ORDER BY "+columnName+" DESC LIMIT 1");
            ResultSet res = stmt.getResultSet();
            result = res.getString(columnName);
            stmt.close();
        } catch (SQLException f) {
            System.out.println("ашибка3");
        }
        assert result != null;
        return Integer.parseInt(result);
    }
}
