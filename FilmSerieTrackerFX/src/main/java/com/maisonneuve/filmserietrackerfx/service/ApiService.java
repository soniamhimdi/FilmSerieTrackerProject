package com.maisonneuve.filmserietrackerfx.service;

import com.maisonneuve.filmserietrackerfx.api.ApiClient;
import com.maisonneuve.filmserietrackerfx.model.Contenu;
import com.maisonneuve.filmserietrackerfx.model.ProgressionSerie;
import com.maisonneuve.filmserietrackerfx.util.JsonUtil;
import com.maisonneuve.filmserietrackerfx.model.Genre;
import java.util.List;
//import com.google.gson.Gson;
public class ApiService {

    private final ApiClient apiClient;
    //private final Gson gson = new Gson();

    public ApiService() {
        this.apiClient = new ApiClient();
    }

    public List<Contenu> getAllContenus() {
        try {
            String response = apiClient.get("/contenus");
            return JsonUtil.fromJsonList(response, Contenu.class);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    /*public void ajouterContenu(Contenu contenu) {
        try {
            String json = JsonUtil.toJson(contenu);
            apiClient.post("/contenus", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public Contenu ajouterContenu(Contenu c){

        try{
            String response =
                    apiClient.post("/contenus",
                            JsonUtil.toJson(c));

            return JsonUtil.fromJson(response,
                    Contenu.class);

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void modifierContenu(Contenu contenu) {
        try {
            String json = JsonUtil.toJson(contenu);
            apiClient.put("/contenus/" + contenu.getId(), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerContenu(int id) {
        try {
            apiClient.delete("/contenus/" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Genre> getAllGenres(){

        try{
            String response = apiClient.get("/genres");
            return JsonUtil.fromJsonList(response,Genre.class);
        }catch(Exception e){
            e.printStackTrace();
            return List.of();
        }
    }
    public ProgressionSerie getProgression(int id){
        try{
            String r = apiClient.get("/progression/"+id);
            return JsonUtil.fromJson(r,ProgressionSerie.class);
        }catch(Exception e){
            return null;
        }
    }

    public void saveProgression(ProgressionSerie p){
        try{
            apiClient.post("/progression",
                    JsonUtil.toJson(p));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
//    public void updateProgression(ProgressionSerie p) {
//
//        try {
//
//            String json = gson.toJson(p);
//
//            apiClient.put("/progression/" + p.getContenuId(), json);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
public void updateProgression(ProgressionSerie p) {

    try {

        apiClient.put(
                "/progression/" + p.getContenuId(),
                JsonUtil.toJson(p)
        );

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    }
