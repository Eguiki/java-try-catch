package school.sptech.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

public class EncapsulationUtil {

    public static void validarGettersSetters(Class<?> clazz) {
        Field[] campos = clazz.getDeclaredFields();

        Stream<Executable> validacoesGetter = Arrays.stream(campos)
              .map((campo) -> () -> {
                  String getName = String.format("get%s",
                        StringUtils.capitalize(campo.getName()));
                  Method getter = clazz.getDeclaredMethod(getName);
                  int getModifier = getter.getModifiers();
                  Assertions.assertTrue(Modifier.isPublic(getModifier));
              });

        Stream<Executable> validacoesSetter = Arrays.stream(campos)
              .map((campo) -> () -> {
                  String setName = String.format("set%s",
                        StringUtils.capitalize(campo.getName()));
                  Method setter = clazz.getDeclaredMethod(setName, campo.getType());
                  int setModifier = setter.getModifiers();
                  Assertions.assertTrue(Modifier.isPublic(setModifier));
              });

        Assertions.assertAll(Stream.concat(validacoesGetter, validacoesSetter));
    }

    public static void validarGettersSettersExcetoSetters(Class<?> clazz, String... fields) {
        Field[] campos = clazz.getDeclaredFields();

        Stream<Executable> validacoesGetter = Arrays.stream(campos)
              .map((campo) -> () -> {
                  String getName = String.format("get%s",
                        StringUtils.capitalize(campo.getName()));
                  Method getter = clazz.getDeclaredMethod(getName);
                  int getModifier = getter.getModifiers();
                  Assertions.assertTrue(Modifier.isPublic(getModifier));
              });

        Stream<Executable> validacoesSetter = Arrays.stream(campos)
              .filter(campo -> Arrays.stream(fields)
                    .noneMatch(field -> field.equals(campo.getName())))
              .map((campo) -> () -> {
                  String setName = String.format("set%s",
                        StringUtils.capitalize(campo.getName()));
                  Method setter = clazz.getDeclaredMethod(setName, campo.getType());
                  int setModifier = setter.getModifiers();
                  Assertions.assertTrue(Modifier.isPublic(setModifier));
              });

        Assertions.assertAll(Stream.concat(validacoesGetter, validacoesSetter));
    }
}
