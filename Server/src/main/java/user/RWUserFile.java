package user;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.Settings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс, выполняющий операции ведения и сохранения справочника пользователей.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-янв-2023 16:19:26
 */
public class RWUserFile {

    public static ConcurrentHashMap<String, User> userList = new ConcurrentHashMap<>();

     private static final String[] userColumnMapping = {"userNickname", "userName"};

    private final Settings settings = new Settings();

    public RWUserFile() {
    }

    /**
     * Метод читает файл справочника участников чата.
     * Результат представлен строкой данных json.
     */
    public String readUserFile() {

        StringBuilder jString = new StringBuilder();
        String fileName = Settings.getPathToFiles() + settings.getListOfUsersFile();

        try (BufferedReader jsonFile = new BufferedReader((new FileReader(fileName)))) {
            String s;
            while ((s = jsonFile.readLine()) != null) jString.append(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jString.toString();
    }

    /**
     * Метод выполняет разбор поступившей на вход json-строки и формирует список
     * ArrayList<User> учетных записей участников чата.
     * При формировании списка создаются экземпляры класса User и определяется
     * максимальный идентификатор экземпляра. В атрибут класса nextUserID помещается
     * следующее за максимальным значение.
     *
     * @param jsonString Строка, содержащая список участников чата в формате json
     */
    public void formUserList(String jsonString) throws ParseException {

        // разбираем строку на объекты класса User
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
                    case "userNickname" -> nick = attr;
                    case "userName" -> name = attr;
                    default -> throw new IllegalStateException("Unexpected value: " + attrName);
                }

                User user = new User(nick, name);
                assert nick != null;
                userList.put(nick, user);
            }
        }
    }

    public void writeUserList() {

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