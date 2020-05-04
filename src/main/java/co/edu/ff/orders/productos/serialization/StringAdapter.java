package co.edu.ff.orders.productos.serialization;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.function.Function;

public class StringAdapter<T extends StringSerializable> implements GsonAdapter<T> {
    private final Function<String, T> factory;

    public StringAdapter(Function<String, T> factory) {
        this.factory = factory;
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String value = jsonElement.getAsString();
        return factory.apply(value);
    }

    @Override
    public JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext) {
        String value = t.valueOf();
        return new JsonPrimitive(value);
    }
}
