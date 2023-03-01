package stringmsg;

import server.IDFactory;
import talkshow.Message;
import talkshow.MessageFactory;
import talkshow.TalkShow;
import talkshow.UserInTalk;
import user.ActiveUser;
import user.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static user.RWUserFile.userList;

/**
 * Класс для осуществления выхода из чата.
 *
 * @author gntsi
 * @version 1.0
 * @created 05-фев-2023 17:42:13
 */
public class ExitTheChat extends ParseStringMessage {

    public ExitTheChat() {
        super();
    }

    public Message exitTheChat(Message mesg) {

        MessageFactory msgFactory = new MessageFactory();

        // определяем участника чата, который завершает беседу (user)
        User user = userList.get(this.senderNick);
        if (user != null) {
            msgFactory.setSender(mesg, this.senderNick);

            //выбираем соответствующий участнику объект состояния (ActiveUser)
            //и деактивируем его
            ActiveUser activeUser = new ActiveUser();
            IDFactory idFactory = new IDFactory();
            ArrayList<Object> list = idFactory.getRelatedObjects(ActiveUser.class, user.getID());
            if (!list.isEmpty()) {
                activeUser = (ActiveUser) list.remove(0);
                activeUser.setCondition(false);

                ArrayList<Object> arrObj = new ArrayList<>();
                //находим беседы, связанные с участником чата
                list = idFactory.getRelatedObjects(UserInTalk.class, user.getID());

                for (Object o : list) {
                    UserInTalk u = (UserInTalk) o;
                    if (u.getUserID() == user.getID()) {
                        arrObj.add(idFactory.getObject(u.getIDTalkShow()));
                        u.setUserID(0);
                    }
                }
                //проверяем, есть ли активные беседы, в которых участвовал участник
                if (!arrObj.isEmpty()) isActiveTalks(arrObj);
            }
        }
        return mesg;
    }

    private void isActiveTalks(ArrayList<Object> arrObj) {
        for (Object o : arrObj) {
            TalkShow t = (TalkShow) o;

            ArrayList<Object> oUserInTalk;
            oUserInTalk = new IDFactory().getRelatedObjects(UserInTalk.class, t.getIDTalkShow());

            boolean active = false;
            if (!oUserInTalk.isEmpty()) {
                for (Object ou : oUserInTalk) {
                    UserInTalk u = (UserInTalk) ou;
                    if (u.getUserID() != 0) {
                        active = true;
                        break;
                    }
                }
            }

            if (!active) {
                t.setActive(false);
                t.setDataEndOfTalk(new GregorianCalendar().getTime());
            }
        }
    }
}