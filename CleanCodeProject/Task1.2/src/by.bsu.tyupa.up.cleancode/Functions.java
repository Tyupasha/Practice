package by.bsu.tyupa.up.cleancode;

import java.io.*;
import java.util.*;

import org.json.simple.*;

import java.text.*;

public class Functions {

    public void message_array_to_json_array(JSONArray json_array, ArrayList<Message> al) {
        json_array.clear();
        for (Message a_nal : al) {
            JSONObject json_object = new JSONObject();
            json_object.put("id", a_nal.get_id());
            json_object.put("author", a_nal.get_author());
            json_object.put("timestamp", a_nal.get_timestamp());
            json_object.put("message", a_nal.get_message());
            json_array.add(json_object);
            }
    }

    public String find_by_substring(String keyWord) throws FileNotFoundException {
        Object obj = JSONValue.parse(new FileReader("history.json"));
        JSONArray json_array = (JSONArray) obj;
        JSONArray key_word_json_array = new JSONArray();

        for (int i = 0; i < json_array.size(); i++) {
            JSONObject json_object = (JSONObject) json_array.get(i);
            String key_word_in_history = (String) json_object.get("message");
            if (key_word_in_history.contains(keyWord)) {
                key_word_json_array.add(json_array.get(i));
            }
        }
        if (key_word_json_array.isEmpty()) {
            return "There is no messages with this key word/lexeme";
        } else {
            return key_word_json_array.toJSONString();
        }
    }

    public Message add_message() {
        String author;
        String message_id;
        String message_text;
        Scanner in = new Scanner(System.in);

        System.out.println("Input your name: ");
        author = in.nextLine();

        message_id = UUID.randomUUID().toString();

        System.out.println("Input message");
        message_text = in.nextLine();

        long current_time = System.currentTimeMillis();

        return new Message(message_id, author, message_text, current_time);
    }

    //JSONArray json_array = (JSONArray) obj;
    public void read_history(FileReader a, ArrayList<Message> al) {
        Object obj = JSONValue.parse(a);
        JSONArray json_array = new JSONArray();

        if(!al.isEmpty()) {
            message_array_to_json_array(json_array, al);;
        }
        System.out.println(json_array);
        json_array.clear();
        json_array = (JSONArray) obj;
        for (Object anArray : json_array) {
            System.out.println(anArray);
        }
    }

    public String load_messages(FileReader a, ArrayList<Message> al) {
        Object ob = JSONValue.parse(a);
        JSONArray json_array = (JSONArray) ob;

        for (Object an_array : json_array) {
            JSONObject obj = (JSONObject) an_array;
            String id = (String) obj.get("id");
            String author = (String) obj.get("author");
            String message = (String) obj.get("message");
            long timestamp = (long) obj.get("timestamp");
            Message mes = new Message(id, author, message, timestamp);
            al.add(mes);
        }
        if (al.isEmpty()) {
            return "History is empty!";
        } else {
            return al.toString();
        }
    }

    public void write_history(FileWriter a, ArrayList<Message> al) {
        JSONArray json_array = new JSONArray();

        message_array_to_json_array(json_array, al);
        try {
            a.write(json_array.toJSONString());
            a.flush();
            a.close();
        } catch (IOException e) {
            System.out.println("Can't rewrite file!" + e.getMessage());
        }
    }

    public String get_date(long milliseconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }

    public Calendar convert_string_date_to_calendar(String str_date) {
        Calendar cal = null;

        if (str_date != null) {
            try {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date = formatter.parse(str_date);
                cal = Calendar.getInstance();
                cal.setTime(date);
            } catch (Exception e) {
                System.out.println("Error in format: " + e.getMessage());
            }
        }

        return cal;
    }

    public String find_by_date(String limit1, String limit2) throws FileNotFoundException {
        Object ob = JSONValue.parse(new FileReader("history.json"));
        JSONArray json_array = (JSONArray) ob;
        Calendar caldate1 = convert_string_date_to_calendar(limit1);
        Calendar caldate2 = convert_string_date_to_calendar(limit2);
        JSONArray json_array_dates = new JSONArray();

        for (int i = 0; i < json_array.size(); i++) {
            JSONObject json_obj = (JSONObject) json_array.get(i);
            long long_time = (long) json_obj.get("timestamp");
            String str_time = get_date(long_time, "dd/MM/yyyy");
            Calendar cal_time = convert_string_date_to_calendar(str_time);
            if (cal_time.after(caldate1) && (cal_time.before(caldate2))) {
                json_array_dates.add(json_array.get(i));

            }
        }
        if (json_array_dates.isEmpty()) {
            return "For this period the history is empty";
        } else {
            return json_array_dates.toJSONString();
        }
    }

    public String find_by_author(String author) throws IOException {
        Object ob = JSONValue.parse(new FileReader("history.json"));
        JSONArray json_array = (JSONArray) ob;
        JSONArray json_array_authors = new JSONArray();

        for (int i = 0; i < json_array.size(); i++) {
            JSONObject json_obj = (JSONObject) json_array.get(i);
            if (json_obj.containsValue(author)) {
                json_array_authors.add(json_array.get(i));
            }
        }
        if (json_array_authors.isEmpty()) {
            return "There is no messages by this author";
        } else {
            return json_array_authors.toJSONString();
        }
    }

    public void delete_by_id(String id) throws IOException {
        Object ob = JSONValue.parse(new FileReader("history.json"));
        JSONArray json_array = (JSONArray) ob;

        for (int i = 0; i < json_array.size(); i++) {
            JSONObject json_obj = (JSONObject) json_array.get(i);
            if (json_obj.containsValue(id)) {
                json_array.remove(i);
                System.out.println("Deleting has been done successfully!");
            } else {
                System.out.println("There is no message with this id!");
            }
        }
        try {
            FileWriter fw = new FileWriter("history.json");
            fw.write(json_array.toJSONString());
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("Can't rewrite file!" + e.getMessage());
        }
    }

    public void show_interface() {
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
    }
}