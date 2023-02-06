package stringmsg;

import talkshow.Message;

/**
 * Класс отвечающий за сборку строки посылаемого сообщения.
 * @author gntsi
 * @version 1.0
 * @created 05-фев-2023 18:16:51
 */
public class BuildStringMessage {

	/**
	 * Собранное посылаемое сообщение.
	 */
	private String buildMessage;
	public Message m_Message;

	public BuildStringMessage(){

	}

	/**
	 * Собранное посылаемое сообщение.
	 */
	public String getBuildMessage(){
		return buildMessage;
	}

	/**
	 * Собранное посылаемое сообщение.
	 */
	public void setBuildMessage(String newVal){
		buildMessage = newVal;
	}

	/**
	 * Метод-сборщик строки посылаемого сообщения.
	 * На входе: объект класса Message, на основании данных которого полностью
	 * собирается сообщение.
	 * На выходе: объект класса BuildStringMessage, который содержит собранное
	 * сообщение.
	 */
	public void messageBuilder(Message mesg){

	}

	/**
	 * Взять результаты обработки поступивших сообщений, дождавшись
	 * завершения соответствующих потоков и запустить потоки на
	 * формирование и отправку сообщений.
	 *
	 * На входе: массив потоков обработки сообщений
	 */

}