
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ahmed
 */
public class DB {

    private static DB db = null;

    private DB() {
    }

    public static DB getInstance() {
        if (db == null) {
            db = new DB();
            return db;
        } else {
            return db;

        }
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
        return con;
    }
}
