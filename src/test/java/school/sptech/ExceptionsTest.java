package school.sptech;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import school.sptech.exceptions.DadoInvalidoException;
import school.sptech.exceptions.ItemIndisponivelException;
import school.sptech.exceptions.ItemNaoEncontradoException;

@DisplayName("Exceptions")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class ExceptionsTest {

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Exceptions 1. Herança")
    class HerancaTest {

        @Test
        @Order(1)
        @DisplayName("1. DadoInvalidoException deve ser Unchecked")
        void test1() {
            assertEquals(RuntimeException.class, DadoInvalidoException.class.getSuperclass());
        }

        @Test
        @Order(2)
        @DisplayName("2. ItemNaoEncontradoException deve ser Unchecked")
        void test2() {
            assertEquals(RuntimeException.class, ItemNaoEncontradoException.class.getSuperclass());
        }

        @Test
        @Order(3)
        @DisplayName("3. ItemIndisponivelException deve ser Checked")
        void test3() {
            assertEquals(Exception.class, ItemIndisponivelException.class.getSuperclass());
        }
    }
}
