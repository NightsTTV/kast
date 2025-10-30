// 4/4/25
import java.io.*;
import java.util.*;

public class CSVFileHandler {

    // Create CSV with headers
    public static void createCSVFile(String filePath, String[] headers) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append(String.join(",", headers)).append("\n");
            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generic method to write to a CSV file
    public static void writeToCSV(String filePath, String[] records, boolean append) {
        try (FileWriter writer = new FileWriter(filePath, append)) {
            writer.append(String.join(",", records)).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generic method to read from CSV file, skipping header
    public static List<String[]> readFromCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return data; // Return empty list if file doesn’t exist
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip the header row
                    continue;
                }
                data.add(line.split(",", -1)); // Use -1 to include empty fields
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Method to add a course to the courses.csv file
    public static void addCourseToCSV(String filePath, Course course) {
        String[] record = {
            String.valueOf(course.getCourseId()),
            course.getCourseName(),
            course.getInstructor(),
            String.valueOf(course.getCredits()),
            course.getSchedule().toString()
        };
        writeToCSV(filePath, record, true);
        System.out.println("Course added to CSV file.");
    }

    // Reads courses from the courses.csv file
    public static List<Course> readCoursesFromCSV(String filePath) {
        List<Course> courses = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("CSV file does not exist.");
            return courses;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",", -1); // Use -1 to include empty fields
                if (data.length < 5) continue; // Skip malformed lines
                try {
                    int courseId = Integer.parseInt(data[0]);
                    String courseName = data[1];
                    String instructor = data[2];
                    double credits = Double.parseDouble(data[3]);
                    String schedule = data[4];
                    Course course = new Course(courseId, courseName, instructor, credits, schedule);
                    courses.add(course);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid data in courses.csv: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Method to add an application to the applications.csv file
    public static void addApplicationToCSV(String filePath, int studentId, int courseId) {
        String[] records = {
            String.valueOf(studentId),
            String.valueOf(courseId)
        };
        writeToCSV(filePath, records, true);
        System.out.println("Application added to CSV file.");
    }

    // Method to read applications from applications.csv
    public static List<String[]> readApplicationsFromCSV(String filePath) {
        return readFromCSV(filePath); // Reuse generic readFromCSV
    }

    // Method to remove an application from applications.csv
    public static void removeApplicationFromCSV(String filePath, int studentId, int courseId) {
        List<String[]> applications = readApplicationsFromCSV(filePath);
        applications.removeIf(app -> app.length >= 2 && 
            Integer.parseInt(app[0]) == studentId && 
            Integer.parseInt(app[1]) == courseId);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("student_id,course_id"); // Write header
            writer.newLine();
            for (String[] app : applications) {
                writer.write(String.join(",", app));
                writer.newLine();
            }
            System.out.println("Application removed from CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to add an enrollment to the enrollments.csv file
    public static void addEnrollmentToCSV(String filePath, int studentId, int courseId) {
        String[] records = {
            String.valueOf(studentId),
            String.valueOf(courseId)
        };
        writeToCSV(filePath, records, true);
        System.out.println("Enrollment added to CSV file.");
    }

    // Method to add Student to the students.csv file
    public static void addStudentToCSV(String filePath, Student student) {
        String[] records = {
            String.valueOf(student.getStudentId()),
            student.getFirstName() + " " + student.getLastName(),
            student.getEmail(),
            student.getPassword()
        };
        writeToCSV(filePath, records, true);
        System.out.println("Student added to CSV file.");
    }

    // Method to read students from the students.csv file
    public static List<Student> readStudentFromCSV(String filePath) {
        List<Student> students = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return students;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",", -1);
                if (data.length < 4) continue; // Skip malformed lines
                try {
                    int studentId = Integer.parseInt(data[0]);
                    String name = data[1];
                    String email = data[2];
                    String password = data[3];
                    Student student = new Student(studentId, name, email, password);
                    students.add(student);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid data in students.csv: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Method to add Teachers to CSV File
    public static void addTeacherToCSV(String filePath, Teacher teacher) {
        String[] records = {
            String.valueOf(teacher.getTeacherId()),
            teacher.getFirstName() + " " + teacher.getLastName(),
            teacher.getEmail(),
            teacher.getPassword()
        };
        writeToCSV(filePath, records, true);
        System.out.println("Teacher added to CSV file.");
    }

    // Method to read Teachers from CSV File
    public static List<Teacher> readTeachersFromCSV(String filePath) {
        List<Teacher> teachers = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return teachers;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",", -1);
                if (data.length < 4) continue; // Skip malformed lines
                try {
                    int teacherId = Integer.parseInt(data[0]);
                    String name = data[1];
                    String email = data[2];
                    String password = data[3];
                    Teacher teacher = new Teacher(teacherId, name, email, password);
                    teachers.add(teacher);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid data in teachers.csv: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    // Method to add a registration to the registrations.csv file
    public static void addRegistrationToCSV(String filePath, int studentId, int courseId) {
        String[] records = {
            String.valueOf(studentId),
            String.valueOf(courseId)
        };
        writeToCSV(filePath, records, true);
        System.out.println("Registration added to CSV file.");
    }

    // Method to read registrations from the registrations.csv file
    public static List<String[]> readRegistrationsFromCSV(String filePath) {
        return readFromCSV(filePath);
    }

    // Method to update courses in CSV
    public static void updateCourseInCSV(String filePath, int courseId, Course updatedCourse) {
        List<Course> courses = readCoursesFromCSV(filePath);
        boolean updated = false;
        for (Course course : courses) {
            if (course.getCourseId() == courseId) {
                course.setCourseName(updatedCourse.getCourseName());
                course.setInstructor(updatedCourse.getInstructor());
                course.setCredits(updatedCourse.getCredits());
                course.setSchedule(updatedCourse.getSchedule().toString()); // Parse TimeSlot from String
                updated = true;
                break;
            }
        }
        if (!updated) {
            System.out.println("Course ID " + courseId + " not found.");
            return;
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("course_id,course_name,instructor,credits,schedule\n");
            for (Course course : courses) {
                writer.append(String.valueOf(course.getCourseId())).append(",");
                writer.append(course.getCourseName()).append(",");
                writer.append(course.getInstructor()).append(",");
                writer.append(String.valueOf(course.getCredits())).append(",");
                writer.append(course.getSchedule().toString()).append("\n"); // Fixed: Use toString()
            }
            System.out.println("Course updated in CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to delete Courses from CSV
        public static void deleteCourseFromCSV(String filePath, int courseId) {
        List<Course> courses = readCoursesFromCSV(filePath);
        boolean removed = courses.removeIf(course -> course.getCourseId() == courseId);
        if (!removed) {
            System.out.println("Course ID " + courseId + " not found.");
            return;
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("course_id,course_name,instructor,credits,schedule\n");
            for (Course course : courses) {
                writer.append(String.valueOf(course.getCourseId())).append(",");
                writer.append(course.getCourseName()).append(",");
                writer.append(course.getInstructor()).append(",");
                writer.append(String.valueOf(course.getCredits())).append(",");
                writer.append(course.getSchedule().toString()).append("\n"); // Fixed: Use toString()
            }
            System.out.println("Course deleted from CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "courses.csv";
        List<Course> courses = readCoursesFromCSV(filePath);
        for (Course course : courses) {
            System.out.println(course);
        }
        createCSVFile(filePath, new String[]{"course_id", "course_name", "instructor", "credits", "schedule"});
    }
}