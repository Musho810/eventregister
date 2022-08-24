package command;

public interface Command {
    int ADD_EVENT = 1;
    int ADD_USER = 2;
    int SHOW_ALL_EVENTS = 3;
    int SHOW_ALL_USERS = 4;

    int EXIT = 0;



    static void PrintCommandsMenu() {
        System.out.println("please input " + ADD_EVENT + " For add event");
        System.out.println("please input " + ADD_USER + " For add user");
        System.out.println("please input " + SHOW_ALL_EVENTS + " For show all events");
        System.out.println("please input " + SHOW_ALL_USERS + " For show all users");

        System.out.println("please input " + EXIT + " For exit");

    }

}
