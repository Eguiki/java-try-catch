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
import school.sptech.exceptions.ItemIndisponivelException;
import school.sptech.factory.ItemBibliotecaFactory;
import school.sptech.factory.UsuarioFactory;
import school.sptech.provider.NullOrBlankProvider;
import school.sptech.util.EncapsulationUtil;

@DisplayName("ItemBiblioteca")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class ItemBibliotecaTest {

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("ItemBiblioteca 1. Atributos")
    class AtributosTest {

        @Test
        @Order(1)
        @DisplayName("1. Validar Atributos")
        void test1() {
            Class<ItemBiblioteca> clazz = ItemBiblioteca.class;

            assertAll(
                  () -> assertDoesNotThrow(() -> clazz.getDeclaredField("titulo")),
                  () -> assertDoesNotThrow(() -> clazz.getDeclaredField("emprestadoPara"))
            );
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("ItemBiblioteca 2. Encapsulamento")
    class EncapsulamentoTest {

        @Test
        @Order(1)
        @DisplayName("1. Atributos Privados")
        void test1() {
            Class<ItemBiblioteca> clazz = ItemBiblioteca.class;
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
            Class<ItemBiblioteca> clazz = ItemBiblioteca.class;

            List<Method> metodos = new ArrayList<>();

            try {
                metodos.add(clazz.getDeclaredMethod("emprestar", Usuario.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("devolver"));
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
            EncapsulationUtil.validarGettersSetters(ItemBiblioteca.class);
        }
    }

    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("ItemBiblioteca 3. Construtor")
    class ConstrutorTest {

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(NullOrBlankProvider.class)
        @Order(1)
        @DisplayName("1. Deve lancar excecao para titulo invalido")
        void test1(String titulo) {
            Class<ItemBiblioteca> clazz = ItemBiblioteca.class;
            assertDoesNotThrow(() -> {
                clazz.getDeclaredConstructor(String.class);
            }, "Construtor com parâmetro (String titulo) deve existir");

            var throwable = assertThrows(Throwable.class,
                  () -> clazz.getDeclaredConstructor(String.class).newInstance(titulo));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(2)
        @DisplayName("2. Deve inicializar titulo corretamente e emprestadoPara como null")
        void test2() throws Throwable {
            Class<ItemBiblioteca> clazz = ItemBiblioteca.class;

            assertDoesNotThrow(() -> {
                clazz.getDeclaredConstructor(String.class);
            }, "Construtor com parâmetro (String nome) deve existir");

            assertDoesNotThrow(
                  () -> clazz.getDeclaredConstructor(String.class).newInstance("Joao"));

            ItemBiblioteca usuario = clazz.getDeclaredConstructor(String.class).newInstance("Joao");
            assertEquals("Joao", ItemBibliotecaFactory.campos().get("titulo").get(usuario));
            assertNull(ItemBibliotecaFactory.campos().get("emprestadoPara").get(usuario));
        }
    }

    @Nested
    @Order(4)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("ItemBiblioteca 4. Metodo - emprestar")
    class MetodoEmprestarTest {

        @Test
        @Order(1)
        @DisplayName("1. Metodo emprestar deve propagar ItemIndisponivelException")
        void test1() throws ReflectiveOperationException {
            Usuario usuario = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Dom Casmurro",
                  UsuarioFactory.build("Joao"));

            Method metodo = ItemBiblioteca.class.getDeclaredMethod("emprestar", Usuario.class);

            // assert if metodo has throws ItemIndisponivelException
            assertTrue(Arrays.asList(metodo.getExceptionTypes())
                        .contains(ItemIndisponivelException.class),
                  "Metodo emprestar deve declarar que lança ItemIndisponivelException");
        }

        @Test
        @Order(2)
        @DisplayName("2. Usuario nulo deve lancar excecao")
        void test2() throws ReflectiveOperationException {
            ItemBiblioteca item = ItemBibliotecaFactory.build("Dom Casmurro", null);
            Usuario usuario = null;

            Method metodo = ItemBiblioteca.class.getDeclaredMethod("emprestar", Usuario.class);

            var throwable = assertThrows(Throwable.class, () -> metodo.invoke(item, usuario));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(3)
        @DisplayName("3. Item inemprestadoPara deve lancar ItemIndisponivelException")
        void test3() throws ReflectiveOperationException {
            Usuario usuario = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Dom Casmurro",
                  UsuarioFactory.build("Joao"));

            Method metodo = ItemBiblioteca.class.getDeclaredMethod("emprestar", Usuario.class);

            var throwable = assertThrows(Throwable.class, () -> metodo.invoke(item, usuario));
            assertEquals(ItemIndisponivelException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(4)
        @DisplayName("4. Deve atualizar emprestadoPara com o usuario que fez o emprestimo")
        void test4() throws Exception {
            Usuario usuario = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Dom Casmurro", null);

            Method metodo = ItemBiblioteca.class.getDeclaredMethod("emprestar", Usuario.class);
            metodo.invoke(item, usuario);

            assertAll(
                  () -> assertEquals(usuario,
                        ItemBibliotecaFactory.campos().get("emprestadoPara").get(item))
            );
        }
    }

    @Nested
    @Order(5)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("ItemBiblioteca 5. Metodo - devolver")
    class MetodoDevolverTest {

        @Test
        @Order(1)
        @DisplayName("1. Deve setar emprestadoPara como null")
        void test1() throws Exception {
            ItemBiblioteca item = ItemBibliotecaFactory.build("Dom Casmurro",
                  UsuarioFactory.build("Joao"));

            Method metodo = ItemBiblioteca.class.getDeclaredMethod("devolver");
            metodo.invoke(item);

            assertNull(ItemBibliotecaFactory.campos().get("emprestadoPara").get(item));
        }
    }

    @Nested
    @Order(6)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("ItemBiblioteca 6. Metodo - getPrazoEmprestimoDias")
    class MetodoPrazoTest {

        @Test
        @Order(1)
        @DisplayName("1. Deve retornar 7 dias")
        void test1() throws ReflectiveOperationException {
            ItemBiblioteca item = ItemBibliotecaFactory.build("Dom Casmurro", null);

            Method metodo = ItemBiblioteca.class.getDeclaredMethod("getPrazoEmprestimoDias");
            Object resposta = metodo.invoke(item);

            assertEquals(7, resposta);
        }
    }
}
