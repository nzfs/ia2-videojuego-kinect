package net.nzfs.sketch.pantallas;

import processing.core.*;

public class Parallax {

	PApplet sketch;
	PImage[] layers;

	public Parallax(PImage[] _layers)
	{
		for (int i = 0; i < _layers.length; i++)
		{
			layers[i] = _layers[i];
		}
	}

	public float xPos = 0f;

	public PGraphics draw()
	{
		xPos--;
		PGraphics parallax = new PGraphics();
		parallax.beginDraw();
		parallax.image(layers[0], 0, 0);
		PImage layer_01 = layers[1];
		parallax.image(layer_01, xPos, 0);
		if (xPos < 0)
		{
			PImage layer_01_copy = layers[1];
			parallax.image(layer_01_copy, xPos + sketch.width, 0);
			if (xPos + sketch.width == 0)
			{
				xPos = 0;
			}
		}
		parallax.endDraw();
		return parallax;
	}

}
