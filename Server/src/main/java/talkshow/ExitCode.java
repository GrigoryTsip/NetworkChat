package talkshow;


/**
 * Коды завершения операций в чате.
 * @author gntsi
 * @version 1.0
 * @created 30-янв-2023 16:19:26
 */
public enum ExitCode {
	/**
	 * Операция успешно исполнена
	 */
	SUCCESS,
	/**
	 * Ошибка инициализации Сервера
	 */
	SERVER_NOT_INITIALIZE,
	/**
	 * Ошибка инициализации сокета Клиента
	 */
	CLIENT_SOCKET_NOT_INITIALIZE,
	/**
	 * Неверный формат сообщения
	 */
	WRONG_MESSAGE_FORMAT,
	/**
	 * Пользователь с таким ником уже зарегистрирован
	 */
	NICK_ALREADY_USED,
	/**
	 * Для регистрации в чате необходимо указать имя пользователя
	 */
	MISSING_USER_NAME,
	/**
	 * Для участия в чате необходимо зарегистрироваться
	 */
	USER_NOT_REGISTER,
	/**
	 * Неверный идентификатор беседы
	 */
	FALSE_TALKSHOW_IDENTIFIER,
	/**
	 * Пользователи со следующими никами не зарегистрированы в чате:
	 */
	USERS_NOT_IN_CHAT,
	/**
	 * Пользователи со слудующими никами не активны в чате:
	 */
	USERS_NOT_ACTIVE;

	/**
	 * По коду возвращает строку сообщения.
	 * 
	 * @param code    Код результата операции.
	 */
	public String toString(ExitCode code){
	
			String s = "";
			switch (code) {
				case SUCCESS -> s = "Сервер запущен";
				case SERVER_NOT_INITIALIZE -> s = "Ошибка инициализации сервера";
				case CLIENT_SOCKET_NOT_INITIALIZE -> s = "Ошибка инициализации сокета клиента";
				case WRONG_MESSAGE_FORMAT -> s = "Неверный формат сообщения";
				case NICK_ALREADY_USED -> s = "Пользователь с таким ником уже зарегистрирован"; 
				case MISSING_USER_NAME -> s = "Для регистрации в чате необходимо указать имя пользователя";
				case USER_NOT_REGISTER -> s = "Для участия в чате необходимо зарегистрироваться";
				case FALSE_TALKSHOW_IDENTIFIER -> s = "Неверный идентификатор беседы";
				case USERS_NOT_IN_CHAT -> s = "Пользователя со следующими никами не зарегистрированы в чате: ";
				case USERS_NOT_ACTIVE -> s = "Пользователи со следующими никами не активны в чате: ";
			}
			return s;
	}

	/**
	 * 
	 * @param code    Код результата операции.
	 */
	public void resultMessage(ExitCode code){
		System.out.println(toString(code));
	}
}