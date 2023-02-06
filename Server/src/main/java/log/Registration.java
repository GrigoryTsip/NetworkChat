package log;

import talkshow.ExitCode;

import static log.OperationType.REGISTRATION;

/**
 * @author gntsi
 * @version 1.0
 * @created 04-фев-2023 13:13:29
 */
public class Registration extends LogRecord {

	private final OperationType type = REGISTRATION;

	public Registration(){ }

	/**
	 * Дата и время операции.
	*/
	public void setData(){
		data = newVal;
	}

	/**
	 * Ник отправителя сообщения.
	 */
	public void setSenderNick(){
		senderNick = newVal;
	}

	/**
	 * Код завершения операции
	 */
	public void setCodeOfResult(ExitCode newVal){
		codeOfResult = newVal;
	}

}