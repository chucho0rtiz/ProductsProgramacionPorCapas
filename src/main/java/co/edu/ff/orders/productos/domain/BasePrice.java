package co.edu.ff.orders.productos.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.productos.serialization.BigdecimalSerializable;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class BasePrice implements BigdecimalSerializable {
    BigDecimal value;

    public BasePrice(BigDecimal value) {
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(value.intValue()>0);

        this.value = value;
    }

    @Override
    public BigDecimal valeuOf() {
        return value;
    }
}
