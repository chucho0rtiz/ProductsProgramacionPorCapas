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

class TaxRateTest {
    @Test
    @DisplayName("No debería crear TaxRate para casos inválidos")
    void validarErrores() {
        BigDecimal bid1 = null;
        BigDecimal big2 = new BigDecimal(2);
        BigDecimal big3 = new BigDecimal(-1);
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> TaxRate.of(bid1)),
                () -> assertThrows(IllegalArgumentException.class, () -> TaxRate.of(big2)),
                () -> assertThrows(IllegalArgumentException.class, () -> TaxRate.of(big3))
        );
    }

    @TestFactory
    @DisplayName("Debería crear TaxRate validas")
    Stream<DynamicTest> validarExito() {
        return Stream.of(
                new BigDecimal(0.1), new BigDecimal(1)
        ).map(data -> {
            String testName = String.format("debería ser valido para el TaxRate: %s", data);
            Executable executable = () -> {
                ThrowingSupplier<TaxRate> taxRateThrowingSupplier = () -> TaxRate.of(data);
                assertAll(
                        () -> assertDoesNotThrow(taxRateThrowingSupplier),
                        () -> assertNotNull(taxRateThrowingSupplier.get())
                );
            };
            return DynamicTest.dynamicTest(testName, executable);
        });
    }
}