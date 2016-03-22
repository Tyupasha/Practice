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
            functions.showInterface();

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
                    functions.loadCurrentHistory(chat);

                    log.info("User read a message history");
                    break;

                case 4:
                    functions.writeCurrentHistoryToFile(chat);

                    log.info("User (re)wrote a message history to file");
                    break;

                case 5:
                    System.out.println(functions.loadAllHistory(chat));

                    log.info("User downloaded messages from history");

                    break;

                case 6:
                    Scanner in6 = new Scanner(System.in);
                    System.out.println("Please input date in format \"dd/MM/yyyy\"");

                    System.out.println("Input first limit: ");
                    String lim1 = in6.nextLine();

                    System.out.println("Input second limit: ");
                    String lim2 = in6.nextLine();

                    System.out.println(functions.findByBate(lim1, lim2));

                    log.info("User found messages by period of time");
                    break;

                case 7:
                    Scanner in5 = new Scanner(System.in);
                    System.out.println("Input author: ");

                    String author = in5.nextLine();
                    System.out.println(functions.findByAuthor(author));

                    log.info("User found messages by author");
                    break;

                case 8:
                    System.out.println("Input key word/lexeme: ");
                    Scanner in8 = new Scanner(System.in);
                    String keyWord = in8.nextLine();

                    System.out.println(functions.findBySubstring(keyWord));

                    log.info("User found messages by key word/lexeme");
                    break;

                case 9:
                    System.out.println("Input regular expression: ");
                    Scanner in9 = new Scanner(System.in);
                    String regExp = in9.nextLine();

                    System.out.println(functions.findByRegEx(regExp));

                    log.info("User found messages by regular expression");
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
