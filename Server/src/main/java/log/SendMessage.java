package log;

import talkshow.ExitCode;

import static log.OperationType.SEND_MESSAGE;

/**
 * @author gntsi
 * @version 1.0
 * @created 04-фев-2023 13:33:04
 */
public class SendMessage extends LogRecord {

	private final OperationType type = SEND_MESSAGE;

	public SendMessage(){ }

	/**
	 * Дата и время операции.
	 */
	public void setData(){
		data = newVal;
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