package co.edu.ff.orders.productos.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.productos.serialization.LongSerializable;
import lombok.Value;

@Value(staticConstructor = "of")
public class ProductId implements LongSerializable {
    Long value;

    public ProductId(Long value){
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(value>0);

        this.value = value;
    }

    @Override
    public Long valueOf() {
        return value;
    }
}
