import command.Command;
import manager.EventManager;
import manager.UserManager;
import model.Event;
import model.EventType;
import model.User;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Main implements Command {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserManager userManager = new UserManager();
    private static final EventManager eventManager = new EventManager();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {


        boolean run = true;
        while (run) {
            Command.PrintCommandsMenu();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT -> run = false;
                case ADD_EVENT -> addEvent();
                case ADD_USER -> addUser();
                case SHOW_ALL_EVENTS -> showAllEvents();
                case SHOW_ALL_USERS -> showAllUsers();

                default -> System.out.println("Invalid command");
            }

        }

    }


    private static void showAllUsers() {
        List<User> all = userManager.getAll();
        for (User user : all) {
            System.out.println(user);
        }

    }

    private static void showAllEvents() {

        List<Event> all = eventManager.getAll();
        for (Event event : all) {
            System.out.println(event);
        }

    }


    private static void addEvent() {

        System.out.println("Please input event name, place,price, isOnline,eventType,date (YYYY-MM-DD HH:MM:SS)");
        String eventDataStr = scanner.nextLine();
        String[] eventData = eventDataStr.split(",");
        Event event = null;
        try {
            event = Event.builder()
                    .name(eventData[0])
                    .place(eventData[1])
                    .price(Double.parseDouble(eventData[2]))
                    .isOnline(Boolean.parseBoolean(eventData[3]))
                    .eventType(EventType.valueOf(eventData[4]))
                    .eventDate(sdf.parse(eventData[5]))
                    .build();
            eventManager.add(event);
            System.out.println("Event added");
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }

    }

    private static void addUser() {
        showAllEvents();
        System.out.println("Please choose event`s id");
        int eventId = Integer.parseInt(scanner.nextLine());
        Event event = eventManager.getById(eventId);
        if (event == null) {
            System.out.println("Please choose correct event Id");
        } else {
            System.out.println("Please input user name, surname,email");
            String usertDataStr = scanner.nextLine();
            String[] usertData = usertDataStr.split(",");
            User user = User.builder()
                    .name(usertData[0])
                    .surname(usertData[1])
                    .email(usertData[2])
                    .event(event)

                    .build();
            userManager.add(user);
            System.out.println("User added");
        }
    }
}


