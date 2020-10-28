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
	private PImage[] astronautaMuertoIzq, astronautaMuertoDer;
	private int frameCaminata;
	private int frameMuerte;
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
		astronautaMuertoIzq = new PImage[4];
		astronautaMuertoDer = new PImage[4];
		dir = _dir;
		vivo = true;

		for (int i = 0; i < astronautaIzq.length; i++)
		{
			astronautaIzq[i] = sketch.loadImage("sprites/astronauta/00" + (i + 1) + "_izq.png");
			astronautaIzq[i].resize(75, 100);
			astronautaDer[i] = sketch.loadImage("sprites/astronauta/00" + (i + 1) + ".png");
			astronautaDer[i].resize(75, 100);
		}

		for (int i = 0; i < astronautaMuertoIzq.length; i++)
		{
			astronautaMuertoIzq[i] = sketch.loadImage("sprites/astronauta/00" + (i + 1) + "_muerto_izq.png");
			astronautaMuertoIzq[i].resize(75, 100);
			astronautaMuertoDer[i] = sketch.loadImage("sprites/astronauta/00" + (i + 1) + "_muerto.png");
			astronautaMuertoDer[i].resize(75, 100);
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
		} else
		{
			//mundo.remove(this);
			setGroupIndex(-2);
			animacionMatar();
		}
	}

	public void matar()
	{
		vivo = false;
	}

	private void animacionMatar()
	{
		if (dir > .5f)
		{
			// se mueve hacia la derecha
			attachImage(astronautaMuertoDer[frameMuerte]);
			// sketch.image(astronautaMuertoDer[frameMuerte], this.getX(), this.getY());
			// setVelocity(90, getVelocityY());
		} else if (dir < .5f)
		{
			// se mueve hacia la izquierda
			attachImage(astronautaMuertoIzq[frameMuerte]);
			// setVelocity(-90, getVelocityY());
		}
		if (sketch.frameCount % 4 == 0)
		{
			frameMuerte = (frameMuerte + 1) % 4;
			if (frameMuerte == 3)
				mundo.remove(this);
		}
	}
}
