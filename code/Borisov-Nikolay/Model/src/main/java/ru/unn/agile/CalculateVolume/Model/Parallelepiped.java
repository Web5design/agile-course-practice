package ru.unn.agile.CalculateVolume.Model;

public class Parallelepiped {
    private final double length, width, height;

    public Parallelepiped(final double length, final double width, final double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public double calculateVolume() {
        return length * width * height;
    }
}
