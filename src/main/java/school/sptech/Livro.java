package school.sptech;

import school.sptech.exceptions.DadoInvalidoException;

public class Livro extends ItemBiblioteca {

    private String autor;

    public Livro(String titulo, String autor) {
        super(titulo);
        setAutor(autor);
    }

    @Override
    public Integer getPrazoEmprestimoDias() {
        return 14;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            throw new DadoInvalidoException(
                    "O autor não pode ser nulo ou vazio."
            );
        }

        this.autor = autor;
    }
}