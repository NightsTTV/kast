// 4/4/25
import java.time.LocalTime;
import java.util.EnumSet;

public class TimeSlot {
    private EnumSet<DayOfWeek> days;
    private LocalTime startTime;
    private LocalTime endTime;

    public enum DayOfWeek {
        MONDAY("M"), TUESDAY("T"), WEDNESDAY("W"), THURSDAY("Th"), FRIDAY("F"), SATURDAY("S"), SUNDAY("Su");

        private final String abbreviation;

        DayOfWeek(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public static DayOfWeek fromAbbreviation(String abbr) {
            for (DayOfWeek day : values()) {
                if (day.getAbbreviation().equalsIgnoreCase(abbr)) return day;
            }
            throw new IllegalArgumentException("Invalid day abbreviation: " + abbr);
        }
    }

    public TimeSlot(EnumSet<DayOfWeek> days, LocalTime startTime, LocalTime endTime) {
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
    }

    public static TimeSlot parse(String schedule) {
        String[] parts = schedule.trim().split("\\s+", 2);
        if (parts.length != 2) throw new IllegalArgumentException("Invalid schedule format: " + schedule);

        // Parse days
        String dayString = parts[0];
        EnumSet<DayOfWeek> days = EnumSet.noneOf(DayOfWeek.class);
        if (dayString.contains("M")) days.add(DayOfWeek.MONDAY);
        if (dayString.contains("T")) days.add(DayOfWeek.TUESDAY);
        if (dayString.contains("W")) days.add(DayOfWeek.WEDNESDAY);
        if (dayString.contains("Th")) days.add(DayOfWeek.THURSDAY);
        if (dayString.contains("F")) days.add(DayOfWeek.FRIDAY);
        if (dayString.contains("S")) days.add(DayOfWeek.SATURDAY);
        if (dayString.contains("Su")) days.add(DayOfWeek.SUNDAY);

        // Parse times
        String[] times = parts[1].split("-");
        if (times.length != 2) throw new IllegalArgumentException("Invalid time format: " + parts[1]);

        LocalTime start = parseTime(times[0]);
        LocalTime end = parseTime(times[1]);

        return new TimeSlot(days, start, end);
    }

    // Helper method to parse flexible time formats
    private static LocalTime parseTime(String time) {
        time = time.trim();
        if (time.contains(":")) {
            // Already in HH:mm or H:mm format
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
            return LocalTime.of(hour, minute);
        } else {
            // Single-digit or double-digit hour (e.g., "9" or "10")
            int hour = Integer.parseInt(time);
            return LocalTime.of(hour, 0);
        }
    }

    public boolean overlaps(TimeSlot other) {
        for (DayOfWeek day : days) {
            if (other.days.contains(day)) {
                if (!(endTime.isBefore(other.startTime) || startTime.isAfter(other.endTime))) {
                    return true; // Overlap detected
                }
            }
        }
        return false;
    }

    public EnumSet<DayOfWeek> getDays() { return days; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DayOfWeek day : days) {
            sb.append(day.getAbbreviation());
        }
        return sb + " " + startTime.toString().substring(0, 5) + "-" + endTime.toString().substring(0, 5);
    }
}