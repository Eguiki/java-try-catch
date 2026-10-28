package school.sptech.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import school.sptech.Dvd;
import school.sptech.ItemBiblioteca;
import school.sptech.Usuario;
import school.sptech.util.ObjectFieldBuilder;
import school.sptech.util.ObjectFieldMapping;

public class DvdFactory {

    public static Map<String, Field> campos() throws ReflectiveOperationException {
        Map<String, Field> mapCampos = new HashMap<>();
        ObjectFieldMapping<Dvd> mapping = new ObjectFieldMapping<>(Dvd.class);

        mapCampos.put("titulo", mapping.get("titulo"));
        mapCampos.put("emprestadoPara", mapping.get("emprestadoPara"));
        mapCampos.put("duracao", mapping.get("duracao"));

        return mapCampos;
    }

    public static ItemBiblioteca build(String titulo, Usuario emprestadoPara, Integer duracao)
          throws ReflectiveOperationException {
        return (ItemBiblioteca) (Object) new ObjectFieldBuilder<>(Dvd.class)
              .with("titulo", titulo)
              .with("emprestadoPara", emprestadoPara)
              .with("duracao", duracao)
              .build();
    }
}
