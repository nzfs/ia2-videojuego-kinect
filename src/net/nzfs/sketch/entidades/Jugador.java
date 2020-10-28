package net.nzfs.sketch.entidades;

import fisica.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Jugador extends FCircle {

	private PApplet sketch;
	private FWorld mundo;
	private FMouseJoint cadena;
	public PImage mano[];
	private int frameMano;
	private int dir = 1;

	public Jugador(PApplet _sketch, FWorld _mundo)
	{
		super(50);
		sketch = _sketch;
		mundo = _mundo;
		mano = new PImage[8];
		for (int i = 0; i < mano.length; i++)
		{
			mano[i] = sketch.loadImage("sprites/mano/mano_00" + (i + 1) + ".png");
		}

		setName("jugador");
		setGroupIndex(-1);
		mundo.add(this);
		attachImage(mano[0]);
		cadena = new FMouseJoint(this, 0, 0);
		cadena.setDrawable(false);
		mundo.add(cadena);
	}

	public PImage animacion()
	{
		if (sketch.frameCount % 4 == 0)
		{
			frameMano = (frameMano + dir);
			if (frameMano >= 7 || frameMano == 0)
			{
				dir = -dir;
			}
		}
		return mano[frameMano];
	}

	public void update(float x, float y)
	{
		attachImage(animacion());

		cadena.setTarget(x, y);
	}

}
