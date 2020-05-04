package co.edu.ff.orders.productos.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {
    @Test
    @DisplayName("No debería crear Name para casos inválidos")
    void validarFallos(){
        String name1 = null;
        String name2 = "";
        String name3 = "valor";

        assertAll(
                ()-> assertThrows(NullPointerException.class, () -> Name.of(name1)),
                ()-> assertThrows(IllegalArgumentException.class, () -> Name.of(name2)),
                ()-> assertThrows(IllegalArgumentException.class, () -> Name.of(name3))
        );
    }

    @TestFactory
    @DisplayName("Debería crear Name validos")
    Stream<DynamicTest> validarExito(){
        return Stream.of(
                "texto uno",
                "este es el texto de prueba numero 2",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"

        ).map(data -> {
            String textName = String.format("El valor deberia ser: %s", data);
            Executable executable = () ->{
                ThrowingSupplier<Name> nameThrowingSupplier = () -> Name.of(data);
                assertAll(
                        () -> assertDoesNotThrow(nameThrowingSupplier),
                        () -> assertNotNull(nameThrowingSupplier.get())
                );
            };
            return DynamicTest.dynamicTest(textName, executable);
        });
    }
}