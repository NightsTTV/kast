// 4/4/25
import java.util.*;

public class Course implements Comparable<Course> {
    private int courseId;
    private String courseName;
    private String instructor;
    private double credits;
    private TimeSlot schedule;

    public Course(int courseId, String courseName, String instructor, double credits, String schedule) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.credits = credits;
        this.schedule = TimeSlot.parse(schedule);
    }

    // Setters
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setCredits(double credits) { this.credits = credits; }
    public void setSchedule(String schedule) { this.schedule = TimeSlot.parse(schedule); } // Parse String to TimeSlot

    // Getters
    public int getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getInstructor() { return instructor; }
    public double getCredits() { return credits; }
    public TimeSlot getSchedule() { return schedule; }

    @Override
    public String toString() {
        return "CourseID: " + courseId + ", Name: " + courseName
            + ", Instructor: " + instructor + ", Credits: " + credits + ", Schedule: " + schedule;
    }

    @Override
    public int compareTo(Course other) {
        return Integer.compare(this.courseId, other.courseId);
    }
}