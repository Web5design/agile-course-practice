package ru.unn.agile.CalculateVolume.Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConeTest {
    private static final double DELTA = 1e-10;

    @Test
    public void canInitializationHeightAndRadiusWithValues() {
        Cone cone = new Cone(1, 1.0);
        assertNotNull(cone);
    }

    @Test
    public void canInitializationHeightAndRadiusWithObtainedIntegerValues() {
        Cone cone = new Cone(1, 1);
        assertNotNull(cone);
    }

    @Test
    public void canInitializationHeightAndRadiusWithObtainedRealValues() {
        Cone cone = new Cone(1.0, 1.0);
        assertNotNull(cone);
    }

    @Test
    public void isCorrectCalculationConeVolumeWithIntegerValues() {
        Cone cone = new Cone(1, 1);
        assertEquals(Math.PI / 3, cone.calculateVolume(), DELTA);
    }

    @Test
    public void isCorrectCalculationConeVolumeWithRealValues() {
        Cone cone = new Cone(1.0, 1.0);
        assertEquals(Math.PI / 3, cone.calculateVolume(), DELTA);
    }

    @Test
    public void isCorrectCalculationConeVolumeWithZeroValues() {
        Cone cone = new Cone(0, 0);
        assertEquals(0, cone.calculateVolume(), DELTA);
    }
}
