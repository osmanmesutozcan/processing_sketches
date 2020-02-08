package com.cardboardcode.example;

import hype.H;
import hype.HBox;
import hype.HDrawable;
import hype.HDrawablePool;
import processing.core.PApplet;

/**
 * Minimal example that draws a moving line
 */
public class Sketch_002 extends PApplet {
    private static final Float SCALE_FACTOR = 0.8f;

    private float ROT_ = 45;

    private int _randomSize() {
        return (int) this.random(2, 10);
    }


    @Override
    public void settings() {
        size(1080 / 2, 1350 / 2 , P3D);
        smooth(5);
    }

    @Override
    public void setup() {

        surface.setLocation(0, 0);

        H.init(this);
        H.background(0xFF021859);
        H.use3D(true);

        lights();

        HDrawablePool pool = new HDrawablePool(1200);
        HDrawable box = new HBox().fill(0xFA0433BF).stroke(0xFFF25CA2).strokeWeight(1);

        pool.autoAddToStage()
                .add(box)
                .onCreate(
                        obj -> {
                            HBox d = (HBox) obj;
                            d
                                    .depth((int) random(20, 700)) // depth is a 3D/HBox specific method, so put this first
                                    .width(this._randomSize())
                                    .height(this._randomSize())
                                    .loc((int) random(120, width - 120), (int) random(120, height - 120), (int) random(-1000, 400))
                                    .anchorAt(H.CENTER);
                        }
                )
                .requestAll();

//        new IOUtils(this).saveVector(Sketch_001.class.getSimpleName());
        noLoop();
    }

    @Override
    public void draw() {
        H.drawStage();
    }

    public static void main(String[] args) {
        // Start the application
        PApplet.main(Sketch_002.class.getName());

    }

}
