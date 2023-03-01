package server;


import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Класс настроек Сервера.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-янв-2023 16:52:30
 */
public class Settings {

    /**
     * Номер порта Сервера.
     */
    private static final int port = 8089;
    /**
     * Если true, то методы println, printf или format будут очищать выходной буфер.
     */
    private static final boolean autoFlash = true;
    /**
     * Формат даты и времени, применяемый в программе.
     */
    public static final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    /**
     * Разрешено повторное использование адреса.
     */
    private static final int reUseAddress = 4;
    /**
     * Символьная строка, обозначающая начало сообщения.
     * Сразу за messageHead следует блок адресов участников беседы.
     */
    public static final String messageSeparator = "@@_%";
    /**
     * Символьная строка - команда завершения беседы в чате.
     * Если у участника чата бесед не осталось, то он помечается как отсутствующий в
     * чате.
     */
    private static final String talkShowExit = "/exit";
    /**
     * Путь к файлам, необходимым для работы чата.
     */
    private static final String pathToFiles = "src/main/resources/";

    /**
     * Количество резервируемых потоков в пуле потоков.
     */
    private static final int numberOfThreads = 6;
    /**
     * JSON-файл справочника участников чата.
     */
    private static final String listOfUsersFile = "Users.json";

    public static Calendar calendar = new GregorianCalendar();

    private static FileWriter referenceToLogFile;

    public Settings(boolean begin) {
        new IDFactory().initFactory();
        new ThreadService().initPoolThreads(numberOfThreads);
    }

    public Settings() {
    }

    public FileWriter getReferenceToLogFile() {
        return referenceToLogFile;
    }

    /**
     * Разрешено повторное использование адреса.
     */
    public int getReUseAddress() {
        return reUseAddress;
    }

    /**
     * Символьная строка, обозначающая начало сообщения.
     * Сразу за messageHead следует блок адресов участников беседы.
     */
    public String getMessageSeparator() {
        return messageSeparator;
    }

    /**
     * Номер порта Сервера.
     */
    public int getPort() {
        return port;
    }

    /**
     * Символьная строка - команда завершения беседы в чате.
     * Если у участника чата бесед не осталось, то он помечается как отсутствующий в
     * чате.
     */
    public String getTalkShowExit() {
        return talkShowExit;
    }

    /**
     * Количество резервируемых потоков в пуле потоков.
     */
    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    /**
     * Путь к файлам, необходимым для работы чата.
     */
    public static String getPathToFiles() {
        return pathToFiles;
    }

    /**
     * JSON-файл справочника участников чата.
     */
    public String getListOfUsersFile() {
        return listOfUsersFile;
    }

}