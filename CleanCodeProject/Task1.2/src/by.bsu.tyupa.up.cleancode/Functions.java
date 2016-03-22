package Practice1_2;

import java.io.*;
import java.util.*;

import org.json.simple.*;

import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    public void convertMessageArrayToJsonArray(JSONArray jsonArray, ArrayList<Message> al) {
        jsonArray.clear();
        for (Message message : al) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", message.get_id());
            jsonObject.put("author", message.get_author());
            jsonObject.put("timestamp", message.get_timestamp());
            jsonObject.put("message", message.get_message());
            jsonArray.add(jsonObject);
        }
    }

    public String findBySubstring(String str) {
        try {
            Object obj = JSONValue.parse(new FileReader("history.json"));
            JSONArray jsonArray = (JSONArray) obj;
            JSONArray keyWordJsonArray = new JSONArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String keyWord = (String) jsonObject.get("message");
                if (keyWord.contains(str)) {
                    keyWordJsonArray.add(jsonArray.get(i));
                }
            }
            if (keyWordJsonArray.isEmpty()) {
                return "There are no messages with this key word/lexeme";
            } else {
                return keyWordJsonArray.toJSONString();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File error!" + e.getMessage());
        }
        catch (NullPointerException e) {
            System.out.println("Message history is empty!");
        }
        return " ";
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

        long currentTime = System.currentTimeMillis();

        return new Message(messageId, author, messageText, currentTime);
    }

    public void loadCurrentHistory(ArrayList<Message> al) {
        JSONArray jsonArray = new JSONArray();

        if(!al.isEmpty()) {
            convertMessageArrayToJsonArray(jsonArray, al);
            System.out.println(jsonArray);
        }
        else {
            System.out.println("Current message history is empty!");
        }
    }

    public String loadAllHistory(ArrayList<Message> al) {
        try {
            Object ob = JSONValue.parse(new FileReader("history.json"));
            JSONArray jsonArray = (JSONArray) ob;

            for (Object an_array : jsonArray) {
                JSONObject jsonObj = (JSONObject) an_array;
                String id = (String) jsonObj.get("id");
                String author = (String) jsonObj.get("author");
                String message = (String) jsonObj.get("message");
                long timestamp = (long) jsonObj.get("timestamp");
                Message mes = new Message(id, author, message, timestamp);
                al.add(mes);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File error!");
        }
        catch (NullPointerException e) {
            System.out.println("Previous message history is empty!");
        }
        if (al.isEmpty()) {
            return "Current history is empty!";
        } else {
            return al.toString();
        }
    }

    public void writeCurrentHistoryToFile(ArrayList<Message> al) {
        try {
            JSONArray jsonArray = new JSONArray();
            convertMessageArrayToJsonArray(jsonArray, al);
            if(jsonArray.isEmpty()) {
                System.out.println("Current message history is empty!");
            } else {
                FileWriter fw = new FileWriter("history.json");
                fw.write(jsonArray.toJSONString());
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            System.out.println("Can't (re)write file!" + e.getMessage());
        }
    }

    public String getDate(long milliseconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }

    public Calendar convertStringDateToCalendarFormat(String str_date) {
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

    public String findByBate(String limit1, String limit2) {
        try {
            Object ob = JSONValue.parse(new FileReader("history.json"));
            JSONArray jsonArray = (JSONArray) ob;
            Calendar calDate1 = convertStringDateToCalendarFormat(limit1);
            Calendar calDate2 = convertStringDateToCalendarFormat(limit2);
            JSONArray jsonArrayDates = new JSONArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                long longTime = (long) jsonObj.get("timestamp");
                String strTime = getDate(longTime, "dd/MM/yyyy");
                Calendar calTime = convertStringDateToCalendarFormat(strTime);
                if (calTime.after(calDate1) && (calTime.before(calDate2))) {
                    jsonArrayDates.add(jsonArray.get(i));

                }
            }
            if (jsonArrayDates.isEmpty()) {
                return "For this period the history is empty";
            } else {
                return jsonArrayDates.toJSONString();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File error!");
        }
        return " ";
    }

    public String findByAuthor(String author) {
        try {
            Object ob = JSONValue.parse(new FileReader("history.json"));
            JSONArray jsonArray = (JSONArray) ob;
            JSONArray jsonArrayAuthors = new JSONArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                if (jsonObj.containsValue(author)) {
                    jsonArrayAuthors.add(jsonArray.get(i));
                }
            }
            if (jsonArrayAuthors.isEmpty()) {
                return "There is no messages by this author";
            } else {
                return jsonArrayAuthors.toJSONString();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File error!");
        }
        return " ";
    }

    public void deleteById(String id) {
        try {
            Object ob = JSONValue.parse(new FileReader("history.json"));
            JSONArray jsonArray = (JSONArray) ob;

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                if (jsonObj.containsValue(id)) {
                    jsonArray.remove(i);
                    System.out.println("Deleting has been done successfully!");
                } else {
                    System.out.println("There is no message with this id!");
                }
            }
            FileWriter fw = new FileWriter("history.json");
            fw.write(jsonArray.toJSONString());
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File error!");
        }
        catch (IOException e) {
            System.out.println("Can't rewrite history file");
        }
        catch (NullPointerException e) {
            System.out.println("History file is empty!");
        }
    }

    public String findByRegEx(String regExp) {
        try {
            Object ob = JSONValue.parse(new FileReader("history.json"));
            JSONArray jsonArray = (JSONArray) ob;
            JSONArray jsonArrayRegExp = new JSONArray();
            String str;

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                str = (String) jsonObj.get("message");
                Pattern p = Pattern.compile(regExp);
                Matcher m = p.matcher(str);
                if (m.matches()) {
                    jsonArrayRegExp.add(jsonArray.get(i));
                }
            }
            if (jsonArrayRegExp.isEmpty()) {
                return "There is no messages with such regular expression";
            } else {
                return jsonArrayRegExp.toString();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File error!");
        }
        catch (NullPointerException e) {
            System.out.println("History file is empty");
        }
        return " ";
    }

    public void showInterface() {
        System.out.println("Hey, user! What u gonna do?");
        System.out.println("1 - Add message");
        System.out.println("2 - Delete message by id");
        System.out.println("3 - Show current message history");
        System.out.println("4 - Load current message history to file");
        System.out.println("5 - Show all message history");
        System.out.println("6 - Find by period of time");
        System.out.println("7 - Find messages by author");
        System.out.println("8 - Find message by key word/lexeme");
        System.out.println("9 - Find message by regular expression");
        System.out.println("0 - Exit");
        System.out.println("Your choice? ");
    }
}
