import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Dima on 15.02.2016.
 */
public class UserInterface {
    public static void main(String[] args) throws IOException {
        int choice = 9;
        ArrayList<Message> chat = new ArrayList();

        while (choice != 0) {
            System.out.println("Hey, user! What u gonna do?");
            System.out.println("1 - Add message");
            System.out.println("2 - Delete message by id");
            System.out.println("3 - Look at history");
            System.out.println("4 - Download current message history to file");
            System.out.println("5 - Find messages by author");
            System.out.println("6 - Find by period of time");
            System.out.println("7 - Download messages from history");
            System.out.println("0 - Exit");
            System.out.println("Your choice? ");
            Scanner in = new Scanner(System.in);
            choice = in.nextInt();


            switch (choice) {
                case 1:
                    Message message = Functions.add_message();
                    chat.add(message);
                    break;
                case 2:
                    System.out.println("Input id: ");
                    Scanner in2 = new Scanner(System.in);
                    String id = in2.nextLine();
                    Functions.delete_by_id(id);
                    break;
                case 3:
                    Functions.read_history(new FileReader("history.json"));
                    break;
                case 4:
                    Functions.write_history(new FileWriter("history.json"), chat);
                    break;
                case 5:
                    System.out.println("Input author: ");
                    Scanner in3 = new Scanner(System.in);
                    String author = in3.nextLine();
                    System.out.println(Functions.find_by_author(author));
                    break;
                case 6:
                    System.out.println("Input first limit: ");
                    Scanner in6 = new Scanner(System.in);
                    long lim1 = in6.nextLong();
                    System.out.println("Input second limit: ");
                    long lim2 = in6.nextLong();
                    System.out.println(Functions.find_by_date(lim1, lim2));
                    break;
                case 7:
                    System.out.println(Functions.download_messages(new FileReader("history.json"), chat));
                case 0:
                    break;
                default:
                    System.out.println("Incorrect input!");
            }
        }
    }
}
