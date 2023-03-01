package client;


import log.LogRecord;
import membermessage.MemberMessage;
import showmessage.ShowMessage;
import talkshow.Message;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 *  ласс, предназначенный дл€ организации и обслуживани€ потоков (нитей).
 * ¬ключает потокозащищенные очереди задач потоков и их результатов.
 *  онтролирует завершение задач и очистку очередей.
 *
 * @author gntsi
 * @version 1.0
 * @created 22-фев-2023 6:15:10
 */
public class ClientThreadService {

	/**
	 * ѕул потоков.
	 */
	public static ExecutorService threadPool = null;
	/**
	 * –азмер очереди необработанных сообщений.
	 */
	public static final int ABQ_CAPACITY = 30;
	/**
	 * ќчередь поступивших  сообщений.
	 */
	public static final ArrayBlockingQueue<MemberMessage> receiveMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);
	/**
	 * ќчередь полученных сообщений на обработку и вывод в консоль.
	 */
	public static final ArrayBlockingQueue<MemberMessage> parseMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);

	/**
	 * ќчередь обработанных сообщений, готовых к логированию.
	 */
	public static final ArrayBlockingQueue<Message> logMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);
	/**
	 * ќчередь задач на чтение приход€щих сообщений.
	 */
	public static final ArrayBlockingQueue<Future<MemberMessage>> receiveMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
	/**
	 * ќчередь задач на обработку и вывод сообщений в консоль.
	 */
	public static final ArrayBlockingQueue<Future<MemberMessage>> parseMemberTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);

	/**
	 * ќчередь задач отправки сообщений.
	 */
	public static final ArrayBlockingQueue<Future<LogRecord>> logMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);

	public ClientThreadService(){

	}

	/**
	 * »нициализируем пул потоков.
	 * 
	 * @param threadsNumber     оличество потоков в пуле
	 */
	public void initPoolThreads(int threadsNumber){
		threadPool = Executors.newFixedThreadPool(threadsNumber);
	}

	/**
	 * —ервис контрол€ потоков чтени€ вход€щих сообщений.
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
	 * ћетод контрол€ потоков обработки сообщений дл€ вывода в консоль и
	 * логировани€.
	 */
	public void controlPrepareTask() throws InterruptedException, ExecutionException {
		ArrayList<Future<MemberMessage>> removeableTask = new ArrayList<>();
	
		for (Future<MemberMessage> task : parseMemberTask) {
		    if (task.isDone()) {
		        MemberMessage msg = task.get();
				// Message msgLog = new Message(msg);
		        parseMessages.put(msg);
		       // logMessages.put(msgLog); // написать переход от строкового к внутреннему формату
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
	 * —ервис контрол€ потоков записи в лог.
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