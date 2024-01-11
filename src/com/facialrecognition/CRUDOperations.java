package com.facialrecognition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CRUDOperations {
    private static final String INSERT_QUERY = "INSERT INTO user (firstname, lastname, email) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE user SET firstname = ?, lastname = ?, email = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM user";

    public static void insertData(String value1, String value2, String value3) {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, value1);
            preparedStatement.setString(2, value2);
            preparedStatement.setString(3, value3);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateData(int id, String updatedValue1, String updatedValue2, String updatedValue3) {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, updatedValue1);
            preparedStatement.setString(2, updatedValue2);
            preparedStatement.setString(3, updatedValue3);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteData(int id) {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void selectData() {
        try (Connection connection = DBConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3)+ " " + resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}