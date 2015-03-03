package ru.unn.agile.CalculateVolume.Model;

import static org.junit.Assert.*;
import org.junit.Test;

public class EllipsoidTest {
    private static final double DELTA = 0.0001;

    @Test
    public void canInitializationRadiusABCWithValues() {
        Ellipsoid ellipsoid = new Ellipsoid(1, 1, 1.0);
        assertNotNull(ellipsoid);
    }

    @Test
    public void canInitializationRadiusABCWithObtainedIntegerValues() {
        Ellipsoid ellipsoid = new Ellipsoid(1, 1, 1);
        assertNotNull(ellipsoid);
    }

    @Test
    public void canInitializationRadiusABCWithObtainedRealValues() {
        Ellipsoid ellipsoid = new Ellipsoid(1.0, 1.0, 1.0);
        assertNotNull(ellipsoid);
    }

    @Test
    public void isCorrectCalculationEllipsoidVolumeWithIntegerValues() {
        Ellipsoid ellipsoid = new Ellipsoid(1, 1, 1);
        assertEquals(Math.PI * 4 / 3, ellipsoid.calculateVolume(), DELTA);
    }

    @Test
    public void isCorrectCalculationEllipsoidVolumeWithRealValues() {
        Ellipsoid ellipsoid = new Ellipsoid(1.0, 1.0, 1.0);
        assertEquals(Math.PI * 4 / 3, ellipsoid.calculateVolume(), DELTA);
    }

    @Test
    public void isCorrectCalculationEllipsoidVolumeWithZeroValues() {
        Ellipsoid ellipsoid = new Ellipsoid(0, 0, 0);
        assertEquals(0, ellipsoid.calculateVolume(), DELTA);
    }
}
