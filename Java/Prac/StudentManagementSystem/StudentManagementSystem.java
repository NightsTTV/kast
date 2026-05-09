// 4/4/25
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;

public class StudentManagementSystem {

    private HashMap<Integer, Course> courses; // Course Lookup (search)
    private HashMap<Integer, Teacher> teachers; // Teacher Lookup (search)
    private Scanner input;
    private int loggedInTeacherId = -1; // Tracks the logged-in teacher's ID
    private int loggedInStudentId = -1; // Tracks the logged-in student's ID

    public StudentManagementSystem() {
        courses = new HashMap<>();
        teachers = new HashMap<>();
        input = new Scanner(System.in);

        initializeTeachers();

        initializeStudent();

        initializeCourses();

        // Initialize CSV Files with headers if they do not already exist
        File studentFile = new File("students.csv");
        if (!studentFile.exists()) {
            CSVFileHandler.createCSVFile("students.csv", new String[]{"student_id", "name", "email", "password"});
        }

        File teacherFile = new File("teachers.csv");
        if (!teacherFile.exists()) {
            CSVFileHandler.createCSVFile("teachers.csv", new String[]{"teacher_id", "name", "email", "password"});
        } else {
            List<Teacher> teacherList = CSVFileHandler.readTeachersFromCSV("teachers.csv");
            for (Teacher teacher : teacherList) {
                teachers.put(teacher.getTeacherId(), teacher);
            }
        }

        File adminsFile = new File("admins.csv");
        if (!adminsFile.exists()) {
            CSVFileHandler.createCSVFile("admins.csv", new String[]{"admin_id", "username", "password"});
            String[] adminRecord = {"1", "admin", "7911"};
            CSVFileHandler.writeToCSV("admins.csv", adminRecord, true);
        }

        File file = new File("courses.csv");
        if (!file.exists()) {
            CSVFileHandler.createCSVFile("courses.csv", new String[]{"course_id", "course_name", "instructor", "credits", "schedule"});
        } else {
            List<Course> courseList = CSVFileHandler.readCoursesFromCSV("courses.csv");
            for (Course course : courseList) {
                courses.put(course.getCourseId(), course);
            }
        }

        File applicationsFile = new File("applications.csv");
        if (!applicationsFile.exists()) {
            CSVFileHandler.createCSVFile("applications.csv", new String[]{"student_id", "course_id"});
        }

        File enrollmentsFile = new File("enrollments.csv");
        if (!enrollmentsFile.exists()) {
            CSVFileHandler.createCSVFile("enrollments.csv", new String[]{"student_id", "course_id"});
        }
    }

    // Method to add Course
    public void addCourse() {
        boolean isAdmin = loggedInTeacherId == -1; // Assume admin if no teacher logged in
        System.out.println("Enter Course ID:");
        int courseId = getValidIntInput();
        if (courses.containsKey(courseId)) {
            System.out.println("Course ID " + courseId + " already exists.");
            return;
        }
        System.out.println("Enter Course Name:");
        String courseName = input.nextLine();
    
        String instructor;
        if (isAdmin) {
            System.out.println("Enter Instructor Name:");
            instructor = input.nextLine();
        } else {
            Teacher loggedInTeacher = teachers.get(loggedInTeacherId);
            if (loggedInTeacher == null) {
                System.out.println("Error: Logged-in teacher not found.");
                return;
            }
            instructor = loggedInTeacher.getName();
            System.out.println("Instructor: " + instructor);
        }
    
        System.out.println("Enter Credits:");
        double credits = getValidDoubleInput();
    
        System.out.println("Enter Schedule (e.g., 'MWF 9-10' or 'TTh 13:30-15:00'):");
        String scheduleInput = input.nextLine();
        TimeSlot newSchedule;
        try {
            newSchedule = TimeSlot.parse(scheduleInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid schedule format. Use 'MWF 9-10' or similar.");
            return;
        }
    
        // Check for conflicts with existing courses for this instructor
        for (Course existingCourse : courses.values()) {
            if (existingCourse.getInstructor().equals(instructor) && 
                existingCourse.getSchedule().overlaps(newSchedule)) {
                System.out.println("Schedule conflict detected with " + existingCourse.getCourseName() + 
                                   " (" + existingCourse.getSchedule() + ").");
                return;
            }
        }
    
        Course course = new Course(courseId, courseName, instructor, credits, scheduleInput);
        courses.put(courseId, course);
        CSVFileHandler.addCourseToCSV("courses.csv", course);
        System.out.println("Course added successfully!");
    }

    // Method to Display all Courses
    public void displayCourses() {
        List<Course> courseList = CSVFileHandler.readCoursesFromCSV("courses.csv");
        if (courseList.isEmpty()) {
            System.out.println("There are no courses to display.");
        } else {
            Collections.sort(courseList); // Sort by ID
            for (Course course : courseList) {
                System.out.println(course);
            }
        }
    }

    // Method to remove a course (by ID)
    public void removeCourse() {
        System.out.println("Enter Course ID to remove:");
        int courseId = getValidIntInput();
        if (courses.remove(courseId) != null) {
            CSVFileHandler.deleteCourseFromCSV("courses.csv", courseId);
            System.out.println("Course removed successfully!");
        } else {
            System.out.println("Course ID " + courseId + " not found.");
        }
    }

    // Method to search courses by ID or name
    public void searchCourses() {
        System.out.println("Search by: 1. Course ID  2. Course Name");
        int choice = getValidIntInput();

        if (choice == 1) {
            System.out.println("Enter Course ID:");
            int courseId = getValidIntInput();
            Course course = courses.get(courseId);
            if (course != null) {
                System.out.println("Found: " + course);
            } else {
                System.out.println("Course ID " + courseId + " not found.");
            }
        } else if (choice == 2) {
            System.out.println("Enter Course Name (keyword search):");
            String searchName = input.nextLine().toLowerCase();
            List<Course> matches = new ArrayList<>();
            for (Course course : courses.values()) {
                if (course.getCourseName().toLowerCase().contains(searchName)) {
                    matches.add(course);
                }
            }
            if (matches.isEmpty()) {
                System.out.println("No courses found with name containing '" + searchName + "'.");
            } else {
                System.out.println("Matching courses:");
                for (Course course : matches) {
                    System.out.println(course);
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Method to display courses sorted by a chosen field
    public void displaySortedCourses() {
        if (courses.isEmpty()) {
            System.out.println("There are no courses to display.");
            return;
        }

        System.out.println("Sort by: 1. Course ID  2. Course Name  3. Credits");
        int choice = getValidIntInput();
        List<Course> courseList = new ArrayList<>(courses.values());

        switch (choice) {
            case 1:
                Collections.sort(courseList); // Uses Comparable (courseId)
                break;
            case 2:
                courseList.sort(Comparator.comparing(Course::getCourseName));
                break;
            case 3:
                courseList.sort(Comparator.comparingDouble(Course::getCredits));
                break;
            default:
                System.out.println("Invalid choice. Displaying unsorted.");
                break;
        }

        System.out.println("Sorted Courses (Low to High):");
        for (Course course : courseList) {
            System.out.println(course);
        }
    }

    // Admin Permissions
    public void viewPendingApplications() {
        List<String[]> applications = CSVFileHandler.readApplicationsFromCSV("applications.csv");
        if (applications.isEmpty()) {
            System.out.println("No pending applications.");
            return;
        }

        System.out.println("\nPending Applications:");
        for (String[] app : applications) {
            int studentId = Integer.parseInt(app[0]);
            int courseId = Integer.parseInt(app[1]);
            System.out.println("Student ID " + studentId + ", Course ID: " + courseId);

            System.out.print("Approve (1) or Reject (2)? ");
            int decision = getValidIntInput();

            if (decision == 1) {
                CSVFileHandler.addEnrollmentToCSV("enrollments.csv", studentId, courseId);
                CSVFileHandler.removeApplicationFromCSV("applications.csv", studentId, courseId);
                System.out.println("Application approved and student enrolled.");
            } else if (decision == 2) {
                CSVFileHandler.removeApplicationFromCSV("applications.csv", studentId, courseId);
                System.out.println("Application rejected."); // Fixed message
            } else {
                System.out.println("Invalid choice. Application remains pending.");
            }
        }
    }

    public void viewAllStudents() {
        List<Student> students = CSVFileHandler.readStudentFromCSV("students.csv");
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("Sort by: 1. First Name  2. Last Name");
        int choice = getValidIntInput();
        if (choice == 1) {
            students.sort(Comparator.comparing(Student::getFirstName));
        } else if (choice == 2) {
            students.sort(Comparator.comparing(Student::getLastName));
        } else {
            System.out.println("Invalid choice. Displaying unsorted.");
        }
        System.out.println("\nAll Students:");
        for (Student student : students) {
            System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getName() + ", Email: " + student.getEmail());
        }
    }

    public void viewStudentSchedule() {
        System.out.println("Enter Student ID:");
        int studentId = getValidIntInput();
        List<String[]> enrollments = CSVFileHandler.readFromCSV("enrollments.csv");
        List<Course> schedule = new ArrayList<>();
        for (String[] enrollment : enrollments) {
            if (enrollment.length < 2) continue; // Skip malformed lines
            if (Integer.parseInt(enrollment[0]) == studentId) {
                int courseId = Integer.parseInt(enrollment[1]);
                Course course = courses.get(courseId);
                if (course != null) schedule.add(course);
            }
        }
        if (schedule.isEmpty()) {
            System.out.println("No schedule found for Student ID " + studentId + ".");
        } else {
            System.out.println("\nSchedule for Student ID " + studentId + ":");
            for (Course course : schedule) {
                System.out.println(course);
            }
        }
    }

    public void viewAllTeachers() {
        List<Teacher> teacherList = CSVFileHandler.readTeachersFromCSV("teachers.csv");
        if (teacherList.isEmpty()) {
            System.out.println("No teachers registered.");
            return;
        }
        System.out.println("Sort by: 1. First Name  2. Last Name");
        int choice = getValidIntInput();
        if (choice == 1) {
            teacherList.sort(Comparator.comparing(Teacher::getFirstName));
        } else if (choice == 2) {
            teacherList.sort(Comparator.comparing(Teacher::getLastName));
        } else {
            System.out.println("Invalid choice. Displaying unsorted.");
        }
        System.out.println("\nAll Teachers:");
        for (Teacher teacher : teacherList) {
            System.out.println("ID: " + teacher.getTeacherId() + ", Name: " + teacher.getName() + ", Email: " + teacher.getEmail());
        }
    }

    public void viewTeacherSchedule() {
        System.out.println("Enter Teacher ID:");
        int teacherId = getValidIntInput();
        Teacher teacher = teachers.get(teacherId);
        if (teacher == null) {
            System.out.println("Teacher ID " + teacherId + " not found.");
            return;
        }
        List<Course> schedule = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.getInstructor().equals(teacher.getName())) {
                schedule.add(course);
            }
        }
        if (schedule.isEmpty()) {
            System.out.println("No schedule found for Teacher ID " + teacherId + ".");
        } else {
            System.out.println("\nSchedule for Teacher ID " + teacherId + " (" + teacher.getName() + "):");
            for (Course course : schedule) {
                System.out.println(course);
            }
        }
    }

    public void viewStudentsInTeacherClass() {
        System.out.println("Enter Teacher ID:");
        int teacherId = getValidIntInput();
        Teacher teacher = teachers.get(teacherId);
        if (teacher == null) {
            System.out.println("Teacher ID " + teacherId + " not found.");
            return;
        }
        List<Course> teacherCourses = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.getInstructor().equals(teacher.getName())) {
                teacherCourses.add(course);
            }
        }
        if (teacherCourses.isEmpty()) {
            System.out.println("No courses taught by Teacher ID " + teacherId + ".");
            return;
        }
        List<String[]> enrollments = CSVFileHandler.readFromCSV("enrollments.csv");
        List<Student> students = CSVFileHandler.readStudentFromCSV("students.csv");
        System.out.println("\nStudents in classes taught by " + teacher.getName() + ":");
        for (Course course : teacherCourses) {
            System.out.println("Course: " + course.getCourseName() + " (ID: " + course.getCourseId() + ")");
            boolean hasStudents = false;
            for (String[] enrollment : enrollments) {
                if (enrollment.length < 2) continue;
                if (Integer.parseInt(enrollment[1]) == course.getCourseId()) {
                    int studentId = Integer.parseInt(enrollment[0]);
                    Student student = students.stream().filter(s -> s.getStudentId() == studentId).findFirst().orElse(null);
                    if (student != null) {
                        System.out.println("  - ID: " + student.getStudentId() + ", Name: " + student.getName());
                        hasStudents = true;
                    }
                }
            }
            if (!hasStudents) System.out.println("  - No students enrolled.");
        }
    }

    // Student Permissions
    public void viewApplicationStatus() {
    if (loggedInStudentId == -1) {
        System.out.println("Error: No student logged in.");
        return;
    }
    List<String[]> applications = CSVFileHandler.readApplicationsFromCSV("applications.csv");
    List<String[]> enrollments = CSVFileHandler.readFromCSV("enrollments.csv");
    System.out.println("\nApplication Status for Student ID " + loggedInStudentId + ":");
    boolean hasPending = false;
    boolean hasApproved = false;

    // Check pending applications
    for (String[] app : applications) {
        if (app.length < 2) continue; // Skip malformed lines
        try {
            int studentId = Integer.parseInt(app[0]);
            if (studentId == loggedInStudentId) {
                int courseId = Integer.parseInt(app[1]);
                Course course = courses.get(courseId);
                System.out.println("Course ID: " + courseId + " (" + (course != null ? course.getCourseName() : "Unknown") + ") - Pending");
                hasPending = true;
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid data in applications.csv: " + String.join(",", app));
            continue;
        }
    }

    // Check approved enrollments
    for (String[] enrollment : enrollments) {
        if (enrollment.length < 2) continue; // Skip malformed lines
        try {
            int studentId = Integer.parseInt(enrollment[0]);
            if (studentId == loggedInStudentId) {
                int courseId = Integer.parseInt(enrollment[1]);
                Course course = courses.get(courseId);
                System.out.println("Course ID: " + courseId + " (" + (course != null ? course.getCourseName() : "Unknown") + ") - Approved");
                hasApproved = true;
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid data in enrollments.csv: " + String.join(",", enrollment));
            continue;
        }
    }

    if (!hasPending && !hasApproved) {
        System.out.println("No applications or enrollments found.");
    }
    }

    public void viewCurrentStudentSchedule() {
        if (loggedInStudentId == -1) {
            System.out.println("Error: No student logged in.");
            return;
        }
        List<String[]> enrollments = CSVFileHandler.readFromCSV("enrollments.csv");
        List<Course> schedule = new ArrayList<>();
        for (String[] enrollment : enrollments) {
            if (enrollment.length < 2) continue;
            if (Integer.parseInt(enrollment[0]) == loggedInStudentId) {
                int courseId = Integer.parseInt(enrollment[1]);
                Course course = courses.get(courseId);
                if (course != null) schedule.add(course);
            }
        }
        if (schedule.isEmpty()) {
        System.out.println("No current schedule for Student ID " + loggedInStudentId + ".");
    } else {
        System.out.println("\nCurrent Schedule for Student ID " + loggedInStudentId + ":");
        schedule.sort(Comparator.comparing(c -> c.getSchedule().getStartTime())); // Sort by start time
        for (Course course : schedule) {
            System.out.println(course + " (Days: " + course.getSchedule().getDays() + ")");
        }
    }
}

       public void applyForCourse() {
       if (loggedInStudentId == -1) {
           System.out.println("Error: No student logged in.");
           return;
       }
       System.out.println("Enter the Course ID to apply for:");
       int courseId = getValidIntInput();
       Course newCourse = courses.get(courseId);
       if (newCourse == null) {
           System.out.println("Course with ID " + courseId + " does not exist.");
           return;
       }
   
       List<String[]> enrollments = CSVFileHandler.readFromCSV("enrollments.csv");
       for (String[] enrollment : enrollments) {
           if (enrollment.length < 2) continue;
           if (Integer.parseInt(enrollment[0]) == loggedInStudentId) {
               int existingCourseId = Integer.parseInt(enrollment[1]);
               Course existingCourse = courses.get(existingCourseId);
               if (existingCourse != null && existingCourse.getSchedule().overlaps(newCourse.getSchedule())) {
                   System.out.println("Schedule conflict with " + existingCourse.getCourseName() + 
                                      " (" + existingCourse.getSchedule() + ").");
                   return;
               }
           }
       }
   
       List<String[]> applications = CSVFileHandler.readApplicationsFromCSV("applications.csv");
       boolean alreadyApplied = applications.stream()
           .anyMatch(app -> app.length >= 2 && Integer.parseInt(app[0]) == loggedInStudentId && Integer.parseInt(app[1]) == courseId);
       if (alreadyApplied) {
           System.out.println("You have already applied for this course.");
           return;
       }
   
       CSVFileHandler.addApplicationToCSV("applications.csv", loggedInStudentId, courseId);
       System.out.println("Application for Course ID " + courseId + " submitted successfully!");
    }

    // Teacher Permissions
    public void viewStudentsInClass() {
        if (loggedInTeacherId == -1) {
            System.out.println("Error: No teacher logged in.");
            return;
        }
        Teacher teacher = teachers.get(loggedInTeacherId);
        if (teacher == null) {
            System.out.println("Error: Logged-in teacher not found.");
            return;
        }
        List<Course> teacherCourses = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.getInstructor().equals(teacher.getName())) {
                teacherCourses.add(course);
            }
        }
        if (teacherCourses.isEmpty()) {
            System.out.println("No courses assigned to Teacher ID " + loggedInTeacherId + ".");
            return;
        }
        List<String[]> enrollments = CSVFileHandler.readFromCSV("enrollments.csv");
        List<Student> students = CSVFileHandler.readStudentFromCSV("students.csv");
        System.out.println("\nStudents in classes taught by " + teacher.getName() + ":");
        for (Course course : teacherCourses) {
            System.out.println("Course: " + course.getCourseName() + " (ID: " + course.getCourseId() + ")");
            boolean hasStudents = false;
            for (String[] enrollment : enrollments) {
                if (enrollment.length < 2) continue;
                if (Integer.parseInt(enrollment[1]) == course.getCourseId()) {
                    int studentId = Integer.parseInt(enrollment[0]);
                    Student student = students.stream().filter(s -> s.getStudentId() == studentId).findFirst().orElse(null);
                    if (student != null) {
                        System.out.println("  - ID: " + student.getStudentId() + ", Name: " + student.getName());
                        hasStudents = true;
                    }
                }
            }
            if (!hasStudents) System.out.println("  - No students enrolled.");
        }
    }

    public void viewCurrentTeacherSchedule() {
        if (loggedInTeacherId == -1) {
            System.out.println("Error: No teacher logged in.");
            return;
        }
        Teacher teacher = teachers.get(loggedInTeacherId);
        if (teacher == null) {
            System.out.println("Error: Logged-in teacher not found.");
            return;
        }
        List<Course> schedule = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.getInstructor().equals(teacher.getName())) {
                schedule.add(course);
            }
        }
        if (schedule.isEmpty()) {
        System.out.println("No current schedule for Teacher:  " + loggedInTeacherId + ".");
    } else {
        System.out.println("\nCurrent Schedule for Teacher:  " + loggedInTeacherId + ":");
        schedule.sort(Comparator.comparing(c -> c.getSchedule().getStartTime())); // Sort by start time
        for (Course course : schedule) {
            System.out.println(course + " (Days: " + course.getSchedule().getDays() + ")");
        }
    }
}

    // Predefined Teachers, Students, and classes
    //-----------------------------------------------------------
    private void initializeTeachers() {
        // Define the three teachers
        Teacher tom = new Teacher(1, "Tom Tom", "tom@example.com", "pass1");
        Teacher kaspian = new Teacher(2, "Kaspian Thomas", "kaspian2005@gmail.com", "Littlebro2");
        Teacher bob = new Teacher(3, "Bob Bob", "bob@example.com", "pass3");

        // Add to teachers HashMap
        teachers.put(tom.getTeacherId(), tom);
        teachers.put(kaspian.getTeacherId(), kaspian);
        teachers.put(bob.getTeacherId(), bob);

        // Overwrite teachers.csv with initial data
        CSVFileHandler.createCSVFile("teachers.csv", new String[]{"teacher_id", "name", "email", "password"});
        CSVFileHandler.addTeacherToCSV("teachers.csv", tom);
        CSVFileHandler.addTeacherToCSV("teachers.csv", kaspian);
        CSVFileHandler.addTeacherToCSV("teachers.csv", bob);
    }

    private void initializeCourses() {
        // Define 15 generic courses with assigned teachers
        Course[] initialCourses = {
            new Course(101, "Algebra I", "Tom Tom", 3.0, "MWF 9-10"),
            new Course(102, "Algebra II", "Tom Tom", 3.0, "MWF 10-11"),
            new Course(103, "Algebra III", "Tom Tom", 3.0, "MWF 11-12"),
            new Course(104, "Geometry I", "Kaspian Thomas", 3.0, "TTh 9-10:30"),
            new Course(105, "Geometry II", "Kaspian Thomas", 3.0, "TTh 10:30-12"),
            new Course(106, "Geometry III", "Kaspian Thomas", 3.0, "TTh 1-2:30"),
            new Course(107, "History", "Bob Bob", 3.0, "MWF 1-2"),
            new Course(108, "Art", "Bob Bob", 2.0, "TTh 2-3:30"),
            new Course(109, "Science", "Tom Tom", 4.0, "MWF 2-3:30"),
            new Course(110, "English I", "Kaspian Thomas", 3.0, "MWF 8-9"),
            new Course(111, "English II", "Kaspian Thomas", 3.0, "MWF 9-10"),
            new Course(112, "English III", "Kaspian Thomas", 3.0, "MWF 10-11"),
            new Course(113, "P.E.", "Bob Bob", 1.0, "TTh 3:30-4:30"),
            new Course(114, "Biology", "Tom Tom", 4.0, "MWF 3:30-5"),
            new Course(115, "World Literature", "Kaspian Thomas", 3.0, "TTh 1-2:30")
        };

        // Add to courses HashMap and overwrite courses.csv
        CSVFileHandler.createCSVFile("courses.csv", new String[]{"course_id", "course_name", "instructor", "credits", "schedule"});
        for (Course course : initialCourses) {
            courses.put(course.getCourseId(), course);
            CSVFileHandler.addCourseToCSV("courses.csv", course);
        }
    }

    private void initializeStudent() {
        // Define the initial student
        Student kaspianStudent = new Student(101, "Kaspian Thomas", "kaspian2005@gmail.com", "Littlebro2");

        // Overwrite students.csv with initial data
        CSVFileHandler.createCSVFile("students.csv", new String[]{"student_id", "name", "email", "password"});
        CSVFileHandler.addStudentToCSV("students.csv", kaspianStudent);
    }

    // Signup and Login Methods
    //-------------------------------------------------------------------
    public void studentSignup() {
        System.out.println("\nStudent Sign Up");
        int studentId = 0;
        boolean validId = false;
        while (!validId) {
            System.out.println("Enter Student ID:");
            studentId = getValidIntInput();
            List<Student> students = CSVFileHandler.readStudentFromCSV("students.csv");
            final int finalStudentId = studentId;
            boolean idExists = students.stream().anyMatch(s -> s.getStudentId() == finalStudentId);
            if (idExists) {
                System.out.println("This ID is already taken. Please try another one.");
            } else {
                validId = true;
            }
        }
        System.out.println("Enter Name (First and Last): ");
        String name = input.nextLine();
        String email;
        while (true) {
            System.out.println("Enter Email: ");
            email = input.nextLine().trim();
            if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                System.out.println("Invalid email format. Please try again.");
                continue;
            }
            List<Student> students = CSVFileHandler.readStudentFromCSV("students.csv");
            final String finalEmail = email;
            boolean emailExists = students.stream().anyMatch(s -> s.getEmail().equalsIgnoreCase(finalEmail));
            if (emailExists) {
                System.out.println("This email is already registered. Please use another email.");
            } else {
                break;
            }
        }
        System.out.println("Enter password: ");
        String password = input.nextLine();
        if (UserAuth.studentSignup(studentId, name, email, password)) {
            System.out.println("Student account created successfully!");
        } else {
            System.out.println("Failed to create student account.");
        }
    }

    public void teacherSignup() {
        System.out.println("\nTeacher Sign Up");
        int teacherId = 0;
        boolean validId = false;
        while (!validId) {
            System.out.println("Enter Teacher ID:");
            teacherId = getValidIntInput();
            List<Teacher> teacherList = CSVFileHandler.readTeachersFromCSV("teachers.csv");
            final int finalTeacherId = teacherId;
            boolean idExists = teacherList.stream().anyMatch(t -> t.getTeacherId() == finalTeacherId);
            if (idExists) {
                System.out.println("This ID is already taken. Please try another one.");
            } else {
                validId = true;
            }
        }
        System.out.println("Enter Name (First and Last): ");
        String name = input.nextLine();
        String email;
        while (true) {
            System.out.println("Enter Email: ");
            email = input.nextLine().trim();
            if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                System.out.println("Invalid email format. Please try again.");
                continue;
            }
            List<Teacher> teacherList = CSVFileHandler.readTeachersFromCSV("teachers.csv");
            final String finalEmail = email;
            boolean emailExists = teacherList.stream().anyMatch(t -> t.getEmail().equalsIgnoreCase(finalEmail));
            if (emailExists) {
                System.out.println("This email is already registered. Please use another email.");
            } else {
                break;
            }
        }
        System.out.println("Enter password: ");
        String password = input.nextLine();
        if (UserAuth.teacherSignup(teacherId, name, email, password)) {
            Teacher newTeacher = new Teacher(teacherId, name, email, password);
            teachers.put(teacherId, newTeacher);
            System.out.println("Teacher account created successfully!");
        } else {
            System.out.println("Failed to create teacher account.");
        }
    }

    public void studentLogin() {
        System.out.println("Student Login");
        System.out.println("Enter Email:");
        String email = input.nextLine();
        System.out.println("Enter Password:");
        String password = input.nextLine();
        if (UserAuth.studentLogin(email, password)) {
            System.out.println("Student login successful!");
            List<Student> students = CSVFileHandler.readStudentFromCSV("students.csv");
            loggedInStudentId = students.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .map(Student::getStudentId)
                .orElse(-1);
            studentMenu();
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    public void teacherLogin() {
        System.out.println("Teacher Login");
        System.out.println("Enter Email:");
        String email = input.nextLine();
        System.out.println("Enter Password:");

        String password = input.nextLine();
        if (UserAuth.teacherLogin(email, password)) {
            System.out.println("Teacher login successful!");
            List<Teacher> teacherList = CSVFileHandler.readTeachersFromCSV("teachers.csv");
            loggedInTeacherId = teacherList.stream()
                .filter(t -> t.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .map(Teacher::getTeacherId)
                .orElse(-1);
                teachers.put(loggedInTeacherId, teacherList.stream()
                    .filter(t -> t.getTeacherId() == loggedInTeacherId)
                    .findFirst().orElse(null));
            teacherMenu();
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    public void adminLogin() {
        System.out.println("Administrator Login");
        System.out.println("Enter Username:");
        String username = input.nextLine();
        System.out.println("Enter Password:");
        String password = input.nextLine();
        if (UserAuth.adminLogin(username, password)) {
            System.out.println("Admin login successful!");
            adminMenu();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    // Menus
    public void studentMenu() {
        int choice;
        do {
            System.out.println("\nStudent Menu");
            System.out.println("1. Display Courses");
            System.out.println("2. Apply for a Course");
            System.out.println("3. Search Courses");
            System.out.println("4. Display Sorted Courses");
            System.out.println("5. View Application Status");
            System.out.println("6. View Current Schedule");
            System.out.println("7. Logout");
            System.out.println("Enter your choice:");
            choice = getValidIntInput();
            switch (choice) {
                case 1: displayCourses(); break;
                case 2: applyForCourse(); break;
                case 3: searchCourses(); break;
                case 4: displaySortedCourses(); break;
                case 5: viewApplicationStatus(); break;
                case 6: viewCurrentStudentSchedule(); break;
                case 7:
                    System.out.println("Logging out...");
                    loggedInStudentId = -1;
                    break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private void teacherMenu() {
        int choice;
        do {
            System.out.println("\nTeacher Menu");
            System.out.println("1. Add Course");
            System.out.println("2. Display Courses");
            System.out.println("3. Search Courses");
            System.out.println("4. Display Sorted Courses");
            System.out.println("5. View Students in Class");
            System.out.println("6. View Current Schedule");
            System.out.println("7. Logout");
            System.out.println("Enter your choice:");
            choice = getValidIntInput();
            switch (choice) {
                case 1: addCourse(); break;
                case 2: displayCourses(); break;
                case 3: searchCourses(); break;
                case 4: displaySortedCourses(); break;
                case 5: viewStudentsInClass(); break;
                case 6: viewCurrentTeacherSchedule(); break;
                case 7:
                    System.out.println("Logging out...");
                    loggedInTeacherId = -1;
                    break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    public void adminMenu() {
        int choice;
        do {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Course");
            System.out.println("2. Display Courses");
            System.out.println("3. Remove Course");
            System.out.println("4. View Pending Applications");
            System.out.println("5. Search Courses");
            System.out.println("6. Display Sorted Courses");
            System.out.println("7. View All Students");
            System.out.println("8. View Student Schedule");
            System.out.println("9. View All Teachers");
            System.out.println("10. View Teacher Schedule");
            System.out.println("11. View Students in Teacher's Class");
            System.out.println("12. Logout");
            System.out.println("Enter your choice:");
            choice = getValidIntInput();
            switch (choice) {
                case 1: addCourse(); break;
                case 2: displayCourses(); break;
                case 3: removeCourse(); break;
                case 4: viewPendingApplications(); break;
                case 5: searchCourses(); break;
                case 6: displaySortedCourses(); break;
                case 7: viewAllStudents(); break;
                case 8: viewStudentSchedule(); break;
                case 9: viewAllTeachers(); break;
                case 10: viewTeacherSchedule(); break;
                case 11: viewStudentsInTeacherClass(); break;
                case 12: System.out.println("Logging out..."); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 12);
    }

    public void mainMenu() {
        int choice;
        do {
            System.out.println("\nStudent Management System");
            System.out.println("1. Student Login");
            System.out.println("2. Teacher Login");
            System.out.println("3. Administrator Login");
            System.out.println("4. Student Sign Up");
            System.out.println("5. Teacher Sign Up");
            System.out.println("6. Exit");
            System.out.println("Enter your choice:");
            choice = getValidIntInput();
            switch (choice) {
                case 1: studentLogin(); break;
                case 2: teacherLogin(); break;
                case 3: adminLogin(); break;
                case 4: studentSignup(); break;
                case 5: teacherSignup(); break;
                case 6: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private int getValidIntInput() {
        while (true) {
            try {
                int value = input.nextInt();
                input.nextLine();
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a numeric value:");
                input.nextLine();
            }
        }
    }

    private double getValidDoubleInput() {
        while (true) {
            try {
                double value = input.nextDouble();
                input.nextLine();
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a numeric value:");
                input.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.mainMenu();
    }
}