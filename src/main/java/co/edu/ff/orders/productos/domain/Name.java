package co.edu.ff.orders.productos.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.productos.serialization.StringSerializable;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value(staticConstructor = "of")
public class Name implements StringSerializable {
    String value;

    public Name(String value) {
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(StringUtils.isNoneBlank(value));
        Preconditions.checkArgument(value.length()<=100);

        this.value = value;
    }

    @Override
    public String valueOf() {
        return value;
    }
}
