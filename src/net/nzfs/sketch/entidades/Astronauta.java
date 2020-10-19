package net.nzfs.sketch.entidades;

import fisica.FBox;
import fisica.FWorld;
import processing.core.PApplet;
import processing.core.PImage;

public class Astronauta extends FBox {

	private PApplet sketch;
	private FWorld mundo;

	private boolean vivo;
	private PImage[] astronautaIzq, astronautaDer;
	private int frameCaminata;
	private float dir;

	public Astronauta(PApplet _sketch, FWorld _mundo, float _posX, float _dir)
	{
		super(75, 100);
		sketch = _sketch;
		mundo = _mundo;
		setName("astronauta");
		setPosition(_posX, sketch.height - 75);
		setGroupIndex(-1);
		mundo.add(this);
		astronautaIzq = new PImage[7];
		astronautaDer = new PImage[7];
		dir = _dir;
		vivo = true;

		for (int i = 0; i < astronautaIzq.length; i++)
		{
			astronautaIzq[i] = sketch.loadImage("sprites/astronauta/00" + (i + 1) + "_izq.png");
			astronautaIzq[i].resize(75, 100);
			astronautaDer[i] = sketch.loadImage("sprites/astronauta/00" + (i + 1) + ".png");
			astronautaDer[i].resize(75, 100);
		}
	}

	public void update()
	{
		if (vivo)
		{
			if (dir > .5f)
			{
				// se mueve hacia la derecha
				attachImage(astronautaDer[frameCaminata]);
				setVelocity(90, getVelocityY());
			} else if (dir < .5f)
			{
				// se mueve hacia la izquierda
				attachImage(astronautaIzq[frameCaminata]);
				setVelocity(-90, getVelocityY());
			}
			if (sketch.frameCount % 4 == 0)
			{
				frameCaminata = (frameCaminata + 1) % 7;
			}
		}
	}

	public void matar()
	{
		animacionMatar();
		vivo = false;
		mundo.remove(this);
	}

	private void animacionMatar()
	{

	}
}
