package model;

public class Dimension {
    private int length; 
    private int width;  
    private int height;

    public Dimension(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVolume() { // Use in fees calculation
        return length * width * height;
    }

    @Override
    public String toString() {
        return length + "x" + width + "x" + height;
    }
}

