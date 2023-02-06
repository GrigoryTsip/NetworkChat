package log;

import talkshow.ExitCode;

import static log.OperationType.SEND_MESSAGE;

/**
 * @author gntsi
 * @version 1.0
 * @created 04-���-2023 13:33:04
 */
public class SendMessage extends LogRecord {

	private final OperationType type = SEND_MESSAGE;

	public SendMessage(){ }

	/**
	 * ���� � ����� ��������.
	 */
	public void setData(){
		data = newVal;
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