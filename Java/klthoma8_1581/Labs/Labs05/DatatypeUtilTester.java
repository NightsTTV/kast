public class DatatypeUtilTester{

	public static void main(String[] args){
		String type;
		
		type = DatatypeUtil.getType(1.0);
		System.out.println(type);

		type = DatatypeUtil.getType(1.0f);
		System.out.println(type);
		
		type = DatatypeUtil.getType(1);
		System.out.println(type);

		type = DatatypeUtil.getType(1L);
		System.out.println(type);

		type = DatatypeUtil.getType('1');
		System.out.println(type);

		type = DatatypeUtil.getType(true);
		System.out.println(type);

		type = DatatypeUtil.getType("Hello");
		System.out.println(type);
	}
}