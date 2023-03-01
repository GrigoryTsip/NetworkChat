package ru.netology;

import org.json.simple.parser.ParseException;
import server.Server;
import server.Settings;
import stringmsg.StringMessage;
import user.RWUserFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ParseException, InterruptedException, ExecutionException, IOException {

        // Инициализируем необходимые статические переменные
        Settings settings = new Settings(true);
        RWUserFile rwUserFile = new RWUserFile();
        Server server = new Server();
        StringMessage stringMessage = new StringMessage();

 //       rwUserFile.formUserList(rwUserFile.readUserFile());
//        server.runServer(settings.getPort(), settings.getReUseAddress());

        server.runServer(settings.getPort(), settings.getReUseAddress());
//        rwUserFile.writeUserList();

    }
}