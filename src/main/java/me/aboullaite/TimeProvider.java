package me.aboullaite;

import javax.enterprise.context.RequestScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequestScoped
public class TimeProvider {
    public String getCurrentTime() {
        LocalDateTime now =  LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
