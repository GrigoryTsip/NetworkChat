package membermessage;


import client.ClientThreadService;
import server.IDFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static client.ClientThreadService.parseMemberTask;
import static client.ClientThreadService.receiveMessages;
import static server.ThreadService.threadPool;

/**
 * Строковое сообщение: полученное или готовое к отправке.
 * @author gntsi
 * @version 1.0
 * @created 21-фев-2023 18:04:59
 */
public class MemberMessage {

	/**
	 * Идентификатор сообщения.
	 */
	private final long memberMessageID;
	/**
	 * Полученное строковое сообщение в необработанном виде.
	 */
	private String memberMessage;
	/**
	 * Ник владельца беседы, в рамках которой получено сообщение.
	 */
	private String talkOwnerNick;

	public MemberMessage(){
		this.memberMessageID = new IDFactory().buildID(this);
		new ParseMemberMessage(this);
	}

	/**
	 * Идентификатор сообщения.
	 */
	public long getMemberMessageID(){
		return memberMessageID;
	}

	/**
	 * Полученное строковое сообщение в необработанном виде.
	 */
	public String getMemberMessage(){
		return memberMessage;
	}

	/**
	 * Полученное строковое сообщение в необработанном виде.
	 */
	public void setMemberMessage(String newVal){
		memberMessage = newVal;
	}

	/**
	 * Ник владельца беседы, в рамках которой получено сообщение.
	 */
	public String getTalkOwnerNick(){
		return talkOwnerNick;
	}

	/**
	 * Ник владельца беседы, в рамках которой получено сообщение.
	 */
	public void setTalkOwnerNick(String newVal){
		talkOwnerNick = newVal;
	}

	/**
	 * Метод запуска потока на обработку поступивших сообщений:
	 * ждет, когда в очереди появятся результаты потока чтения сообщений,
	 * забирает сообщение из очереди и запускает поток обработки.
	 */
	public void takeMemberMessage() throws InterruptedException, ExecutionException {
		Callable<MemberMessage> stringParse = () -> {

			MemberMessage curMessage = receiveMessages.take();
			ParseMemberMessage parseMemberMessage =
					new ParseMemberMessage(curMessage);
			parseMemberMessage.parseMessage(curMessage);
			return curMessage;
		};

		ClientThreadService threadService = new ClientThreadService();

		Future<MemberMessage> taskParseMessage = threadPool.submit(stringParse);
		parseMemberTask.put(taskParseMessage);
		threadService.controlPrepareTask();
	}
}