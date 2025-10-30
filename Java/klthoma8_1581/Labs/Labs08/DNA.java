public class DNA {
    private char[] sequence;
 
    public DNA(char[] sequence) {
        this.sequence = sequence.clone();
    }
 
    public char[] getSequence() {
        return sequence.clone();
    }
 
    public DNA swap(DNA other, int swapPoint) {
        if (swapPoint < 0 || swapPoint >= this.sequence.length || this.sequence.length != other.sequence.length) {
            throw new IllegalArgumentException("Invalid swap point or sequence length mismatch.");
        }
 
        char[] newSequence = new char[this.sequence.length];
 
        System.arraycopy(this.sequence, 0, newSequence, 0, swapPoint);

        System.arraycopy(other.sequence, swapPoint, newSequence, swapPoint, this.sequence.length - swapPoint);
 
        return new DNA(newSequence);
    }
 
    public boolean equals(DNA other) {
        if (other == null || this.sequence.length != other.sequence.length) {
            return false;
        }
 
        for (int i = 0; i < this.sequence.length; i++) {
            if (this.sequence[i] != other.sequence[i]) {
                return false;
            }
        }
        return true;
    }
 
    public String toString() {
        return new String(sequence);
    }
}