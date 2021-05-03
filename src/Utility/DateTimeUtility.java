package Utility;

import java.time.*;

/**
 * A utility class to handle date and time based conversions.
 */
public class DateTimeUtility {
    public static ZoneId zoneId = ZoneId.systemDefault();

    /**
     * Takes a LocalDateTime and returns the same time in EST.
     * @param time
     * @return
     */
    public static LocalDateTime toEST(LocalDateTime time) {
        ZoneId est = ZoneId.of("America/New_York");
        ZonedDateTime toZone = time.atZone(zoneId);
        ZonedDateTime toEST = toZone.withZoneSameInstant(est);
        return toEST.toLocalDateTime().withNano(0).withSecond(0);
    }
}
