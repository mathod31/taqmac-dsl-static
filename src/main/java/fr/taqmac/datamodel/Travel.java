package main.java.fr.taqmac.datamodel;

import fr.taqmac.datamodel.TravelGeo;
import fr.taqmac.datamodel.TransportChoice;

public class Travel {
	TravelGeo travelGeo;
	TransportChoice vehicle;
	String description;

	public Travel(TravelGeo travel, TransportChoice veh, String des) {
		travelGeo = travel;
		vehicle = veh;
		description = des;
	}

	public String getDescription() {
		return description;
	}
}
