package school.sptech.provider.biblioteca;

import static org.junit.jupiter.params.provider.Arguments.argumentSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import school.sptech.Biblioteca;
import school.sptech.Dvd;
import school.sptech.ItemBiblioteca;
import school.sptech.Livro;
import school.sptech.factory.BibliotecaFactory;
import school.sptech.factory.DvdFactory;
import school.sptech.factory.ItemBibliotecaFactory;
import school.sptech.factory.LivroFactory;

public class CalcularPrazoMedioEmprestimoProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Biblioteca bibliotecaSemItens = BibliotecaFactory.build("SPTech", new ArrayList<>());
        ItemBiblioteca comum = ItemBibliotecaFactory.build("Base", null);
        ItemBiblioteca livro = LivroFactory.build("Livro", null, "Autor");
        ItemBiblioteca dvd = DvdFactory.build("Filme", null, 100);
        Biblioteca bibliotecaComItens = BibliotecaFactory.build("SPTech",
              new ArrayList<>(List.of(comum, livro, dvd)));

        return Stream.of(
              argumentSet("biblioteca sem itens deve retornar 0", bibliotecaSemItens, 0),
              argumentSet("item comum (7) + livro (14) + dvd (3) deve retornar 8",
                    bibliotecaComItens, 8)
        );
    }
}
