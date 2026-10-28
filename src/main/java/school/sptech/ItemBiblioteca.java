package school.sptech;

import school.sptech.exceptions.DadoInvalidoException;
import school.sptech.exceptions.ItemIndisponivelException;

public class ItemBiblioteca {

    private String titulo;
    private Usuario emprestadoPara;

    public ItemBiblioteca(String titulo) {
        setTitulo(titulo);
        this.emprestadoPara = null;
    }

    public void emprestar(Usuario usuario)
            throws ItemIndisponivelException {

        if (usuario == null) {
            throw new DadoInvalidoException(
                    "O usuário não pode ser nulo."
            );
        }

        if (emprestadoPara != null) {
            throw new ItemIndisponivelException(
                    "Esse item já está emprestado para outro usuário."
            );
        }

        this.emprestadoPara = usuario;
    }

    public void devolver() {
        this.emprestadoPara = null;
    }

    public Integer getPrazoEmprestimoDias() {
        return 7;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new DadoInvalidoException(
                    "O título não pode ser nulo ou vazio."
            );
        }

        this.titulo = titulo;
    }

    public Usuario getEmprestadoPara() {
        return emprestadoPara;
    }

    public void setEmprestadoPara(Usuario emprestadoPara) {
        this.emprestadoPara = emprestadoPara;
    }
}