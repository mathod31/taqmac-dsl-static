package fr.taqmac.services;

import fr.taqmac.utils.ResponseHttpUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class OpenStreetMapService {

    public static final String getBaseURL = "https://nominatim.openstreetmap.org/";


    @GetMapping(value = "/map/search/{localisation}")
    private ResponseEntity<String> search(@PathVariable String localisation) throws IOException {
        localisation = URLEncoder.encode(localisation, StandardCharsets.UTF_8.toString());
        System.out.println(localisation);

        ResponseHttpUtils response = HTTPService.call(getBaseURL + "/search/" + localisation + "?format=json", HTTPService.GET);
        String detailLocalisation = response.getResultContent();
        if (response.getResultCode() == HttpStatus.OK.value())
            return HTTPService.createResponse(detailLocalisation, HttpStatus.OK);
        else
            return HTTPService.createResponse("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/getArrival")
    private ResponseEntity<String> getArrival() throws IOException {
        String arrival = "Altran";
        return HTTPService.createResponse(arrival,HttpStatus.OK);
    }



}

