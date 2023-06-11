import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyTime {
    int year, month, date, hour, minute, seconds;

    public MyTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.year = now.getYear();
        this.month = now.getMonthValue();
        this.date = now.getDayOfMonth();
        this.hour = now.getHour();
        this.minute = now.getMinute();
        this.seconds = now.getMinute();
    }

    public MyTime(int year, int month, int date, int hour, int minute, int seconds) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.seconds = seconds;
    }
}
