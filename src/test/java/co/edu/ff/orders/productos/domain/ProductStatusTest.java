package co.edu.ff.orders.productos.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductStatusTest {
    @Test
    @DisplayName("No debería crear ProductStatus para casos inválidos")
    void validarFallos(){
        String name1 = null;
        String name2 = "";
        String name3 = "valor";

        assertAll(
                ()-> assertThrows(NullPointerException.class, () -> ProductStatus.valueOf(name1)),
                ()-> assertThrows(IllegalArgumentException.class, () -> ProductStatus.valueOf(name2)),
                ()-> assertThrows(IllegalArgumentException.class, () -> ProductStatus.valueOf(name3)),
                ()-> assertThrows(IllegalArgumentException.class, () -> ProductStatus.valueOf(name3))
        );
    }

    @TestFactory
    @DisplayName("Debería crear ProductStatus validos")
    Stream<DynamicTest> validarExito(){
        return Stream.of(
                "Borrado",
                "Publicado"

        ).map(data -> {
            String textName = String.format("El valor deberia ser: %s", data);
            Executable executable = () ->{
                ThrowingSupplier<ProductStatus> productStatusThrowingSupplier = () -> ProductStatus.valueOf(data);
                assertAll(
                        () -> assertDoesNotThrow(productStatusThrowingSupplier),
                        () -> assertNotNull(productStatusThrowingSupplier.get())
                );
            };
            return DynamicTest.dynamicTest(textName, executable);
        });
    }
}