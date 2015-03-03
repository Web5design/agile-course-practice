package ru.unn.agile.CalculateVolume.Model;

import static org.junit.Assert.*;
import org.junit.Test;

public class CylinderTest {
    private static final double DELTA = 1e-10;

    @Test
    public void canInitializationHeightAndRadiusWithValues() {
        Cylinder cylinder = new Cylinder(1, 1.0);
        assertNotNull(cylinder);
    }

    @Test
    public void canInitializationHeightAndRadiusWithObtainedIntegerValues() {
        Cylinder cylinder = new Cylinder(1, 1);
        assertNotNull(cylinder);
    }

    @Test
    public void canInitializationHeightAndRadiusWithObtainedRealValues() {
        Cylinder cylinder = new Cylinder(1.0, 1.0);
        assertNotNull(cylinder);
    }

    @Test
    public void isCorrectCalculationCylinderVolumeWithIntegerValues() {
        Cylinder cylinder = new Cylinder(1, 1);
        assertEquals(Math.PI, cylinder.calculateVolume(), DELTA);
    }

    @Test
    public void isCorrectCalculationCylinderVolumeWithRealValues() {
        Cylinder cylinder = new Cylinder(1.0, 1.0);
        assertEquals(Math.PI, cylinder.calculateVolume(), DELTA);
    }

    @Test
    public void isCorrectCalculationCylinderVolumeWithZeroValues() {
        Cylinder cylinder = new Cylinder(0, 0);
        assertEquals(0, cylinder.calculateVolume(), DELTA);
    }
}
