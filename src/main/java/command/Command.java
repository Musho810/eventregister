package command;

public interface Command {
    int ADD_EVENT = 1;
    int ADD_USER = 2;
    int SHOW_ALL_EVENTS = 3;
    int SHOW_ALL_USERS = 4;
    int FIND_EVENT_BY_NAME = 5;
    int FIND_USER_BY_EMAIL = 6;
    int EXPORT_ALL_EVENTS_IN_EXCELL = 7;
    int EXPORT_ALL_USERS_IN_EXCELL = 8;
    int EXIT = 0;

    int CONCERT = 0;
    int EXHIBITION = 1;
    int KINO = 2;


    static void PrintCommandsMenu() {
        System.out.println("please input " + ADD_EVENT + " For add event");
        System.out.println("please input " + ADD_USER + " For add user");
        System.out.println("please input " + SHOW_ALL_EVENTS + " For show all events");
        System.out.println("please input " + SHOW_ALL_USERS + " For show all users");
        System.out.println("please input " + FIND_EVENT_BY_NAME + " For find event by name");
        System.out.println("please input " + FIND_USER_BY_EMAIL + " For find user by email");
        System.out.println("please input " + EXPORT_ALL_EVENTS_IN_EXCELL + " For export all events (EXCELL)");
        System.out.println("please input " + EXPORT_ALL_USERS_IN_EXCELL + " For export all users (EXCELL)");
        System.out.println("please input " + EXIT + " For exit");

    }
     static void eventTypeCommand() {
         System.out.println("please input " + CONCERT + " For choose concert");
         System.out.println("please input " + EXHIBITION + " For choose exhibition");
         System.out.println("please input " + KINO + " For choose kino");
    }
}
