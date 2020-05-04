package co.edu.ff.orders.productos.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BasePriceTest {
    @Test
    @DisplayName("No debería crear BasePrice para casos inválidos")
    void validarErrores() {
        BigDecimal bid1 = null;
        BigDecimal big2 = new BigDecimal(0);

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> BasePrice.of(bid1)),
                () -> assertThrows(IllegalArgumentException.class, () -> BasePrice.of(big2))
        );
    }

    @TestFactory
    @DisplayName("Debería crear BasePrice validas")
    Stream<DynamicTest> validarExito() {
        return Stream.of(
                new BigDecimal(1), new BigDecimal(2)
        ).map(data -> {
            String testName = String.format("debería ser valido para el BasePrice: %s", data);
            Executable executable = () -> {
                ThrowingSupplier<BasePrice> basePriceThrowingSupplier = () -> BasePrice.of(data);
                assertAll(
                        () -> assertDoesNotThrow(basePriceThrowingSupplier),
                        () -> assertNotNull(basePriceThrowingSupplier.get())
                );
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }
}