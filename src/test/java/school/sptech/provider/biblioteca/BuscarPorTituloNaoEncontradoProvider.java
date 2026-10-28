package school.sptech.provider.biblioteca;

import static org.junit.jupiter.params.provider.Arguments.argumentSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.Biblioteca;
import school.sptech.ItemBiblioteca;
import school.sptech.factory.BibliotecaFactory;
import school.sptech.factory.ItemBibliotecaFactory;

public class BuscarPorTituloNaoEncontradoProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        ItemBiblioteca item = ItemBibliotecaFactory.build("Dom Casmurro", null);
        Biblioteca bibliotecaComItens = BibliotecaFactory.build("SPTech",
              new ArrayList<>(List.of(item)));
        Biblioteca bibliotecaVazia = BibliotecaFactory.build("SPTech", new ArrayList<>());

        return Stream.of(
              argumentSet("biblioteca com itens e titulo inexistente", bibliotecaComItens,
                    "Titulo Inexistente"),
              argumentSet("biblioteca sem itens", bibliotecaVazia, "Titulo Inexistente")
        );
    }
}
