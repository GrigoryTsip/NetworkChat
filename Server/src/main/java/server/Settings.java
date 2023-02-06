package server;


import log.LogRecord;
import stringmsg.StringMessage;
import talkshow.Message;
import talkshow.TalkShow;
import user.ActiveUser;
import user.RWUserFile;

import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public static final DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    /**
     * ��������� ��������� ������������� ������.
     */
    private static final int reUseAddress = 4;
    /**
     * ���������� ������, ������������ ������ ���������.
     * ����� �� messageHead ������� ���� ������� ���������� ������.
     */
    private static final String messageSeparator = "@@_%";
    /**
     * ���������� ������ - ������� ���������� ������ � ����.
     * ���� � ��������� ���� ����� �� ��������, �� �� ���������� ��� ������������� �
     * ����.
     */
    private static final String talkShowExit = "/exit";
    /**
     * ���� � ������, ����������� ��� ������ ����.
     */
    private static final String pathToFiles = "Server/src/main/resources/";
    /**
     * ���� ����������� �������� � ����.
     */
    private static final String logFile = "File.log";
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

        IDFactory idFactory = new IDFactory();
        idFactory.initFactory();
        /*
        new Message(begin);
        new TalkShow(begin);
        new RWUserFile(begin);
        new StringMessage(begin);
        new ActiveUser(begin);
        */

       referenceToLogFile = new LogRecord().openLogFile(pathToFiles, logFile);
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
    public String getPathToFiles() {
        return pathToFiles;
    }

    /**
     * ���� ����������� �������� � ����.
     */
    public String getLogFile() {
        return logFile;
    }

    /**
     * JSON-���� ����������� ���������� ����.
     */
    public String getListOfUsersFile() {
        return listOfUsersFile;
    }

}