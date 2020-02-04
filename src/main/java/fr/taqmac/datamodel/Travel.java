package main.java.fr.taqmac.datamodel;

public class Travel {
	TravelGeo travelGeo;
	TransportChoice vehicle;


	public Travel(TravelGeo travel,TransportChoice veh) {
		travelGeo = travel;
		vehicle = veh;
	}
}
