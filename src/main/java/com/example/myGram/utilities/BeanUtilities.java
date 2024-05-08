package com.example.myGram.utilities;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class BeanUtilities {
    @SneakyThrows
    public void copyNotNullProperties(Object source, Object destination){
        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getFields();

        for (Field field : fields){
            field.setAccessible(true);
            Object values = field.get(source);

            if(values != null){
                field.set(destination, values);
            }
        }
    }
}
