package fr.taqmac.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.taqmac.datamodel.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class OpenRouteServiceParser {
    public static ArrayList<TravelComplete> getJourney(String json) throws IOException {
        HashMap result = new ObjectMapper().readValue(json,HashMap.class);
        ArrayList features = (ArrayList) result.get("features");
        LinkedHashMap features0 = (LinkedHashMap) features.get(0);

        LinkedHashMap geometry = (LinkedHashMap) features0.get("geometry");
        ArrayList coordinates = (ArrayList) geometry.get("coordinates");

        LinkedHashMap properties = (LinkedHashMap) features0.get("properties");
        ArrayList segments = (ArrayList) properties.get("segments");
        LinkedHashMap segements0 = (LinkedHashMap) segments.get(0);
        ArrayList steps = (ArrayList) segements0.get("steps");
        ArrayList<TravelComplete> listTravel = new ArrayList<>();
        TravelComplete fulltrav = new TravelComplete();
        for(Object o: steps){
            LinkedHashMap step = (LinkedHashMap) o;
            ModeTransport modeTransport = ModeTransport.CAR;
            String des = step.get("instruction").toString() + "\n";
            TravelGeo travG;
            String posD = step.get("name").toString();
            String nD = step.get("name").toString();
            ArrayList wayPoints = (ArrayList) step.get("way_points");

            ArrayList dPointGeo = (ArrayList) coordinates.get((int) wayPoints.get(0));
            Double xD = Double.parseDouble(dPointGeo.get(0).toString());
            Double yD = Double.parseDouble(dPointGeo.get(1).toString());
            StartPoint departPoint = new StartPoint(posD,nD,xD,yD);

            String posE = step.get("name").toString();
            String nE = step.get("name").toString();

            ArrayList ePointGeo = (ArrayList) coordinates.get((int) wayPoints.get(1));
            Double xE = Double.parseDouble(ePointGeo.get(0).toString());
            Double yE = Double.parseDouble(ePointGeo.get(1).toString());
            EndPoint endPoint = new EndPoint(posE, nE, xE, yE);

            travG = new TravelGeo(departPoint, endPoint);
            TransportChoice transC = new TransportChoice((modeTransport));
            Travel trav = new Travel(travG, transC, des);
            fulltrav.addTravel(trav);
        }
        listTravel.add(fulltrav);
        return listTravel;
    }

}
