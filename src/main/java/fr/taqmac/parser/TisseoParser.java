package fr.taqmac.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import fr.taqmac.datamodel.*;

public class TisseoParser {
    public ArrayList<Point> getPlaces(String json) throws IOException {
        HashMap value = new ObjectMapper().readValue(json, HashMap.class);
        //HashMap placesList = new ObjectMapper().readValue(value.get("placesList"), HashMap.class);
        LinkedHashMap value2 = (LinkedHashMap) value.get("placesList");
        ArrayList value3 = (ArrayList) value2.get("place");
        ArrayList<Point> result = new ArrayList<>();
        for (Object o : value3) {
            LinkedHashMap value4 = (LinkedHashMap) o;
            String pos = value4.get("key").toString();
            String n = value4.get("label").toString();
            Double x = (Double)value4.get("x");
            Double y = (Double)value4.get("y");
            result.add(new Point(pos,n,x,y));
        }
        return result;
    }
    public ArrayList<String> getJourneysText(String json) throws IOException {
        HashMap value = new ObjectMapper().readValue(json, HashMap.class);
        LinkedHashMap value2 = (LinkedHashMap) value.get("routePlannerResult");
        ArrayList value3 = (ArrayList) value2.get("journeys");
        ArrayList<TravelComplete> listTravel = new ArrayList<>();
        for(int j = 0; j <value3.size()-1;j++) {
            LinkedHashMap value4 = (LinkedHashMap) value3.get(j);
            TravelComplete fulltrav = new TravelComplete();
            LinkedHashMap value5 = (LinkedHashMap) value4.get("journey");
            ArrayList value6 = (ArrayList) value5.get("chunks");
            for(int i = 0; i <value6.size()-1;i=i+2){
                LinkedHashMap departPointLog = (LinkedHashMap) value6.get(i);
                LinkedHashMap departPointLogGeo = (LinkedHashMap) departPointLog.get("stop");
                LinkedHashMap departPointLogGeoInfo = (LinkedHashMap) departPointLogGeo.get("connectionPlace");

                String posD = departPointLogGeoInfo.get("name").toString();
                String nD = departPointLogGeoInfo.get("name").toString();
                Double xD = Double.parseDouble(departPointLogGeoInfo.get("x").toString());
                Double yD = Double.parseDouble(departPointLogGeoInfo.get("y").toString());
                StartPoint departPoint = new StartPoint(posD,nD,xD,yD);

                LinkedHashMap endPointLog = (LinkedHashMap) value6.get(i+2);
                LinkedHashMap endPointLogGeo = (LinkedHashMap) endPointLog.get("stop");
                LinkedHashMap endPointLogGeoInfo = (LinkedHashMap) endPointLogGeo.get("connectionPlace");

                String posE = endPointLogGeoInfo.get("name").toString();
                String nE = endPointLogGeoInfo.get("name").toString();
                Double xE = Double.parseDouble(endPointLogGeoInfo.get("x").toString());
                Double yE = Double.parseDouble(endPointLogGeoInfo.get("y").toString());
                EndPoint endPoint = new EndPoint(posE,nE,xE,yE);

                TravelGeo travG = new TravelGeo(departPoint,endPoint);

                LinkedHashMap trans = (LinkedHashMap) value6.get(i+1);
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
