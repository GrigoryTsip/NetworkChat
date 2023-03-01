package client;


import log.LogRecord;
import membermessage.MemberMessage;
import showmessage.ShowMessage;
import talkshow.Message;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * �����, ��������������� ��� ����������� � ������������ ������� (�����).
 * �������� ���������������� ������� ����� ������� � �� �����������.
 * ������������ ���������� ����� � ������� ��������.
 *
 * @author gntsi
 * @version 1.0
 * @created 22-���-2023 6:15:10
 */
public class ClientThreadService {

	/**
	 * ��� �������.
	 */
	public static ExecutorService threadPool = null;
	/**
	 * ������ ������� �������������� ���������.
	 */
	public static final int ABQ_CAPACITY = 30;
	/**
	 * ������� �����������  ���������.
	 */
	public static final ArrayBlockingQueue<MemberMessage> receiveMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);
	/**
	 * ������� ���������� ��������� �� ��������� � ����� � �������.
	 */
	public static final ArrayBlockingQueue<MemberMessage> parseMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);

	/**
	 * ������� ������������ ���������, ������� � �����������.
	 */
	public static final ArrayBlockingQueue<Message> logMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);
	/**
	 * ������� ����� �� ������ ���������� ���������.
	 */
	public static final ArrayBlockingQueue<Future<MemberMessage>> receiveMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
	/**
	 * ������� ����� �� ��������� � ����� ��������� � �������.
	 */
	public static final ArrayBlockingQueue<Future<MemberMessage>> parseMemberTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);

	/**
	 * ������� ����� �������� ���������.
	 */
	public static final ArrayBlockingQueue<Future<LogRecord>> logMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);

	public ClientThreadService(){

	}

	/**
	 * �������������� ��� �������.
	 * 
	 * @param threadsNumber    ���������� ������� � ����
	 */
	public void initPoolThreads(int threadsNumber){
		threadPool = Executors.newFixedThreadPool(threadsNumber);
	}

	/**
	 * ������ �������� ������� ������ �������� ���������.
	 */
	public void controlReadTask() throws ExecutionException, InterruptedException {
		ArrayList<Future<MemberMessage>> removableTask = new ArrayList<>();

		for (Future<MemberMessage> task : receiveMessageTask) {
		    if (task.isDone()) {
		        receiveMessages.put(task.get());
		        new MemberMessage().takeMemberMessage();
		        removableTask.add(task);
		    }
		}
	
		for (Future<MemberMessage> task : removableTask) {
		    receiveMessageTask.remove(task);
		}
	}

	/**
	 * ����� �������� ������� ��������� ��������� ��� ������ � ������� �
	 * �����������.
	 */
	public void controlPrepareTask() throws InterruptedException, ExecutionException {
		ArrayList<Future<MemberMessage>> removeableTask = new ArrayList<>();
	
		for (Future<MemberMessage> task : parseMemberTask) {
		    if (task.isDone()) {
		        MemberMessage msg = task.get();
				// Message msgLog = new Message(msg);
		        parseMessages.put(msg);
		       // logMessages.put(msgLog); // �������� ������� �� ���������� � ����������� �������
		        new ShowMessage().myTalkShows();
		        //new LogRecord().formLogRecord();
		        removeableTask.add(task);
		    }
		}
	
		for (Future<MemberMessage> task : removeableTask) {
		    parseMemberTask.remove(task);
		}
	}

	/**
	 * ������ �������� ������� ������ � ���.
	 */
	public void controlLogTask() throws ExecutionException, InterruptedException {
		ArrayList<Future<LogRecord>> removableTask = new ArrayList<>();
	
		for (Future<LogRecord> task : logMessageTask) {
		    if (task.isDone()) task.get();
			removableTask.add(task);		
		}
	
		for (Future<LogRecord> task : removableTask) {
		    logMessageTask.remove(task);
		}
	}

}