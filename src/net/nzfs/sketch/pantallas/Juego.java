package net.nzfs.sketch.pantallas;

import java.util.ArrayList;

import fisica.FWorld;
import net.nzfs.sketch.CapturaJugador;
import net.nzfs.sketch.entidades.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Juego extends Pantallas {

	private FWorld mundo;
	private CapturaJugador jugador;
	private ArrayList<Astronauta> astronautas;
	private ArrayList<Asteroide> asteroides;
	private boolean timer;

	public Jugador jugadorIzq;
	public Jugador jugadorDer;

	public Juego(PApplet _sketch, PImage _fondo, FWorld _mundo, CapturaJugador _jugador)
	{
		super(_sketch, _fondo);
		mundo = _mundo;
		jugador = _jugador;

		jugadorIzq = new Jugador(sketch, mundo);
		jugadorDer = new Jugador(sketch, mundo);

		astronautas = new ArrayList<Astronauta>();
		asteroides = new ArrayList<Asteroide>();

		puntaje = 10;
		timer = true;
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

	public void update()
	{
		timer();
		tiempo = (sketch.millis() - tiempoInicio) / 1000;

		mundo.step();
		jugador.trackSkeleton();

		PImage jugadorMask = jugador.userMask(fondo);
		//jugadorMask.resize(640, 480);
		sketch.image(jugadorMask, 0, 0, sketch.width, sketch.height);

		sketch.fill(255, 0, 0);
		sketch.noStroke();

		sketch.pushStyle();
		sketch.textSize(10);
		sketch.fill(200);
		sketch.text("Astronautas: " + puntaje, sketch.width - 175, 30);
		sketch.text("Tiempo: " + tiempo + " segundos", sketch.width - 175, 50);
		sketch.popStyle();

		// asteroides //
		if (sketch.frameCount % 30 == 0)
		{
			// @SuppressWarnings("unused")
			// Asteroide asteroide = new Asteroide(sketch, mundo);
			asteroides.add(new Asteroide(sketch, mundo));
		}

		for (Asteroide a : asteroides)
		{
			a.addTorque(10);
			a.addImpulse(sketch.random(-10, 10), sketch.random(10));
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
		if (jugador.tracking)
		{
			// println(jugador.leftHandPosition, jugador.rightHandPosition);
		}

		if (jugador.leftHandPosition != null && jugador.leftHandPosition.x != 0 && jugador.leftHandPosition.y != 0)
		{
			jugadorIzq.update(jugador.leftHandPosition.x, jugador.leftHandPosition.y);
		}
		if (jugador.rightHandPosition != null && jugador.rightHandPosition.x != 0 && jugador.rightHandPosition.y != 0)
		{
			jugadorDer.update(jugador.rightHandPosition.x, jugador.rightHandPosition.y);
		}

		if (puntaje <= 0)
		{
			remove();

			for (Asteroide a : asteroides)
			{
				a.remove();
			}

			astronautas.clear();
			asteroides.clear();
			timer = true;
			puntaje = 10;
		}
	}
}
