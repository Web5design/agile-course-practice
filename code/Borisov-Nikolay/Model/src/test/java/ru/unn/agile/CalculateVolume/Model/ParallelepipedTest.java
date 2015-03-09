package ru.unn.agile.CalculateVolume.Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParallelepipedTest {
    private static final double DELTA = 1e-10;

    @Test
    public void canInitializationLengthWidthHeightWithValues() {
        Parallelepiped parallelepiped = new Parallelepiped(1, 1, 1.0);
        assertNotNull(parallelepiped);
    }

    @Test
    public void canInitializationRadiusAndHeightWithObtainedIntegerValues() {
        Parallelepiped parallelepiped = new Parallelepiped(1, 1, 1);
        assertNotNull(parallelepiped);
    }

    @Test
    public void canInitializationRadiusAndHeightWithObtainedRealValues() {
        Parallelepiped parallelepiped = new Parallelepiped(1.0, 1.0, 1.0);
        assertNotNull(parallelepiped);
    }

    @Test
    public void isCorrectCalculationParallelepipedVolumeWithIntegerValues() {
        Parallelepiped parallelepiped = new Parallelepiped(1, 1, 1);
        assertEquals(1, parallelepiped.calculateVolume(), DELTA);
    }

    @Test
    public void isCorrectCalculationParallelepipedVolumeWithRealValues() {
        Parallelepiped parallelepiped = new Parallelepiped(1.0, 1.0, 1.0);
        assertEquals(1, parallelepiped.calculateVolume(), DELTA);
    }

    @Test
    public void isCorrectCalculationParallelepipedVolumeWithZeroValues() {
        Parallelepiped parallelepiped = new Parallelepiped(0, 0, 0);
        assertEquals(0, parallelepiped.calculateVolume(), DELTA);
    }
}
