package de.telran.bankapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDelete {

    @Override
    public City update(City data) {
        try (
                Connection connection = connector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CITY);
        ) {
            preparedStatement.setInt(5, data.getId());
            preparedStatement.setString(1, data.getName());
            preparedStatement.setString(2, data.getCountryCode());
            preparedStatement.setString(3, data.getDistrict());
            preparedStatement.setInt(4, data.getPopulation());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;

    }

    @Override
    public void delete (Integer integ){
        try (
                Connection connection = connector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CITY_BY_ID);
        ) {
            preparedStatement.setInt(1, integ);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
