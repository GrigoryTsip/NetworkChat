package stringmsg;

import talkshow.Message;

/**
 * ����� ���������� �� ������ ������ ����������� ���������.
 * @author gntsi
 * @version 1.0
 * @created 05-���-2023 18:16:51
 */
public class BuildStringMessage {

	/**
	 * ��������� ���������� ���������.
	 */
	private String buildMessage;
	public Message m_Message;

	public BuildStringMessage(){

	}

	/**
	 * ��������� ���������� ���������.
	 */
	public String getBuildMessage(){
		return buildMessage;
	}

	/**
	 * ��������� ���������� ���������.
	 */
	public void setBuildMessage(String newVal){
		buildMessage = newVal;
	}

	/**
	 * �����-������� ������ ����������� ���������.
	 * �� �����: ������ ������ Message, �� ��������� ������ �������� ���������
	 * ���������� ���������.
	 * �� ������: ������ ������ BuildStringMessage, ������� �������� ���������
	 * ���������.
	 */
	public void messageBuilder(Message mesg){

	}

	/**
	 * ����� ���������� ��������� ����������� ���������, ����������
	 * ���������� ��������������� ������� � ��������� ������ ��
	 * ������������ � �������� ���������.
	 *
	 * �� �����: ������ ������� ��������� ���������
	 */

}