package ru.unn.agile.CalculateVolume.Model;

public class Ellipsoid {
    private final double radiusA, radiusB, radiusC;
    private static final double NUMERATOR_COEFFICIENT_ELLIPSOID = 4;
    private static final double DENOMINATOR_COEFFICIENT_ELLIPSOID = 3;

    public Ellipsoid(final double radiusA, final double radiusB, final double radiusC) {
        this.radiusA = radiusA;
        this.radiusB = radiusB;
        this.radiusC = radiusC;
    }

    public double calculateVolume() {
        return Math.PI * radiusA * radiusB * radiusC * NUMERATOR_COEFFICIENT_ELLIPSOID
                / DENOMINATOR_COEFFICIENT_ELLIPSOID;
    }
}
