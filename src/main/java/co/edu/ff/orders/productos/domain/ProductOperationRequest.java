package co.edu.ff.orders.productos.domain;

import co.edu.ff.orders.common.Preconditions;
import lombok.Value;

@Value(staticConstructor = "from")
public class ProductOperationRequest {
    Name name;
    Description description;
    BasePrice basePrice;
    TaxRate taxRate;
    ProductStatus productStatus;
    InventoryQuantity inventoryQuantity;

    public ProductOperationRequest(Name name, Description description, BasePrice basePrice, TaxRate taxRate, ProductStatus productStatus, InventoryQuantity inventoryQuantity) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(description);
        Preconditions.checkNotNull(basePrice);
        Preconditions.checkNotNull(taxRate);
        Preconditions.checkNotNull(productStatus);
        Preconditions.checkNotNull(inventoryQuantity);

        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.taxRate = taxRate;
        this.productStatus = productStatus;
        this.inventoryQuantity = inventoryQuantity;
    }
}
