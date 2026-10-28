package school.sptech;

import school.sptech.exceptions.DadoInvalidoException;

public class Dvd extends ItemBiblioteca {

    private Integer duracao;

    public Dvd(String titulo, Integer duracao) {
        super(titulo);
        setDuracao(duracao);
    }

    @Override
    public Integer getPrazoEmprestimoDias() {
        return 3;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        if (duracao == null || duracao <= 0) {
            throw new DadoInvalidoException(
                    "A duração deve ser maior que zero."
            );
        }

        this.duracao = duracao;
    }
}