package fr.taqmac.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import fr.taqmac.datamodel.*;

public class TisseoParser {

    /**
     * Returns all places result of the search of a location in an Tisseo API call result.
     *
     * @param json Tisseo API call result
     * @return places result of the location search
     * @throws IOException .
     */
    public ArrayList<Point> getPlaces(String json) throws IOException {
        HashMap result = new ObjectMapper().readValue(json, HashMap.class);
        //HashMap placesList = new ObjectMapper().readValue(result.get("placesList"), HashMap.class);
        LinkedHashMap placesList = (LinkedHashMap) result.get("placesList");
        ArrayList places = (ArrayList) placesList.get("place");
        ArrayList<Point> results = new ArrayList<>();
        for (Object o : places) {
            LinkedHashMap place = (LinkedHashMap) o;
            String pos = place.get("key").toString();
            String n = place.get("label").toString();
            Double x = (Double)place.get("x");
            Double y = (Double)place.get("y");
            results.add(new Point(pos,n,x,y));
        }
        return results;
    }

    public ArrayList<String> getJourneysText(String json) throws IOException {
        HashMap result = new ObjectMapper().readValue(json, HashMap.class);
        LinkedHashMap routePlannerResult = (LinkedHashMap) result.get("routePlannerResult");
        ArrayList journeys = (ArrayList) routePlannerResult.get("journeys");
        ArrayList<TravelComplete> listTravel = new ArrayList<>();
        for (int j = 0; j < journeys.size() - 1; j++) {
            LinkedHashMap value = (LinkedHashMap) journeys.get(j);
            TravelComplete fulltrav = new TravelComplete();
            LinkedHashMap journey = (LinkedHashMap) value.get("journey");
            ArrayList chunks = (ArrayList) journey.get("chunks");
            for (int i = 0; i < chunks.size() - 1; i += 2) {
                LinkedHashMap departPointLog = (LinkedHashMap) chunks.get(i);
                LinkedHashMap departPointLogGeo = (LinkedHashMap) departPointLog.get("stop");
                LinkedHashMap departPointLogGeoInfo = (LinkedHashMap) departPointLogGeo.get("connectionPlace");

                String posD = departPointLogGeoInfo.get("name").toString();
                String nD = departPointLogGeoInfo.get("name").toString();
                Double xD = Double.parseDouble(departPointLogGeoInfo.get("x").toString());
                Double yD = Double.parseDouble(departPointLogGeoInfo.get("y").toString());
                StartPoint departPoint = new StartPoint(posD,nD,xD,yD);

                LinkedHashMap endPointLog = (LinkedHashMap) chunks.get(i+2);
                LinkedHashMap endPointLogGeo = (LinkedHashMap) endPointLog.get("stop");
                LinkedHashMap endPointLogGeoInfo = (LinkedHashMap) endPointLogGeo.get("connectionPlace");

                String posE = endPointLogGeoInfo.get("name").toString();
                String nE = endPointLogGeoInfo.get("name").toString();
                Double xE = Double.parseDouble(endPointLogGeoInfo.get("x").toString());
                Double yE = Double.parseDouble(endPointLogGeoInfo.get("y").toString());
                EndPoint endPoint = new EndPoint(posE,nE,xE,yE);

                TravelGeo travG = new TravelGeo(departPoint,endPoint);

                LinkedHashMap trans = (LinkedHashMap) chunks.get(i+1);
                LinkedHashMap transInfo = (LinkedHashMap) trans.get("service");
                LinkedHashMap transInfoText = (LinkedHashMap) transInfo.get("text");
                String des = (String) transInfoText.get("text");

                LinkedHashMap transInfoDesStop = (LinkedHashMap) transInfo.get("destinationStop");
                LinkedHashMap transInfoDesStopLine = (LinkedHashMap) transInfoDesStop.get("line");
                LinkedHashMap transInfoDesStopLineTransMode = (LinkedHashMap) transInfoDesStopLine.get("transportMode");

                String transInfoMode = (String) transInfoDesStopLineTransMode.get("name");
                ModeTransport modeTransport;
                switch (transInfoMode){
                    case "métro":
                        modeTransport = ModeTransport.SUBWAY;
                    case "bus":

                }

                //TransportChoice transC = new TransportChoice();

                //Travel trav = new Travel(travG,transC,des);

            }
            listTravel.add(fulltrav);
        }
        return null;
    }


}
