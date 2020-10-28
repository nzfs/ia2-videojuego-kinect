package net.nzfs.sketch.Sonido;

import ddf.minim.*;
import processing.core.*;

public class Sonidos {

	private Minim minim;
	private AudioPlayer musica;
	public AudioSample[] explociones;
	public AudioSample hit;

	public Sonidos(PApplet _sketch)
	{

		minim = new Minim(_sketch);
		explociones = new AudioSample[3];

		for (int i = 0; i < explociones.length; i++)
		{
			explociones[i] = minim.loadSample("sonidos/explosion" + i + ".wav", 512);
		}
		
		hit = minim.loadSample("sonidos/hit.wav");
		
		musica = minim.loadFile("sonidos/sun_ra.mp3", 512);
	}

	public void play()
	{
		musica.play();
		musica.loop();
	}
}
