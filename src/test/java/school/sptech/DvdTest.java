package school.sptech;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import school.sptech.exceptions.DadoInvalidoException;
import school.sptech.factory.DvdFactory;
import school.sptech.provider.DuracaoInvalidaProvider;
import school.sptech.util.EncapsulationUtil;

@DisplayName("Dvd")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class DvdTest {

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Dvd 1. Atributos")
    class AtributosTest {

        @Test
        @Order(1)
        @DisplayName("1. Validar Atributos")
        void test1() {
            Class<Dvd> clazz = Dvd.class;

            assertAll(
                  () -> assertDoesNotThrow(() -> clazz.getDeclaredField("duracao"))
            );
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Dvd 2. Encapsulamento")
    class EncapsulamentoTest {

        @Test
        @Order(1)
        @DisplayName("1. Atributos Privados")
        void test1() {
            Class<Dvd> clazz = Dvd.class;
            Field[] campos = clazz.getDeclaredFields();

            Stream<Executable> validacoes = Arrays.stream(campos)
                  .map(campo -> () -> assertTrue(Modifier.isPrivate(campo.getModifiers()),
                        String.format("%s deve ser privado", campo.getName())));

            assertAll(validacoes);
        }

        @Test
        @Order(2)
        @DisplayName("2. Métodos Públicos")
        void test2() {
            Class<Dvd> clazz = Dvd.class;

            List<Method> metodos = new ArrayList<>();

            try {
                metodos.add(clazz.getDeclaredMethod("getDuracao"));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("setDuracao", Integer.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("getPrazoEmprestimoDias"));
            } catch (ReflectiveOperationException ignored) {
            }

            Stream<Executable> validacoes = metodos.stream()
                  .map(metodo -> () -> assertTrue(Modifier.isPublic(metodo.getModifiers())));

            assertAll(validacoes);
        }

        @Test
        @Order(3)
        @DisplayName("3. Atributos devem possuir getters e setters")
        void test3() {
            EncapsulationUtil.validarGettersSetters(Dvd.class);
        }
    }

    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Dvd 3. Construtor")
    class ConstrutorTest {

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(DuracaoInvalidaProvider.class)
        @Order(1)
        @DisplayName("1. Deve lancar excecao para duracao invalida")
        void test1(Integer duracao) {
            Class<Dvd> clazz = Dvd.class;
            assertDoesNotThrow(() -> {
                clazz.getDeclaredConstructor(String.class, Integer.class);
            }, "Construtor com parâmetros (String, Integer) deve existir");

            var throwable = assertThrows(Throwable.class,
                  () -> clazz.getDeclaredConstructor(String.class, Integer.class)
                        .newInstance("Matrix", duracao));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(2)
        @DisplayName("2. Deve inicializar titulo, duracao e emprestadoPara corretamente")
        void test2() throws ReflectiveOperationException {
            Class<Dvd> clazz = Dvd.class;

            assertDoesNotThrow(() -> {
                clazz.getDeclaredConstructor(String.class, Integer.class);
            }, "Construtor com parâmetros (String, Integer) deve existir");

            assertDoesNotThrow(
                  () -> clazz.getDeclaredConstructor(String.class, Integer.class)
                        .newInstance("Matrix", 136));

            Dvd dvd = clazz.getDeclaredConstructor(String.class, Integer.class)
                  .newInstance("Matrix", 136);

            assertAll(
                  () -> assertEquals("Matrix", DvdFactory.campos().get("titulo").get(dvd)),
                  () -> assertEquals(136, DvdFactory.campos().get("duracao").get(dvd)),
                  () -> assertNull(DvdFactory.campos().get("emprestadoPara").get(dvd))
            );
        }
    }

    @Nested
    @Order(4)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Dvd 4. Herança")
    class HerancaTest {

        @Test
        @Order(1)
        @DisplayName("1. Dvd deve ser um ItemBiblioteca")
        void test1() {
            Class<Dvd> clazz = Dvd.class;
            assertTrue(ItemBiblioteca.class.isAssignableFrom(clazz),
                  "Dvd deve ser um ItemBiblioteca");
        }
    }

    @Nested
    @Order(5)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Dvd 5. Metodo - getPrazoEmprestimoDias")
    class MetodoPrazoTest {

        @Test
        @Order(1)
        @DisplayName("1. Deve retornar 3 dias")
        void test2() throws ReflectiveOperationException {
            ItemBiblioteca dvd = DvdFactory.build("Matrix", null, 136);
            Method metodo = Dvd.class.getDeclaredMethod("getPrazoEmprestimoDias");
            Object resposta = metodo.invoke(dvd);

            assertEquals(3, resposta);
        }
    }
}
