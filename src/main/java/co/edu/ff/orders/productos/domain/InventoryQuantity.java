package co.edu.ff.orders.productos.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.productos.serialization.IntegerSerializable;
import lombok.Value;

@Value(staticConstructor = "of")
public class InventoryQuantity implements IntegerSerializable {
    Integer value;

    public InventoryQuantity(Integer value) {
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(value>0);

        this.value = value;
    }

    @Override
    public Integer valueOf() {
        return value;
    }
}
