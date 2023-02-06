package log;

import talkshow.ExitCode;

import static log.OperationType.RECEIVE_MESSAGE;

/**
 * @author gntsi
 * @version 1.0
 * @created 04-���-2023 13:13:29
 */
public class ReceiveMessage extends LogRecord {

	private final OperationType type = RECEIVE_MESSAGE;

	public ReceiveMessage(){ }

	/**
	 * ���� � ����� ��������.
	 */
	public void setData () {
		this.data = newVal;
	}

	/**
	 * ��� ����������� ���������.
	 */
	public void setSenderNick(String newVal){
		senderNick = newVal;
	}

	/**
	 * ���� ���������.
	 */
	public void setMessageBody(String newVal){
		messageBody = newVal;
	}

	/**
	 * ������ � ������ �����������. � �������� ������������ ������������ ������� @.
	 */
	public void setRecipientNick(String newVal){
		recipientNick = newVal;
	}

	/**
	 * ��� ���������� ��������
   */
	public void setCodeOfResult(ExitCode newVal){
		codeOfResult = newVal;
	}

}