package main.java.fr.taqmac.datamodel;

import java.util.ArrayList;
import java.util.List;

public class TravelComplete {
	List<Travel> listTravel;

	public TravelComplete(){
		listTravel = new ArrayList<Travel>();
	}

	public void addTravel(Travel trav){
		listTravel.add(trav);
	}
}
