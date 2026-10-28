package school.sptech;

import school.sptech.exceptions.DadoInvalidoException;

public class Usuario {

    private String nome;

    public Usuario(String nome) {
        setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DadoInvalidoException(
                    "O nome do usuário não pode ser nulo ou vazio."
            );
        }

        this.nome = nome;
    }
}