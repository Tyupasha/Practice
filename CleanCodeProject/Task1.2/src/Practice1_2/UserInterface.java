package Practice1_2;



import java.io.*;
import java.util.*;
import java.util.logging.*;


public class UserInterface {

    public static void main(String[] args) throws IOException {

        Logger log = Logger.getLogger(UserInterface.class.getName());
        FileHandler fileHandler = new FileHandler("log.txt");
        log.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);

        final String FILE_NAME = "history.json";
        boolean condition = true;

        Functions functions = new Functions();
        ArrayList<Message> chat = new ArrayList<>();

        while(condition) {
                System.out.println("Hey, user! What u gonna do?");
                System.out.println("1 - Add message");
                System.out.println("2 - Delete message by id");
                System.out.println("3 - Look at history");
                System.out.println("4 - Download current message history to file");
                System.out.println("5 - Find messages by author");
                System.out.println("6 - Find by period of time");
                System.out.println("7 - Show messages from history");
                System.out.println("8 - Find message by key word/lexeme");
                System.out.println("0 - Exit");
                System.out.println("Your choice? ");

                Scanner in = new Scanner(System.in);



                switch (in.nextInt()) {

                    case 1:
                        Message message = functions.addMessage();
                        chat.add(message);

                        log.info("User add message");
                        break;

                    case 2:
                        Scanner in2 = new Scanner(System.in);
                        System.out.println("Input id: ");

                        String id = in2.nextLine();
                        functions.deleteById(id);

                        log.info("User deleted message by id");
                        break;

                    case 3:
                        functions.readHistory(new FileReader(FILE_NAME));

                        log.info("User called a message history");
                        break;

                    case 4:
                        functions.writeHistory(new FileWriter(FILE_NAME), chat);

                        log.info("User rewrote a message history");
                        break;

                    case 5:
                        Scanner in5 = new Scanner(System.in);
                        System.out.println("Input author: ");

                        String author = in5.nextLine();
                        System.out.println(functions.findByAuthor(author));

                        log.info("User found messages by author");
                        break;

                    case 6:
                        Scanner in6 = new Scanner(System.in);
                        System.out.println("Please input date in format \"dd/MM/yyyy\"");

                        System.out.println("Input first limit: ");
                        String lim1 = in6.nextLine();

                        System.out.println("Input second limit: ");
                        String lim2 = in6.nextLine();

                        System.out.println(functions.findByDate(lim1, lim2));

                        log.info("User found messages by period of time");
                        break;

                    case 7:
                        System.out.println(functions.downloadMessages(new FileReader(FILE_NAME), chat));

                        log.info("User downloaded messages from history");
                        break;

                    case 8:
                        System.out.println("Input key word/lexeme: ");
                        Scanner in8 = new Scanner(System.in);
                        String keyWord = in8.nextLine();

                        System.out.println(functions.findBySubstring(keyWord));

                        log.info("User found messages by key word/lexeme");
                        break;

                    case 0:
                        condition = false;
                        break;

                    default:
                        System.out.println("Incorrect input!");
                }
        }
    }
}
