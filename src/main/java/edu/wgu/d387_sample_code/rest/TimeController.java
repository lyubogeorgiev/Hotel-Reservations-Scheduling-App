package edu.wgu.d387_sample_code.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/time")
@CrossOrigin
public class TimeController {

//    private final String startTime = "07:00PM EST";
//    private final String dateTimeFormat = "HH:mma z";
//    private final String[] timeZones =new String[]{"EST", "MST", "UTC"};
//    private final SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);

    private static final String START_TIME = "2025-03-23 17:00:00";
    private static final ZoneId[] timeZones = new ZoneId[]{
            ZoneId.of("America/New_York"),
            ZoneId.of("America/Los_Angeles"),
            ZoneId.of("UTC"),
            ZoneId.systemDefault()
    };

    @GetMapping("/presentation")
    public ResponseEntity<List<String>> getLivePresentationTimes() {
        List<String> presentationTimes = new ArrayList<>();

        LocalDateTime localDateTime = LocalDateTime.parse(START_TIME,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ZonedDateTime easternTime = localDateTime.atZone(timeZones[0]);

        for (ZoneId timeZone : timeZones) {

            if (presentationTimes.isEmpty()) {

                presentationTimes.add(easternTime
                        .format(DateTimeFormatter
                                .ofPattern("EEEE, MMM dd, yyyy 'at' hh:mm:ss a zzzz")));
            } else {

                ZonedDateTime currentZone = easternTime.withZoneSameInstant(timeZone);
                presentationTimes
                        .add(currentZone
                                .format(DateTimeFormatter.ofPattern("hh:mm:ss a zzzz")));

            }
        }


        return ResponseEntity.ok(presentationTimes);
    }
}
