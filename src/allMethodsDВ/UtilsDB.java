package allMethodsDВ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DB.ConnectionDB.connectionDB;

class UtilsDB {
    /**************************************************************************************************************/

    static int getLastValueIntOfColumn(String tableName, String columnName) {
        String result = null;
        try {
            Statement stmt;
            stmt = connectionDB.createStatement();
            stmt.execute("SELECT * FROM " + tableName);
            ResultSet res = stmt.getResultSet();
            if (!res.next()) {
                System.out.println("You don't have browsers. Please, create signature1.");
                res.close();
            } else {
                stmt.execute("SELECT * FROM " + tableName + " ORDER BY " + columnName + " DESC LIMIT 1");
                res = stmt.getResultSet();
                if (!res.next()) {
                    System.out.println("You don't have browsers. Please, create signature2.");
                    res.close();
                } else {
                    result = res.getString(columnName);
                    stmt.close();
                }
            }
        } catch (SQLException f) {
            System.out.println("ласт_ашибка3");
            f.printStackTrace();
        }
        if (result == null) {
            return 0;
        } else {
            return Integer.parseInt(result);
        }
    }

    /**************************************************************************************************************/
}
