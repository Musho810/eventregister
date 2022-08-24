import command.Command;
import managers.EventManager;
import managers.UserManager;
import model.Event;
import model.EventType;
import model.User;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import static dateUtil.DateUtil.stringToDate;
import static model.EventType.*;

public class Main implements Command {
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) throws SQLException {


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
                case FIND_EVENT_BY_NAME -> findEventByName();
                case FIND_USER_BY_EMAIL -> findUserByEmail();
                case EXPORT_ALL_EVENTS_IN_EXCELL -> eventExell();
                case EXPORT_ALL_USERS_IN_EXCELL -> userExcell();
                default -> System.out.println("Invalid command");
            }

        }

    }

    private static void userExcell() {
        UserManager userManager = new UserManager();
        System.out.println("Please input file location");
        String fileDir = scanner.nextLine();
        try {
            userManager.writeUsersToExcel(fileDir);

        } catch (IOException | InvalidFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void eventExell() {
        EventManager eventManager = new EventManager();
        System.out.println("Please input file location");
        String fileDir = scanner.nextLine();
        try {
            eventManager.writeEventsToExcel(fileDir);

        } catch (IOException | InvalidFormatException | SQLException e) {
            e.printStackTrace();
        }
    }


    private static void findUserByEmail() throws SQLException {
        UserManager userManager = new UserManager();
        System.out.println("Please input user email");
        String mailUser = scanner.nextLine();
        userManager.findByEmail(mailUser);
    }

    private static void findEventByName() throws SQLException {
        EventManager eventManager = new EventManager();
        System.out.println("Please input event name");
        String nameEvent = scanner.nextLine();
        eventManager.findEventByName(nameEvent);
    }

    private static void showAllUsers() throws SQLException {
        UserManager userManager = new UserManager();
        userManager.showUsers();
    }

    private static void addUser() throws SQLException {
        System.out.println("Please input user name");
        String username = scanner.nextLine();
        System.out.println("Please input user surname");
        String usersurname = scanner.nextLine();
        System.out.println("Please input user email ");
        String useremail = scanner.nextLine();
        System.out.println("Please choose event id");
        showAllEvents();
        int eventid = Integer.parseInt(scanner.nextLine());

        User user = new User(username, usersurname, useremail, eventid);
        UserManager userManager = new UserManager();
        userManager.add(user);

    }

    private static void showAllEvents() throws SQLException {
        EventManager eventManager = new EventManager();
        eventManager.showAllEvents();
    }

    private static void addEvent() throws SQLException {
        boolean isOnline;

        System.out.println("Please input event name");
        String eventname = scanner.nextLine();
        System.out.println("Please input event place");
        String eventplace = scanner.nextLine();
        System.out.println("Please input event price");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Please input event activity (DD.MM.YYYY)");
        String eventactivity = scanner.nextLine();
        isOnline = Objects.requireNonNull(stringToDate(eventactivity)).after(new Date());
        System.out.println("Please choose event type");
        Command.eventTypeCommand();
        int command1;
        EventType eventType = EVENT_NOT_FOUND;
        try {
            command1 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            command1 = -1;
        }
        switch (command1) {
            case CONCERT -> eventType = EventType.CONCERT;
            case EXHIBITION -> eventType = EventType.EXHIBITION;
            case KINO -> eventType = EventType.KINO;

            default -> System.out.println("Event type not found");
        }

        Event event = new Event(eventname, eventplace, price, isOnline, eventType);
        EventManager eventManager = new EventManager();
        eventManager.add(event);
        System.out.println("Event created");
    }


}


