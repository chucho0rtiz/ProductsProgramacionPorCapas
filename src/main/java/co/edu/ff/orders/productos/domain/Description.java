package co.edu.ff.orders.productos.domain;

import co.edu.ff.orders.common.Preconditions;
import co.edu.ff.orders.productos.serialization.StringSerializable;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value(staticConstructor = "of")
public class Description implements StringSerializable {
    String value;

    public Description(String value) {
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(StringUtils.isNoneBlank(value));
        Preconditions.checkArgument(value.length()<=280);

        this.value = value;
    }

    @Override
    public String valueOf() {
        return value;
    }
}
