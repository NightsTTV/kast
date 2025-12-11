public class MyArrayList<AnyType> {
	
	private Object[] data;
	private int size;

	public MyArrayList() {
		this.size = 0;
		this.data = new Object[10];
	}

	public int size() {
		return this.size;
	}

	public void add(AnyType element) {

		data[size] = element;
		size++;

	}

	public AnyType get(int index) {
		return (AnyType) data[index];
	}

	public static void main(String[] args) {
		MyArrayList<String> names = new MyArrayList<String>();

		System.out.println(" Size of names after build: " + names.size());
		names.add("Rick");
		names.add("Morty");
		names.add("Summer");
		names.add("Jerry");

		for (int i=1; i<=9; i++) {
			names.add("Mr. Meeseeks #" + i);
		}
	}
}
