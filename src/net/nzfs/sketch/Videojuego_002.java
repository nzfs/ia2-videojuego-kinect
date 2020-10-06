//----------------------------------------
// nzfs 2020
// nzfs@nzfs.net
// nzfs.net
//----------------------------------------

package net.nzfs.sketch;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import net.nzfs.sketch.entidades.*;
import net.nzfs.sketch.pantallas.*;

import java.util.ArrayList;

import fisica.*;

public class Videojuego_002 extends PApplet implements fisica.FContactListener {

	public void settings()
	{
		size(640, 480);
		// fullScreen();
	}

	// -------------------------------------------------------------------------------

	public PFont font;
	public Pantallas inicial;
	public Pantallas juego;
	public Pantallas cargando;
	public Pantallas fin;
	public CapturaJugador jugador;
	public PImage fondo;
	public PImage asteroideHijoImg[];
	public PImage pisoImg;
	public PGraphics parallaxGraph;
	public String estado;

	// parallax
	public Parallax parallax;
	public PImage[] parallaxLayers;

	// fisica
	public FWorld mundo;
	public FBox piso;
	public ArrayList<FCircle> asteroideHijo;

	// -------------------------------------------------------------------------------

	public void setup()
	{
		fondo = loadImage("bg5.jpg");
		cargando = new Cargando(this, fondo);
		font = createFont("8-bit.ttf", 20);
		textFont(font);
		thread("load");
	}

	// -------------------------------------------------------------------------------

	public boolean loaded;

	public void load()
	{
		loaded = false;

		inicial = new Inicial(this, fondo);
		fin = new Final(this, fondo);
		jugador = new CapturaJugador(this);
		fondo = createImage(width, height, RGB);
		fondo = loadImage("bg5.jpg");
		asteroideHijoImg = new PImage[6];

		for (int i = 0; i < asteroideHijoImg.length; i++)
		{
			asteroideHijoImg[i] = loadImage("sprites/asteroide/small/a" + i + "_small.png");
		}

		Fisica.init(this);
		mundo = new FWorld();
		juego = new Juego(this, fondo, mundo, jugador);

		piso = new FBox(width, 10);
		pisoImg = loadImage("sprites/piso.png");
		piso.attachImage(pisoImg);
		piso.setPosition(0 + width / 2, height);
		piso.setGrabbable(false);
		piso.setStatic(true);
		piso.setName("piso");
		piso.setRestitution(1.5f);
		mundo.add(piso);

		asteroideHijo = new ArrayList<FCircle>();

		// parallax
		// parallaxLayers = new PImage[3];
		// parallax = new Parallax(parallaxLayers);

		println("Juego cargado");
		loaded = true;
		cargando.remove();
	}

	// ----------------------------------------------------------------------------

	public void draw()
	{
		surface.setTitle("Videojuego IA2" + "  |  " + "fr: " + (int) frameRate);
		background(0);
		if (!loaded)
		{
			cargando.display();
			estado = "cargando";
		} else
		{
			if (inicial.isActive())
			{
				estado = "inicial";
				inicial.display();
			} else if (!jugador.kinect.isInit())
			{
				pushStyle();
				textAlign(CENTER);
				textSize(30);
				text("Kinect no disponible", width / 2, height / 2);
				popStyle();
			} else
			{
				if (juego.isActive())
				{
					estado = "juego";
					juego.update();
				}
				mundo.draw();
			}

			if (!juego.isActive())
			{
				estado = "final";
				fin.active();
				fin.display();
				for (FCircle hijo : asteroideHijo)
				{
					mundo.remove(hijo);
				}
			}
		}
	}

	// -------------------------------------------------------------------------------------------------

	@Override
	public void contactStarted(FContact contacto)
	{
		FBody cuerpo1 = contacto.getBody1();
		FBody cuerpo2 = contacto.getBody2();

		String nombre1 = conseguirNombre(cuerpo1);
		String nombre2 = conseguirNombre(cuerpo2);

		// jugador //
		if (nombre1.equals("jugador") && nombre2.equals("asteroide"))
		{
			dividirAsteroide((FBody) cuerpo2);
		} else if (nombre2.equals("jugador") && nombre1.equals("asteroide"))
		{
			dividirAsteroide((FBody) cuerpo1);
		} else if (nombre1.equals("jugador") && nombre2.equals("asteroideHijo"))
		{
			mundo.remove(cuerpo2);
		} else if (nombre2.equals("jugador") && nombre1.equals("asteroideHijo"))
		{
			mundo.remove(cuerpo1);
		}

		// astronautas //
		if (nombre1.equals("asteroide") && nombre2.equals("astronauta"))
		{
			matarAstronauta((Astronauta) cuerpo2);
			juego.puntaje--;
		} else if (nombre2.equals("asteroide") && nombre1.equals("astronauta"))
		{
			matarAstronauta((Astronauta) cuerpo1);
			juego.puntaje--;
		}

		if (nombre1.equals("asteroideHijo") && nombre2.equals("astronauta"))
		{
			matarAstronauta((Astronauta) cuerpo2);
			juego.puntaje--;
		} else if (nombre2.equals("asteroideHijo") && nombre1.equals("astronauta"))
		{
			matarAstronauta((Astronauta) cuerpo1);
			juego.puntaje--;
		}

		// asteroides //
		if (nombre1.equals("asteroide") && nombre2.equals("asteroide"))
		{
			dividirAsteroide(cuerpo1);
			dividirAsteroide(cuerpo2);
		} else if (nombre1.equals("asteroide") && nombre2.equals("piso"))
		{
			dividirAsteroide(cuerpo1);
		} else if (nombre2.equals("asteroide") && nombre1.equals("piso"))
		{
			dividirAsteroide(cuerpo2);
		}

		if (nombre1.equals("asteroideHijo") && nombre2.equals("piso"))
		{
			mundo.remove(cuerpo1);
		} else if (nombre2.equals("asteroideHijo") && nombre1.equals("piso"))
		{
			mundo.remove(cuerpo2);
		}
	}

	// -----------------------------------------------------------------------

	String conseguirNombre(FBody cuerpo)
	{
		String nombre = "nada";
		if (cuerpo != null)
		{
			nombre = cuerpo.getName();
			if (nombre == null)
			{
				nombre = "nada";
			}
		}
		return nombre;
	}

	// ---------------------------------------------------------------------

	public void dividirAsteroide(FBody asteroide)
	{
		float t = 30;
		float x = asteroide.getX();
		float y = asteroide.getY();

		mundo.remove(asteroide);

		if (t < 5)
			return;

		for (int i = 0; i < 3; i++)
		{
			FCircle hijo = new FCircle(t);
			asteroideHijo.add(hijo);

			hijo.setPosition(x + random(-t * 2, t * 2), y + random(-t * 2, t * 2));
			hijo.setFill(200, 0, 0);
			hijo.setName("asteroideHijo");
			hijo.attachImage(asteroideHijoImg[(int) random(6)]);
			hijo.addForce(random(-50000, 50000), -random(50000));
			// agrego al mundo
			mundo.add(hijo);
		}
	}

	// ---------------------------------------------------------------------------------

	public void matarAstronauta(Astronauta p)
	{
		p.matar();
	}

	// --------------------------------------------------------------------------------

	public void keyPressed()
	{
		if (inicial.isActive())
		{
			inicial.remove();
		}

		if (fin.isActive())
		{
			println("fin");
			fin.remove();
			inicial.active();
			juego.active();
		}
	}

	// --------------------------------------------------------------------------------

	@Override
	public void contactEnded(FContact contacto)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void contactPersisted(FContact contacto)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void contactResult(FContactResult contacto)
	{
		// TODO Auto-generated method stub
	}

	// --------------------------------------------------------------------------------

	public static void main(String[] args)
	{
		String[] processingArgs = { "Sketch" };
		Videojuego_002 sketch = new Videojuego_002();
		PApplet.runSketch(processingArgs, sketch);
	}
}
