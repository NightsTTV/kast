public class StringUtilTest{

	public static void main(String[] args){
		String toString;

		toString = StringUtil.toString(1.0);
		System.out.println(toString);
		toString = StringUtil.toString(1.0f);
		System.out.println(toString);
		toString = StringUtil.toString(1);
		System.out.println(toString);
		
		toString = StringUtil.toString(1L);
		System.out.println(toString);

		toString = StringUtil.toString('1');
		System.out.println(toString);
		toString = StringUtil.toString(true);
		System.out.println(toString);
	}
}