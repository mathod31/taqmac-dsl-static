package fr.taqmac;


import fr.taqmac.services.HTTPService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OpenStreetMap {

    @GetMapping(value = "/map/search/{localisation}")
    private ResponseEntity<String> search(@PathVariable String localisation) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        String detailLocalisation = HTTPService.call("https://nominatim.openstreetmap.org/search/" + localisation + "?format=json", HTTPService.GET);
        return new ResponseEntity<>(detailLocalisation,headers, HttpStatus.OK);

    }

}
