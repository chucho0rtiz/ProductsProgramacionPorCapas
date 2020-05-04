package co.edu.ff.orders.productos.controllers;

import co.edu.ff.orders.productos.domain.Product;
import co.edu.ff.orders.productos.domain.ProductOperation;
import co.edu.ff.orders.productos.domain.ProductOperationRequest;
import co.edu.ff.orders.productos.services.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServices services;

    @PostMapping
    public ProductOperation create(@RequestBody ProductOperationRequest productOperationRequest){
        return services.createProduct(productOperationRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductOperationRequest productOperationRequest){
        ProductOperation product = services.updateOne(id, productOperationRequest);
        if (product.isValid()){
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(product);
    }

    @GetMapping
    public List<Product> findAll(){
        return services.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ProductOperation product = services.findById(id);
        if (product.isValid()){
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ProductOperation product = services.deleteOne(id);
        if (product.isValid()){
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(product);
    }


}
