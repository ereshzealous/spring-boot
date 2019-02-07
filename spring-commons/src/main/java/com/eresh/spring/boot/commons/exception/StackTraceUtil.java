package com.eresh.spring.boot.commons.exception;

import java.io.*;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
public class StackTraceUtil {
    private StackTraceUtil() {
        // Prevent instantiation
    }

    /**
     * This method takes a exception as an input argument and returns the stacktrace as a string.
     */
    public static String getStackTrace(Throwable exception) {
        if (exception == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        //CHECKSTYLE:OFF
        exception.printStackTrace(pw);
        //CHECKSTYLE:ON
        return sw.toString();
    }

    public static String getAnalysisStackTrace(Throwable e) {
        if (e == null) {
            return null;
        }
        if (e instanceof RemoteException && e.getCause() != null) {
            return getAnalysisStackTrace(e.getCause());
        }
        StringBuilder output = new StringBuilder(1024);

        output.append(StackTraceUtil.getAbbreviatedStackTraceWithoutCause(0, e, false));
        Throwable root = e;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        output.append("Root analysis: ");
        output.append(StackTraceUtil.getFilteredAbbreviatedStackTraceWithoutCause(-1, root, false, ".*com\\.viyatra\\..*"));

        output.append("StackTrace: ");
        output.append(StackTraceUtil.getAbbreviatedStackTrace(4, 2, 15, e));
        return output.toString();
    }

    /**
     * Gets a stack trace with the specified number of "at"
     * lines in the top exception, root exception, and intervening exceptions
     * -1 for any number of lines causes all lines to be returned
     * if the root and top exception are the same exception, then
     * rootLines wins
     *
     * @param topLines
     * @param interveningLines
     * @param rootLines
     * @param exception
     * @return
     */
    private static String getAbbreviatedStackTrace(int topLines, int interveningLines, int rootLines, Throwable e) {
        StringBuilder output = new StringBuilder(1024);
        boolean first = true;
        while (e != null) {
            if (e.getCause() != null) {
                int lines = interveningLines;
                if (first) {
                    first = false;
                    lines = topLines;
                }
                output.append(getAbbreviatedStackTraceWithoutCause(lines, e, true));
                output.append("Caused by: ");
            } else {
                output.append(getAbbreviatedStackTraceWithoutCause(rootLines, e, true));
            }

            e = e.getCause();
        }

        return output.toString();
    }

    /**
     * Gets only the first few "at" lines for an exception, ignoreing
     * the "Caused by" lines.
     *
     * @param atLinesMax
     * @param exception
     * @param showMore if true, then "...n more" is appended as necessary
     * @return
     */
    public static String getAbbreviatedStackTraceWithoutCause(int atLinesMax, Throwable exception, boolean showMore) {
        return getFilteredAbbreviatedStackTraceWithoutCause(atLinesMax, exception, showMore, ".*");
    }

    /**
     * Gets only the first few "at" lines for an exception, ignoring
     * the "Caused by" lines, filtered by the given regular expression
     *
     * @param atLinesMax
     * @param exception
     * @param showMore if true, then "...n more" is appended as necessary
     * @return
     */
    private static String getFilteredAbbreviatedStackTraceWithoutCause(int atLinesMax, Throwable exception, boolean showMore, String expression) {
        Pattern regex = Pattern.compile(expression);

        try {
            String fullStackTrace = getStackTrace(exception);
            StringBuilder output = new StringBuilder(4096);

            BufferedReader reader = new BufferedReader(new StringReader(fullStackTrace));
            int atLinesCount = 0;
            boolean foundCause = false;
            String line = reader.readLine();
            while (foundCause == false && line != null) {
                if (line.startsWith("\tat ")) {
                    atLinesCount++;
                    if (atLinesCount > atLinesMax && atLinesMax != -1) {
                        break;
                    }
                }

                if (line.startsWith("Caused by:")) {
                    foundCause = true;
                    break;
                }

                if (!line.startsWith("\tat ") || regex.matcher(line).matches()) {
                    output.append(line);
                    output.append(SystemProperties.LINE_SEPARATOR);
                }
                line = reader.readLine();
            }

            if (showMore && !foundCause) {
                int skippedAtLinesCount = 0;
                while (foundCause == false && line != null) {
                    if (line.startsWith("\tat ") && regex.matcher(line).matches()) {
                        skippedAtLinesCount ++;
                    } else if (line.trim().startsWith("Caused by")) {
                        foundCause = true;
                        break;
                    }

                    line = reader.readLine();
                }

                if (skippedAtLinesCount > 0) {
                    output.append("\t... " + skippedAtLinesCount + " more");
                    output.append(SystemProperties.LINE_SEPARATOR);
                }
            }

            return output.toString();
        }
        catch (IOException e) {
            // um, IOException reading from a string...stupid Sun.  This should
            // be an unchecked exception...but maybe they all should be
            throw new RuntimeException(e);
        }
    }

    /**
     * Get an abbreviated stack trace that only includes entries for com.cubic packages.
     */
    public static String getAbbreviatedCubicStackTrace(Throwable exception) {
        Pattern regex = Pattern.compile(".*com\\.cubic\\..*");
        int atLinesMax = -1;
        int outputAtLinesMax = 8;

        try {
            String fullStackTrace = getStackTrace(exception);
            StringBuilder output = new StringBuilder(2048);

            BufferedReader reader = new BufferedReader(new StringReader(fullStackTrace));
            int atLinesCount = 0;
            int outputAtLines = 0;
            String line = null;
            String causedByLine = null;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("\tat ")) {
                    atLinesCount++;
                    if (atLinesCount > atLinesMax && atLinesMax != -1) {
                        break;
                    }
                }

                if (line.startsWith("Caused by:")) {
                    causedByLine = line;
                    continue;
                }

                if (line.startsWith("\tat ") && regex.matcher(line).matches()) {
                    if (causedByLine != null) {
                        output.append(causedByLine);
                        output.append(SystemProperties.LINE_SEPARATOR);
                        causedByLine = null;
                    }
                    output.append(line);
                    output.append(SystemProperties.LINE_SEPARATOR);
                    if (++outputAtLines >= outputAtLinesMax) {
                        break;
                    }
                }
            }

            if (output.length() > 0) {
                output.setLength(output.length() - 1); // Remove final "\n"
            }
            return output.toString();
        }
        catch (IOException e) {
            // um, IOException reading from a string...stupid Sun.  This should
            // be an unchecked exception...but maybe they all should be
            throw new RuntimeException(e);
        }
    }
}
