package stringmsg;

import server.IDFactory;
import talkshow.Message;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.*;

import static server.Server.*;

/**
 * Класс представляющий получаемое или отправляемое сообщение.
 * Каждое получаемое сообщение представляет собой строку, включающую:
 * разделитель - подстрока messageSeparator и строкового представления даты и
 * времени его отправки;
 * далее за разделителем идет идентификатор беседы (idTalkShow) и
 * разделитель; если идентификатор не определен, то подряд идут два разделителя;
 * следующий блок - адрес отправителя, представленный ником отправителя
 * (userNickname) и разделитель;
 * затем располагается блок с никами получателей сообщения, представленными
 * никами участников беседы; ники в этом блоке разделены "точкой с запятой"; блок
 * завершается разделителем;
 * после блока получателей идет собственно тело сообщения; при регистрации
 * участника чата в теле сообщения передается его имя;
 * конец сообщения обозначен разделителем.
 * <p>
 * Обработка сообщения порождает ответное сообщение отправителю, если в сообщении
 * обнаружены ошибки. Строка ответного сообщения об ошибке:
 * начальный разделитель;
 * представления даты и времени его отправки ответного сообщения;
 * далее за разделителем помещается код ошибки обработки исходного сообщения
 * и разделитель;
 * следующий блок - нераспознанные ники и ники не зарегистрированных в чате
 * адресатов (если есть) и разделитель;
 * затем располагается блок с никами неактивных пользователей, которым не
 * удалось отправить сообщение (если есть); ники в этом блоке разделены "точкой с
 * запятой"; блок завершается разделителем;
 * далее идет блок тела сообщения - повтор полученного сообщения для
 * визуального сопоставления ответа с исходным сообщением;
 * конец сообщения обозначен разделителем.
 * <p>
 * Передаваемые Сервером сообщения адресатам в точности дублируют исходное
 * сообщение, за исключением блока получателей - в этом блоке только один ник.
 *
 * @author gntsi
 * @version 1.0
 * @updated 02-фев-2023 10:55:40
 */
public class StringMessage {

    /**
     * Строка полученного или отправляемого сообщения.
     */
    private final long stringMessageID;
    private String message;
    /**
     * Объект (сокет), определяющий каналы взаимодействия Сервера
     * и каждого участника чата.
     */
    private Socket userSocket;

    public StringMessage() {
        IDFactory idFactory = new IDFactory();
        this.stringMessageID = idFactory.buildID(this);
    }

    /**
     * Конструктор класса. Принимает необработанное сообщение в виде строки для
     * дальнейшей обработки.
     *
     * @param message Полученное сообщение в необработанном виде
     */

    public StringMessage(String message) {
        IDFactory idFactory = new IDFactory();
        this.stringMessageID = idFactory.buildID(this);
        this.message = message;
    }

    /**
     * Выходной канал, по которому должен быть отправлен ответ.
     */
    public void setUserSocket(Socket newVal) {
        this.userSocket = newVal;
    }

    public Socket getUserSocket() {
        return this.userSocket;
    }

    /**
     * Строка полученного или отправляемого сообщения.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Строка полученного или отправляемого сообщения.
     */
    public void setMessage(String newVal) {
        message = newVal;
    }

    /**
     * Получить ИД записи (например, для привязки объекта Message).
     */
    public long getStringMessageID() {
        return this.stringMessageID;
    }

    /**
     * Конструктор класса. Принимает необработанное сообщение в виде строки для
     * дальнейшей обработки.
     *
     * @param message Полученное сообщение в необработанном виде
     * @param userSocket      Ссылка на сокет участника чата
     */
    public StringMessage(String message, Socket userSocket) {
        IDFactory idFactory = new IDFactory();
        this.stringMessageID = idFactory.buildID(this);
        this.message = message;
        this.userSocket = userSocket;
    }

    public void takeStringMessage() throws InterruptedException, ExecutionException {

        Callable<Message> stringParse = () -> {

            IDFactory idFactory = new IDFactory();

            ParseStringMessage parseStringMessage = new ParseStringMessage();
            StringMessage curMessage = stringMessages.take();
            idFactory.objectConnection(
                    parseStringMessage.getIDParseStringMessage(),
                    curMessage.getStringMessageID());
            return parseStringMessage.parseMessage(curMessage);
        };

        ArrayBlockingQueue<Future<StringMessage>> removableTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
        while (!readMessageTask.isEmpty()) {
            for (Future<StringMessage> taskReadMessage : readMessageTask) {
                if (taskReadMessage.isDone()) {
                    removableTask.put(taskReadMessage);

                    stringMessages.put(taskReadMessage.get());
                    taskReadMessage.cancel(true);
                }
            }

            while (!removableTask.isEmpty()) {
                Future<StringMessage> task = removableTask.take();
                readMessageTask.remove(task);
                task.cancel(true);

                Future<Message> taskParseMessage = threadPool.submit(stringParse);
                parseMessageTask.put(taskParseMessage);
            }
        }
    }

    public void buildSendMessage() {

    }
}