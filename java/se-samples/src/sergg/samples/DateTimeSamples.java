package sergg.samples;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DateTimeSamples {
    public static void main(String[] args) {
        sample1();
    }

    public static void sample1() {
        ZoneId zone = ZoneId.of("Europe/Madrid");
        LocalDate cycleStartDate = LocalDate.of(2022, 4, 1);
        OffsetDateTime cycleStartDateTime = cycleStartDate.atStartOfDay(zone).toOffsetDateTime();
        LocalDate cycleEndDate = LocalDate.of(2022, 4, 30);
        OffsetDateTime cycleEndDateTime = cycleEndDate.plusDays(1).atStartOfDay(zone).toOffsetDateTime().minusNanos(1);
        OffsetDateTime cycleEndDateTime1 = cycleEndDate.atStartOfDay(zone).toOffsetDateTime().plusHours(23).plusMinutes(59).plusSeconds(59).plusNanos(999999999);

        System.out.printf("cycleStartDate: %s,\ncycleStartDateTime: %s,\ncycleEndDate: %s,\ncycleEndDateTime: %s,\ncycleEndDateTime1: %s",
                cycleStartDate,
                cycleStartDateTime,
                cycleEndDate,
                cycleEndDateTime,
                cycleEndDateTime1);
    }
}
