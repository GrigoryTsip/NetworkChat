package server;


import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * ����� �������� �������.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:52:30
 */
public class Settings {

    /**
     * ����� ����� �������.
     */
    private static final int port = 8089;
    /**
     * ���� true, �� ������ println, printf ��� format ����� ������� �������� �����.
     */
    private static final boolean autoFlash = true;
    /**
     * ������ ���� � �������, ����������� � ���������.
     */
    public static final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    /**
     * ��������� ��������� ������������� ������.
     */
    private static final int reUseAddress = 4;
    /**
     * ���������� ������, ������������ ������ ���������.
     * ����� �� messageHead ������� ���� ������� ���������� ������.
     */
    public static final String messageSeparator = "@@_%";
    /**
     * ���������� ������ - ������� ���������� ������ � ����.
     * ���� � ��������� ���� ����� �� ��������, �� �� ���������� ��� ������������� �
     * ����.
     */
    private static final String talkShowExit = "/exit";
    /**
     * ���� � ������, ����������� ��� ������ ����.
     */
    private static final String pathToFiles = "src/main/resources/";

    /**
     * ���������� ������������� ������� � ���� �������.
     */
    private static final int numberOfThreads = 6;
    /**
     * JSON-���� ����������� ���������� ����.
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
     * ��������� ��������� ������������� ������.
     */
    public int getReUseAddress() {
        return reUseAddress;
    }

    /**
     * ���������� ������, ������������ ������ ���������.
     * ����� �� messageHead ������� ���� ������� ���������� ������.
     */
    public String getMessageSeparator() {
        return messageSeparator;
    }

    /**
     * ����� ����� �������.
     */
    public int getPort() {
        return port;
    }

    /**
     * ���������� ������ - ������� ���������� ������ � ����.
     * ���� � ��������� ���� ����� �� ��������, �� �� ���������� ��� ������������� �
     * ����.
     */
    public String getTalkShowExit() {
        return talkShowExit;
    }

    /**
     * ���������� ������������� ������� � ���� �������.
     */
    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    /**
     * ���� � ������, ����������� ��� ������ ����.
     */
    public static String getPathToFiles() {
        return pathToFiles;
    }

    /**
     * JSON-���� ����������� ���������� ����.
     */
    public String getListOfUsersFile() {
        return listOfUsersFile;
    }

}