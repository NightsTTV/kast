public class RGBColor {
    private int red;
    private int green;
    private int blue;

    public RGBColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

     public boolean equals(RGBColor other) {
        boolean isSame = false; 
        if (this.red == other.red && this.green == other.green && this.blue == other.blue) {
            isSame = true;
        }
        return isSame;
    }

    public String toString() {
        return String.format("rgb(%d,%d,%d)", red, green, blue);
    }

    public String toHex() {
        return String.format("#%02x%02x%02x", red, green, blue);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
