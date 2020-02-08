package com.cardboardcode.util;

import hype.H;
import processing.pdf.PGraphicsPDF;
import processing.core.PApplet;
import processing.core.PGraphics;

public class IOUtils {
    PApplet app;

    public IOUtils(PApplet app) {
        this.app = app;
    }

    public void saveVector(String fileName) {
        PGraphics tmp = this.app.beginRaw(PGraphicsPDF.PDF, "outputs/" + fileName + ".pdf");
        H.stage().paintAll(tmp, true, 1);

        this.app.endRaw();
    }

    public void saveHIRes(String filename, Integer scaleFactor) {
        PGraphics hires = this.app.createGraphics(
                this.app.width * scaleFactor,
                this.app.height * scaleFactor,
                this.app.P3D
        );

        this.app.beginRecord(hires);
        hires.scale(scaleFactor);
        H.stage().paintAll(hires, true, 1);

        hires.save("outputs/" + filename + ".png");
        this.app.endRecord();
    }
}
