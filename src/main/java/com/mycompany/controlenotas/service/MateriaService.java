package com.mycompany.controlenotas.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.http.GsonProvider;
import com.mycompany.controlenotas.model.MateriaDTO;
import java.util.List;

public class MateriaService {

    private final Gson gson = GsonProvider.get();

    public List<MateriaDTO> listar() throws Exception {
        String json = ApiClient.get("/materias");
        return gson.fromJson(json, new TypeToken<List<MateriaDTO>>(){}.getType());
    }
}
