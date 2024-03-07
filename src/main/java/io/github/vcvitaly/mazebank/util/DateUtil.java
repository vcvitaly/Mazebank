package io.github.vcvitaly.mazebank.util;

import io.github.vcvitaly.mazebank.exception.MalformedDataException;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {

    public static LocalDate parseDate(String s) {
        final String[] dateParts = s.split("-");
        if (dateParts.length != 3) {
            throw new MalformedDataException("Invalid date format: " + s);
        }
        return LocalDate.of(
                Integer.parseInt(dateParts[0]),
                Integer.parseInt(dateParts[1]),
                Integer.parseInt(dateParts[2])
        );
    }
}
