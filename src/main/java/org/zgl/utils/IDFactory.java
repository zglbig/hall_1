package org.zgl.utils;

public class IDFactory {
    private static final Object lock = new Object();

    public static long getId(int id) {
        synchronized (lock) {
            String idInitStr = "18";
            if (id < 10) {
                idInitStr += "000" + id;
            } else if (id >= 10 && id < 100) {
                idInitStr += "00" + id;
            } else if (id >= 100 && id < 1000) {
                idInitStr += "0" + id;
            } else if (id >= 1000 && id < 100000) {
                idInitStr += id;
            }
            return Long.parseLong(idInitStr);
        }
    }

    public static void main(String[] args) {
//        for(int i = 0;i<1500;i++) {
//            System.out.println(getId());
//        }
    }
}
