package user;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.IDFactory;
import server.Settings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * �����, ����������� �������� ������� � ���������� ����������� �������������.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:19:26
 */
public class RWUserFile {

    public static ConcurrentHashMap<String, User> userList = new ConcurrentHashMap<>();

    /**
     * ��������������� ��������� ��� ������������� �
     * �������� �������� � ����� ������������� �������.
     */
    private static final Integer monitor = 0;

    protected long maxUserID = 0;
    private static final String[] userColumnMapping = {"userID", "userNickname", "userName"};

    private final Settings settings = new Settings();

    public RWUserFile() {
    }

    /**
     * ����� ������ ���� ����������� ���������� ����.
     * ��������� ����������� ������� ������  json.
     */
    public String readUserFile() {

        StringBuilder jString = new StringBuilder();
        String fileName = settings.getPathToFiles() + settings.getListOfUsersFile();

        try (BufferedReader jsonFile = new BufferedReader((new FileReader(fileName)))) {
            String s;
            while ((s = jsonFile.readLine()) != null) jString.append(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jString.toString();
    }

    /**
     * ����� ��������� ������ ����������� �� ���� json-������ � ��������� ������
     * ArrayList<User> ������� ������� ���������� ����.
     * ��� ������������ ������ ��������� ���������� ������ User � ������������
     * ������������ ������������� ����������. � ������� ������ nextUserID ����������
     * ��������� �� ������������ ��������.
     *
     * @param jsonString ������, ���������� ������ ���������� ���� � ������� json
     */
    public void formUserList(String jsonString) throws ParseException {


        // ��������� ������ �� ������� ������� AboutGoods � Order
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jsonString);
        JSONArray jsonObj = (JSONArray) obj;

        for (Object o : jsonObj) {
            JSONObject jsonElement = (JSONObject) o;
            for (String attrName : userColumnMapping) {
                String attr = String.valueOf(jsonElement.get(attrName));

                long id = 0;
                String nick = null;
                String name = null;
                switch (attrName) {
                    case "userID" -> id = classIDValue(attr);
                    case "userNickname" -> nick = attr;
                    case "userName" -> name = attr;
                    default -> throw new IllegalStateException("Unexpected value: " + attrName);
                }

                assert nick != null;
                userList.put(nick, new User(id, nick, name));
            }
        }
    }

    protected long classIDValue(String id) {
        long idUser = Long.parseLong(id);
        maxUserID = Math.max(maxUserID, idUser);
        return idUser;
    }

    public void writeOrderList() {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setPrettyPrinting()
                .create();
        Collection<User> collection = userList.values();
        try (FileWriter file = new FileWriter(settings.getPathToFiles() + settings.getListOfUsersFile())) {
            file.write(gson.toJson(collection));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}