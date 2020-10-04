package net.nzfs.sketch.pantallas;

import processing.core.PApplet;
import processing.core.PImage;

public class Inicial extends Pantallas {

	public Inicial(PApplet _sketch, PImage _fondo)
	{
		super(_sketch, _fondo);
	}

	@Override
	public void display()
	{
		sketch.pushStyle();
		sketch.image(fondo, 0, 0);
		sketch.textAlign(PApplet.CENTER);
		sketch.textSize(40);
		sketch.fill(255);
		sketch.text("ASTEROIDES", sketch.width / 2, sketch.height / 2 - 100);
		sketch.textSize(30);
		sketch.text("Videojuego IA2", sketch.width / 2, sketch.height / 2);
		sketch.textSize(20);
		sketch.text("Precione una tecla cualquiera", sketch.width / 2, sketch.height / 2 + 100);
		sketch.textSize(10);
		sketch.text("(esto deberia ser un menu)", sketch.width / 2, sketch.height / 2 + 140);
		sketch.popStyle();
	}

}
