public class DNATester {
    public static void main(String[] args) {
        char[] first = {'a', 't', 'c', 'g'};
        DNA d1 = new DNA(first);
 
        char[] second = {'t', 'c', 'a', 'a'};
        DNA d2 = new DNA(second);

        System.out.println("d1: " + d1.toString());
 
        DNA nextDNA = d1.swap(d2, 2);
 
        System.out.println("Swapped DNA: " + nextDNA.toString());
    }
}