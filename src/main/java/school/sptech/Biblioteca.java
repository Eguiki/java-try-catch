package school.sptech;

import school.sptech.exceptions.DadoInvalidoException;
import school.sptech.exceptions.ItemIndisponivelException;
import school.sptech.exceptions.ItemNaoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class Biblioteca {

    private String nome;
    private List<ItemBiblioteca> itens;

    public Biblioteca(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DadoInvalidoException(
                    "O nome da biblioteca não pode ser nulo ou vazio."
            );
        }

        this.nome = nome;
        this.itens = new ArrayList<>();
    }

    // GETTERS E SETTERS

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DadoInvalidoException(
                    "O nome da biblioteca não pode ser nulo ou vazio."
            );
        }

        this.nome = nome;
    }

    // ADICIONAR ITEM

    public void adicionarItem(ItemBiblioteca item) {

        if (item == null) {
            throw new DadoInvalidoException(
                    "O item não pode ser nulo."
            );
        }

        itens.add(item);
    }

    // BUSCAR POR TÍTULO

    public ItemBiblioteca buscarPorTitulo(String titulo) {

        if (titulo == null) {
            throw new DadoInvalidoException(
                    "O título não pode ser nulo."
            );
        }

        for (ItemBiblioteca item : itens) {

            if (item.getTitulo().equalsIgnoreCase(titulo)) {
                return item;
            }
        }

        throw new ItemNaoEncontradoException(
                "Item não encontrado: " + titulo
        );
    }

    // EMPRESTAR ITEM

    public void emprestarItem(String titulo, Usuario usuario) {

        if (titulo == null || usuario == null) {
            throw new DadoInvalidoException(
                    "Título e usuário não podem ser nulos."
            );
        }

        ItemBiblioteca item = buscarPorTitulo(titulo);

        try {
            item.emprestar(usuario);

        } catch (ItemIndisponivelException e) {

            System.out.println(
                    "Esse item já está emprestado para outro usuário."
            );
        }
    }

    // DEVOLVER ITEM

    public void devolverItem(String titulo) {

        if (titulo == null) {
            throw new DadoInvalidoException(
                    "O título não pode ser nulo."
            );
        }

        ItemBiblioteca item = buscarPorTitulo(titulo);

        item.devolver();
    }

    // VERIFICAR DISPONIBILIDADE

    public Boolean possuiItemDisponivel(String titulo) {

        if (titulo == null || titulo.isBlank()) {
            throw new DadoInvalidoException(
                    "O título não pode ser nulo ou vazio."
            );
        }

        ItemBiblioteca item = buscarPorTitulo(titulo);

        return item.getEmprestadoPara() == null;
    }

    // LISTAR DISPONÍVEIS

    public List<ItemBiblioteca> listarDisponiveis() {

        List<ItemBiblioteca> disponiveis = new ArrayList<>();

        for (ItemBiblioteca item : itens) {

            if (item.getEmprestadoPara() == null) {
                disponiveis.add(item);
            }
        }

        return disponiveis;
    }

    // VALIDAR TIPO

    private void validarTipo(String tipo) {

        if (tipo == null || tipo.isBlank()) {
            throw new DadoInvalidoException(
                    "O tipo não pode ser nulo ou vazio."
            );
        }

        if (!tipo.equalsIgnoreCase("DVD")
                && !tipo.equalsIgnoreCase("Livro")) {

            throw new DadoInvalidoException(
                    "Tipo inválido. Utilize DVD ou Livro."
            );
        }
    }

    // CONTAR POR TIPO

    public Integer contarPorTipo(String tipo) {

        validarTipo(tipo);

        int quantidade = 0;

        for (ItemBiblioteca item : itens) {

            if (tipo.equalsIgnoreCase("DVD")
                    && item instanceof Dvd) {

                quantidade++;

            } else if (tipo.equalsIgnoreCase("Livro")
                    && item instanceof Livro) {

                quantidade++;
            }
        }

        return quantidade;
    }

    // CALCULAR PRAZO MÉDIO

    public Integer calcularPrazoMedioEmprestimo() {

        if (itens.isEmpty()) {
            return 0;
        }

        int total = 0;

        for (ItemBiblioteca item : itens) {
            total += item.getPrazoEmprestimoDias();
        }

        return total / itens.size();
    }

    // CALCULAR PRAZO MÉDIO POR TIPO

    public Integer calcularPrazoMedioPorTipo(String tipo) {

        validarTipo(tipo);

        int total = 0;
        int quantidade = 0;

        for (ItemBiblioteca item : itens) {

            if (tipo.equalsIgnoreCase("DVD")
                    && item instanceof Dvd) {

                total += item.getPrazoEmprestimoDias();
                quantidade++;

            } else if (tipo.equalsIgnoreCase("Livro")
                    && item instanceof Livro) {

                total += item.getPrazoEmprestimoDias();
                quantidade++;
            }
        }

        if (quantidade == 0) {
            return 0;
        }

        return total / quantidade;
    }

    // LISTAR EMPRESTADOS PARA USUÁRIO

    public List<ItemBiblioteca> listarEmprestadosPara(Usuario usuario) {

        if (usuario == null) {
            throw new DadoInvalidoException(
                    "O usuário não pode ser nulo."
            );
        }

        List<ItemBiblioteca> emprestados = new ArrayList<>();

        for (ItemBiblioteca item : itens) {

            Usuario usuarioAtual = item.getEmprestadoPara();

            if (usuarioAtual != null
                    && Objects.equals(
                    usuarioAtual.getNome(),
                    usuario.getNome()
            )) {

                emprestados.add(item);
            }
        }

        return emprestados;
    }

    // DEVOLVER TODOS OS ITENS DO USUÁRIO

    public void devolverTodosDoUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new DadoInvalidoException(
                    "O usuário não pode ser nulo."
            );
        }

        for (ItemBiblioteca item : itens) {

            Usuario usuarioAtual = item.getEmprestadoPara();

            if (usuarioAtual != null
                    && Objects.equals(
                    usuarioAtual.getNome(),
                    usuario.getNome()
            )) {

                item.devolver();
            }
        }
    }

    // EMPRESTAR VÁRIOS ITENS

    public void emprestarVarios(List<String> titulos, Usuario usuario) {

        // 1. VALIDAR LISTA E USUÁRIO

        if (titulos == null || usuario == null || titulos.isEmpty()) {
            throw new DadoInvalidoException(
                    "Lista de títulos e usuário devem ser válidos."
            );
        }

        // 2. VALIDAR TÍTULOS E DUPLICIDADES

        Set<String> titulosUnicos = new HashSet<>();

        for (String titulo : titulos) {

            if (titulo == null || titulo.isBlank()) {
                throw new DadoInvalidoException(
                        "Os títulos não podem ser nulos ou vazios."
                );
            }

            String normalizado = titulo.toLowerCase(Locale.ROOT);

            if (!titulosUnicos.add(normalizado)) {
                throw new DadoInvalidoException(
                        "A lista contém títulos duplicados: " + titulo
                );
            }
        }

        // 3. BUSCAR TODOS OS ITENS

        List<ItemBiblioteca> itensSelecionados = new ArrayList<>();

        for (String titulo : titulos) {

            ItemBiblioteca item = buscarPorTitulo(titulo);

            itensSelecionados.add(item);
        }

        // 4. VERIFICAR DISPONIBILIDADE DE TODOS

        for (ItemBiblioteca item : itensSelecionados) {

            if (item.getEmprestadoPara() != null) {

                System.out.println(
                        "Não foi possível emprestar o lote: o item '"
                                + item.getTitulo()
                                + "' está indisponível."
                );

                return;
            }
        }

        // 5. REALIZAR TODOS OS EMPRÉSTIMOS

        for (ItemBiblioteca item : itensSelecionados) {

            try {
                item.emprestar(usuario);

            } catch (ItemIndisponivelException e) {

                // DESFAZER EMPRÉSTIMOS ANTERIORES DO LOTE

                for (ItemBiblioteca emprestado : itensSelecionados) {

                    if (emprestado.getEmprestadoPara() == usuario) {
                        emprestado.devolver();
                    }
                }

                System.out.println(
                        "Não foi possível emprestar o lote: o item '"
                                + item.getTitulo()
                                + "' está indisponível."
                );

                return;
            }
        }
    }
}
