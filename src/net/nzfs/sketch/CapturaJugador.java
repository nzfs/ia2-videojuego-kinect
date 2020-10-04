package net.nzfs.sketch;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.*;

public class CapturaJugador {

	private PApplet sketch;
	public SimpleOpenNI kinect;
	public PVector rightHandPosition;
	public PVector leftHandPosition;

	public boolean tracking;

	int[] userMap;
	PImage mask;

	public CapturaJugador(PApplet _sketch)
	{
		sketch = _sketch;
		kinect = new SimpleOpenNI(this.sketch);
		kinect.enableDepth();
		kinect.enableUser();
		kinect.enableRGB();
		// kinect.alternativeViewPointDepthToImage();
		// mask = new PImage(sketch.width, sketch.height);
		mask = sketch.createImage(sketch.width, sketch.height, PApplet.RGB);
		sketch.fill(255, 0, 0);
		kinect.setMirror(false);
		rightHandPosition = new PVector();
		leftHandPosition = new PVector();
		tracking = false;
	}

	public void trackSkeleton()
	{
		kinect.update();
		IntVector userList = new IntVector();
		kinect.getUsers(userList);
		if (userList.size() > 0)
		{
			int userId = userList.get(0);

			if (!kinect.isTrackingSkeleton(userId) && tracking == false)
			{
				onNewUser(kinect, userId);
			}
			if (kinect.isTrackingSkeleton(userId))
			{
				// drawSkeleton(userId);
				rightHandPosition = getRightHandPosition(userId);
				leftHandPosition = getLeftHandPosition(userId);
			}
		}
	}

	private PVector getRightHandPosition(int userId)
	{
		PVector joint = new PVector();
		float confidence = kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND, joint);
		if (confidence < 0.5)
		{
			return null;
		}
		PVector convertedJoint = new PVector();
		kinect.convertRealWorldToProjective(joint, convertedJoint);
		return convertedJoint;
	}

	private PVector getLeftHandPosition(int userId)
	{
		PVector joint = new PVector();
		float confidence = kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND, joint);
		if (confidence < 0.5)
		{
			return null;
		}
		PVector convertedJoint = new PVector();
		kinect.convertRealWorldToProjective(joint, convertedJoint);
		return convertedJoint;
	}

	/*
	 * private void drawSkeleton(int userId) { // sketch.stroke(0); //
	 * sketch.strokeWeight(5); drawJoint(userId, SimpleOpenNI.SKEL_RIGHT_HAND);
	 * drawJoint(userId, SimpleOpenNI.SKEL_LEFT_HAND); }
	 */
	/*
	 * private void drawJoint(int userId, int jointId) { PVector joint = new
	 * PVector(); float confidence = kinect.getJointPositionSkeleton(userId,
	 * jointId, joint); if (confidence < 0.5) { return; } PVector convertedJoint =
	 * new PVector(); kinect.convertRealWorldToProjective(joint, convertedJoint); //
	 * sketch.pushStyle(); sketch.fill(255, 0, 0); sketch.noStroke();
	 * sketch.ellipse(convertedJoint.x, convertedJoint.y, 50, 50); //
	 * sketch.popStyle(); }
	 */

	private void onNewUser(SimpleOpenNI kinect, int userId)
	{
		PApplet.println("Start skeleton tracking");
		kinect.startTrackingSkeleton(userId);
		tracking = true;
	}

	public PImage userMask(PImage _fondo)
	{
		PImage fondo = _fondo;
		PImage rgbImage = kinect.rgbImage();
		// rgbImage.resize(sketch.width, sketch.height);
		// fondo.resize(sketch.width, sketch.height);
		rgbImage.loadPixels();
		fondo.loadPixels();
		int[] userList = kinect.getUsers();
		if (userList.length > 0)
		{
			userMap = kinect.userMap();
			// load sketches pixels
			mask.loadPixels();
			for (int i = 0; i < userMap.length; i++)
			{
				if (userMap[i] != 0)
				{
					// set the sketch pixel to the color pixel
					// mask.pixels[i] = rgbImage.pixels[i];
					mask.pixels[i] = sketch.color(150, 0, 255);
				} else
				{
					mask.pixels[i] = fondo.pixels[i];
				}
			}
			mask.updatePixels();
		}
		return mask;
	}
}
