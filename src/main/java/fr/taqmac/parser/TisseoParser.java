package fr.taqmac.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TisseoParser {
    public ArrayList<String> getPlaces(String json) throws IOException {
        HashMap value = new ObjectMapper().readValue(json, HashMap.class);
        //HashMap placesList = new ObjectMapper().readValue(value.get("placesList"), HashMap.class);
        LinkedHashMap value2 = (LinkedHashMap) value.get("placesList");
        ArrayList value3 = (ArrayList) value2.get("place");
        ArrayList<String> result = new ArrayList<>();
        for (Object o : value3) {
            LinkedHashMap value4 = (LinkedHashMap) o;
            result.add(value4.get("key").toString());
        }
        return result;
    }
    public ArrayList<String> getJourneysText(String json) throws IOException {
        HashMap value = new ObjectMapper().readValue(json, HashMap.class);
        LinkedHashMap value2 = (LinkedHashMap) value.get("routePlannerResult");
        LinkedHashMap value3 = (LinkedHashMap) value2.get("journeys");
        for (int i = 0; i < value3.size();i++){

        }
        return null;
    }

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\Etu\\IdeaProjects\\taqmac-dsl-static\\journeys.json");
            HashMap value = new ObjectMapper().readValue(file, HashMap.class);
            LinkedHashMap value2 = (LinkedHashMap) value.get("routePlannerResult");
            ArrayList value3 = (ArrayList) value2.get("journeys");
            ArrayList<>
            for (Object o : value3) {
                LinkedHashMap value4 = (LinkedHashMap) o;
                result.add(value4.get("key").toString());
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
