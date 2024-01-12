package com.facialrecognition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CRUDOperations {
    private static final String INSERT_QUERY = "INSERT INTO user (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE user SET firstname = ?, lastname = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM user";
    private static final String SELECT_PROFILE_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String LOGIN_QUERY = "SELECT id, email, password FROM user";

    public static String insertData(String value1, String value2, String value3, String value4) {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, value1);
            preparedStatement.setString(2, value2);
            preparedStatement.setString(3, value3);
            preparedStatement.setString(4, value4);
            preparedStatement.executeUpdate();
            return "-1";
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
    public static String updateData(int id, String updatedValue1, String updatedValue2, String updatedValue3, String updatedValue4) {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, updatedValue1);
            preparedStatement.setString(2, updatedValue2);
            preparedStatement.setString(3, updatedValue3);
            preparedStatement.setString(4, updatedValue4);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            return "-1";
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String deleteData(int id) {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return "-1";
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String selectData() {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3)+ " " + resultSet.getString(4));
            }
            return "-1";
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    
    public static String selectProfileData(int id) {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROFILE_QUERY)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder result = new StringBuilder();

                while (resultSet.next()) {
                    result.append(resultSet.getString(2)).append(" ")
                          .append(resultSet.getString(3)).append(" ")
                          .append(resultSet.getString(4)).append(" ")
                          .append(resultSet.getString(5)).append("\n");
                }

                return result.toString().trim();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    public static int login(String email, String passwd) {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                if(email.equals(resultSet.getString(2)) && passwd.equals(resultSet.getString(3)))
                {
                	return resultSet.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}