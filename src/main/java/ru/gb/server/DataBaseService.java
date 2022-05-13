package ru.gb.server;
import java.sql.*;

public class DataBaseService {
    private Connection connection;
    private Statement statement;

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public DataBaseService() {
        try {
            connectDb();
            System.out.println("Connected ok");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void connectDb() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:ServerDB");
        statement = connection.createStatement();
    }

    public void creatRegistration(String login, String password, String name, String surname) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO ClientsDate (Login, Password, Name, Surname) VALUES (?, ?, ?, ?);")){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, surname);
            preparedStatement.executeUpdate();
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO ClientsAuth (Login, Password) VALUES (?, ?)")){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        }
    }
    public boolean hasRegistration(String login) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT Login FROM ClientsDate WHERE Login = ?;")){
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }
        return false;
    }
    public boolean hasAuth(String login, String password) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT Login,Password FROM ClientsDate WHERE Login = ? AND Password = ?;")){
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    if(resultSet.getString(1).equals(login)&&resultSet.getString(2).equals(password)){
                        return true;
                    }
                }
        }
        return false;
    }
}
