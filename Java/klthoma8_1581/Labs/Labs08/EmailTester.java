public class EmailTester {
    public static void main(String[] args) {
        Email firstEmail = new Email("Email 1", "oessa@uno.edu", "klthoma8@uno.edu", "Test");
		Email secondEmail = new Email("Email 1", "oessa@uno.edu", "klthoma8@uno.edu", "Test");
        Email thirdEmail = new Email("Email 3", "john@uno.edu", "klthoma8@uno.edu", "Test3");


        String subject = firstEmail.getSubject();
        System.out.printf("The subject is: %s\n", subject);

        String body = firstEmail.getBody();
        System.out.printf("The body is: %s\n", body);

           	System.out.println(firstEmail.toString());
           	System.out.println(firstEmail);
        	System.out.println(secondEmail);
       		System.out.println(firstEmail.equals(secondEmail));
        	System.out.println(thirdEmail.equals(firstEmail));

        String body2 = "Thank you.";
        firstEmail.setBody(body2);
        System.out.printf("Updated body: %s\n", firstEmail.getBody());

        String text = ("The arabic equivalent is shukran.");
        firstEmail.addToBody(text);
        System.out.println(firstEmail.getBody());
    }
}
