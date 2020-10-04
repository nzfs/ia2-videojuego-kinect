package net.nzfs.sketch.entidades;

import fisica.*;
import processing.core.PApplet;

public class Asteroide extends FCircle {

	private PApplet sketch;
	private FWorld mundo;

	public Asteroide(PApplet _sketch, FWorld _mundo)
	{
		super(25);
		sketch = _sketch;
		mundo = _mundo;

		setNoFill();
		setStroke(255);
		setName("asteroide");
		setPosition(sketch.random(sketch.width), 0);
		mundo.add(this);
	}

}
