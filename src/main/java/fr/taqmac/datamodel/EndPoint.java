package fr.taqmac.datamodel;

public class EndPoint extends Point {
	public EndPoint(String postion) {
		super(postion);
	}	
	public EndPoint(String postion,String n) {
		super(postion,n);
	}
	public EndPoint(String position, String n, Double x, Double y) {
		super(position, n, x, y);
	}
}
