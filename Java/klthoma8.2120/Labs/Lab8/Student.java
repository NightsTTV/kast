public class Student implements Comparable<Student> {


    private int studentID;
    private String sex;
    private String ethnicGroup;
    private String lastName;
    private String firstName;
    private String middleInitial;
    private String program;
    private String academicPlan;
    private String subPlan;
    private int    strtLevel;
    private double total;
    private double gpa;
    private String fafsa;
    private double takePrgrs;
    private double financialNeed;

    public Student() {
        this("","",0);
    }

    public Student(String firstName, String lastName, int studentID) {
        this.studentID = studentID;
        sex = "";
        ethnicGroup = "";
        this.lastName = lastName;
        this.firstName = firstName;
        middleInitial = "";
        program = "";
        academicPlan = "";
        subPlan = "";
        strtLevel = 0;
        total = 0.0;
        gpa = 0.0;
        fafsa = "";
        takePrgrs = 0.0;
        financialNeed = 0.0;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return this.sex;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public String getEthnicGroup() {
        return this.ethnicGroup;
    }

    public void setFirstName(String n) {
        this.firstName = n;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String n) {
        this.lastName = n;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setMiddleInitial(String n) {
        this.middleInitial = n;
    }

    public String getMiddleInitial() {
        return this.middleInitial;
    }

    public void  setProgram(String program) {
        this.program = program;
    }

    public String  getProgram() {
        return this.program;
    }
    
    public void setAcademicPlan(String academicPlan) {
        this.academicPlan = academicPlan;
    }

    public String getAcademicPlan() {
        return this.academicPlan;
    }

    public void setSubPlan(String subPlan) {
        this.subPlan = subPlan;
    }

    public String getSubPlan() {
        return this.subPlan;
    }

    public void setStrtLevel(int strtLevel) {
        this.strtLevel = strtLevel;
    }

    public int getStrtLevel() {
        return this.strtLevel;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return this.total;
    }

    public void setGPA(double gpa) {
        this.gpa = gpa;
    }

    public double getGPA() {
        return this.gpa;
    }

    public void setFAFSA(String fafsa) {
        this.fafsa = fafsa;
    }

    public String getFAFSA() {
        return this.fafsa;
    }

    public void setTakePrgrs(double takePrgrs) {
        this.takePrgrs = takePrgrs;
    }

    public double getTakePrgrs() {
        return this.takePrgrs;
    }

    public void setFinancialNeed(double financialNeed) {
        this.financialNeed = financialNeed;
    } 

    public double getFinancialNeed() {
        return this.financialNeed;
    } 

    @Override
    public boolean equals(Object other) {
        if (other instanceof Student) {
            Student otherAsStudent = (Student)other;
            return studentID == otherAsStudent.getStudentID() &&
                sex.equals(otherAsStudent.getSex()) &&
                ethnicGroup.equals(otherAsStudent.getEthnicGroup()) &&
                lastName.equals(otherAsStudent.getLastName()) &&
                firstName.equals(otherAsStudent.getFirstName()) &&
                middleInitial.equals(otherAsStudent.getMiddleInitial()) &&
                program.equals(otherAsStudent.getProgram()) &&
                academicPlan.equals(otherAsStudent.getAcademicPlan()) &&
                subPlan.equals(otherAsStudent.getSubPlan()) &&
                strtLevel == otherAsStudent.getStrtLevel() &&
                Math.abs(total - otherAsStudent.getTotal()) < 0.01 &&
                Math.abs(gpa - otherAsStudent.getGPA()) < 0.01 &&
                fafsa.equals(otherAsStudent.getFAFSA()) &&
                Math.abs(takePrgrs - otherAsStudent.getTakePrgrs()) < 0.01 &&
                Math.abs(financialNeed - otherAsStudent.getFinancialNeed()) < 0.01;
        }
        return false;
    }

    public String toString() {
        String returnVal = "";
        returnVal += studentID + " : " + firstName + " " + middleInitial + " " + lastName + " : " + gpa + " : " + financialNeed + " FAFSA: " + fafsa;
        return returnVal;
    }
        
    @Override
        public int compareTo(Student other) {
            // alphabetical order by first name
            return this.firstName.compareTo(other.firstName);
    }
}
