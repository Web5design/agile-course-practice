package ru.unn.agile.CalculateVolume.Model;

public class Cylinder {

    private final double radius, height;

    public Cylinder(final double radius, final double height) {
        this.radius = radius;
        this.height = height;
    }

    public double calculateVolume() {
        return Math.PI * radius * radius * height;
    }
}
