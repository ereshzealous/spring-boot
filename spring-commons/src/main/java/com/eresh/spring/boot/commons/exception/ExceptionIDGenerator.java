package com.eresh.spring.boot.commons.exception;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
public class ExceptionIDGenerator {
    private static String hostname;
    private static final String ISO_FMT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat dateformat = new SimpleDateFormat(ISO_FMT);

    // Prevent instantiation
    private ExceptionIDGenerator() {
    }

    static {
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            hostname = "Unknown Host";
        }
    }

    public static synchronized String getExceptionID() {
        StringBuilder exceptionId = new StringBuilder(80);
        Date now = new Date();
        exceptionId.append(hostname);
        exceptionId.append("|");
        exceptionId.append(dateformat.format(now));
        exceptionId.append("|");
        exceptionId.append(nextSeq());
        return exceptionId.toString();
    }

    /**
     * Generates a four digit wrapping sequence number.
     * @return
     */
    private static int nextSeq() {
        seq++;
        if (seq < 1111 || seq > 9999) {
            seq = 1111;
        }
        return seq;
    }

    private static int seq;
}
