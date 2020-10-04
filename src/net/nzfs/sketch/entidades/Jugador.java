package net.nzfs.sketch.entidades;

import fisica.*;
import processing.core.PApplet;

public class Jugador extends FCircle {

	@SuppressWarnings("unused")
	private PApplet sketch;
	private FWorld mundo;
	private FMouseJoint cadena;

	public Jugador(PApplet _sketch, FWorld _mundo)
	{
		super(50);
		sketch = _sketch;
		mundo = _mundo;
		setName("jugador");
		mundo.add(this);
		cadena = new FMouseJoint(this, 0, 0);
		mundo.add(cadena);
	}

	public void update(float x, float y)
	{
		cadena.setTarget(x, y);
	}

}
