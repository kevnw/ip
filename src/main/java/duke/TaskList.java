package duke;

import exception.InvalidDeadlineException;
import exception.InvalidEventException;
import exception.InvalidTodoException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskList {

    public void addDeadline(Ui ui, ArrayList<Task> list, String str, boolean isNew, boolean condition)
            throws InvalidDeadlineException {
        String[] deadline;
        if (isNew) {
            deadline = str.split("/by");
        } else {
            deadline = str.split("on");
        }
        if (deadline.length > 2) {
            throw new InvalidDeadlineException("☹ Task deadline must be written after `/by`.");
        }
        if (deadline[0].equals("")) {
            throw new InvalidDeadlineException("☹ Task description must be specified.");
        }
        if (deadline.length == 1) {
            throw new InvalidDeadlineException("☹ Task deadline must be specified.");
        }
        String description = deadline[0].trim();
        try {
            Deadline curr = new Deadline(description, false, LocalDate.parse(deadline[1].trim()));
            list.add(curr);
            if (isNew) {
                ui.describeTask(list, curr);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid Date format! Must be (yyyy-mm-dd).");
        }
    }

    public void addEvent(Ui ui, ArrayList<Task> list, String str, boolean isNew, boolean condition) throws InvalidEventException {
        String[] event;
        if (isNew) {
            event = str.split("/at");
        } else {
            event = str.split("on");
        }
        if (event.length > 2) {
            throw new InvalidEventException("☹ Event time must be written after `/at`.");
        }
        if (event[0].equals("")) {
            throw new InvalidEventException("☹ Event description must be specified.");
        }
        if (event.length == 1) {
            throw new InvalidEventException("☹ Event time must be specified.");
        }
        String description = event[0].trim();
        try {
            Event curr = new Event(description, condition, LocalDate.parse(event[1].trim()));
            list.add(curr);
            if (isNew) {
                ui.describeTask(list, curr);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid Date format! Must be (yyyy-mm-dd).");
        }
    }

    public void addTodo(Ui ui, ArrayList<Task> list, String str, boolean isNew, boolean condition)
            throws InvalidTodoException {
        String description = str.trim();
        if (description.equals("")) {
            throw new InvalidTodoException("☹ Todo description must be specified.");
        }
        Todo curr = new Todo(description, condition);
        list.add(curr);
        if (isNew) {
            ui.describeTask(list, curr);
        }
    }

    public ArrayList<Task> findTask(ArrayList<Task> list, String query) {
        ArrayList<Task> suitableTasks = new ArrayList<>();
        list.forEach(x -> {
            if (x.getDescription().contains(query)) {
                suitableTasks.add(x);
            }
        });
        return suitableTasks;
    }
}
