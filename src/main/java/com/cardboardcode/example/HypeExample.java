package com.cardboardcode.example;

import hype.*;
import hype.extended.colorist.HColorPool;
import hype.extended.layout.HGridLayout;
import processing.core.PApplet;

/**
 * Minimal example that draws a moving line
 */
public class HypeExample extends PApplet {

	private int _randomSize() {
		return (int)this.random(30, 120);
	}

	@Override
	public void settings() {
		size(1080, 1350, P3D);
		smooth(3);
	}

	@Override
	public void setup() {
		float ROT_ = 10;

		surface.setLocation(0, 0);

		H.init(this);
		H.background(0xFF021859);
		H.use3D(true);

		lights();

		HDrawablePool pool = new HDrawablePool(120);
		HDrawable box = new HBox().fill(0xFA0433BF).stroke(0xFFF25CA2).strokeCap(20).strokeWeight(4);

		pool.autoAddToStage()
				.add(box)
				.onCreate(
						obj -> {
							HBox d = (HBox) obj;
							d
									.depth((int)random(20, 500)) // depth is a 3D/HBox specific method, so put this first
									.width(this._randomSize())
									.height(this._randomSize())
                                    .loc((int)random(-50, width + 50), (int)random(-50, height + 50), (int)random(-1000, 400))
									.rotate(random(-ROT_, ROT_))
									.anchorAt(H.CENTER);
						}
				)
				.requestAll();

		H.drawStage();
		noLoop();
	}

	@Override
	public void draw() {
	    //
	}

	public static void main(String[] args) {

		// Start the application
		PApplet.main(HypeExample.class.getName());

	}

}
