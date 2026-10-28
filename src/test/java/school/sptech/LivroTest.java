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
import school.sptech.factory.LivroFactory;
import school.sptech.provider.NullOrBlankProvider;
import school.sptech.util.EncapsulationUtil;

@DisplayName("Livro")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class LivroTest {

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Livro 1. Atributos")
    class AtributosTest {

        @Test
        @Order(1)
        @DisplayName("1. Validar Atributos")
        void test1() {
            Class<Livro> clazz = Livro.class;

            assertAll(
                  () -> assertDoesNotThrow(() -> clazz.getDeclaredField("autor"))
            );
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Livro 2. Encapsulamento")
    class EncapsulamentoTest {

        @Test
        @Order(1)
        @DisplayName("1. Atributos Privados")
        void test1() {
            Class<Livro> clazz = Livro.class;
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
            Class<Livro> clazz = Livro.class;

            List<Method> metodos = new ArrayList<>();

            try {
                metodos.add(clazz.getDeclaredMethod("getAutor"));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("setAutor", String.class));
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
            EncapsulationUtil.validarGettersSetters(Livro.class);
        }
    }

    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Livro 3. Construtor")
    class ConstrutorTest {

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(NullOrBlankProvider.class)
        @Order(1)
        @DisplayName("1. Deve lancar excecao para autor invalido")
        void test1(String autor) {
            Class<Livro> clazz = Livro.class;
            assertDoesNotThrow(() -> {
                clazz.getDeclaredConstructor(String.class, String.class);
            }, "Construtor com parâmetros (String, String) deve existir");

            var throwable = assertThrows(Throwable.class,
                  () -> clazz.getDeclaredConstructor(String.class, String.class)
                        .newInstance("Clean Code", autor));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(2)
        @DisplayName("2. Deve inicializar titulo, autor e emprestadoPara corretamente")
        void test2() throws ReflectiveOperationException {
            Class<Livro> clazz = Livro.class;

            assertDoesNotThrow(() -> {
                clazz.getDeclaredConstructor(String.class, String.class);
            }, "Construtor com parâmetros (String, String) deve existir");

            assertDoesNotThrow(
                  () -> clazz.getDeclaredConstructor(String.class, String.class)
                        .newInstance("Clean Code", "Robert C. Martin"));

            Livro livro = clazz.getDeclaredConstructor(String.class, String.class)
                  .newInstance("Clean Code", "Robert C. Martin");

            assertAll(
                  () -> assertEquals("Clean Code", LivroFactory.campos().get("titulo").get(livro)),
                  () -> assertEquals("Robert C. Martin",
                        LivroFactory.campos().get("autor").get(livro)),
                  () -> assertNull(LivroFactory.campos().get("emprestadoPara").get(livro))
            );
        }
    }

    @Nested
    @Order(4)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Livro 4. Herança")
    class HerancaTest {

        @Test
        @Order(1)
        @DisplayName("1. Livro deve ser um ItemBiblioteca")
        void test1() throws ReflectiveOperationException {
            Class<Livro> clazz = Livro.class;
            assertTrue(ItemBiblioteca.class.isAssignableFrom(clazz),
                  "Livro deve ser um ItemBiblioteca");
        }
    }

    @Nested
    @Order(5)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Livro 5. Metodo - getPrazoEmprestimoDias")
    class MetodoPrazoTest {

        @Test
        @Order(1)
        @DisplayName("1. Deve retornar 14 dias")
        void test2() throws ReflectiveOperationException {
            ItemBiblioteca livro = LivroFactory.build("Clean Code", null, "Robert C. Martin");
            Method metodo = Livro.class.getDeclaredMethod("getPrazoEmprestimoDias");
            Object resposta = metodo.invoke(livro);

            assertEquals(14, resposta);
        }
    }
}
