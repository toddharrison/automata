package com.eharrison.automata.ui;

import com.eharrison.automata.game.Signal;
import com.eharrison.automata.game.Vector2D;

import java.awt.*;

public class PingRing implements RenderElement {
    private final int distance;
    private final Vector2D center;
    private final Signal signal;
    private final int duration;
    private double radius;
    private int elapsedTime;

    public PingRing(int distance, Signal signal, int duration) {
        this.distance = distance;
        this.center = signal.location();
        this.signal = signal;
        this.duration = duration;
        this.radius = 0;
        this.elapsedTime = 0;
    }

    @Override
    public void expand(int deltaTime) {
        elapsedTime += deltaTime;
        radius = distance * (elapsedTime / (double) duration);
    }

    public Vector2D center() {
        return center;
    }

    public double radius() {
        return radius;
    }

    public float getAlpha() {
        float alpha = 1.0f - (elapsedTime / (float) duration);
        return Math.max(0.0f, Math.min(1.0f, alpha));
    }

    public Color getColor() {
        float hue = (float) (signal.frequency() / 100.0);
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }
}
