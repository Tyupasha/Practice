package Practice1_2;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import java.text.*;

public class Functions {

    public String findBySubstring(String keyWord) throws FileNotFoundException {
        Object obj = JSONValue.parse(new FileReader("history.json"));
        JSONArray array = (JSONArray) obj;
        JSONArray keyWordJsonArray = new JSONArray();

        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            String keyWordInHistory = (String) jsonObject.get("message");
            if (keyWordInHistory.contains(keyWord)) {
                keyWordJsonArray.add(array.get(i));
            }
        }
        if (keyWordJsonArray.isEmpty()) {
            return "There is no messages with this key word/lexeme";
        } else {
            return keyWordJsonArray.toJSONString();
        }
    }

    public Message addMessage() {
        String author;
        String messageId;
        String messageText;
        Scanner in = new Scanner(System.in);

        System.out.println("Input your name: ");
        author = in.nextLine();

        messageId = UUID.randomUUID().toString();

        System.out.println("Input message");
        messageText = in.nextLine();

        long curTime = System.currentTimeMillis();

        return new Message(messageId, author, messageText, curTime);
    }

    public void readHistory(FileReader a) {
        Object obj= JSONValue.parse(a);
        JSONArray JsArray = (JSONArray) obj;

        for (Object anArray : JsArray) {
            System.out.println(anArray);
        }
    }

    public String downloadMessages(FileReader a, ArrayList<Message> al) {
        Object ob = JSONValue.parse(a);
        JSONArray JsArray = (JSONArray) ob;

        for (Object anArray : JsArray) {
            JSONObject obj = (JSONObject) anArray;
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

    public void writeHistory(FileWriter a, ArrayList<Message> al) {
        JSONArray JsArray = new JSONArray();

        for (Message anAl : al) {
            JSONObject object = new JSONObject();
            object.put("id", anAl.get_id());
            object.put("author", anAl.get_author());
            object.put("timestamp", anAl.get_timestamp());
            object.put("message", anAl.get_message());
            JsArray.add(object);
        }
        try {
            a.write(JsArray.toJSONString());
            a.flush();
            a.close();
        } catch (IOException e) {
            System.out.println("Can't rewrite file!" + e.getMessage());
        }
    }

    public String getDate(long milliseconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }

    public Calendar convertStringDateToCalendar(String strDate) {
        Calendar cal = null;

        if (strDate != null) {
            try {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date = formatter.parse(strDate);
                cal = Calendar.getInstance();
                cal.setTime(date);
            } catch (Exception e) {
                System.out.println("Error in format: " + e.getMessage());
            }
        }

        return cal;
    }

    public String findByDate(String limit1, String limit2) throws FileNotFoundException {
        Object ob = JSONValue.parse(new FileReader("history.json"));
        JSONArray JsArray = (JSONArray) ob;
        Calendar calDate1 = convertStringDateToCalendar(limit1);
        Calendar calDate2 = convertStringDateToCalendar(limit2);
        JSONArray JsArrayDates = new JSONArray();

        for (int i = 0; i < JsArray.size(); i++) {
            JSONObject obj = (JSONObject) JsArray.get(i);
            long longTime = (long) obj.get("timestamp");
            String strTime = getDate(longTime, "dd/MM/yyyy");
            Calendar calTime = convertStringDateToCalendar(strTime);
            if (calTime.after(calDate1) && (calTime.before(calDate2))) {
                JsArrayDates.add(JsArray.get(i));

            }
        }
        if (JsArrayDates.isEmpty()) {
            return "For this period the history is empty";
        } else {
            return JsArrayDates.toJSONString();
        }
    }

    public String findByAuthor(String author) throws IOException {
        Object ob = JSONValue.parse(new FileReader("history.json"));
        JSONArray JsArray = (JSONArray) ob;
        JSONArray JsArrayAuthors = new JSONArray();

        for (int i = 0; i < JsArray.size(); i++) {
            JSONObject obj = (JSONObject) JsArray.get(i);
            if (obj.containsValue(author)) {
                JsArrayAuthors.add(JsArray.get(i));
            }
        }
        if (JsArrayAuthors.isEmpty()) {
            return "There is no messages by this author";
        } else {
            return JsArrayAuthors.toJSONString();
        }
    }

    public void deleteById(String id) throws IOException {
        Object ob = JSONValue.parse(new FileReader("history.json"));
        JSONArray JsArray = (JSONArray) ob;

        for (int i = 0; i < JsArray.size(); i++) {
            JSONObject obj = (JSONObject) JsArray.get(i);
            if (obj.containsValue(id)) {
                JsArray.remove(i);
                System.out.println("Deleting has been done successfully!");
            } else {
                System.out.println("There is no message with this id!");
            }
        }
        try {
            FileWriter fw = new FileWriter("history.json");
            fw.write(JsArray.toJSONString());
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("Can't rewrite file!" + e.getMessage());
        }
    }




}
