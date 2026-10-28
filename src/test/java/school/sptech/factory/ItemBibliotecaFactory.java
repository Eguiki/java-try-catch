package school.sptech.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import school.sptech.ItemBiblioteca;
import school.sptech.Usuario;
import school.sptech.util.ObjectFieldBuilder;
import school.sptech.util.ObjectFieldMapping;

public class ItemBibliotecaFactory {

    public static Map<String, Field> campos() throws ReflectiveOperationException {
        Map<String, Field> mapCampos = new HashMap<>();
        ObjectFieldMapping<ItemBiblioteca> mapping = new ObjectFieldMapping<>(ItemBiblioteca.class);

        mapCampos.put("titulo", mapping.get("titulo"));
        mapCampos.put("emprestadoPara", mapping.get("emprestadoPara"));

        return mapCampos;
    }

    public static ItemBiblioteca build(String titulo, Usuario emprestadoPara)
          throws ReflectiveOperationException {
        return new ObjectFieldBuilder<>(ItemBiblioteca.class)
              .with("titulo", titulo)
              .with("emprestadoPara", emprestadoPara)
              .build();
    }
}
