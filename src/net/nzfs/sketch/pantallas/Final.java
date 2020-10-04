package net.nzfs.sketch.pantallas;

import processing.core.PApplet;
import processing.core.PImage;

public class Final extends Pantallas {

	public Final(PApplet _sketch, PImage _fondo)
	{
		super(_sketch, _fondo);
		active = false;
	}

	public void display()
	{
		sketch.pushStyle();
		sketch.background(0);
		sketch.textSize(15);
		sketch.fill(150);
		sketch.textAlign(PApplet.CENTER);
		sketch.text("Perdiste", sketch.width / 2, sketch.height / 2);
		sketch.text("Protegiste a los astronautas por " + tiempo + " segundos", sketch.width / 2,
				sketch.height / 2 + 50);
		sketch.text("Volver a jugar", sketch.width / 2, sketch.height / 2 + 105);
		sketch.popStyle();
	}
}
