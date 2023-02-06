package talkshow;


/**
 * ���� ���������� �������� � ����.
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:19:26
 */
public enum ExitCode {
	/**
	 * �������� ������� ���������
	 */
	SUCCESS,
	/**
	 * ������ ������������� �������
	 */
	SERVER_NOT_INITIALIZE,
	/**
	 * ������ ������������� ������ �������
	 */
	CLIENT_SOCKET_NOT_INITIALIZE,
	/**
	 * �������� ������ ���������
	 */
	WRONG_MESSAGE_FORMAT,
	/**
	 * ������������ � ����� ����� ��� ���������������
	 */
	NICK_ALREADY_USED,
	/**
	 * ��� ����������� � ���� ���������� ������� ��� ������������
	 */
	MISSING_USER_NAME,
	/**
	 * ��� ������� � ���� ���������� ������������������
	 */
	USER_NOT_REGISTER,
	/**
	 * �������� ������������� ������
	 */
	FALSE_TALKSHOW_IDENTIFIER,
	/**
	 * ������������ �� ���������� ������ �� ���������������� � ����:
	 */
	USERS_NOT_IN_CHAT,
	/**
	 * ������������ �� ���������� ������ �� ������� � ����:
	 */
	USERS_NOT_ACTIVE;

	/**
	 * �� ���� ���������� ������ ���������.
	 * 
	 * @param code    ��� ���������� ��������.
	 */
	public String toString(ExitCode code){
	
			String s = "";
			switch (code) {
				case SUCCESS -> s = "������ �������";
				case SERVER_NOT_INITIALIZE -> s = "������ ������������� �������";
				case CLIENT_SOCKET_NOT_INITIALIZE -> s = "������ ������������� ������ �������";
				case WRONG_MESSAGE_FORMAT -> s = "�������� ������ ���������";
				case NICK_ALREADY_USED -> s = "������������ � ����� ����� ��� ���������������"; 
				case MISSING_USER_NAME -> s = "��� ����������� � ���� ���������� ������� ��� ������������";
				case USER_NOT_REGISTER -> s = "��� ������� � ���� ���������� ������������������";
				case FALSE_TALKSHOW_IDENTIFIER -> s = "�������� ������������� ������";
				case USERS_NOT_IN_CHAT -> s = "������������ �� ���������� ������ �� ���������������� � ����: ";
				case USERS_NOT_ACTIVE -> s = "������������ �� ���������� ������ �� ������� � ����: ";
			}
			return s;
	}

	/**
	 * 
	 * @param code    ��� ���������� ��������.
	 */
	public void resultMessage(ExitCode code){
		System.out.println(toString(code));
	}
}