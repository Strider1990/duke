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
import java.util.Scanner;

public class Duke {
    private final Promptable<Task> prompt;
    private final IOParser<Command, Scanner> parser;

    public Duke(Promptable<Task> prompt, IOParser<Command, Scanner> parser) {
        this.prompt = prompt;
        this.parser = parser;
    }

    public static void main(String[] args) {
        Duke d = new Duke(new Prompt(), new Parser());
        TaskList tasks = TaskList.getInstance();
        System.out.println(d.prompt.start());

        Scanner in = new Scanner(System.in);
        boolean receiveInput = true;

        while (receiveInput && in.hasNext()) {
            try {
                Command command = d.parser.readInput(in);
                StringBuilder output = new StringBuilder();
                switch (command.getType()) {
                case ADD:
                    Task newTask = TaskFactory.getInstance(command);
                    tasks.add(newTask);
                    output.append(d.prompt.add(newTask, tasks.size()));
                    break;
                case COMPLETE:
                    int idx = Integer.parseInt(command.getArgs());
                    Task doneTask = tasks.getTask(idx - 1);
                    doneTask.setDone(true);
                    output.append(d.prompt.done(doneTask));
                    break;
                case LIST:
                    output.append(d.prompt.list(tasks.get()));
                    break;
                case EXIT:
                    receiveInput = false;
                    break;
                }
                System.out.println(output);
            } catch (InvalidCommandException ice) {
                System.out.println(d.prompt.error(ice.getErrorHeader(), ice.getMessage()));
            } catch (InvalidCommandFormatException icfe) {
                System.out.println(d.prompt.error(icfe.getErrorHeader(), icfe.getMessage()));
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.out.println(d.prompt.error(ex.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(d.prompt.exit());
    }
}
