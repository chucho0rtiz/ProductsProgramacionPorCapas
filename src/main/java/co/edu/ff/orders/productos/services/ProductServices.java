package co.edu.ff.orders.productos.services;

import co.edu.ff.orders.productos.domain.*;
import co.edu.ff.orders.productos.exceptions.ProductDoesNotExists;
import co.edu.ff.orders.productos.exceptions.ProductException;
import co.edu.ff.orders.productos.repositories.ProductRepository;
import co.edu.ff.orders.user.domain.*;
import co.edu.ff.orders.user.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductOperation createProduct(ProductOperationRequest productOperationRequest){
        return productRepository.insertOne(productOperationRequest);
    }

    public ProductOperation findById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public ProductOperation updateOne(Long id, ProductOperationRequest productOperationRequest){
        return productRepository.updateOne(id, productOperationRequest);
    }

    public ProductOperation deleteOne(Long id){
        return productRepository.deleteOne(id);

    }
}
