package school.sptech;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
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
import school.sptech.factory.UsuarioFactory;
import school.sptech.provider.NullOrBlankProvider;
import school.sptech.util.EncapsulationUtil;

@DisplayName("Usuario")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class UsuarioTest {

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Usuario 1. Atributos")
    class AtributosTest {

        @Test
        @Order(1)
        @DisplayName("1. Validar Atributos")
        void test1() {
            Class<Usuario> clazz = Usuario.class;

            assertAll(
                  () -> assertDoesNotThrow(() -> clazz.getDeclaredField("nome"),
                        "Deve possuir o atributo nome")
            );
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Usuario 2. Encapsulamento")
    class EncapsulamentoTest {

        @Test
        @Order(1)
        @DisplayName("1. Atributos Privados")
        void test1() {
            Class<Usuario> clazz = Usuario.class;
            Field[] campos = clazz.getDeclaredFields();

            Stream<Executable> validacoes = Arrays.stream(campos)
                  .map((campo) -> () -> assertTrue(Modifier.isPrivate(campo.getModifiers()),
                        String.format("%s deve ser protegido", campo.getName())));

            assertAll(validacoes);
        }

        @Test
        @Order(2)
        @DisplayName("2. Atributos devem possuir getters e setters")
        void test3() {
            EncapsulationUtil.validarGettersSetters(Usuario.class);
        }
    }

    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Usuario 3. Construtor")
    class ConstrutorTest {

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(NullOrBlankProvider.class)
        @Order(1)
        @DisplayName("1. Deve lancar excecao para nome invalido")
        void test1(String nome) throws Exception {
            Class<Usuario> clazz = Usuario.class;

            assertDoesNotThrow(() -> {
                clazz.getDeclaredConstructor(String.class);
            }, "Construtor com parâmetro (String nome) deve existir");

            var throwable = assertThrows(Throwable.class,
                  () -> clazz.getDeclaredConstructor(String.class).newInstance(nome));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(2)
        @DisplayName("2. Deve inicializar nome corretamente")
        void test2() throws Throwable {
            Class<Usuario> clazz = Usuario.class;

            assertDoesNotThrow(() -> {
                clazz.getDeclaredConstructor(String.class);
            }, "Construtor com parâmetro (String nome) deve existir");

            assertDoesNotThrow(
                  () -> clazz.getDeclaredConstructor(String.class).newInstance("Joao"));

            Usuario usuario = (Usuario) clazz.getDeclaredConstructor(String.class)
                  .newInstance("Joao");
            assertEquals("Joao", UsuarioFactory.campos().get("nome").get(usuario));
        }
    }

}
