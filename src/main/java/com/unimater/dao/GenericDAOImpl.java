package com.unimater.dao;


import com.unimater.model.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class GenericDAOImpl<T extends Entity> implements GenericDAO<T> {

    Connection connection;
    String tableName;

    private Supplier<T> supplier;

    public GenericDAOImpl(Supplier<T> supplier, Connection connection) {
        this.supplier = supplier;
        this.connection = connection;
    }


    @Override
    public List<T> getAll() {
        List<T> productTypes = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            while (rs.next()) {
                T productType = (T) supplier.get().constructFromResultSet(rs);
                productTypes.add(productType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productTypes;
    }
}
