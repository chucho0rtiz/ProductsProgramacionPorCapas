package co.edu.ff.orders.productos.serialization;

import co.edu.ff.orders.productos.domain.BasePrice;
import co.edu.ff.orders.productos.domain.ProductId;
import co.edu.ff.orders.productos.domain.TaxRate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LongAdapterTest {

    static Gson gson;

    @BeforeAll
    static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(ProductId.class, new LongAdapter<>(ProductId::of))
                .create();
    }

    @Test
    void deserialize() {
        ProductId productId = ProductId.of(1L);
        String actual1 = gson.toJson(productId);
        ProductId deserializeActual = gson.fromJson(actual1, ProductId.class);
        assertEquals(deserializeActual.valueOf(), productId.valueOf());
    }

    @Test
    void serialize() {
        ProductId productId = ProductId.of(1L);
        String actual = gson.toJson(productId);
        String expected = String.format("%s", productId.getValue());
        assertEquals(actual, expected);
    }
}