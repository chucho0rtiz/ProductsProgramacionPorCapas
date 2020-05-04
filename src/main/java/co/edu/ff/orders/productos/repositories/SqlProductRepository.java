package co.edu.ff.orders.productos.repositories;

import co.edu.ff.orders.productos.domain.*;
import co.edu.ff.orders.productos.exceptions.ProductDoesNotExists;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.*;

public class SqlProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public SqlProductRepository(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    public final RowMapper<Product> rowMapper =(resultSet, i)->{
        ProductId id = ProductId.of(resultSet.getLong("productid"));
        Name name = Name.of(resultSet.getString("name"));
        Description description = Description.of(resultSet.getString("description"));
        BasePrice basePrice = BasePrice.of(resultSet.getBigDecimal("baseprice"));
        TaxRate taxRate = TaxRate.of(resultSet.getBigDecimal("taxrate"));
        ProductStatus productStatus = ProductStatus.valueOf(resultSet.getString("productstatus"));
        InventoryQuantity inventoryQuantity = InventoryQuantity.of(resultSet.getInt("inventoryquantity"));
    return Product.of(id, name, description, basePrice, taxRate, productStatus, inventoryQuantity);
    };

    @Override
    public ProductOperation insertOne(ProductOperationRequest operationRequest) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name",operationRequest.getName().getValue());
        parameters.put("description",operationRequest.getDescription().getValue());
        parameters.put("basePrice",operationRequest.getBasePrice().getValue());
        parameters.put("taxRate",operationRequest.getTaxRate().getValue());
        parameters.put("productStatus",operationRequest.getProductStatus());
        parameters.put("inventoryQuantity",operationRequest.getInventoryQuantity().getValue());
        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        ProductId id = ProductId.of(number.longValue());
        Product producto = Product.of(
                id,
                operationRequest.getName(),
                operationRequest.getDescription(),
                operationRequest.getBasePrice(),
                operationRequest.getTaxRate(),
                operationRequest.getProductStatus(),
                operationRequest.getInventoryQuantity()
        );

        return ProductOperationSuccess.of(producto);
    }

    @Override
    public ProductOperation findById(Long id) {
        String SQL = "SELECT * FROM PRODUCTS WHERE productid = ?";
        Object[] objects = {id};
        try{
            Product product = jdbcTemplate.queryForObject(SQL, objects, rowMapper);
            return ProductOperationSuccess.of(product);
        }catch (EmptyResultDataAccessException e){
            return ProductOperationFailure.of(ProductDoesNotExists.of(id));
        }

    }

    @Override
    public List<Product> findAll() {
        String SQL = "SELECT * FROM PRODUCTS";
        return jdbcTemplate.query(SQL, rowMapper);
    }

    @Override
    public ProductOperation updateOne(Long id, ProductOperationRequest operationRequest) {
        System.out.println("id = " + operationRequest);
        String SQL = "UPDATE PRODUCTS SET name=?, description=?, baseprice=?, taxrate=?, productstatus=?, inventoryquantity=? WHERE productid = ?";
        Object[] objects = { operationRequest.getName().getValue(), operationRequest.getDescription().getValue(), operationRequest.getBasePrice().getValue(),
                operationRequest.getTaxRate().getValue(), operationRequest.getProductStatus().toString(), operationRequest.getInventoryQuantity().getValue(), id };

        Integer getValue = jdbcTemplate.update(SQL, objects);

        if(getValue == 1){
            ProductId productId = ProductId.of(id);
            Product producto = Product.of(
                    productId,
                    operationRequest.getName(),
                    operationRequest.getDescription(),
                    operationRequest.getBasePrice(),
                    operationRequest.getTaxRate(),
                    operationRequest.getProductStatus(),
                    operationRequest.getInventoryQuantity()
            );
            return ProductOperationSuccess.of(producto);
        }else{
            return ProductOperationFailure.of(ProductDoesNotExists.of(id));
        }
    }

    @Override
    public ProductOperation deleteOne(Long id) {
        String SQL = "DELETE FROM PRODUCTS WHERE productid = ?";
        Object[] objects = {id};
        Product product = findById(id).value();

        Integer getValue = jdbcTemplate.update(SQL, objects);
        if (getValue == 1){
            return ProductOperationSuccess.of(product);
        }else{
            return ProductOperationFailure.of(ProductDoesNotExists.of(id));
        }

    }
}
