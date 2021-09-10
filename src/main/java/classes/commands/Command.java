package classes.commands;

import classes.enums.CommandType;
import classes.tasks.Task;
import classes.tasks.TaskList;
import exceptions.InvalidCommandFormatException;
import interfaces.Promptable;

public abstract class Command {
    private CommandType type;
    private String keyword;
    private String args;

    public Command(CommandType type, String keyword) {
        this.setKeyword(keyword);
        this.setType(type);
    }

    public Command(CommandType type, String keyword, String args) {
        this(type, keyword);
        this.setArgs(args);
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public abstract String execute(TaskList tasks, Promptable<Task> prompt) throws InvalidCommandFormatException;
}