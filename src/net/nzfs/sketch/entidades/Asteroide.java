package net.nzfs.sketch.entidades;

import fisica.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Asteroide extends FCircle {

	private PApplet sketch;
	private FWorld mundo;
	// private PImage[] asteroides = new PImage[10];
	private PImage[] asteroides = new PImage[2];

	public Asteroide(PApplet _sketch, FWorld _mundo)
	{
		super(50);
		sketch = _sketch;
		mundo = _mundo;

		for (int i = 0; i < asteroides.length; i++)
		{
			// asteroides[i] = sketch.loadImage("sprites/asteroide/medium/a" + i + ".png");
			asteroides[i] = sketch.loadImage("sprites/asteroide/medium_" + i + ".png");
		}

		setNoFill();
		setStroke(255);
		setName("asteroide");
		// attachImage(asteroides[(int) sketch.random(10)]);
		attachImage(asteroides[(int) sketch.random(2)]);
		setPosition(sketch.random(sketch.width), 0);
		setGroupIndex(-2);
		mundo.add(this);
	}

	public void remove()
	{
		mundo.remove(this);
	}
}
