package com.mycompany.controlenotas.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.controlenotas.http.ApiClient;
import com.mycompany.controlenotas.http.GsonProvider;
import com.mycompany.controlenotas.model.TipoAvaliacaoDTO;
import java.util.List;

public class TipoAvaliacaoService {

    private final Gson gson = GsonProvider.get();

    public List<TipoAvaliacaoDTO> listar() throws Exception {
        String json = ApiClient.get("/tipos-avaliacao");
        return gson.fromJson(json, new TypeToken<List<TipoAvaliacaoDTO>>(){}.getType());
    }
}
