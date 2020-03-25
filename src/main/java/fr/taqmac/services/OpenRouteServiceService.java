package fr.taqmac.services;

import fr.taqmac.parser.OpenRouteServiceParser;
import fr.taqmac.utils.ResponseHttpUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OpenRouteServiceService {
    public final String getBaseURL="https://api.openrouteservice.org/v2";
    public final String getKey = "5b3ce3597851110001cf6248c3e0f4ea0c98469b87828a7ec113cc29";

    @GetMapping(value = "/car/listPoints/{localisationStart}/{localisationEnd}")
    private ResponseEntity<String> listPoints(@PathVariable String localisationStart,
                                              @PathVariable String localisationEnd)
            throws IOException {
        ResponseHttpUtils response = HTTPService.call(getBaseURL + "/directions/driving-car?start=" + localisationStart
                + "&end=" + localisationEnd + "&api_key=" + getKey, HTTPService.GET);
        String journeys = response.getResultContent();

        System.out.printf(OpenRouteServiceParser.getJourney(journeys).toString());
        if (response.getResultCode() == HttpStatus.OK.value())
            return HTTPService.createResponse(journeys, HttpStatus.OK);
        else
            return HTTPService.createResponse("", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
