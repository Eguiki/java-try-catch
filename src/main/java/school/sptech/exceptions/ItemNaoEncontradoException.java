package school.sptech.exceptions;

public class ItemNaoEncontradoException extends RuntimeException {

    public ItemNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}