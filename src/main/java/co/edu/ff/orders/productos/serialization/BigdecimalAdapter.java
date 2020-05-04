package co.edu.ff.orders.productos.serialization;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.function.Function;

public class BigdecimalAdapter<T extends BigdecimalSerializable> implements GsonAdapter<T> {
    private final Function<BigDecimal, T> factory;

    public BigdecimalAdapter(Function<BigDecimal, T> factory) {
        this.factory = factory;
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        BigDecimal value = jsonElement.getAsBigDecimal();
        return factory.apply(value);
    }

    @Override
    public JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext) {
        BigDecimal value = t.valeuOf();
        return new JsonPrimitive(value);
    }
}
