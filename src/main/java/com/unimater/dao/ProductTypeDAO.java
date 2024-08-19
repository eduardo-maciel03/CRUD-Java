package com.unimater.dao;

import com.unimater.model.ProductType;

import java.sql.*;

public class ProductTypeDAO extends GenericDAOImpl<ProductType> implements GenericDAO<ProductType> {

    private Connection connection;
    private final String TABLE_NAME = "product_type";

    public ProductTypeDAO(Connection connection) {
        super(ProductType::new, connection);
        super.tableName = TABLE_NAME;
    }

    @Override
    public ProductType getById(int id) {
        ProductType productType = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM product_type WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                productType = new ProductType(rs.getInt("id"),
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productType;
    }

    @Override
    public void upsert(ProductType productType) {
        try {
            PreparedStatement pstmt;
            if (productType.getId() == 0) {
                pstmt = connection.prepareStatement("INSERT INTO product_type (description) VALUES (?)");
                pstmt.setString(1, productType.getDescription());
            } else {
                pstmt = connection.prepareStatement("UPDATE product_type SET description = ? WHERE id = ?");
                pstmt.setString(1, productType.getDescription());
                pstmt.setInt(2, productType.getId());
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM product_type WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
