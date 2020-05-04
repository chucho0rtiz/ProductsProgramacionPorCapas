package co.edu.ff.orders.productos.domain;

public interface ProductOperation {
    Product value();

    String errorMessage();

    Boolean isValid();
}
