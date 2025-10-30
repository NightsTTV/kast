public class RGBColorTester {
    public static void main(String[] args) {
        RGBColor x = new RGBColor(99, 16, 0);
        RGBColor y = new RGBColor(0, 0, 0);
        
        System.out.println("x.equals(y): " + x.equals(y)); 
        System.out.println("x.toString(): " + x.toString()); 
        System.out.println("x.toHex(): " + x.toHex()); 
    }
}
