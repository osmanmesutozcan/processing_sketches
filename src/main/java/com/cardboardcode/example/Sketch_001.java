package com.cardboardcode.example;

import hype.H;
import hype.HBox;
import hype.HDrawable;
import hype.HDrawablePool;
import processing.core.PApplet;

/**
 * Minimal example that draws a moving line
 */
public class Sketch_001 extends PApplet {

	private int _randomSize() {
		return (int)this.random(2, 10);
	}

	@Override
	public void settings() {
		size(800, 1000, P3D);
		smooth(5);
	}

	@Override
	public void setup() {
		float ROT_ = 10;

		surface.setLocation(0, 0);

		H.init(this);
		H.background(0xFF021859);
		H.use3D(true);

		lights();

		HDrawablePool pool = new HDrawablePool(1000);
		HDrawable box = new HBox().fill(0xFA0433BF).stroke(0xFFF25CA2).strokeWeight(1);

		pool.autoAddToStage()
				.add(box)
				.onCreate(
						obj -> {
							HBox d = (HBox) obj;
							d
									.depth((int)random(20, 700)) // depth is a 3D/HBox specific method, so put this first
									.width(this._randomSize())
									.height(this._randomSize())
                                    .loc((int)random(120, width - 120), (int)random(120, height - 120), (int)random(-1000, 400))
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
		PApplet.main(Sketch_001.class.getName());

	}

}
