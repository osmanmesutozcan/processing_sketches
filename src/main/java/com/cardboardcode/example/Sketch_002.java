package com.cardboardcode.example;

import lombok.*;
import processing.core.PApplet;

/**
 * Reaction Diffusion --
 * <p>
 * ### Sources
 * https://www.algosome.com/articles/reaction-diffusion-gray-scott.html
 * https://karlsims.com/rd.html
 */
public class Sketch_002 extends PApplet {
    // Settings
    private static final Integer WIDTH_ = 1080 / 2;
    private static final Integer HEIGHT_ = 1350 / 2;

    // Variables
    private final ReactionDiffusion rd = new ReactionDiffusion(this);

    @Override
    public void settings() {
        size(WIDTH_, HEIGHT_, P2D);
    }

    @Override
    public void setup() {
        surface.setLocation(0, 0);
    }

    @Override
    public void draw() {
        loadPixels();
        rd.update();
        updatePixels();
    }

    public static void main(String[] args) {
        // Start the application
        PApplet.main(Sketch_002.class.getName());
    }

    // CODE

    public static class ReactionDiffusion {
        private PApplet app;

        private DiffusionMatrix current = new DiffusionMatrix(WIDTH_, HEIGHT_);
        private DiffusionMatrix next = new DiffusionMatrix(WIDTH_, HEIGHT_);

        // Settings
        private final Float dT = 1f;

        private Float dA = 1.0f;
        private Float dB = 0.3f;

        private Float feed = .01f;
        private Float feed$ = .01f;

        private Float kill = .045f;
        private Float kill$ = .045f;

        public ReactionDiffusion(PApplet app) {
            this.app = app;

            for (int x = 0; x < WIDTH_; x++) {
                for (int y = 0; y < 100; y++) {
                    current.setValue(x, y, new DiffusionData(0f, 1f));
                }
            }

            for (int x = 0; x < 100; x++) {
                for (int y = 0; y < HEIGHT_; y++) {
                    current.setValue(x, y, new DiffusionData(0f, 1f));
                }
            }
        }

        public void update() {
            kill = kill$;

            for (var x = 0; x < WIDTH_; x++) {

                kill += .00002f;
                feed = feed$;

                for (var y = 0; y < HEIGHT_; y++) {

                    feed += .00013f;

                    var currValue = current.getValue(x, y);
                    next.setValue(x, y, new DiffusionData(
                            constrain(calculateA(x, y, currValue.getA(), currValue.getB()), 0, 1),
                            constrain(calculateB(x, y, currValue.getA(), currValue.getB()), 0, 1)
                    ));

                    var pix = (x + y * WIDTH_);
                    var value = current.getValue(x, y);

                    app.pixels[pix] = app.color(value.a * 255, 0, value.b * 255);
                }
            }

            current = next;
        }

        private Float calculateA(Integer x, Integer y, Float a, Float b) {
            return a +
                    ((dA * laplaceA(x, y)) -
                            (a * b * b) +
                            (feed * (1 - a))) * dT;
        }

        private Float calculateB(Integer x, Integer y, Float a, Float b) {
            return b +
                    ((dB * laplaceB(x, y)) +
                            (a * b * b) -
                            ((kill + feed) * b)) * dT;
        }

        private Float laplaceA(Integer x, Integer y) {
            var sumA = 0f;

            int minusX = (x - 1) < 0 ? WIDTH_ - 1 : x - 1;
            int minusY = (y - 1) < 0 ? HEIGHT_ - 1 : y - 1;

            int plusX = (x + 1) == WIDTH_ ? 0 : x + 1;
            int plusY = (y + 1) == HEIGHT_ ? 0 : y + 1;

            sumA += current.getValue(x, y).getA() * -1f;
            sumA += current.getValue(minusX, y).getA() * .2f;
            sumA += current.getValue(plusX, y).getA() * .2f;
            sumA += current.getValue(x, plusY).getA() * .2f;
            sumA += current.getValue(x, minusY).getA() * .2f;
            sumA += current.getValue(minusX, minusY).getA() * .05f;
            sumA += current.getValue(plusX, minusY).getA() * .05f;
            sumA += current.getValue(plusX, plusY).getA() * .05f;
            sumA += current.getValue(minusX, plusY).getA() * .05f;

            return sumA;
        }

        private Float laplaceB(Integer x, Integer y) {
            var sumB = 0f;

            int minusX = (x - 1) < 0 ? WIDTH_ - 1 : x - 1;
            int minusY = (y - 1) < 0 ? HEIGHT_ - 1 : y - 1;

            int plusX = (x + 1) == WIDTH_ ? 0 : x + 1;
            int plusY = (y + 1) == HEIGHT_ ? 0 : y + 1;

            sumB += current.getValue(x, y).getB() * -1f;
            sumB += current.getValue(minusX, y).getB() * .2f;
            sumB += current.getValue(plusX, y).getB() * .2f;
            sumB += current.getValue(x, minusY).getB() * .2f;
            sumB += current.getValue(x, minusY).getB() * .2f;
            sumB += current.getValue(minusX, minusY).getB() * .05f;
            sumB += current.getValue(plusX, minusY).getB() * .05f;
            sumB += current.getValue(plusX, plusY).getB() * .05f;
            sumB += current.getValue(minusX, plusY).getB() * .05f;

            return sumB;
        }
    }

    @Data
    @AllArgsConstructor
    public static class DiffusionData {
        private Float a;
        private Float b;
    }

    public static class DiffusionMatrix {

        private DiffusionData[][] matrix;

        public DiffusionMatrix(Integer width, Integer height) {

            matrix = new DiffusionData[width][height];

            for (var x = 0; x < width; x++) {
                matrix[x] = new DiffusionData[height];

                for (var y = 0; y < height; y++) {
                    matrix[x][y] = new DiffusionData(1f, 0f);
                }
            }
        }

        public DiffusionData getValue(Integer x, Integer y) {
            return matrix[x][y];
        }

        public void setValue(Integer x, Integer y, DiffusionData value) {
            matrix[x][y] = value;
        }

    }
}
