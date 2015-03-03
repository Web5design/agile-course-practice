package ru.unn.agile.CalculateVolume.Model;

public class Cone {

    private final double radius, height;
    private static final int COEFFICIENT_CONE = 3;

    public Cone(final double radius, final double height) {
        this.radius = radius;
        this.height = height;
    }

    public double calculateVolume() {
        return Math.PI * radius * radius * height / COEFFICIENT_CONE;
    }
}

