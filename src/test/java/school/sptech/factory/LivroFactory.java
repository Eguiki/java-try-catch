package school.sptech.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import school.sptech.Livro;
import school.sptech.ItemBiblioteca;
import school.sptech.Usuario;
import school.sptech.util.ObjectFieldBuilder;
import school.sptech.util.ObjectFieldMapping;

public class LivroFactory {

    public static Map<String, Field> campos() throws ReflectiveOperationException {
        Map<String, Field> mapCampos = new HashMap<>();
        ObjectFieldMapping<Livro> mapping = new ObjectFieldMapping<>(Livro.class);

        mapCampos.put("titulo", mapping.get("titulo"));
        mapCampos.put("emprestadoPara", mapping.get("emprestadoPara"));
        mapCampos.put("autor", mapping.get("autor"));

        return mapCampos;
    }

    public static ItemBiblioteca build(String titulo, Usuario emprestadoPara, String autor)
          throws ReflectiveOperationException {
        return (ItemBiblioteca) (Object) new ObjectFieldBuilder<>(Livro.class)
              .with("titulo", titulo)
              .with("emprestadoPara", emprestadoPara)
              .with("autor", autor)
              .build();
    }
}
