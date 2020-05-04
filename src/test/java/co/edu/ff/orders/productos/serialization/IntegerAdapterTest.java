package co.edu.ff.orders.productos.serialization;

import co.edu.ff.orders.productos.domain.BasePrice;
import co.edu.ff.orders.productos.domain.InventoryQuantity;
import co.edu.ff.orders.productos.domain.TaxRate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IntegerAdapterTest {

    static Gson gson;

    @BeforeAll
    static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(InventoryQuantity.class, new IntegerAdapter<>(InventoryQuantity::of))
                .create();
    }

    @Test
    void deserialize() {
        InventoryQuantity inventoryQuantity = InventoryQuantity.of(1);
        String actual = gson.toJson(inventoryQuantity);
        InventoryQuantity deserializeActual = gson.fromJson(actual, InventoryQuantity.class);
        assertEquals(deserializeActual.valueOf(), inventoryQuantity.valueOf());
    }

    @Test
    void serialize() {
        InventoryQuantity inventoryQuantity = InventoryQuantity.of(1);
        String actual = gson.toJson(inventoryQuantity);
        String expected = String.format("%s", inventoryQuantity.getValue());
        assertEquals(actual, expected);
    }
}