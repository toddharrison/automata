package com.eharrison.automata.ui;

import com.eharrison.automata.game.Signal;
import com.eharrison.automata.game.Vector2D;

import java.awt.*;

public class ScanRing implements RenderElement {
    private final int distance;
    private final Vector2D center;
    private final Signal signal;
    private final int delay;
    private final int duration;
    private double radius;
    private int elapsedTime;

    public ScanRing(int distance, Signal signal, int delay, int duration) {
        this.distance = distance;
        this.center = signal.location();
        this.signal = signal;
        this.delay = delay;
        this.duration = duration;
        this.radius = 0;
        this.elapsedTime = 0;
    }

    @Override
    public void expand(int deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime > delay) {
            radius = distance * ((elapsedTime - delay) / (double) duration);
        }
    }

    public Vector2D center() {
        return center;
    }

    public double radius() {
        return radius;
    }

    public double getStartDegrees() {
        return -Math.toDegrees(signal.minAngle());
    }

    public double getArcDegrees() {
        return -Math.toDegrees(Vector2D.arcLength(signal.minAngle(), signal.maxAngle()));
    }

    public float getAlpha() {
        float alpha = 1.0f - ((elapsedTime - delay) / (float) duration);
        return Math.max(0.0f, Math.min(1.0f, alpha));
    }

    public Color getColor() {
        float hue = (float) (signal.frequency() / 100.0);
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }
}
