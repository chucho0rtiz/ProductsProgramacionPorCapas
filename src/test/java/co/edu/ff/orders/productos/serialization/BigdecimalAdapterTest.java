package co.edu.ff.orders.productos.serialization;

import co.edu.ff.orders.productos.domain.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BigdecimalAdapterTest {
    static Gson gson;

    @BeforeAll
    static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(BasePrice.class, new BigdecimalAdapter<>(BasePrice::of))
                .registerTypeAdapter(TaxRate.class, new BigdecimalAdapter<>(TaxRate::of))
                .create();
    }

    @Test
    void deserialize() {
        //BigDecimal bigDecimal = new BigDecimal(14);
        BasePrice basePrice = BasePrice.of(new BigDecimal(14));
        TaxRate taxRate = TaxRate.of(new BigDecimal(1));

        String serializeActual1 = gson.toJson(basePrice);
        String serializeActual2 = gson.toJson(taxRate);

        BasePrice deserializeActual1 = gson.fromJson(serializeActual1, BasePrice.class);
        TaxRate deserializeActual2 = gson.fromJson(serializeActual2, TaxRate.class);

        assertEquals(deserializeActual1.valeuOf(), basePrice.valeuOf());
        assertEquals(deserializeActual2.valeuOf(), taxRate.valeuOf());
    }

    @Test
    void serialize() {
        BasePrice basePrice = BasePrice.of(new BigDecimal(14));
        TaxRate taxRate = TaxRate.of(new BigDecimal(1));

        String actual1 = gson.toJson(basePrice);
        String actual2 = gson.toJson(taxRate);

        String expected1 = String.format("%s", basePrice.getValue());
        assertEquals(actual1, expected1);
        String expected2 = String.format("%s", taxRate.getValue());
        assertEquals(actual2, expected2);
    }

}