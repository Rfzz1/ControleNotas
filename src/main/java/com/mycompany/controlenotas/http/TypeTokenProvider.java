package com.mycompany.controlenotas.http;

import com.google.gson.reflect.TypeToken;
import java.util.List;

public class TypeTokenProvider {

    public static <T> TypeToken<List<T>> listOf(Class<T> clazz) {
        return new TypeToken<List<T>>() {};
    }
}
