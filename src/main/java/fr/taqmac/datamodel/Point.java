package main.java.fr.taqmac.datamodel;

public class Point {
	Position coordonees;
	String position;
	String name;

	public Point(String pos) {
		this(pos,"",0.0,0.0);
	}

	public Point(String pos, String n){
		this(pos, n, 0.0, 0.0);
	}

	public Point(String pos, String n, Double x, Double y) {
		coordonees = new Position(x,y);
		position = pos;
		name = n;
	}
}
