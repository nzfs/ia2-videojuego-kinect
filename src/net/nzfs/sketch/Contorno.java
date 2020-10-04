package net.nzfs.sketch;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import SimpleOpenNI.SimpleOpenNI;
import gab.opencv.*;

public class Contorno {

	private PApplet sketch;
	private OpenCV opencv;
	private SimpleOpenNI kinect;

	PImage src, dst, depth;

	public int threshold = 200;
	private ArrayList<Contour> contours;
	// private ArrayList<Contour> polygons;

	public Contorno(PApplet _sketch, SimpleOpenNI _kinect) {
		kinect = _kinect;
		sketch = _sketch;
		opencv = new OpenCV(this.sketch, 640, 480);
	}

	public void getContorno() {
		depth = kinect.depthImage();

		opencv.loadImage(depth);
		opencv.gray();
		opencv.threshold(threshold);
		dst = opencv.getOutput();
		contours = opencv.findContours();
	}

	public void dibujarContorno() {
		sketch.pushStyle();
		for (Contour contour : contours) {
			sketch.stroke(0, 255, 0);
			contour.draw();
			sketch.stroke(0, 0, 255);
			sketch.beginShape();
			sketch.noFill();
			for (PVector point : contour.getPolygonApproximation().getPoints()) {
				sketch.vertex(point.x, point.y);
			}
			sketch.endShape();
		}
		sketch.popStyle();
	}
}
