public class Email {
    private String subject;
    private String from;
    private String to;
    private String body;

    public Email(String subject, String from, String to, String body) {
        this.subject = subject;
        this.from = from;
        this.to = to;
        this.body = body;
    }

    public Email(String subject, String from, String to) {
        this.subject = subject;
        this.from = from;
        this.to = to;
        this.body = "";
    }

    public String getSubject() {
        return this.subject;
    }

    public String getTo() {
        return this.to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addToBody(String text) {
        this.body += text;
    }

    public boolean equals(Email other) {
        boolean isSame = false;
        if (this.subject.equals(other.subject) &&
            this.body.equals(other.body) &&
            this.to.equals(other.to) &&
            this.from.equals(other.from)) {
            isSame = true;
        }
        return isSame;
    }

    @Override
    public String toString() {
        return String.format("From: %s; To: %s; Subject: %s; Body: %s",
                             this.to, this.from, this.subject, this.body);
    }
}
