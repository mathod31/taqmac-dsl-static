package fr.taqmac;


import fr.taqmac.services.HTTPService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OpenStreetMap {

    @GetMapping(value = "/map/search/{localisation}")
    private String search(@PathVariable String localisation) throws IOException {
        String detailLocalisation = HTTPService.call("https://nominatim.openstreetmap.org/search/" + localisation + "?format=json", HTTPService.GET);
        return detailLocalisation;
    }

}
