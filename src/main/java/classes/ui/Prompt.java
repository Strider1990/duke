package classes.ui;

import classes.enums.TaskType;
import classes.tasks.Deadline;
import classes.tasks.Event;
import classes.tasks.Task;
import classes.tasks.Todo;
import interfaces.Promptable;

public class Prompt implements Promptable<Task> {
    private static final String PADDING = "    ";
    private static final String HORIZONTAL_LINE = "_________________________________________";
    private static final String START = "Hello! I'm Duke\n    What can I do for you?";
    private static final String END = "Bye. Hope to see you again soon!";
    private static final String DONE = "Nice! I've marked this task as done:";
    private static final String ADD = "added: ";
    private static final String REMOVE = "Noted. I've removed this task:";

    @Override
    public String start() {
        return formatOutput(formatLine(START));
    }

    @Override
    public String add(Task printable, int length) {
        String output = formatLine(ADD + printable.toString());
        output += count(length);
        return formatOutput(output);
    }

    @Override
    public String done(Task printable) {
        String output = formatLine(DONE);
        output += formatLine("   " + printable.toStatusString());
        return formatOutput(output);
    }

    @Override
    public String remove(Task printable, int length) {
        String output = formatLine(REMOVE);
        output += formatLine("   " + printable.toStatusString());
        output += count(length);
        return formatOutput(output);
    }

    @Override
    public String error(String header, String errorMessage) {
        String output = formatLine(header) + formatLine(errorMessage);
        return formatOutput(output);
    }

    @Override
    public String error(String errorMessage) {
        return formatOutput(formatLine(errorMessage));
    }

    @Override
    public String list(Iterable<Task> inputs) {
        StringBuilder output = new StringBuilder();
        int count = 1;
        for (Task input : inputs) {
            String taskStr = "";
            if (input.getType() == TaskType.DEADLINE) {
                Deadline down = (Deadline) input;
                taskStr = down.toStatusString();
            } else if (input.getType() == TaskType.EVENT) {
                Event down = (Event) input;
                taskStr = down.toStatusString();
            } else if (input.getType() == TaskType.TODO) {
                Todo down = (Todo) input;
                taskStr = down.toStatusString();
            }
            output.append(formatLine(count + ". " + taskStr));
            count++;
        }
        return formatOutput(output.toString());
    }

    @Override
    public String exit() {
        return formatOutput(formatLine(END));
    }

    private String count(int length) {
        String multipleStr = (length > 1) ? "tasks" : "task";
        String taskStr = length + " " + multipleStr;
        return formatLine("Now you have " + taskStr + " in the list.");
    }

    private String formatLine(String output) {
        return PADDING + output + System.lineSeparator();
    }

    private String formatOutput(String output) {
        return formatLine(HORIZONTAL_LINE) + output + PADDING + HORIZONTAL_LINE;
    }
}
