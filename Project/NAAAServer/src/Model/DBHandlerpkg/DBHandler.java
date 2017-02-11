/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DBHandlerpkg;

import oracle.jdbc.driver.*;
import java.sql.*;

/**
 *
 * @author adelz
 */
public class DBHandler{

    public DBHandler() {
         try {
            DriverManager.registerDriver(new OracleDriver());
            connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "nour", "iti"); //Change your name and pass here
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private PreparedStatement preparedStatement;
    private Connection connection;
    private ResultSet resultSet;

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public void closeConnection() {
//        try {
//            if(!connection.isClosed())
//            connection.close();
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
