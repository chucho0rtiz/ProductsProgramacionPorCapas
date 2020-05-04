package co.edu.ff.orders.configuration;

import co.edu.ff.orders.productos.domain.*;
import co.edu.ff.orders.productos.exceptions.ProductException;
import co.edu.ff.orders.productos.serialization.BigdecimalAdapter;
import co.edu.ff.orders.productos.serialization.IntegerAdapter;
import co.edu.ff.orders.productos.serialization.LongAdapter;
import co.edu.ff.orders.productos.serialization.StringAdapter;
import co.edu.ff.orders.user.domain.Password;
import co.edu.ff.orders.user.domain.Username;
import co.edu.ff.orders.user.exceptions.UserException;
import co.edu.ff.orders.user.serialization.StringValueAdapter;
import co.edu.ff.orders.user.serialization.UsernameAdapter;
import com.google.gson.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.function.Function;

@Configuration
public class GsonConfiguration {

    @Bean
    public Gson gson(){

        return new GsonBuilder()
                .registerTypeAdapter(Username.class, new StringValueAdapter<>(Username::of))
                .registerTypeAdapter(Password.class, new StringValueAdapter<Password>(Password::of))
                .registerTypeAdapter(UserException.class, new JsonSerializer<UserException>() {
                    @Override
                    public JsonElement serialize(UserException src, Type typeOfSrc, JsonSerializationContext context) {
                        JsonObject jsonObject = new JsonObject();
                        String message = src.getMessage();
                        JsonPrimitive errorValue = new JsonPrimitive(message);
                        jsonObject.add("error", errorValue);
                        return jsonObject;
                    }
                })
                .registerTypeAdapter(ProductId.class, new LongAdapter<>(ProductId::of))
                .registerTypeAdapter(Name.class, new StringAdapter<>(Name::of))
                .registerTypeAdapter(Description.class, new StringAdapter<>(Description::of))
                .registerTypeAdapter(BasePrice.class, new BigdecimalAdapter<>(BasePrice::of))
                .registerTypeAdapter(TaxRate.class, new BigdecimalAdapter<>(TaxRate::of))
                .registerTypeAdapter(InventoryQuantity.class, new IntegerAdapter<>(InventoryQuantity::of))
                .registerTypeAdapter(ProductException.class, new JsonSerializer<ProductException>() {
                    @Override
                    public JsonElement serialize(ProductException e, Type type, JsonSerializationContext jsonSerializationContext) {
                        JsonObject jsonObject = new JsonObject();
                        String message = e.getMessage();
                        JsonPrimitive errprValue = new JsonPrimitive(message);
                        jsonObject.add("error", errprValue);
                        return jsonObject;
                    }
                })
                .create();
    }
}
