package school.sptech.provider.biblioteca;

import static org.junit.jupiter.params.provider.Arguments.argumentSet;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class EmprestarVariosTitulosDuplicadosProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
              argumentSet("[Duna, Duna] - mesmo titulo repetido",
                    Arrays.asList("Duna", "Duna")),
              argumentSet("[Duna, duna] - repetido com letras minusculas",
                    Arrays.asList("Duna", "duna")),
              argumentSet("[Duna, Matrix, DUNA] - repetido com letras maiusculas",
                    Arrays.asList("Duna", "Matrix", "DUNA")),
              argumentSet("[Duna, Matrix, Dracula, mAtRiX] - repetido com letras misturadas",
                    Arrays.asList("Duna", "Matrix", "Dracula", "mAtRiX")),
              argumentSet("[Inexistente, Inexistente] - duplicado deve ser validado antes da busca",
                    Arrays.asList("Inexistente", "Inexistente"))
        );
    }
}
