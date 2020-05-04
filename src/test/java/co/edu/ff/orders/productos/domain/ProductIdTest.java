package co.edu.ff.orders.productos.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {
    @Test
    @DisplayName("No debería crear ProductId para casos inválidos")
    void validarFallos(){
        Long n1 = null;
        Long n2 = 0L;
        Long n3 = -1L;

        assertAll(
                ()-> assertThrows(NullPointerException.class, () -> ProductId.of(n1)),
                ()-> assertThrows(IllegalArgumentException.class, () -> ProductId.of(n2)),
                ()-> assertThrows(IllegalArgumentException.class, () -> ProductId.of(n3))
        );
    }

    @TestFactory
    @DisplayName("Debería crear ProductId validos")
    Stream<DynamicTest> validarExito(){
        return Stream.of(
                145L, 1L, 456486475641L

        ).map(data -> {
            String textName = String.format("El valor deberia ser: %s", data);
            Executable executable = () ->{
                ThrowingSupplier<ProductId> productIdThrowingSupplier = () -> ProductId.of(data);
                assertAll(
                        () -> assertDoesNotThrow(productIdThrowingSupplier),
                        () -> assertNotNull(productIdThrowingSupplier.get())
                );
            };
            return DynamicTest.dynamicTest(textName, executable);
        });
    }
}