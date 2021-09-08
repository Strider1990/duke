package classes.tasks;

import classes.Command;
import classes.enums.TaskType;
import exceptions.InvalidCommandFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskFactory {
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";
    private static final String DEADLINE_PATTERN = "^(?<" + KEY_DESCRIPTION + ">.+) /by (?<" + KEY_DATE + ">.+)$";
    private static final String EVENT_PATTERN = "^(?<" + KEY_DESCRIPTION + ">.+) /at (?<" + KEY_DATE + ">.+)$";

    public static Task getInstance(Command cmd) throws InvalidCommandFormatException {
        String keyword = cmd.getKeyword();
        String args = cmd.getArgs();
        if (keyword.equalsIgnoreCase(TaskType.DEADLINE.toString())) {
            Matcher deadlineMatch = Pattern.compile(DEADLINE_PATTERN, Pattern.CASE_INSENSITIVE).matcher(args);
            if (deadlineMatch.matches()) {
                String description = deadlineMatch.group(KEY_DESCRIPTION);
                String dueDate = deadlineMatch.group(KEY_DATE);
                return new Deadline(description, dueDate);
            } else {
                throw new InvalidCommandFormatException();
            }
        } else if (keyword.equalsIgnoreCase(TaskType.EVENT.toString())) {
            Matcher eventMatch = Pattern.compile(EVENT_PATTERN, Pattern.CASE_INSENSITIVE).matcher(args);
            if (eventMatch.matches()) {
                String description = eventMatch.group(KEY_DESCRIPTION);
                String timing = eventMatch.group(KEY_DATE);
                return new Event(description, timing);
            } else {
                throw new InvalidCommandFormatException();
            }
        } else if (keyword.equalsIgnoreCase(TaskType.TODO.toString())) {
            return new Todo(args);
        }
        throw new InvalidCommandFormatException();
    }
}
