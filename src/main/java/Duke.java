import classes.tasks.Task;
import classes.tasks.TaskFactory;
import classes.tasks.TaskList;
import classes.ui.Command;
import classes.ui.Parser;
import classes.ui.Prompt;
import exceptions.InvalidCommandException;
import exceptions.InvalidCommandFormatException;
import interfaces.IOParser;
import interfaces.Promptable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Duke {
    private static Duke instance;
    private final Promptable<Task> prompt;
    private final IOParser<Command, Scanner> parser;

    private Duke(Promptable<Task> prompt, IOParser<Command, Scanner> parser) {
        this.prompt = prompt;
        this.parser = parser;
    }

    public static Duke getInstance() {
        if (instance == null) {
            instance = new Duke(new Prompt(), new Parser());
        }

        return instance;
    }

    private static String handleAdd(Command cmd) throws InvalidCommandFormatException {
        Task newTask = TaskFactory.getInstance(cmd);
        Duke main = Duke.getInstance();
        TaskList tasks = TaskList.getInstance();
        tasks.add(newTask);

        return main.prompt.add(newTask, tasks.size());
    }

    private static String handleComplete(Command cmd) throws NumberFormatException {
        int idx = Integer.parseInt(cmd.getArgs()) - 1;
        TaskList tasks = TaskList.getInstance();
        Duke main = Duke.getInstance();
        Task doneTask = tasks.getTask(idx);
        doneTask.setDone(!doneTask.getDone());
        return main.prompt.done(doneTask);
    }

    private static String handleRemove(Command cmd) throws NumberFormatException {
        int idx = Integer.parseInt(cmd.getArgs()) - 1;
        TaskList tasks = TaskList.getInstance();
        Duke main = Duke.getInstance();
        Task removedTask = tasks.remove(idx);
        return main.prompt.remove(removedTask, tasks.size());
    }

    private static String handleList() {
        TaskList tasks = TaskList.getInstance();
        Duke main = Duke.getInstance();
        return main.prompt.list(tasks.get());
    }

    public static void main(String[] args) {
        Duke main = Duke.getInstance();
        TaskList tasks = TaskList.getInstance();
        System.out.println(main.prompt.start());

        Scanner in = new Scanner(System.in);
        boolean receiveInput = true;

        while (receiveInput && in.hasNext()) {
            try {
                Command command = main.parser.readInput(in);
                StringBuilder output = new StringBuilder();
                switch (command.getType()) {
                case ADD:
                    output.append(handleAdd(command));
                    break;
                case COMPLETE:
                    output.append(handleComplete(command));
                    break;
                case LIST:
                    output.append(handleList());
                    break;
                case REMOVE:
                    output.append(handleRemove(command));
                    break;
                case EXIT:
                    receiveInput = false;
                    break;
                }
                System.out.println(output);
            } catch (InvalidCommandException ice) {
                System.out.println(main.prompt.error(ice.getErrorHeader(), ice.getMessage()));
            } catch (InvalidCommandFormatException icfe) {
                System.out.println(main.prompt.error(icfe.getErrorHeader(), icfe.getMessage()));
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.out.println(main.prompt.error(ex.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(main.prompt.exit());
    }
}
