package main.java.fr.taqmac.datamodel;

public class Point {

	String position;
	String name;

	public Point(String pos) {
		position = pos;
		name = "";
	}

	public Point(String pos, String n) {
		position = pos;
		name = n;
	}
}
