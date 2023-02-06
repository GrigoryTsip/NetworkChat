package server;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ������� �� ������ � ���������������� ��������. ��������� ��������� ��������:
 * ������������ ��������������;
 * ���������� ������� � ���������������� ���� (���� - �������������);
 * ����� ������� �� �������������� �� ����.
 *
 * @author gntsi
 * @version 1.0
 * @updated 04-���-2023 17:06:51
 */
public class IDFactory {

    /**
     * ����� ������� ��������������� ��� ���� ����� ��������.
     */
    private static final AtomicLong nextID = new AtomicLong();

    /**
     * ���� ��� �������� ��������: ���� - �������������, �������� - ������.
     */
    public static ConcurrentHashMap<Long, Object> objectList;

    /**
     * ���� ��� �������� ������ ��������:
     * ���� - �������������,
     * �������� - ��������� ��������������� ��������� ��������.
     */
    public static ConcurrentHashMap<Long, Set<Long>> objectConnectList;

    public IDFactory() {
    }

    /**
     * ������������� ���� � �������� ���������������. ����������� �� Server.
     */
    public void initFactory() {
        nextID.set(0);
        objectList = new ConcurrentHashMap<>();
        objectConnectList = new ConcurrentHashMap<>();
    }

    /**
     * ������ �� ������������ �������������� ������� � ���������� ������� � ����.
     * ���������� �� ������������� ��������.
     *
     * @param object ��� �������, � �������� ��������� ��
     */
    public long buildID(Object object) {
        long id = nextID.getAndIncrement();
        objectList.put(id, object);
        return id;
    }

    /**
     * ���� ��� �������� ������ ��������:
     * ���� - �������������,
     * �������� - ��������� ��������������� ��������� ��������.
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
     * ����� ������� ��������� � ��������, �������� ���������������
     * ���� ������� ��������� ��������������� ��� ��� ��������� �������
     * �� �������, ������������ null.
     *
     * @param id ������������� ��������� �������
     */
    public Set<Long> connectionObjects(long id) {
        if (objectConnectList.containsKey(id)) return objectConnectList.get(id);
        return null;
    }

    /**
     * ����� ������ � ���� �� ��������������.
     * ���� ������ �� ������, ������������ null.
     *
     * @param id ������������� �������� �������
     */
    public Object getObject(long id) {
        if (objectList.containsKey(id)) return objectList.get(id);
        return null;
    }

    /**
     * ����� ������� ��������� ������, ��������� � �������� �
     * ��������� ���������������.
     * ���� ������ �� ������, ������������ null.
     *
     * @param id ��������� ������������� �������
     * @param object ������ ��� ���������� ��������� ������
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