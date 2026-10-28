package school.sptech.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import school.sptech.Usuario;
import school.sptech.util.ObjectFieldBuilder;
import school.sptech.util.ObjectFieldMapping;

public class UsuarioFactory {

    public static Map<String, Field> campos() throws ReflectiveOperationException {
        Map<String, Field> mapCampos = new HashMap<>();
        ObjectFieldMapping<Usuario> mapping = new ObjectFieldMapping<>(Usuario.class);

        mapCampos.put("nome", mapping.get("nome"));

        return mapCampos;
    }

    public static Usuario build(String nome)
          throws ReflectiveOperationException {
        return new ObjectFieldBuilder<>(Usuario.class)
              .with("nome", nome)
              .build();
    }
}
