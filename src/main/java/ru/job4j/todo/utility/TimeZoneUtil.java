package ru.job4j.todo.utility;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.TimeZone;
import java.util.stream.Collectors;

public final class TimeZoneUtil {

    private TimeZoneUtil() {
    }

    public static Collection<Task> timeZoneWrap(User user, Collection<Task> tasks) {
        for (Task task : tasks) {
            task.setCreated(task.getCreated()
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of(user.getTimezone())).toLocalDateTime());
        }
        return tasks;
    }

    public static Collection<TimeZone> getTimeZones() {
        return Arrays.stream(TimeZone.getAvailableIDs()).map(TimeZone::getTimeZone).collect(Collectors.toList());
    }
}
