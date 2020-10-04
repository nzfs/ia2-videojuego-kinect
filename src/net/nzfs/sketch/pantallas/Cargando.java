package net.nzfs.sketch.pantallas;

import processing.core.PApplet;
import processing.core.PImage;

public class Cargando extends Pantallas {

	private PImage astro;
	
	public Cargando(PApplet _sketch, PImage _fondo)
	{
		super(_sketch, _fondo);
		astro = sketch.loadImage("sprites/001.png");
	}
	
	private float angulo;
	
	public void display()
	{
		sketch.image(fondo, 0, 0);
		sketch.pushStyle();
		sketch.textSize(50);
		sketch.textAlign(PApplet.CENTER);
		sketch.fill(255);
		sketch.text("cargando", sketch.width / 2, sketch.height / 2 - 30);
		sketch.popStyle();
		sketch.pushMatrix();
		sketch.pushStyle();
		sketch.translate(sketch.width / 2, sketch.height / 2 + 30);
		sketch.imageMode(PApplet.CENTER);
		sketch.rotate(angulo / 2);
		sketch.image(astro, 0,  0, 70, 70);
		angulo += 0.09f;
		sketch.popStyle();
		sketch.popMatrix();
	}

}
