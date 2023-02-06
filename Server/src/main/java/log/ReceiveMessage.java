package log;

import talkshow.ExitCode;

import static log.OperationType.RECEIVE_MESSAGE;

/**
 * @author gntsi
 * @version 1.0
 * @created 04-фев-2023 13:13:29
 */
public class ReceiveMessage extends LogRecord {

	private final OperationType type = RECEIVE_MESSAGE;

	public ReceiveMessage(){ }

	/**
	 * Дата и время операции.
	 */
	public void setData () {
		this.data = newVal;
	}

	/**
	 * Ник отправителя сообщения.
	 */
	public void setSenderNick(String newVal){
		senderNick = newVal;
	}

	/**
	 * Тело сообщения.
	 */
	public void setMessageBody(String newVal){
		messageBody = newVal;
	}

	/**
	 * Строка с никами получателей. В качестве разделителей используется ведущий @.
	 */
	public void setRecipientNick(String newVal){
		recipientNick = newVal;
	}

	/**
	 * Код завершения операции
   */
	public void setCodeOfResult(ExitCode newVal){
		codeOfResult = newVal;
	}

}