package school.sptech.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import school.sptech.Biblioteca;
import school.sptech.ItemBiblioteca;
import school.sptech.util.ObjectFieldBuilder;
import school.sptech.util.ObjectFieldMapping;

public class BibliotecaFactory {

    public static Map<String, Field> campos() throws ReflectiveOperationException {
        Map<String, Field> mapCampos = new HashMap<>();
        ObjectFieldMapping<Biblioteca> mapping = new ObjectFieldMapping<>(Biblioteca.class);

        mapCampos.put("nome", mapping.get("nome"));
        mapCampos.put("itens", mapping.get("itens"));

        return mapCampos;
    }

    public static Biblioteca build(String nome, List<Object> itens)
          throws ReflectiveOperationException {
        return new ObjectFieldBuilder<>(Biblioteca.class)
              .with("nome", nome)
              .with("itens", itens)
              .build();
    }
}
