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
 * ��������� ���������: ���������� ��� ������� � ��������.
 * @author gntsi
 * @version 1.0
 * @created 21-���-2023 18:04:59
 */
public class MemberMessage {

	/**
	 * ������������� ���������.
	 */
	private final long memberMessageID;
	/**
	 * ���������� ��������� ��������� � �������������� ����.
	 */
	private String memberMessage;
	/**
	 * ��� ��������� ������, � ������ ������� �������� ���������.
	 */
	private String talkOwnerNick;

	public MemberMessage(){
		this.memberMessageID = new IDFactory().buildID(this);
		new ParseMemberMessage(this);
	}

	/**
	 * ������������� ���������.
	 */
	public long getMemberMessageID(){
		return memberMessageID;
	}

	/**
	 * ���������� ��������� ��������� � �������������� ����.
	 */
	public String getMemberMessage(){
		return memberMessage;
	}

	/**
	 * ���������� ��������� ��������� � �������������� ����.
	 */
	public void setMemberMessage(String newVal){
		memberMessage = newVal;
	}

	/**
	 * ��� ��������� ������, � ������ ������� �������� ���������.
	 */
	public String getTalkOwnerNick(){
		return talkOwnerNick;
	}

	/**
	 * ��� ��������� ������, � ������ ������� �������� ���������.
	 */
	public void setTalkOwnerNick(String newVal){
		talkOwnerNick = newVal;
	}

	/**
	 * ����� ������� ������ �� ��������� ����������� ���������:
	 * ����, ����� � ������� �������� ���������� ������ ������ ���������,
	 * �������� ��������� �� ������� � ��������� ����� ���������.
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