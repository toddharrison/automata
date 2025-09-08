package com.eharrison.automata.game;

import lombok.val;

public record Vector2D(double x, double y) {
    public static final double TAU = 2 * Math.PI;

    public static double normalizeAngle(double angle) {
        return ((angle % TAU) + TAU) % TAU;
    }

    public static boolean isInWedge(double angle, double minAngle, double maxAngle) {
        val normalized = Vector2D.normalizeAngle(angle);
        val normalizedMin = Vector2D.normalizeAngle(minAngle);
        val normalizedMax = Vector2D.normalizeAngle(maxAngle);

        if (normalizedMin < normalizedMax) {
            return normalized >= normalizedMin && normalized <= normalizedMax;
        } else {
            // The wedge wraps around the 0 angle
            return normalized >= normalizedMin || normalized <= normalizedMax;
        }
    }

    public static double arcLength(double minAngle, double maxAngle) {
        val normalizedMin = Vector2D.normalizeAngle(minAngle);
        val normalizedMax = Vector2D.normalizeAngle(maxAngle);

        if (normalizedMin < normalizedMax) {
            return normalizedMax - normalizedMin;
        } else {
            // The wedge wraps around the 0 angle
            return (TAU - normalizedMin) + normalizedMax;
        }
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double angleTo(final Vector2D other) {
        val angle = Math.atan2(other.y - y, other.x - x);

        return normalizeAngle(angle);
    }

    public double distanceTo(final Vector2D other) {
        val dx = this.x - other.x;
        val dy = this.y - other.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public Vector2D translateTo(final double angle, final double distance) {
        return new Vector2D(this.x + distance * Math.cos(angle), this.y + distance * Math.sin(angle));
    }

    public Vector2D vectorTo(final Vector2D other) {
        return other.subtract(this);
    }

    public Vector2D add(final Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(final Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D normalize() {
        val length = Math.sqrt(x * x + y * y);

        return new Vector2D(x / length, y / length);
    }

    public Vector2D clamp(final double xMin, final double xMax, final double yMin, final double yMax) {
        return new Vector2D(Math.clamp(x, xMin, xMax), Math.clamp(y, yMin, yMax));
    }

    public double dot(final Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }
}
