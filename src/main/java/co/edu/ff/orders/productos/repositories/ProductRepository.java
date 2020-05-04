package co.edu.ff.orders.productos.repositories;

import co.edu.ff.orders.productos.domain.Product;
import co.edu.ff.orders.productos.domain.ProductId;
import co.edu.ff.orders.productos.domain.ProductOperation;
import co.edu.ff.orders.productos.domain.ProductOperationRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {
    ProductOperation insertOne(ProductOperationRequest operationRequest);
    ProductOperation findById(Long id);
    List<Product> findAll();
    ProductOperation updateOne(Long id,ProductOperationRequest operationRequest);
    ProductOperation deleteOne(Long id);
}
