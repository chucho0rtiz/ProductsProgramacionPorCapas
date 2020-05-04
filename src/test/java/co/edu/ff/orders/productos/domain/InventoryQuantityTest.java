package co.edu.ff.orders.productos.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InventoryQuantityTest {

    @Test
    @DisplayName("No debería crear InventoryQuantity para casos inválidos")
    void validarFallos(){
        Integer numero1 = null;
        Integer numero2 = 0;

        assertAll(
                ()-> assertThrows(NullPointerException.class, () -> InventoryQuantity.of(numero1)),
                ()-> assertThrows(IllegalArgumentException.class, () -> InventoryQuantity.of(numero2))
        );
    }

    @TestFactory
    @DisplayName("Debería crear InventoryQuantity validos")
    Stream<DynamicTest> validarExito(){
        return Stream.of(
                1, 4, 154
        ).map(data -> {
            String textName = String.format("El valor deveria ser: %s", data);
            Executable executable = () ->{
                ThrowingSupplier<InventoryQuantity> inventoryQuantityThrowingSupplier = () -> InventoryQuantity.of(data);
                assertAll(
                        () -> assertDoesNotThrow(inventoryQuantityThrowingSupplier),
                        () -> assertNotNull(inventoryQuantityThrowingSupplier.get())
                );
            };
            return DynamicTest.dynamicTest(textName, executable);
        });
    }

}