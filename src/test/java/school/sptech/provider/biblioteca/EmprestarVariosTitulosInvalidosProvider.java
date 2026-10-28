package school.sptech.provider.biblioteca;

import static org.junit.jupiter.params.provider.Arguments.argumentSet;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class EmprestarVariosTitulosInvalidosProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
              argumentSet("[null] - unico titulo nulo",
                    Arrays.asList((String) null)),
              argumentSet("[Duna, null] - titulo nulo no fim",
                    Arrays.asList("Duna", null)),
              argumentSet("[null, Duna] - titulo nulo no inicio",
                    Arrays.asList(null, "Duna")),
              argumentSet("[Duna, null, Matrix] - titulo nulo no meio",
                    Arrays.asList("Duna", null, "Matrix")),
              argumentSet("[Duna, \"\", Matrix] - titulo vazio no meio",
                    Arrays.asList("Duna", "", "Matrix")),
              argumentSet("[Duna, \" \", Matrix] - titulo em branco no meio",
                    Arrays.asList("Duna", " ", "Matrix")),
              argumentSet("[Duna, \"   \"] - titulo em branco no fim",
                    Arrays.asList("Duna", "   ")),
              argumentSet("[Inexistente, null] - titulo nulo deve ser validado antes da busca",
                    Arrays.asList("Inexistente", null))
        );
    }
}
