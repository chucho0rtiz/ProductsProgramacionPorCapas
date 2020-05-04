package co.edu.ff.orders.productos.exceptions;

import co.edu.ff.orders.productos.domain.Product;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value(staticConstructor = "of")
public class ProductDoesNotExists extends ProductException {
    Long id;

    public ProductDoesNotExists(Long id) {
        super(String.format("El producto con el id %s no existe", id));
        this.id = id;
    }
}
