package fr.taqmac.services;

import fr.taqmac.parser.TisseoParser;
import fr.taqmac.utils.ResponseHttpUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class TisseoApiParserService {

	public static final String getBaseURL = "https://api.tisseo.fr/v1";
	public static final String getKey = "6c4a04f8-06db-405e-a5fd-58c89937b44f";


	@GetMapping(value = "/tisseo/search/{localisation}")
	private ResponseEntity<String> search(@PathVariable String localisation) throws IOException {

		ResponseHttpUtils response = HTTPService.call(getBaseURL + "/places.json?term=" + localisation + "&key=" + getKey, HTTPService.GET);
		String detailLocalisation = response.getResultContent();
		if (response.getResultCode() == HttpStatus.OK.value())
			return HTTPService.createResponse(detailLocalisation, HttpStatus.OK);
		else
			return HTTPService.createResponse("", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/tisseo/listPoints/{localisationStart}/{localisationEnd}")
	private ResponseEntity<String> listPoints(@PathVariable String localisationStart,
											  @PathVariable String localisationEnd)
			throws IOException {
		ResponseHttpUtils response = HTTPService.call(getBaseURL + "/journeys.json?departurePlace=" + localisationStart
				+ "&arrivalPlace=" + localisationEnd + "&displayWording=1&key=" + getKey, HTTPService.GET);
		String journeys = response.getResultContent();

		System.out.printf(TisseoParser.getJourneysText(journeys).toString());
		if (response.getResultCode() == HttpStatus.OK.value())
			return HTTPService.createResponse(journeys, HttpStatus.OK);
		else
			return HTTPService.createResponse("", HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
