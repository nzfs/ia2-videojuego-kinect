package net.nzfs.sketch.pantallas;

import processing.core.*;

public abstract class Pantallas {

	PApplet sketch;
	PImage fondo;
	
	public int puntaje;
	
	protected boolean active;
	protected static int tiempoInicio;
	protected static int tiempo;

	public Pantallas(PApplet _sketch, PImage _fondo)
	{
		sketch = _sketch;
		fondo = _fondo;

		active = true;
	}

	public void remove()
	{
		active = false;
	}
	
	public void active()
	{
		active = true;
	}

	public boolean isActive()
	{
		return active;
	}

	public void display()
	{

	}

	public void update()
	{

	}
}
