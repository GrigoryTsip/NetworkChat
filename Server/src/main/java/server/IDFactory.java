//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class IDFactory {
    private static final AtomicLong nextID = new AtomicLong();
    public static ConcurrentHashMap<Long, Object> objectList = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Long, HashSet<Long>> objectConnectList = new ConcurrentHashMap<>();

    public IDFactory() {
    }

    public void initFactory() {
        nextID.set(1L);
    }

    public long buildID(Object object) {
        long id = nextID.getAndIncrement();
        objectList.put(id, object);
        return id;
    }

    public void objectConnection(long id1, long id2) {
        this.objCon(id1, id2);
        this.objCon(id2, id1);
    }

    private void objCon(long id1, long id2) {
        HashSet<Long> objectSet = new HashSet<>();
        if (objectConnectList.containsKey(id1)) {
            objectSet.addAll(objectConnectList.get(id1));
        }
        objectSet.add(id2);
        objectConnectList.put(id1, objectSet);
    }

    public Set<Long> connectionObjects(long id) {
        return objectConnectList.getOrDefault(id, null);
    }

    public Set<Object> allClassInstant(Class cls) {
        Set<Object> instant = new HashSet<>();
        Set<Object> classInstant = new HashSet<>();

        instant = (HashSet<Object>) objectList.values();
        for (Object o : instant) if (cls == o.getClass()) classInstant.add(o);
        return classInstant;
    }

    public Object getObject(long id) {
        return objectList.getOrDefault(id, null);
    }

    public ArrayList<Object> getRelatedObjects(Class cls, long id) {
        ArrayList objectList = new ArrayList();
        Set<Long> set = this.connectionObjects(id);
        if (set != null) {
            for (long j : set) {
                Object obj = this.getObject(j);
                if (obj != null && cls == obj.getClass()) objectList.add(obj);
            }
        }
        return set != null && !objectList.isEmpty() ? objectList : null;
    }
}
