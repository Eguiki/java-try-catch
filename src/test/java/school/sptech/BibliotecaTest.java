package school.sptech;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import org.junit.jupiter.params.provider.ValueSource;
import school.sptech.exceptions.DadoInvalidoException;
import school.sptech.exceptions.ItemIndisponivelException;
import school.sptech.exceptions.ItemNaoEncontradoException;
import school.sptech.factory.BibliotecaFactory;
import school.sptech.factory.DvdFactory;
import school.sptech.factory.ItemBibliotecaFactory;
import school.sptech.factory.LivroFactory;
import school.sptech.factory.UsuarioFactory;
import school.sptech.provider.NullOrBlankProvider;
import school.sptech.provider.biblioteca.BuscarPorTituloNaoEncontradoProvider;
import school.sptech.provider.biblioteca.BuscarPorTituloProvider;
import school.sptech.provider.biblioteca.CalcularPrazoMedioEmprestimoProvider;
import school.sptech.provider.biblioteca.EmprestarVariosTitulosDuplicadosProvider;
import school.sptech.provider.biblioteca.EmprestarVariosTitulosInvalidosProvider;
import school.sptech.util.EncapsulationUtil;
import school.sptech.util.EqualsUtil;

@DisplayName("Biblioteca")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class BibliotecaTest {

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 1. Atributos")
    class AtributosTest {

        @Test
        @Order(1)
        @DisplayName("1. Validar Atributos")
        void test1() {
            Class<Biblioteca> clazz = Biblioteca.class;

            assertAll(
                  () -> assertDoesNotThrow(() -> clazz.getDeclaredField("nome")),
                  () -> assertDoesNotThrow(() -> clazz.getDeclaredField("itens"))
            );
        }
    }

    @Nested
    @Order(2)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 2. Metodos")
    class MetodosExistenciaTest {

        @Test
        @Order(1)
        @DisplayName("1. Validar Metodos")
        void test1() {
            Class<Biblioteca> clazz = Biblioteca.class;

            assertAll(
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("adicionarItem", ItemBiblioteca.class)),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("buscarPorTitulo", String.class)),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("emprestarItem", String.class,
                              Usuario.class)),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("devolverItem", String.class)),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("calcularPrazoMedioEmprestimo")),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("listarEmprestadosPara", Usuario.class)),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("devolverTodosDoUsuario", Usuario.class)),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("contarPorTipo", String.class)),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("possuiItemDisponivel", String.class)),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("listarDisponiveis")),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("calcularPrazoMedioPorTipo", String.class)),
                  () -> assertDoesNotThrow(
                        () -> clazz.getDeclaredMethod("emprestarVarios", List.class,
                              Usuario.class))
            );
        }
    }

    @Nested
    @Order(3)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 3. Encapsulamento")
    class EncapsulamentoTest {

        @Test
        @Order(1)
        @DisplayName("1. Atributos Privados")
        void test1() {
            Class<Biblioteca> clazz = Biblioteca.class;
            Field[] campos = clazz.getDeclaredFields();

            Stream<Executable> validacoes = Arrays.stream(campos)
                  .map((campo) -> () -> assertTrue(Modifier.isPrivate(campo.getModifiers()),
                        String.format("%s deve ser privado", campo.getName())));

            assertAll(validacoes);
        }

        @Test
        @Order(2)
        @DisplayName("2. Metodos Publicos")
        void test2() {
            Class<Biblioteca> clazz = Biblioteca.class;
            List<Method> metodos = new ArrayList<>();

            try {
                metodos.add(clazz.getDeclaredMethod("adicionarItem", ItemBiblioteca.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("buscarPorTitulo", String.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("emprestarItem", String.class, Usuario.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("devolverItem", String.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("calcularPrazoMedioEmprestimo"));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("listarEmprestadosPara", Usuario.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("devolverTodosDoUsuario", Usuario.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("contarPorTipo", String.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("possuiItemDisponivel", String.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("listarDisponiveis"));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("calcularPrazoMedioPorTipo", String.class));
            } catch (ReflectiveOperationException ignored) {
            }

            try {
                metodos.add(clazz.getDeclaredMethod("emprestarVarios", List.class, Usuario.class));
            } catch (ReflectiveOperationException ignored) {
            }

            Stream<Executable> validacoes = metodos.stream()
                  .map((metodo) -> () -> assertTrue(Modifier.isPublic(metodo.getModifiers()),
                        String.format("%s deve ser publico", metodo.getName())));

            assertAll(validacoes);
        }

        @Test
        @Order(3)
        @DisplayName("3. Atributos devem possuir getters e setters")
        void test3() {
            EncapsulationUtil.validarGettersSetters(Biblioteca.class);
        }
    }

    @Nested
    @Order(4)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 4. Construtor")
    class ConstrutorTest {

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(NullOrBlankProvider.class)
        @Order(1)
        @DisplayName("1. Deve lancar excecao para nome invalido")
        void test1(String nome) {
            Class<Biblioteca> clazz = Biblioteca.class;
            assertDoesNotThrow(() -> clazz.getDeclaredConstructor(String.class),
                  "Construtor com parâmetro (String nome) deve existir");
            var throwable = assertThrows(Throwable.class,
                  () -> clazz.getDeclaredConstructor(String.class).newInstance(nome));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(2)
        @DisplayName("2. Deve inicializar nome e itens corretamente")
        void test2() throws Throwable {
            Class<Biblioteca> clazz = Biblioteca.class;
            assertDoesNotThrow(() -> clazz.getDeclaredConstructor(String.class),
                  "Construtor com parâmetro (String nome) deve existir");
            assertDoesNotThrow(
                  () -> clazz.getDeclaredConstructor(String.class).newInstance("SPTech"));
            Biblioteca biblioteca = clazz.getDeclaredConstructor(String.class)
                  .newInstance("SPTech");
            assertAll(
                  () -> assertEquals("SPTech",
                        BibliotecaFactory.campos().get("nome").get(biblioteca)),
                  () -> assertNotNull(BibliotecaFactory.campos().get("itens").get(biblioteca))
            );
        }
    }

    @Nested
    @Order(5)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 5. Metodo - adicionarItem")
    class MetodoAdicionarItemTest {

        @Test
        @Order(1)
        @DisplayName("1. Item nulo deve lancar excecao")
        void test1() throws ReflectiveOperationException {
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech", new ArrayList<>());
            Method metodoAdicionarItem = Biblioteca.class.getDeclaredMethod("adicionarItem",
                  ItemBiblioteca.class);
            var throwable = assertThrows(Throwable.class,
                  () -> metodoAdicionarItem.invoke(biblioteca, new Object[]{null}));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(2)
        @DisplayName("2. Deve adicionar item corretamente")
        void test2() throws Exception {
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech", new ArrayList<>());
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", null);

            Method metodoAdicionarItem = Biblioteca.class.getDeclaredMethod("adicionarItem",
                  ItemBiblioteca.class);
            assertDoesNotThrow(() -> metodoAdicionarItem.invoke(biblioteca, item));

            List<ItemBiblioteca> itens = (List<ItemBiblioteca>) BibliotecaFactory.campos()
                  .get("itens").get(biblioteca);
            assertTrue(itens.contains(item));
        }
    }

    @Nested
    @Order(6)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 6. Metodo - buscarPorTitulo")
    class MetodoBuscarPorTituloTest {

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(BuscarPorTituloProvider.class)
        @Order(1)
        @DisplayName("1. Deve ignorar maiusculas e minusculas")
        void test1(Biblioteca biblioteca, String tituloBusca, ItemBiblioteca itemEsperado)
              throws ReflectiveOperationException {
            Method metodoBuscarPorTitulo = Biblioteca.class.getDeclaredMethod("buscarPorTitulo",
                  String.class);
            ItemBiblioteca resposta = (ItemBiblioteca) metodoBuscarPorTitulo.invoke(biblioteca,
                  tituloBusca);
            assertTrue(EqualsUtil.equals(itemEsperado, resposta));
        }

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(BuscarPorTituloNaoEncontradoProvider.class)
        @Order(2)
        @DisplayName("2. Deve lancar excecao para item inexistente")
        void test2(Biblioteca biblioteca, String tituloBusca) throws ReflectiveOperationException {
            Method metodoBuscarPorTitulo = Biblioteca.class.getDeclaredMethod("buscarPorTitulo",
                  String.class);
            var throwable = assertThrows(Throwable.class,
                  () -> metodoBuscarPorTitulo.invoke(biblioteca, tituloBusca));
            assertEquals(ItemNaoEncontradoException.class, throwable.getCause().getClass());
        }
    }

    @Nested
    @Order(7)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 7. Metodo - emprestarItem")
    class MetodoEmprestarItemTest {

        @Test
        @Order(1)
        @DisplayName("1. Não deve propagar ItemIndisponivelException")
        void test0() throws ReflectiveOperationException {
            Method metodoEmprestarItem = Biblioteca.class.getDeclaredMethod("emprestarItem",
                  String.class, Usuario.class);
            assertFalse(Arrays.asList(metodoEmprestarItem.getExceptionTypes())
                        .contains(ItemIndisponivelException.class),
                  "Metodo emprestarItem não deve propagar ItemIndisponivelException");
        }


        @Test
        @Order(2)
        @DisplayName("2. Titulo nulo deve lancar excecao")
        void test2() throws ReflectiveOperationException {
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech", new ArrayList<>());
            Usuario usuario = UsuarioFactory.build("Ana");

            Method metodoEmprestarItem = Biblioteca.class.getDeclaredMethod("emprestarItem",
                  String.class, Usuario.class);
            var throwable = assertThrows(Throwable.class,
                  () -> metodoEmprestarItem.invoke(biblioteca, new Object[]{null, usuario}));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(3)
        @DisplayName("3. Usuario nulo deve lancar excecao")
        void test1_3() throws ReflectiveOperationException {
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech", new ArrayList<>());
            Method metodoEmprestarItem = Biblioteca.class.getDeclaredMethod("emprestarItem",
                  String.class, Usuario.class);
            var throwable = assertThrows(Throwable.class,
                  () -> metodoEmprestarItem.invoke(biblioteca, new Object[]{"Duna", null}));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(4)
        @DisplayName("3. Deve laçar ItemNaoEncontradoException para item inexistente")
        void test3() throws ReflectiveOperationException {
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech", new ArrayList<>());
            Usuario usuario = UsuarioFactory.build("Ana");
            Method metodoEmprestarItem = Biblioteca.class.getDeclaredMethod("emprestarItem",
                  String.class, Usuario.class);
            var throwable = assertThrows(Throwable.class,
                  () -> metodoEmprestarItem.invoke(biblioteca, "Titulo Inexistente", usuario));
            assertEquals(ItemNaoEncontradoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(5)
        @DisplayName("4. Caso item já esteja emprestado para outro usuário deve imprimir mensagem de indisponibilidade e não emprestar para o novo usuário")
        void test4() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", ana);
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech",
                  new ArrayList<>(List.of(item)));

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;

            Method metodoEmprestarItem = Biblioteca.class.getDeclaredMethod("emprestarItem",
                  String.class, Usuario.class);
            try {
                System.setOut(new PrintStream(output));
                metodoEmprestarItem.invoke(biblioteca, "Duna", bia);
            } finally {
                System.setOut(originalOut);
            }
            assertAll(
                  () -> assertEquals(ana,
                        ItemBibliotecaFactory.campos().get("emprestadoPara").get(item)),
                  () -> assertEquals("Esse item já está emprestado para outro usuário.",
                        output.toString().trim())
            );
        }

        @Test
        @Order(6)
        @DisplayName("5. Deve emprestar item para usuario")
        void test5() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", null);
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech",
                  new ArrayList<>(List.of(item)));

            Method metodoEmprestarItem = Biblioteca.class.getDeclaredMethod("emprestarItem",
                  String.class, Usuario.class);
            assertDoesNotThrow(() -> metodoEmprestarItem.invoke(biblioteca, "Duna", ana));
            assertEquals(ana, ItemBibliotecaFactory.campos().get("emprestadoPara").get(item));
        }
    }

    @Nested
    @Order(8)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 8. Metodo - devolverItem")
    class MetodoDevolverItemTest {

        @Test
        @Order(1)
        @DisplayName("1. Titulo nulo deve lancar excecao")
        void test1() throws ReflectiveOperationException {
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech", new ArrayList<>());

            Method metodoDevolverItem = Biblioteca.class.getDeclaredMethod("devolverItem",
                  String.class);
            var throwable = assertThrows(Throwable.class,
                  () -> metodoDevolverItem.invoke(biblioteca, new Object[]{null}));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(2)
        @DisplayName("2. Deve laçar ItemNaoEncontradoException para item inexistente")
        void test2() throws ReflectiveOperationException {
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech", new ArrayList<>());

            Method metodoDevolverItem = Biblioteca.class.getDeclaredMethod("devolverItem",
                  String.class);
            var throwable = assertThrows(Throwable.class,
                  () -> metodoDevolverItem.invoke(biblioteca, "Titulo Inexistente"));
            assertEquals(ItemNaoEncontradoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(3)
        @DisplayName("3. Deve devolver item corretamente")
        void test3() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", ana);
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech",
                  new ArrayList<>(List.of(item)));

            Method metodoDevolverItem = Biblioteca.class.getDeclaredMethod("devolverItem",
                  String.class);
            assertDoesNotThrow(() -> metodoDevolverItem.invoke(biblioteca, "Duna"));
            assertNull(ItemBibliotecaFactory.campos().get("emprestadoPara").get(item));
        }

        @Test
        @Order(4)
        @DisplayName("4. Deve devolver item corretamente")
        void test4() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", ana);
            Biblioteca biblioteca = BibliotecaFactory.build("SPTech",
                  new ArrayList<>(List.of(item)));

            Method metodoDevolverItem = Biblioteca.class.getDeclaredMethod("devolverItem",
                  String.class);
            assertDoesNotThrow(() -> metodoDevolverItem.invoke(biblioteca, "Duna"));
            assertNull(ItemBibliotecaFactory.campos().get("emprestadoPara").get(item));
        }
    }

    @Nested
    @Order(14)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 14. Metodo - listarEmprestadosPara")
    class MetodoListarEmprestadosParaTest {

        @Test
        @Order(1)
        @DisplayName("1. Usuario nulo deve lancar excecao")
        void test1() throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca();
            Method metodo = metodo("listarEmprestadosPara", Usuario.class);

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, new Object[]{null}));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(2)
        @DisplayName("2. Biblioteca sem itens deve retornar lista vazia")
        void test2() throws Exception {
            Biblioteca biblioteca = biblioteca();
            Usuario ana = UsuarioFactory.build("Ana");

            Object resposta = metodo("listarEmprestadosPara", Usuario.class)
                  .invoke(biblioteca, ana);

            assertNotNull(resposta);
            assertTrue(((List<?>) resposta).isEmpty());
        }

        @Test
        @Order(3)
        @DisplayName("3. Usuario sem itens emprestados deve retornar lista vazia")
        void test3() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            Biblioteca biblioteca = biblioteca(
                  ItemBibliotecaFactory.build("Duna", null),
                  LivroFactory.build("Matrix", bia, "Autor"),
                  DvdFactory.build("Dracula", bia, 100)
            );

            Object resposta = metodo("listarEmprestadosPara", Usuario.class)
                  .invoke(biblioteca, ana);

            assertNotNull(resposta);
            assertTrue(((List<?>) resposta).isEmpty());
        }

        @Test
        @Order(4)
        @DisplayName("4. Deve retornar apenas os itens do usuario mantendo a ordem de cadastro")
        void test4() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca livroAna = LivroFactory.build("Livro Ana", ana, "Autor");
            ItemBiblioteca disponivel = ItemBibliotecaFactory.build("Disponivel", null);
            ItemBiblioteca dvdBia = DvdFactory.build("Dvd Bia", bia, 100);
            ItemBiblioteca itemAna = ItemBibliotecaFactory.build("Item Ana", ana);
            ItemBiblioteca dvdAna = DvdFactory.build("Dvd Ana", ana, 100);
            Biblioteca biblioteca = biblioteca(livroAna, disponivel, dvdBia, itemAna, dvdAna);

            List<?> resposta = (List<?>) metodo("listarEmprestadosPara", Usuario.class)
                  .invoke(biblioteca, ana);

            assertEquals(List.of(livroAna, itemAna, dvdAna), resposta);
        }

        @Test
        @Order(5)
        @DisplayName("5. Deve comparar usuarios pelo nome e nao pela referencia")
        void test5() throws Exception {
            Usuario anaDoEmprestimo = UsuarioFactory.build("Ana");
            Usuario anaDaBusca = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", anaDoEmprestimo);
            Biblioteca biblioteca = biblioteca(item);

            List<?> resposta = (List<?>) metodo("listarEmprestadosPara", Usuario.class)
                  .invoke(biblioteca, anaDaBusca);

            assertEquals(List.of(item), resposta);
        }

        @Test
        @Order(6)
        @DisplayName("6. Nao deve retornar itens de usuario com nome diferente")
        void test6() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario outraAna = UsuarioFactory.build("Ana Maria");
            Usuario bia = UsuarioFactory.build("Bia");
            Biblioteca biblioteca = biblioteca(
                  ItemBibliotecaFactory.build("Duna", outraAna),
                  ItemBibliotecaFactory.build("Matrix", bia)
            );

            List<?> resposta = (List<?>) metodo("listarEmprestadosPara", Usuario.class)
                  .invoke(biblioteca, ana);

            assertTrue(resposta.isEmpty());
        }

        @Test
        @Order(7)
        @DisplayName("7. Deve retornar nova lista sem alterar a lista de itens da biblioteca")
        @SuppressWarnings("unchecked")
        void test7() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca item1 = ItemBibliotecaFactory.build("Duna", ana);
            ItemBiblioteca item2 = ItemBibliotecaFactory.build("Matrix", ana);
            Biblioteca biblioteca = biblioteca(item1, item2);

            List<ItemBiblioteca> resposta = (List<ItemBiblioteca>) metodo(
                  "listarEmprestadosPara", Usuario.class).invoke(biblioteca, ana);

            assertNotSame(itensDe(biblioteca), resposta);
            try {
                resposta.clear();
            } catch (UnsupportedOperationException ignored) {
            }
            assertEquals(List.of(item1, item2), itensDe(biblioteca));
        }

        @Test
        @Order(8)
        @DisplayName("8. Nao deve alterar o estado dos itens")
        void test8() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca itemAna = ItemBibliotecaFactory.build("Duna", ana);
            ItemBiblioteca itemBia = ItemBibliotecaFactory.build("Matrix", bia);
            ItemBiblioteca disponivel = ItemBibliotecaFactory.build("Dracula", null);
            Biblioteca biblioteca = biblioteca(itemAna, itemBia, disponivel);

            metodo("listarEmprestadosPara", Usuario.class).invoke(biblioteca, ana);

            assertAll(
                  () -> assertEquals(3, itensDe(biblioteca).size()),
                  () -> assertEquals(ana, emprestadoPara(itemAna)),
                  () -> assertEquals(bia, emprestadoPara(itemBia)),
                  () -> assertNull(emprestadoPara(disponivel))
            );
        }
    }

    @Nested
    @Order(15)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 15. Metodo - devolverTodosDoUsuario")
    class MetodoDevolverTodosDoUsuarioTest {

        @Test
        @Order(1)
        @DisplayName("1. Usuario nulo deve lancar excecao")
        void test1() throws ReflectiveOperationException {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", ana);
            Biblioteca biblioteca = biblioteca(item);
            Method metodo = metodo("devolverTodosDoUsuario", Usuario.class);

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, new Object[]{null}));
            assertAll(
                  () -> assertEquals(DadoInvalidoException.class, throwable.getCause().getClass()),
                  () -> assertEquals(ana, emprestadoPara(item))
            );
        }

        @Test
        @Order(2)
        @DisplayName("2. Biblioteca sem itens nao deve lancar excecao")
        void test2() throws Exception {
            Biblioteca biblioteca = biblioteca();
            Usuario ana = UsuarioFactory.build("Ana");
            Method metodo = metodo("devolverTodosDoUsuario", Usuario.class);

            assertDoesNotThrow(() -> metodo.invoke(biblioteca, ana));
            assertTrue(itensDe(biblioteca).isEmpty());
        }

        @Test
        @Order(3)
        @DisplayName("3. Usuario sem itens emprestados nao deve lancar excecao nem alterar itens")
        void test3() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca itemBia = ItemBibliotecaFactory.build("Duna", bia);
            ItemBiblioteca disponivel = ItemBibliotecaFactory.build("Matrix", null);
            Biblioteca biblioteca = biblioteca(itemBia, disponivel);
            Method metodo = metodo("devolverTodosDoUsuario", Usuario.class);

            assertDoesNotThrow(() -> metodo.invoke(biblioteca, ana));
            assertAll(
                  () -> assertEquals(bia, emprestadoPara(itemBia)),
                  () -> assertNull(emprestadoPara(disponivel))
            );
        }

        @Test
        @Order(4)
        @DisplayName("4. Deve devolver todos os itens do usuario")
        void test4() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Item", ana);
            ItemBiblioteca livro = LivroFactory.build("Livro", ana, "Autor");
            ItemBiblioteca dvd = DvdFactory.build("Dvd", ana, 100);
            Biblioteca biblioteca = biblioteca(item, livro, dvd);

            metodo("devolverTodosDoUsuario", Usuario.class).invoke(biblioteca, ana);

            assertAll(
                  () -> assertNull(emprestadoPara(item)),
                  () -> assertNull(emprestadoPara(livro)),
                  () -> assertNull(emprestadoPara(dvd))
            );
        }

        @Test
        @Order(5)
        @DisplayName("5. Nao deve alterar itens disponiveis ou emprestados para outros usuarios")
        void test5() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca itemAna1 = ItemBibliotecaFactory.build("Duna", ana);
            ItemBiblioteca itemBia = LivroFactory.build("Matrix", bia, "Autor");
            ItemBiblioteca disponivel = DvdFactory.build("Dracula", null, 100);
            ItemBiblioteca itemAna2 = LivroFactory.build("Senhor dos Aneis", ana, "Autor");
            Biblioteca biblioteca = biblioteca(itemAna1, itemBia, disponivel, itemAna2);

            metodo("devolverTodosDoUsuario", Usuario.class).invoke(biblioteca, ana);

            assertAll(
                  () -> assertNull(emprestadoPara(itemAna1)),
                  () -> assertNull(emprestadoPara(itemAna2)),
                  () -> assertEquals(bia, emprestadoPara(itemBia)),
                  () -> assertNull(emprestadoPara(disponivel))
            );
        }

        @Test
        @Order(6)
        @DisplayName("6. Deve comparar usuarios pelo nome e nao pela referencia")
        void test6() throws Exception {
            Usuario anaDoEmprestimo = UsuarioFactory.build("Ana");
            Usuario anaDaDevolucao = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", anaDoEmprestimo);
            Biblioteca biblioteca = biblioteca(item);

            metodo("devolverTodosDoUsuario", Usuario.class).invoke(biblioteca, anaDaDevolucao);

            assertNull(emprestadoPara(item));
        }

        @Test
        @Order(7)
        @DisplayName("7. Nao deve devolver itens de usuario com nome diferente")
        void test7() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario anaMaria = UsuarioFactory.build("Ana Maria");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", anaMaria);
            Biblioteca biblioteca = biblioteca(item);

            metodo("devolverTodosDoUsuario", Usuario.class).invoke(biblioteca, ana);

            assertEquals(anaMaria, emprestadoPara(item));
        }

        @Test
        @Order(8)
        @DisplayName("8. Nao deve remover itens da biblioteca")
        void test8() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca item1 = ItemBibliotecaFactory.build("Duna", ana);
            ItemBiblioteca item2 = ItemBibliotecaFactory.build("Matrix", null);
            Biblioteca biblioteca = biblioteca(item1, item2);

            metodo("devolverTodosDoUsuario", Usuario.class).invoke(biblioteca, ana);

            assertEquals(List.of(item1, item2), itensDe(biblioteca));
        }
    }

    @Nested
    @Order(11)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 11. Metodo - contarPorTipo")
    class MetodoContarPorTipoTest {

        private Biblioteca bibliotecaMista() throws ReflectiveOperationException {
            Usuario ana = UsuarioFactory.build("Ana");
            return biblioteca(
                  LivroFactory.build("Livro 1", ana, "Autor"),
                  LivroFactory.build("Livro 2", null, "Autor"),
                  DvdFactory.build("Dvd 1", null, 100),
                  DvdFactory.build("Dvd 2", ana, 100),
                  DvdFactory.build("Dvd 3", null, 100),
                  ItemBibliotecaFactory.build("Item Comum", null)
            );
        }

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(NullOrBlankProvider.class)
        @Order(1)
        @DisplayName("1. Tipo nulo ou em branco deve lancar excecao")
        void test1(String tipo) throws ReflectiveOperationException {
            Biblioteca biblioteca = bibliotecaMista();
            Method metodo = metodo("contarPorTipo", String.class);

            var throwable = assertThrows(Throwable.class, () -> metodo.invoke(biblioteca, tipo));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @ParameterizedTest(name = "{index}. tipo = \"{0}\"")
        @ValueSource(strings = {"CD", "Revista", "Item", "ItemBiblioteca", "DVDs", "Livros",
              "DVD Livro", "", "Biblioteca"})
        @Order(2)
        @DisplayName("2. Tipo diferente de DVD ou Livro deve lancar excecao")
        void test2(String tipo) throws ReflectiveOperationException {
            Biblioteca biblioteca = bibliotecaMista();
            Method metodo = metodo("contarPorTipo", String.class);

            var throwable = assertThrows(Throwable.class, () -> metodo.invoke(biblioteca, tipo));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @ParameterizedTest(name = "{index}. tipo = \"{0}\"")
        @ValueSource(strings = {"CD", "  "})
        @Order(3)
        @DisplayName("3. Tipo invalido deve lancar excecao mesmo sem itens cadastrados")
        void test3(String tipo) throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca();
            Method metodo = metodo("contarPorTipo", String.class);

            var throwable = assertThrows(Throwable.class, () -> metodo.invoke(biblioteca, tipo));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @ParameterizedTest(name = "{index}. tipo = \"{0}\"")
        @ValueSource(strings = {"DVD", "dvd", "Dvd", "dVd", "dvD"})
        @Order(4)
        @DisplayName("4. Deve contar apenas DVDs ignorando maiusculas e minusculas")
        void test4(String tipo) throws Exception {
            Biblioteca biblioteca = bibliotecaMista();

            Object resposta = metodo("contarPorTipo", String.class).invoke(biblioteca, tipo);

            assertEquals(3, resposta);
        }

        @ParameterizedTest(name = "{index}. tipo = \"{0}\"")
        @ValueSource(strings = {"Livro", "livro", "LIVRO", "lIvRo", "livRO"})
        @Order(5)
        @DisplayName("5. Deve contar apenas Livros ignorando maiusculas e minusculas")
        void test5(String tipo) throws Exception {
            Biblioteca biblioteca = bibliotecaMista();

            Object resposta = metodo("contarPorTipo", String.class).invoke(biblioteca, tipo);

            assertEquals(2, resposta);
        }

        @Test
        @Order(6)
        @DisplayName("6. Deve contar itens disponiveis e emprestados")
        void test6() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Biblioteca biblioteca = biblioteca(
                  DvdFactory.build("Dvd 1", ana, 100),
                  DvdFactory.build("Dvd 2", null, 100)
            );

            Object resposta = metodo("contarPorTipo", String.class).invoke(biblioteca, "DVD");

            assertEquals(2, resposta);
        }

        @Test
        @Order(7)
        @DisplayName("7. Item comum nao deve ser contado como Livro nem como DVD")
        void test7() throws Exception {
            Biblioteca biblioteca = biblioteca(
                  ItemBibliotecaFactory.build("Item 1", null),
                  ItemBibliotecaFactory.build("Item 2", null)
            );

            assertAll(
                  () -> assertEquals(0,
                        metodo("contarPorTipo", String.class).invoke(biblioteca, "DVD")),
                  () -> assertEquals(0,
                        metodo("contarPorTipo", String.class).invoke(biblioteca, "Livro"))
            );
        }

        @Test
        @Order(8)
        @DisplayName("8. Biblioteca sem itens deve retornar 0")
        void test8() throws Exception {
            Biblioteca biblioteca = biblioteca();

            assertAll(
                  () -> assertEquals(0,
                        metodo("contarPorTipo", String.class).invoke(biblioteca, "DVD")),
                  () -> assertEquals(0,
                        metodo("contarPorTipo", String.class).invoke(biblioteca, "Livro"))
            );
        }

        @Test
        @Order(9)
        @DisplayName("9. Biblioteca sem itens do tipo informado deve retornar 0")
        void test9() throws Exception {
            Biblioteca biblioteca = biblioteca(
                  LivroFactory.build("Livro 1", null, "Autor"),
                  LivroFactory.build("Livro 2", null, "Autor")
            );

            Object resposta = metodo("contarPorTipo", String.class).invoke(biblioteca, "DVD");

            assertEquals(0, resposta);
        }

        @Test
        @Order(10)
        @DisplayName("10. Nao deve alterar a lista de itens da biblioteca")
        void test10() throws Exception {
            Biblioteca biblioteca = bibliotecaMista();
            List<ItemBiblioteca> antes = new ArrayList<>(itensDe(biblioteca));

            metodo("contarPorTipo", String.class).invoke(biblioteca, "Livro");

            assertEquals(antes, itensDe(biblioteca));
        }
    }

    @Nested
    @Order(9)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 9. Metodo - possuiItemDisponivel")
    class MetodoPossuiItemDisponivelTest {

        @Test
        @Order(1)
        @DisplayName("1. Deve retornar Boolean")
        void test1() throws ReflectiveOperationException {
            assertEquals(Boolean.class,
                  metodo("possuiItemDisponivel", String.class).getReturnType());
        }

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(NullOrBlankProvider.class)
        @Order(2)
        @DisplayName("2. Titulo nulo ou em branco deve lancar excecao")
        void test2(String titulo) throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca(ItemBibliotecaFactory.build("Duna", null));
            Method metodo = metodo("possuiItemDisponivel", String.class);

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, titulo));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(NullOrBlankProvider.class)
        @Order(3)
        @DisplayName("3. Titulo nulo ou em branco deve lancar DadoInvalidoException mesmo sem itens")
        void test3(String titulo) throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca();
            Method metodo = metodo("possuiItemDisponivel", String.class);

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, titulo));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(4)
        @DisplayName("4. Item inexistente deve lancar excecao")
        void test4() throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca(
                  ItemBibliotecaFactory.build("Duna", null),
                  ItemBibliotecaFactory.build("Matrix", null)
            );
            Method metodo = metodo("possuiItemDisponivel", String.class);

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, "Titulo Inexistente"));
            assertEquals(ItemNaoEncontradoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(5)
        @DisplayName("5. Biblioteca sem itens deve lancar excecao de item nao encontrado")
        void test5() throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca();
            Method metodo = metodo("possuiItemDisponivel", String.class);

            var throwable = assertThrows(Throwable.class, () -> metodo.invoke(biblioteca, "Duna"));
            assertEquals(ItemNaoEncontradoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(6)
        @DisplayName("6. Item disponivel deve retornar true")
        void test6() throws Exception {
            Biblioteca biblioteca = biblioteca(ItemBibliotecaFactory.build("Duna", null));

            Object resposta = metodo("possuiItemDisponivel", String.class)
                  .invoke(biblioteca, "Duna");

            assertEquals(true, resposta);
        }

        @Test
        @Order(7)
        @DisplayName("7. Item emprestado deve retornar false")
        void test7() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Biblioteca biblioteca = biblioteca(ItemBibliotecaFactory.build("Duna", ana));

            Object resposta = metodo("possuiItemDisponivel", String.class)
                  .invoke(biblioteca, "Duna");

            assertEquals(false, resposta);
        }

        @Test
        @Order(8)
        @DisplayName("8. Deve ignorar maiusculas e minusculas no titulo")
        void test8() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Biblioteca biblioteca = biblioteca(
                  ItemBibliotecaFactory.build("Senhor dos Aneis", null),
                  ItemBibliotecaFactory.build("Duna", ana)
            );
            Method metodo = metodo("possuiItemDisponivel", String.class);

            assertAll(
                  () -> assertEquals(true, metodo.invoke(biblioteca, "sEnHoR dOs aNeIs")),
                  () -> assertEquals(false, metodo.invoke(biblioteca, "DUNA"))
            );
        }

        @Test
        @Order(9)
        @DisplayName("9. Deve considerar apenas o item com o titulo informado")
        void test9() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Biblioteca biblioteca = biblioteca(
                  ItemBibliotecaFactory.build("Duna", null),
                  LivroFactory.build("Matrix", ana, "Autor"),
                  DvdFactory.build("Dracula", null, 100)
            );
            Method metodo = metodo("possuiItemDisponivel", String.class);

            assertAll(
                  () -> assertEquals(true, metodo.invoke(biblioteca, "Duna")),
                  () -> assertEquals(false, metodo.invoke(biblioteca, "Matrix")),
                  () -> assertEquals(true, metodo.invoke(biblioteca, "Dracula"))
            );
        }

        @Test
        @Order(10)
        @DisplayName("10. Nao deve alterar o estado do item")
        void test10() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca emprestado = ItemBibliotecaFactory.build("Duna", ana);
            ItemBiblioteca disponivel = ItemBibliotecaFactory.build("Matrix", null);
            Biblioteca biblioteca = biblioteca(emprestado, disponivel);
            Method metodo = metodo("possuiItemDisponivel", String.class);

            metodo.invoke(biblioteca, "Duna");
            metodo.invoke(biblioteca, "Matrix");

            assertAll(
                  () -> assertEquals(ana, emprestadoPara(emprestado)),
                  () -> assertNull(emprestadoPara(disponivel))
            );
        }
    }

    @Nested
    @Order(10)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 10. Metodo - listarDisponiveis")
    class MetodoListarDisponiveisTest {

        @Test
        @Order(1)
        @DisplayName("1. Biblioteca sem itens deve retornar lista vazia")
        void test1() throws Exception {
            Biblioteca biblioteca = biblioteca();

            Object resposta = metodo("listarDisponiveis").invoke(biblioteca);

            assertNotNull(resposta);
            assertTrue(((List<?>) resposta).isEmpty());
        }

        @Test
        @Order(2)
        @DisplayName("2. Todos os itens emprestados deve retornar lista vazia")
        void test2() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Biblioteca biblioteca = biblioteca(
                  ItemBibliotecaFactory.build("Duna", ana),
                  LivroFactory.build("Matrix", ana, "Autor"),
                  DvdFactory.build("Dracula", ana, 100)
            );

            Object resposta = metodo("listarDisponiveis").invoke(biblioteca);

            assertNotNull(resposta);
            assertTrue(((List<?>) resposta).isEmpty());
        }

        @Test
        @Order(3)
        @DisplayName("3. Todos os itens disponiveis deve retornar todos mantendo a ordem de cadastro")
        void test3() throws Exception {
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca livro = LivroFactory.build("Matrix", null, "Autor");
            ItemBiblioteca dvd = DvdFactory.build("Dracula", null, 100);
            Biblioteca biblioteca = biblioteca(item, livro, dvd);

            List<?> resposta = (List<?>) metodo("listarDisponiveis").invoke(biblioteca);

            assertEquals(List.of(item, livro, dvd), resposta);
        }

        @Test
        @Order(4)
        @DisplayName("4. Deve retornar apenas os itens disponiveis mantendo a ordem de cadastro")
        void test4() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca disponivel1 = LivroFactory.build("Livro 1", null, "Autor");
            ItemBiblioteca emprestadoAna = ItemBibliotecaFactory.build("Item Ana", ana);
            ItemBiblioteca disponivel2 = DvdFactory.build("Dvd 1", null, 100);
            ItemBiblioteca emprestadoBia = DvdFactory.build("Dvd Bia", bia, 100);
            ItemBiblioteca disponivel3 = ItemBibliotecaFactory.build("Item 1", null);
            Biblioteca biblioteca = biblioteca(disponivel1, emprestadoAna, disponivel2,
                  emprestadoBia, disponivel3);

            List<?> resposta = (List<?>) metodo("listarDisponiveis").invoke(biblioteca);

            assertEquals(List.of(disponivel1, disponivel2, disponivel3), resposta);
        }

        @Test
        @Order(5)
        @DisplayName("5. Item devolvido deve voltar a aparecer como disponivel")
        void test5() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", ana);
            Biblioteca biblioteca = biblioteca(item);
            Method metodo = metodo("listarDisponiveis");

            assertTrue(((List<?>) metodo.invoke(biblioteca)).isEmpty());

            ItemBiblioteca.class.getDeclaredMethod("devolver").invoke(item);

            assertEquals(List.of(item), metodo.invoke(biblioteca));
        }

        @Test
        @Order(6)
        @DisplayName("6. Deve retornar nova lista sem alterar a lista de itens da biblioteca")
        @SuppressWarnings("unchecked")
        void test6() throws Exception {
            ItemBiblioteca item1 = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca item2 = ItemBibliotecaFactory.build("Matrix", null);
            Biblioteca biblioteca = biblioteca(item1, item2);

            List<ItemBiblioteca> resposta = (List<ItemBiblioteca>) metodo("listarDisponiveis")
                  .invoke(biblioteca);

            assertNotSame(itensDe(biblioteca), resposta);
            try {
                resposta.clear();
            } catch (UnsupportedOperationException ignored) {
            }
            assertEquals(List.of(item1, item2), itensDe(biblioteca));
        }

        @Test
        @Order(7)
        @DisplayName("7. Nao deve alterar o estado dos itens")
        void test7() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca emprestado = ItemBibliotecaFactory.build("Duna", ana);
            ItemBiblioteca disponivel = ItemBibliotecaFactory.build("Matrix", null);
            Biblioteca biblioteca = biblioteca(emprestado, disponivel);

            metodo("listarDisponiveis").invoke(biblioteca);

            assertAll(
                  () -> assertEquals(2, itensDe(biblioteca).size()),
                  () -> assertEquals(ana, emprestadoPara(emprestado)),
                  () -> assertNull(emprestadoPara(disponivel))
            );
        }
    }

    @Nested
    @Order(12)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 12. Metodo - calcularPrazoMedioEmprestimo")
    class MetodoPrazoMedioTest {

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(CalcularPrazoMedioEmprestimoProvider.class)
        @Order(1)
        @DisplayName("1. Deve calcular prazo medio corretamente")
        void test1(Biblioteca biblioteca, Integer prazoMedioEsperado) throws Exception {
            Class<Biblioteca> clazz = Biblioteca.class;
            Method metodoCalcularPrazoMedioEmprestimo = clazz.getDeclaredMethod(
                  "calcularPrazoMedioEmprestimo");
            Integer resposta = (Integer) metodoCalcularPrazoMedioEmprestimo.invoke(biblioteca);
            assertEquals(prazoMedioEsperado, resposta);
        }
    }

    private static Method metodo(String nome, Class<?>... tipos) throws NoSuchMethodException {
        return Biblioteca.class.getDeclaredMethod(nome, tipos);
    }

    private static Biblioteca biblioteca(ItemBiblioteca... itens)
          throws ReflectiveOperationException {
        return BibliotecaFactory.build("SPTech", new ArrayList<>(List.of(itens)));
    }

    @SuppressWarnings("unchecked")
    private static List<ItemBiblioteca> itensDe(Biblioteca biblioteca)
          throws ReflectiveOperationException {
        return (List<ItemBiblioteca>) BibliotecaFactory.campos().get("itens").get(biblioteca);
    }

    private static Usuario emprestadoPara(ItemBiblioteca item)
          throws ReflectiveOperationException {
        return (Usuario) ItemBibliotecaFactory.campos().get("emprestadoPara").get(item);
    }

    private static String capturarSaida(Executable acao) throws Throwable {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(output));
            acao.execute();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString().trim();
    }

    @Nested
    @Order(13)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 13. Metodo - calcularPrazoMedioPorTipo")
    class MetodoCalcularPrazoMedioPorTipoTest {

        private Biblioteca bibliotecaMista() throws ReflectiveOperationException {
            Usuario ana = UsuarioFactory.build("Ana");
            return biblioteca(
                  ItemBibliotecaFactory.build("Item Comum", null),
                  LivroFactory.build("Livro 1", null, "Autor"),
                  DvdFactory.build("Dvd 1", null, 100),
                  LivroFactory.build("Livro 2", ana, "Autor"),
                  DvdFactory.build("Dvd 2", ana, 100)
            );
        }

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(NullOrBlankProvider.class)
        @Order(1)
        @DisplayName("1. Tipo nulo ou em branco deve lancar excecao")
        void test1(String tipo) throws ReflectiveOperationException {
            Biblioteca biblioteca = bibliotecaMista();
            Method metodo = metodo("calcularPrazoMedioPorTipo", String.class);

            var throwable = assertThrows(Throwable.class, () -> metodo.invoke(biblioteca, tipo));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @ParameterizedTest(name = "{index}. tipo = \"{0}\"")
        @ValueSource(strings = {"CD", "Revista", "Item", "ItemBiblioteca", "DVDs", "Livros",
              "DVD Livro", "", "Biblioteca"})
        @Order(2)
        @DisplayName("2. Tipo diferente de DVD ou Livro deve lancar excecao")
        void test2(String tipo) throws ReflectiveOperationException {
            Biblioteca biblioteca = bibliotecaMista();
            Method metodo = metodo("calcularPrazoMedioPorTipo", String.class);

            var throwable = assertThrows(Throwable.class, () -> metodo.invoke(biblioteca, tipo));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @ParameterizedTest(name = "{index}. tipo = \"{0}\"")
        @ValueSource(strings = {"CD", "  "})
        @Order(3)
        @DisplayName("3. Tipo invalido deve lancar excecao mesmo sem itens cadastrados")
        void test3(String tipo) throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca();
            Method metodo = metodo("calcularPrazoMedioPorTipo", String.class);

            var throwable = assertThrows(Throwable.class, () -> metodo.invoke(biblioteca, tipo));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @ParameterizedTest(name = "{index}. tipo = \"{0}\"")
        @ValueSource(strings = {"DVD", "dvd", "Dvd", "dVd", "dvD"})
        @Order(4)
        @DisplayName("4. Deve calcular o prazo medio apenas dos DVDs ignorando maiusculas e minusculas")
        void test4(String tipo) throws Exception {
            Biblioteca biblioteca = bibliotecaMista();

            Object resposta = metodo("calcularPrazoMedioPorTipo", String.class)
                  .invoke(biblioteca, tipo);

            assertEquals(3, resposta);
        }

        @ParameterizedTest(name = "{index}. tipo = \"{0}\"")
        @ValueSource(strings = {"Livro", "livro", "LIVRO", "lIvRo", "livRO"})
        @Order(5)
        @DisplayName("5. Deve calcular o prazo medio apenas dos Livros ignorando maiusculas e minusculas")
        void test5(String tipo) throws Exception {
            Biblioteca biblioteca = bibliotecaMista();

            Object resposta = metodo("calcularPrazoMedioPorTipo", String.class)
                  .invoke(biblioteca, tipo);

            assertEquals(14, resposta);
        }

        @Test
        @Order(6)
        @DisplayName("6. Item comum nao deve entrar no calculo de Livro nem de DVD")
        void test6() throws Exception {
            Biblioteca biblioteca = biblioteca(
                  ItemBibliotecaFactory.build("Item 1", null),
                  ItemBibliotecaFactory.build("Item 2", null),
                  LivroFactory.build("Livro 1", null, "Autor"),
                  DvdFactory.build("Dvd 1", null, 100)
            );
            Method metodo = metodo("calcularPrazoMedioPorTipo", String.class);

            assertAll(
                  () -> assertEquals(14, metodo.invoke(biblioteca, "Livro")),
                  () -> assertEquals(3, metodo.invoke(biblioteca, "DVD"))
            );
        }

        @Test
        @Order(7)
        @DisplayName("7. Deve considerar itens disponiveis e emprestados")
        void test7() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Biblioteca biblioteca = biblioteca(
                  DvdFactory.build("Dvd 1", ana, 100),
                  DvdFactory.build("Dvd 2", null, 100)
            );

            Object resposta = metodo("calcularPrazoMedioPorTipo", String.class)
                  .invoke(biblioteca, "DVD");

            assertEquals(3, resposta);
        }

        @Test
        @Order(8)
        @DisplayName("8. Biblioteca sem itens deve retornar 0")
        void test8() throws Exception {
            Biblioteca biblioteca = biblioteca();
            Method metodo = metodo("calcularPrazoMedioPorTipo", String.class);

            assertAll(
                  () -> assertEquals(0, metodo.invoke(biblioteca, "DVD")),
                  () -> assertEquals(0, metodo.invoke(biblioteca, "Livro"))
            );
        }

        @Test
        @Order(9)
        @DisplayName("9. Biblioteca sem itens do tipo informado deve retornar 0")
        void test9() throws Exception {
            Biblioteca biblioteca = biblioteca(
                  LivroFactory.build("Livro 1", null, "Autor"),
                  ItemBibliotecaFactory.build("Item 1", null)
            );

            Object resposta = metodo("calcularPrazoMedioPorTipo", String.class)
                  .invoke(biblioteca, "DVD");

            assertEquals(0, resposta);
        }

        @Test
        @Order(10)
        @DisplayName("10. Nao deve alterar a lista de itens da biblioteca")
        void test10() throws Exception {
            Biblioteca biblioteca = bibliotecaMista();
            List<ItemBiblioteca> antes = new ArrayList<>(itensDe(biblioteca));

            metodo("calcularPrazoMedioPorTipo", String.class).invoke(biblioteca, "Livro");

            assertEquals(antes, itensDe(biblioteca));
        }
    }

    @Nested
    @Order(16)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("Biblioteca 16. Metodo - emprestarVarios")
    class MetodoEmprestarVariosTest {

        private static final String MENSAGEM_LOTE = "Não foi possível emprestar o lote: o item '%s' está indisponível.";

        private final Method metodo = metodo("emprestarVarios", List.class, Usuario.class);

        MetodoEmprestarVariosTest() throws NoSuchMethodException {
        }

        @Test
        @Order(1)
        @DisplayName("1. Não deve propagar ItemIndisponivelException")
        void test1() {
            assertFalse(Arrays.asList(metodo.getExceptionTypes())
                        .contains(ItemIndisponivelException.class),
                  "Metodo emprestarVarios não deve propagar ItemIndisponivelException");
        }

        @Test
        @Order(2)
        @DisplayName("2. Lista de titulos nula deve lancar excecao")
        void test2() throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca(ItemBibliotecaFactory.build("Duna", null));
            Usuario ana = UsuarioFactory.build("Ana");

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, null, ana));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(3)
        @DisplayName("3. Usuario nulo deve lancar excecao e nao emprestar nenhum item")
        void test3() throws ReflectiveOperationException {
            ItemBiblioteca item = ItemBibliotecaFactory.build("Duna", null);
            Biblioteca biblioteca = biblioteca(item);

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, List.of("Duna"), null));
            assertAll(
                  () -> assertEquals(DadoInvalidoException.class, throwable.getCause().getClass()),
                  () -> assertNull(emprestadoPara(item))
            );
        }

        @Test
        @Order(4)
        @DisplayName("4. Lista de titulos vazia deve lancar excecao")
        void test4() throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca(ItemBibliotecaFactory.build("Duna", null));
            Usuario ana = UsuarioFactory.build("Ana");

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, new ArrayList<String>(), ana));
            assertEquals(DadoInvalidoException.class, throwable.getCause().getClass());
        }

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(EmprestarVariosTitulosInvalidosProvider.class)
        @Order(5)
        @DisplayName("5. Titulo nulo ou em branco dentro da lista deve lancar excecao e nao emprestar nenhum item")
        void test5(List<String> titulos) throws ReflectiveOperationException {
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", null, "Autor");
            Biblioteca biblioteca = biblioteca(duna, matrix);
            Usuario ana = UsuarioFactory.build("Ana");

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, titulos, ana));
            assertAll(
                  () -> assertEquals(DadoInvalidoException.class, throwable.getCause().getClass()),
                  () -> assertNull(emprestadoPara(duna)),
                  () -> assertNull(emprestadoPara(matrix))
            );
        }

        @ParameterizedTest(name = "{index}. {argumentSetName}")
        @ArgumentsSource(EmprestarVariosTitulosDuplicadosProvider.class)
        @Order(6)
        @DisplayName("6. Titulos duplicados (ignorando maiusculas e minusculas) devem lancar excecao e nao emprestar nenhum item")
        void test6(List<String> titulos) throws ReflectiveOperationException {
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", null, "Autor");
            ItemBiblioteca dracula = DvdFactory.build("Dracula", null, 100);
            Biblioteca biblioteca = biblioteca(duna, matrix, dracula);
            Usuario ana = UsuarioFactory.build("Ana");

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, titulos, ana));
            assertAll(
                  () -> assertEquals(DadoInvalidoException.class, throwable.getCause().getClass()),
                  () -> assertNull(emprestadoPara(duna)),
                  () -> assertNull(emprestadoPara(matrix)),
                  () -> assertNull(emprestadoPara(dracula))
            );
        }

        @Test
        @Order(7)
        @DisplayName("7. Titulo inexistente deve lancar excecao")
        void test7() throws ReflectiveOperationException {
            Biblioteca biblioteca = biblioteca(ItemBibliotecaFactory.build("Duna", null));
            Usuario ana = UsuarioFactory.build("Ana");

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, List.of("Titulo Inexistente"), ana));
            assertEquals(ItemNaoEncontradoException.class, throwable.getCause().getClass());
        }

        @Test
        @Order(8)
        @DisplayName("8. Titulo inexistente no fim da lista nao deve emprestar nenhum item")
        void test8() throws ReflectiveOperationException {
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", null, "Autor");
            Biblioteca biblioteca = biblioteca(duna, matrix);
            Usuario ana = UsuarioFactory.build("Ana");

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, List.of("Duna", "Matrix", "Inexistente"), ana));
            assertAll(
                  () -> assertEquals(ItemNaoEncontradoException.class,
                        throwable.getCause().getClass()),
                  () -> assertNull(emprestadoPara(duna)),
                  () -> assertNull(emprestadoPara(matrix))
            );
        }

        @Test
        @Order(9)
        @DisplayName("9. Titulo inexistente no meio da lista nao deve emprestar nenhum item")
        void test9() throws ReflectiveOperationException {
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", null, "Autor");
            Biblioteca biblioteca = biblioteca(duna, matrix);
            Usuario ana = UsuarioFactory.build("Ana");

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, List.of("Duna", "Inexistente", "Matrix"), ana));
            assertAll(
                  () -> assertEquals(ItemNaoEncontradoException.class,
                        throwable.getCause().getClass()),
                  () -> assertNull(emprestadoPara(duna)),
                  () -> assertNull(emprestadoPara(matrix))
            );
        }

        @Test
        @Order(10)
        @DisplayName("10. Titulo inexistente deve ter prioridade sobre item indisponivel")
        void test10() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca indisponivel = ItemBibliotecaFactory.build("Duna", bia);
            ItemBiblioteca disponivel = LivroFactory.build("Matrix", null, "Autor");
            Biblioteca biblioteca = biblioteca(indisponivel, disponivel);

            var throwable = assertThrows(Throwable.class,
                  () -> metodo.invoke(biblioteca, List.of("Duna", "Matrix", "Inexistente"), ana));
            assertAll(
                  () -> assertEquals(ItemNaoEncontradoException.class,
                        throwable.getCause().getClass()),
                  () -> assertEquals(bia, emprestadoPara(indisponivel)),
                  () -> assertNull(emprestadoPara(disponivel))
            );
        }

        @Test
        @Order(11)
        @DisplayName("11. Item indisponivel deve exibir mensagem, nao lancar excecao e nao emprestar nenhum item")
        void test11() throws Throwable {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", bia, "Autor");
            ItemBiblioteca dracula = DvdFactory.build("Dracula", null, 100);
            Biblioteca biblioteca = biblioteca(duna, matrix, dracula);

            String saida = capturarSaida(() -> assertDoesNotThrow(
                  () -> metodo.invoke(biblioteca, List.of("Duna", "Matrix", "Dracula"), ana)));

            assertAll(
                  () -> assertEquals(String.format(MENSAGEM_LOTE, "Matrix"), saida),
                  () -> assertNull(emprestadoPara(duna)),
                  () -> assertEquals(bia, emprestadoPara(matrix)),
                  () -> assertNull(emprestadoPara(dracula))
            );
        }

        @Test
        @Order(12)
        @DisplayName("12. Item indisponivel como primeiro da lista deve exibir mensagem e nao emprestar nenhum item")
        void test12() throws Throwable {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", bia);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", null, "Autor");
            Biblioteca biblioteca = biblioteca(duna, matrix);

            String saida = capturarSaida(() -> assertDoesNotThrow(
                  () -> metodo.invoke(biblioteca, List.of("Duna", "Matrix"), ana)));

            assertAll(
                  () -> assertEquals(String.format(MENSAGEM_LOTE, "Duna"), saida),
                  () -> assertEquals(bia, emprestadoPara(duna)),
                  () -> assertNull(emprestadoPara(matrix))
            );
        }

        @Test
        @Order(13)
        @DisplayName("13. Item indisponivel como ultimo da lista deve exibir mensagem e nao emprestar nenhum item")
        void test13() throws Throwable {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", bia, "Autor");
            Biblioteca biblioteca = biblioteca(duna, matrix);

            String saida = capturarSaida(() -> assertDoesNotThrow(
                  () -> metodo.invoke(biblioteca, List.of("Duna", "Matrix"), ana)));

            assertAll(
                  () -> assertEquals(String.format(MENSAGEM_LOTE, "Matrix"), saida),
                  () -> assertNull(emprestadoPara(duna)),
                  () -> assertEquals(bia, emprestadoPara(matrix))
            );
        }

        @Test
        @Order(14)
        @DisplayName("14. Deve exibir mensagem apenas do primeiro item indisponivel seguindo a ordem da lista")
        void test14() throws Throwable {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", bia);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", bia, "Autor");
            ItemBiblioteca dracula = DvdFactory.build("Dracula", bia, 100);
            Biblioteca biblioteca = biblioteca(duna, matrix, dracula);

            String saida = capturarSaida(() -> assertDoesNotThrow(
                  () -> metodo.invoke(biblioteca, List.of("Dracula", "Duna", "Matrix"), ana)));

            assertEquals(String.format(MENSAGEM_LOTE, "Dracula"), saida);
        }

        @Test
        @Order(15)
        @DisplayName("15. Item ja emprestado para o proprio usuario tambem deve ser considerado indisponivel")
        void test15() throws Throwable {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", ana);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", null, "Autor");
            Biblioteca biblioteca = biblioteca(duna, matrix);

            String saida = capturarSaida(() -> assertDoesNotThrow(
                  () -> metodo.invoke(biblioteca, List.of("Matrix", "Duna"), ana)));

            assertAll(
                  () -> assertEquals(String.format(MENSAGEM_LOTE, "Duna"), saida),
                  () -> assertEquals(ana, emprestadoPara(duna)),
                  () -> assertNull(emprestadoPara(matrix))
            );
        }

        @Test
        @Order(16)
        @DisplayName("16. Deve emprestar todos os itens para o usuario quando todos estiverem disponiveis")
        void test16() throws Throwable {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", null, "Autor");
            ItemBiblioteca dracula = DvdFactory.build("Dracula", null, 100);
            Biblioteca biblioteca = biblioteca(duna, matrix, dracula);

            String saida = capturarSaida(() -> assertDoesNotThrow(
                  () -> metodo.invoke(biblioteca, List.of("Duna", "Matrix", "Dracula"), ana)));

            assertAll(
                  () -> assertEquals("", saida),
                  () -> assertEquals(ana, emprestadoPara(duna)),
                  () -> assertEquals(ana, emprestadoPara(matrix)),
                  () -> assertEquals(ana, emprestadoPara(dracula))
            );
        }

        @Test
        @Order(17)
        @DisplayName("17. Deve emprestar um unico item quando a lista possuir apenas um titulo")
        void test17() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", null, "Autor");
            Biblioteca biblioteca = biblioteca(duna, matrix);

            assertDoesNotThrow(() -> metodo.invoke(biblioteca, List.of("Duna"), ana));

            assertAll(
                  () -> assertEquals(ana, emprestadoPara(duna)),
                  () -> assertNull(emprestadoPara(matrix))
            );
        }

        @Test
        @Order(18)
        @DisplayName("18. Deve ignorar maiusculas e minusculas nos titulos")
        void test18() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca matrix = LivroFactory.build("Matrix", null, "Autor");
            Biblioteca biblioteca = biblioteca(duna, matrix);

            assertDoesNotThrow(() -> metodo.invoke(biblioteca, List.of("dUnA", "MATRIX"), ana));

            assertAll(
                  () -> assertEquals(ana, emprestadoPara(duna)),
                  () -> assertEquals(ana, emprestadoPara(matrix))
            );
        }

        @Test
        @Order(19)
        @DisplayName("19. Nao deve alterar itens que nao estejam na lista")
        void test19() throws Exception {
            Usuario ana = UsuarioFactory.build("Ana");
            Usuario bia = UsuarioFactory.build("Bia");
            ItemBiblioteca duna = ItemBibliotecaFactory.build("Duna", null);
            ItemBiblioteca foraDaLista = LivroFactory.build("Matrix", null, "Autor");
            ItemBiblioteca emprestadoBia = DvdFactory.build("Dracula", bia, 100);
            Biblioteca biblioteca = biblioteca(duna, foraDaLista, emprestadoBia);

            assertDoesNotThrow(() -> metodo.invoke(biblioteca, List.of("Duna"), ana));

            assertAll(
                  () -> assertEquals(ana, emprestadoPara(duna)),
                  () -> assertNull(emprestadoPara(foraDaLista)),
                  () -> assertEquals(bia, emprestadoPara(emprestadoBia)),
                  () -> assertEquals(3, itensDe(biblioteca).size())
            );
        }
    }
}
