package co.edu.ff.orders.productos.serialization;

import co.edu.ff.orders.productos.domain.BasePrice;
import co.edu.ff.orders.productos.domain.Description;
import co.edu.ff.orders.productos.domain.Name;
import co.edu.ff.orders.productos.domain.TaxRate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StringAdapterTest {
    static Gson gson;

    @BeforeAll
    static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Name.class, new StringAdapter<>(Name::of))
                .registerTypeAdapter(Description.class, new StringAdapter<>(Description::of))
                .create();
    }

    @Test
    void deserialize() {
        Name name = Name.of("nombre");
        Description description = Description.of("descripcion");

        String actual1 = gson.toJson(name);
        String actual2 = gson.toJson(description);

        Name deserializeActual1 = gson.fromJson(actual1, Name.class);
        Description deserializeactual2 = gson.fromJson(actual2, Description.class);

        assertEquals(deserializeActual1.valueOf(), name.valueOf());
        assertEquals(deserializeactual2.valueOf(), description.valueOf());
    }

    @Test
    void serialize() {
        Name name = Name.of("nombre");
        Description description = Description.of("descripcion");

        String actual1 = gson.toJson(name);
        String actual2 = gson.toJson(description);

        String expected1 = String.format("\"%s\"", name.getValue());
        assertEquals(actual1, expected1);
        String expected2 = String.format("\"%s\"", description.getValue());
        assertEquals(actual2, expected2);
    }
}