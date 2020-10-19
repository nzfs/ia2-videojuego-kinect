package net.nzfs.sketch.pantallas;

import java.util.ArrayList;

import fisica.FBox;
import fisica.FWorld;
import net.nzfs.sketch.CapturaJugador;
import net.nzfs.sketch.entidades.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Juego extends Pantallas {

	private FWorld mundo;
	private CapturaJugador capturaJugador;
	private ArrayList<Astronauta> astronautas;
	private ArrayList<Asteroide> asteroides;
	private boolean timer;

	public Jugador jugadorIzq;
	public Jugador jugadorDer;

	private FBox piso;
	private PImage pisoImg;

	public Juego(PApplet _sketch, PImage _fondo, FWorld _mundo, CapturaJugador _jugador)
	{
		super(_sketch, _fondo);
		mundo = _mundo;
		capturaJugador = _jugador;

		jugadorIzq = new Jugador(sketch, mundo);
		jugadorDer = new Jugador(sketch, mundo);

		astronautas = new ArrayList<Astronauta>();
		asteroides = new ArrayList<Asteroide>();

		puntaje = 10;
		timer = true;

		// pisoImg = sketch.createImage(sketch.width, sketch.height, PApplet.RGB);
		pisoImg = sketch.loadImage("sprites/piso.png");
		pisoImg.resize(sketch.width, 0);

		piso = new FBox(sketch.width, 10);

		piso.setPosition(0 + sketch.width / 2, sketch.height);
		piso.setGrabbable(false);
		piso.attachImage(pisoImg);
		piso.setStatic(true);
		piso.setName("piso");
		piso.setRestitution(1.5f);
		mundo.add(piso);
	}

	public void display()
	{

	}

	private void timer()
	{
		if (timer)
		{
			tiempoInicio = sketch.millis();
			timer = false;
		}
	}

	public void explosion()
	{

	}

	public void update()
	{
		timer();
		tiempo = (sketch.millis() - tiempoInicio) / 1000;

		mundo.step();
		capturaJugador.trackSkeleton();

		PImage jugadorMask = capturaJugador.userMask(fondo);
		// jugadorMask.resize(640, 480);
		
		sketch.image(jugadorMask, 0, 0, sketch.width, sketch.height);

		sketch.fill(255, 0, 0);
		sketch.noStroke();

		sketch.pushStyle();
		sketch.textSize(20);
		sketch.fill(200);
		sketch.text("Astronautas: " + puntaje, sketch.width - 175*2, 30);
		sketch.text("Tiempo: " + tiempo + " segundos", sketch.width - 175*2, 75);
		sketch.popStyle();

		// asteroides //
		if (sketch.frameCount % 30 == 0)
		{
			asteroides.add(new Asteroide(sketch, mundo));
		}

		for (Asteroide a : asteroides)
		{
			a.addTorque(10);
			// a.addImpulse(sketch.random(-100, 100), sketch.random(100));
		}

		// astronautas //
		if (sketch.frameCount % 45 == 0)
		{
			astronautas.add(new Astronauta(sketch, mundo, sketch.random(sketch.width), sketch.random(1)));
		}

		for (Astronauta a : astronautas)
		{
			a.update();
		}

		// trackeo jugador //
		if (capturaJugador.tracking)
		{
			// println(jugador.leftHandPosition, jugador.rightHandPosition);
		}

		if (capturaJugador.leftHandPosition != null && capturaJugador.leftHandPosition.x != 0
				&& capturaJugador.leftHandPosition.y != 0)
		{
			float leftHandX = PApplet.map(capturaJugador.leftHandPosition.x, 0, 640, 0, sketch.width);
			float leftHandY = PApplet.map(capturaJugador.leftHandPosition.y, 0, 480, 0, sketch.height);
			// jugadorIzq.update(capturaJugador.leftHandPosition.x,
			// capturaJugador.leftHandPosition.y);
			jugadorIzq.update(leftHandX, leftHandY);
		}
		if (capturaJugador.rightHandPosition != null && capturaJugador.rightHandPosition.x != 0
				&& capturaJugador.rightHandPosition.y != 0)
		{
			float rightHandX = PApplet.map(capturaJugador.rightHandPosition.x, 0, 640, 0, sketch.width);
			float rightHandY = PApplet.map(capturaJugador.rightHandPosition.y, 0, 480, 0, sketch.height);
			// jugadorDer.update(capturaJugador.rightHandPosition.x,
			// capturaJugador.rightHandPosition.y);
			jugadorDer.update(rightHandX, rightHandY);
		}

		if (puntaje <= 0)
		{
			remove();

			for (Asteroide a : asteroides)
			{
				a.remove();
			}

			for (Astronauta astronauta : astronautas)
			{
				astronauta.matar();
			}

			astronautas.clear();
			asteroides.clear();
			timer = true;
			puntaje = 10;
		}
	}
}
