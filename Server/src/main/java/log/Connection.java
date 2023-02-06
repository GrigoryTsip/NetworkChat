package log;

import talkshow.ExitCode;

import static log.OperationType.CONNECTION;

/**
 * @author gntsi
 * @version 1.0
 * @created 04-фев-2023 13:13:29
 */
public class Connection extends LogRecord {

	private final OperationType type = CONNECTION;

	public Connection(){ super(); }

	/**
	 * Дата и время операции.
	 */
	public void setData(){
		super.setData();
	}

	/**
	 * Ник отправителя сообщения.
	 */
	public void setSenderNick(String newVal){
		senderNick = newVal;
	}

	/**
	 * Код завершения операции
   */
	public void setCodeOfResult(ExitCode newVal){
		codeOfResult = newVal;
	}

}