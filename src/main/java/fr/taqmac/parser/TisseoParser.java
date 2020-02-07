package main.java.fr.taqmac.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import main.java.fr.taqmac.datamodel.*;

public class TisseoParser {
    public ArrayList<Point> getPlaces(String json) throws IOException {
        HashMap value = new ObjectMapper().readValue(json, HashMap.class);
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
    public ArrayList<TravelComplete> getJourneysText(String json) throws IOException {
        HashMap value = new ObjectMapper().readValue(json, HashMap.class);
        LinkedHashMap value2 = (LinkedHashMap) value.get("routePlannerResult");
        ArrayList value3 = (ArrayList) value2.get("journeys");
        ArrayList<TravelComplete> listTravel = new ArrayList<>();
        for (int j = 0; j < value3.size() - 1; j++) {
            LinkedHashMap value4 = (LinkedHashMap) value3.get(j);
            TravelComplete fulltrav = new TravelComplete();
            LinkedHashMap value5 = (LinkedHashMap) value4.get("journey");
            ArrayList value6 = (ArrayList) value5.get("chunks");
            for (int i = 0; i < value6.size(); i++) {

                LinkedHashMap departPointLog = (LinkedHashMap) value6.get(i);
                ModeTransport modeTransport;
                String des = "";
                TravelGeo travG;
                if(departPointLog.containsKey("stop")||departPointLog.containsKey("service")) {
                    if(i+1<value6.size()) {
                        LinkedHashMap a = (LinkedHashMap) value6.get(i + 1);
                        if (!a.containsKey("street")) {
                            LinkedHashMap departPointLogGeo = (LinkedHashMap) departPointLog.get("stop");
                            LinkedHashMap departPointLogGeoInfo = (LinkedHashMap) departPointLogGeo.get("connectionPlace");

                            String posD = departPointLogGeoInfo.get("name").toString();
                            String nD = departPointLogGeoInfo.get("name").toString();
                            Double xD = Double.parseDouble(departPointLogGeoInfo.get("x").toString());
                            Double yD = Double.parseDouble(departPointLogGeoInfo.get("y").toString());
                            StartPoint departPoint = new StartPoint(posD, nD, xD, yD);

                            LinkedHashMap endPointLog = (LinkedHashMap) value6.get(i + 2);
                            LinkedHashMap endPointLogGeo = (LinkedHashMap) endPointLog.get("stop");
                            LinkedHashMap endPointLogGeoInfo = (LinkedHashMap) endPointLogGeo.get("connectionPlace");

                            String posE = endPointLogGeoInfo.get("name").toString();
                            String nE = endPointLogGeoInfo.get("name").toString();
                            Double xE = Double.parseDouble(endPointLogGeoInfo.get("x").toString());
                            Double yE = Double.parseDouble(endPointLogGeoInfo.get("y").toString());
                            EndPoint endPoint = new EndPoint(posE, nE, xE, yE);

                            travG = new TravelGeo(departPoint, endPoint);
                            if(i==0) {
                                LinkedHashMap departPointLogGeoText = (LinkedHashMap) departPointLogGeo.get("text");
                                des = des + (String) departPointLogGeoText.get("text").toString();
                            }
                            LinkedHashMap trans = (LinkedHashMap) value6.get(i + 1);
                            LinkedHashMap transInfo = (LinkedHashMap) trans.get("service");
                            LinkedHashMap transInfoText = (LinkedHashMap) transInfo.get("text");
                            des = des + (String) transInfoText.get("text");

                            LinkedHashMap endPointLogGeoText = (LinkedHashMap) endPointLogGeo.get("text");
                            des = des + (String) endPointLogGeoText.get("text").toString();

                            LinkedHashMap transInfoDesStop = (LinkedHashMap) transInfo.get("destinationStop");
                            LinkedHashMap transInfoDesStopLine = (LinkedHashMap) transInfoDesStop.get("line");
                            LinkedHashMap transInfoDesStopLineTransMode = (LinkedHashMap) transInfoDesStopLine.get("transportMode");

                            String transInfoMode = (String) transInfoDesStopLineTransMode.get("name");

                            if (transInfoMode.equals("métro")) {
                                modeTransport = ModeTransport.SUBWAY;
                            } else if (transInfoMode.equals("bus")) {
                                modeTransport = ModeTransport.BUS;
                            } else if (transInfoMode.equals("tramway")) {
                                modeTransport = ModeTransport.TRAMWAY;
                            } else {
                                modeTransport = null;
                            }
                            TransportChoice transC = new TransportChoice(modeTransport);

                            Travel trav = new Travel(travG, transC, des);
                            fulltrav.addTravel(trav);
                            i++;
                        }
                    }
                }else{
                    LinkedHashMap pointLogStreet = (LinkedHashMap) departPointLog.get("street");
                    LinkedHashMap pointLogStreetstrAddr = (LinkedHashMap) pointLogStreet.get("startAddress");
                    LinkedHashMap pointLogStreetstrAddrPlace = (LinkedHashMap) pointLogStreetstrAddr.get("connectionPlace");
                    Double xD = Double.parseDouble(pointLogStreetstrAddrPlace.get("longitude").toString());
                    Double yD = Double.parseDouble(pointLogStreetstrAddrPlace.get("latitude").toString());
                    StartPoint departPoint = new StartPoint("", "", xD, yD);

                    LinkedHashMap pointLogStreetEndAddr = (LinkedHashMap) pointLogStreet.get("endAddress");
                    LinkedHashMap pointLogStreetEndAddrPlace = (LinkedHashMap) pointLogStreetEndAddr.get("address");

                    Double xE = Double.parseDouble(pointLogStreetEndAddrPlace.get("longitude").toString());
                    Double yE = Double.parseDouble(pointLogStreetEndAddrPlace.get("latitude").toString());
                    EndPoint endPoint = new EndPoint("", "", xE, yE);

                    travG = new TravelGeo(departPoint, endPoint);

                    LinkedHashMap pointLogText = (LinkedHashMap) pointLogStreet.get("text");
                    des = (String) pointLogText.get("text");

                    modeTransport = ModeTransport.WALK;
                    TransportChoice transC = new TransportChoice(modeTransport);

                    Travel trav = new Travel(travG,transC,des);
                    fulltrav.addTravel(trav);
                }
            }
            listTravel.add(fulltrav);
        }

        return listTravel;
    }

}
