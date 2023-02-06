package log;

import talkshow.ExitCode;

import static log.OperationType.REGISTRATION;

/**
 * @author gntsi
 * @version 1.0
 * @created 04-���-2023 13:13:29
 */
public class Registration extends LogRecord {

	private final OperationType type = REGISTRATION;

	public Registration(){ }

	/**
	 * ���� � ����� ��������.
	*/
	public void setData(){
		data = newVal;
	}

	/**
	 * ��� ����������� ���������.
	 */
	public void setSenderNick(){
		senderNick = newVal;
	}

	/**
	 * ��� ���������� ��������
	 */
	public void setCodeOfResult(ExitCode newVal){
		codeOfResult = newVal;
	}

}