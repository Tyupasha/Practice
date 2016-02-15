/**
 * Created by Dima on 15.02.2016.
 */
import java.io.*;
import java.util.*;
import org.json.simple.*;

public class Functions {
    public static Message add_message() {
        String name, id, message;
        Scanner in = new Scanner(System.in);

        System.out.println("Input your name: ");
        name = in.nextLine();

        id = UUID.randomUUID().toString();

        System.out.println("Input message");
        message = in.nextLine();

        long curTime = System.currentTimeMillis();

        Message mes = new Message(id, name, message, curTime);
        return mes;
    }

    public static void read_history(FileReader a) {
        Object ob = JSONValue.parse(a);
        JSONArray array = (JSONArray) ob;

        for (int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i));
        }
    }

    public static String download_messages(FileReader a, ArrayList<Message> al) {
        Object ob = JSONValue.parse(a);
        JSONArray array = (JSONArray) ob;

        for(int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            String id = (String) obj.get("id");
            String author = (String) obj.get("author");
            String message = (String) obj.get("message");
            long timestamp = (long) obj.get("timestamp");
            Message mes = new Message(id, author, message, timestamp);
            al.add(mes);
        }
        if(al.isEmpty()) {
            return "History is empty!";
        } else {
            return al.toString();
        }
    }

    public static void write_history(FileWriter a, ArrayList<Message> al) {
        JSONArray arr = new JSONArray();

        for (int i = 0; i < al.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("id", al.get(i).get_id());
            object.put("author", al.get(i).get_author());
            object.put("timestamp", al.get(i).get_timestamp());
            object.put("message", al.get(i).get_message());
            arr.add(object);
        }
        try {
            a.write(arr.toJSONString());
            a.flush();
            a.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String find_by_date(long limit1, long limit2) throws FileNotFoundException {
        Object ob = JSONValue.parse(new FileReader("history.json"));
        JSONArray array = (JSONArray) ob;
        Date date1 = new Date(limit1);
        Date date2 = new Date(limit2);
        JSONArray dates = new JSONArray();

        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            long mytime = (long) obj.get("timestamp");
            Date mytimed = new Date(mytime);
            if (mytimed.after(date1) && (mytimed.before(date2))) {
                dates.add(array.get(i));

            }
        }
        if (dates.isEmpty()) {
            return "For this period the history is empty";
        } else {
            return dates.toJSONString();
        }
    }

    public static String find_by_author(String author) throws IOException {
        Object ob = JSONValue.parse(new FileReader("history.json"));
        JSONArray array = (JSONArray) ob;
        JSONArray authors = new JSONArray();

        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            if (obj.containsValue(author)) {
                authors.add(array.get(i));
            }
        }
        if (authors.isEmpty()) {
            return "There is no messages by this author";
        } else {
            return authors.toJSONString();
        }
    }

    public static void delete_by_id(String id) throws IOException {
        Object ob = JSONValue.parse(new FileReader("history.json"));
        JSONArray array = (JSONArray) ob;

        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            if (obj.containsValue(id)) {
                array.remove(i);
                System.out.println("Deleting have been done succesfuly!");
            } else {
                System.out.println("There is no message with such id!");
            }
        }

        FileWriter fw = new FileWriter("history.json");
        try {
            fw.write(array.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
