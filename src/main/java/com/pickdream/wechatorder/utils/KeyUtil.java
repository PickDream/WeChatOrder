package com.pickdream.wechatorder.utils;


import java.util.concurrent.atomic.AtomicLong;

public final class KeyUtil {
    private static AtomicLong seq
            = new AtomicLong(100_000);

    public static String getUniqueKey(){
        return System.currentTimeMillis()
                +String.valueOf(seq.getAndIncrement());
    }
}
