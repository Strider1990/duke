package classes.tasks;

import java.util.ArrayList;
import java.util.Optional;

public class TaskList {
    private static TaskList instance;
    private ArrayList<Task> taskList;

    private TaskList() {
        taskList = new ArrayList<>();
    }

    public static TaskList getInstance() {
        if (instance == null) {
            instance = new TaskList();
        }

        return instance;
    }

    public void add(Task task) {
        taskList.add(task);
    }

    public Task remove(int idx) {
        return taskList.remove(idx);
    }

    public ArrayList<Task> get() {
        return taskList;
    }

    public Task getTask(int idx) {
        return taskList.get(idx);
    }

    public int size() {
        return taskList.size();
    }
}
