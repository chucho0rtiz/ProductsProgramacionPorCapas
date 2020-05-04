package co.edu.ff.orders.productos.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionTest {

    @Test
    @DisplayName("No debería crear descripciones para casos inválidos")
    void validarErrores() {
        String d1 = null;
        String d2 = "";
        String d3 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> Description.of(d1)),
                () -> assertThrows(IllegalArgumentException.class, () -> Description.of(d2)),
                () -> assertThrows(IllegalArgumentException.class, () -> Description.of(d3))
        );
    }

    @TestFactory
    @DisplayName("Debería crear descripciones validas")
    Stream<DynamicTest> validarExito() {
        return Stream.of(
                "Descripcion","Esta es una descripcion!"
        ).map(descripcion -> {
                String testName = String.format("debería ser valido para la descripcion: %s", descripcion);
                Executable executable = () -> {
                    ThrowingSupplier<Description> descripcionThrowingSupplier = () -> Description.of(descripcion);
                    assertAll(
                            () -> assertDoesNotThrow(descripcionThrowingSupplier),
                            () -> assertNotNull(descripcionThrowingSupplier.get())
                    );
                };
                return DynamicTest.dynamicTest(testName, executable);
            });
    }
}