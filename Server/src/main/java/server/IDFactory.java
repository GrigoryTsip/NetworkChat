package server;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Фабрика по работе с идентификаторами объектов. Выполняет следующие операции:
 * формирование идентификатора;
 * сохранение объекта в потокобезопасной мапе (ключ - идентификатор);
 * выбор объекта по идентификатору из мапы.
 *
 * @author gntsi
 * @version 1.0
 * @updated 04-фев-2023 17:06:51
 */
public class IDFactory {

    /**
     * Общий счетчик идентификаторов для всех типов объектов.
     */
    private static final AtomicLong nextID = new AtomicLong();

    /**
     * Мапа для хранения объектов: ключ - идентификатор, значение - объект.
     */
    public static ConcurrentHashMap<Long, Object> objectList;

    /**
     * Мапа для хранения связей объектов:
     * ключ - идентификатор,
     * значение - множество идентификаторов связанных объектов.
     */
    public static ConcurrentHashMap<Long, Set<Long>> objectConnectList;

    public IDFactory() {
    }

    /**
     * Инициализация мапы и счетчика идентификаторов. Запускается из Server.
     */
    public void initFactory() {
        nextID.set(0);
        objectList = new ConcurrentHashMap<>();
        objectConnectList = new ConcurrentHashMap<>();
    }

    /**
     * Запрос на формирование идентификатора объекта и сохранение объекта в мапе.
     * Вызывается из конструкторов объектов.
     *
     * @param object Тип объекта, к которому относится ИД
     */
    public long buildID(Object object) {
        long id = nextID.getAndIncrement();
        objectList.put(id, object);
        return id;
    }

    /**
     * Мапа для хранения связей объектов:
     * ключ - идентификатор,
     * значение - множество идентификаторов связанных объектов.
     */
    public void objectConnection(long id1, long id2) {
        objCon(id1,id2);
        objCon(id2, id1);
    }

    private void objCon (long id1, long id2) {
        Set<Long> objectSet;
        if (objectList.containsKey(id1)) {
            objectSet = objectConnectList.get(id1);
        } else {
            objectSet = new HashSet<>();
        }
        objectSet.add(id2);
        objectConnectList.put(id1,objectSet);
    }

    /**
     * Найти объекты связанные с объектом, заданным идентификатором
     * Если объекта заданного идентификатором нет или связанные объекты
     * не найдены, возвращается null.
     *
     * @param id Идентификатор заданного объекта
     */
    public Set<Long> connectionObjects(long id) {
        if (objectConnectList.containsKey(id)) return objectConnectList.get(id);
        return null;
    }

    /**
     * Найти объект в мапе по идентификатору.
     * Если объект не найден, возвращается null.
     *
     * @param id Идентификатор искомого объекта
     */
    public Object getObject(long id) {
        if (objectList.containsKey(id)) return objectList.get(id);
        return null;
    }

    /**
     * Найти объекты заданного класса, связанные с объектом с
     * указанным идентификатором.
     * Если объект не найден, возвращается null.
     *
     * @param id Указанный идентификатор объекта
     * @param object Объект для определния заданного класса
     */
    public Set<Object> getRelatedObjects(Object object, long id) {
        Set<Object> objectSet = new HashSet<>();
        Set<Long> set = connectionObjects(id);
        if (set != null) {
            for (long j : set) {
                Object obj = getObject(j);
                if (obj != null) {
                    if (object.getClass() == obj.getClass()) objectSet.add(obj);
                }
            }
        }
        if (set == null || objectSet.isEmpty()) return null;
        return objectSet;
    }
}