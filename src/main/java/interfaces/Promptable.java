package interfaces;

import classes.tasks.Task;

public interface Promptable<T extends Task> {
    String start();

    String add(T printable, int length);

    String done(T printable);

    String remove(T printable, int length);

    String error(String header, String errorMessage);

    String error(String errorMessage);

    String list(Iterable<T> inputs);

    String exit();
}
