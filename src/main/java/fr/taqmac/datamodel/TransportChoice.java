package fr.taqmac.datamodel;

public class TransportChoice {

	double speed;
	ModeTransport modeTransport;
	
	public TransportChoice(ModeTransport mode) {
		modeTransport = mode;
		speed = getSpeedByTransportMode(mode);
	}
	
	private double getSpeedByTransportMode(ModeTransport mode) {
		return 1.0;
	}
}
