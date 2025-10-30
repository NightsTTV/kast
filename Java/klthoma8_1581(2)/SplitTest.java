import java.util.scanner;

public class SplitTest {
 public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

  int name = scanner.nextInput();
  for (String arg : args) {
   name = arg;
   System.out.println(name);
  }
 }
}