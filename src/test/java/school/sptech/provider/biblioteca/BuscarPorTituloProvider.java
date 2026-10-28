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

public class BuscarPorTituloProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        ItemBiblioteca item = ItemBibliotecaFactory.build("Senhor dos Aneis", null);
        Biblioteca biblioteca = BibliotecaFactory.build("SPTech", new ArrayList<>(List.of(item)));

        return Stream.of(
              argumentSet("buscar \"seNhoR dOs aNeis\" deve encontrar \"Senhor dos Aneis\"",
                    biblioteca, "seNhoR dOs aNeis", item)
        );
    }
}
