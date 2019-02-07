package com.eresh.spring.boot.commons.exception;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
public class SystemProperties {
    private SystemProperties() {
        // Prevent instantiation
    }

    /** Java Runtime Environment version */
    public static final String JAVA_VERSION = System
            .getProperty("java.version");

    /** Java Runtime Environment vendor */
    public static final String JAVA_VENDOR = System
            .getProperty("java.vendor");

    /** Java vendor URL */
    public static final String JAVA_VENDOR_URL = System
            .getProperty("java.vendor.url");

    /** Java installation directory */
    public static final String JAVA_HOME = System
            .getProperty("java.home");

    /** Java Virtual Machine specification version */
    public static final String JAVA_VM_SPECIFICATION_VERSION = System
            .getProperty("java.vm.specification.version");

    /** Java Virtual Machine specification vendor */
    public static final String JAVA_VM_SPECIFICATION_VENDOR = System
            .getProperty("java.vm.specification.vendor");

    /** Java Virtual Machine specification name */
    public static final String JAVA_VM_SPECIFICATION_NAME = System
            .getProperty("java.vm.specification.name");

    /** Java Virtual Machine implementation version */
    public static final String JAVA_VM_VERSION = System
            .getProperty("java.vm.version");

    /** Java Virtual Machine implementation vendor */
    public static final String JAVA_VM_VENDOR = System
            .getProperty("java.vm.vendor");

    /** Java Virtual Machine implementation name */
    public static final String JAVA_VM_NAME = System
            .getProperty("java.vm.name");

    /** Java Runtime Environment specification version */
    public static final String JAVA_SPECIFICATION_VERSION = System
            .getProperty("java.specification.version");

    /** Java Runtime Environment specification vendor */
    public static final String JAVA_SPECIFICATION_VENDOR = System
            .getProperty("java.specification.vendor");

    /** Java Runtime Environment specification name */
    public static final String JAVA_SPECIFICATION_NAME = System
            .getProperty("java.specification.name");

    /** Java class format version number */
    public static final String JAVA_CLASS_VERSION = System
            .getProperty("java.class.version");

    /** Java class path */
    public static final String JAVA_CLASS_PATH = System
            .getProperty("java.class.path");

    /** Path of extension directory or directories */
    public static final String JAVA_EXT_DIRS = System
            .getProperty("java.ext.dirs");

    /** Operating system name */
    public static final String OS_NAME = System
            .getProperty("os.name");

    /** Operating system architecture */
    public static final String OS_ARCH = System
            .getProperty("os.arch");

    /** Operating system version */
    public static final String OS_VERSION = System
            .getProperty("os.version");

    /** File separator ("/" on UNIX) */
    public static final String FILE_SEPARATOR = System
            .getProperty("file.separator");

    /** Path separator (":" on UNIX) */
    public static final String PATH_SEPARATOR = System
            .getProperty("path.separator");

    /** Line separator ("\n" on UNIX) */
    public static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

    /**
     * User's account name. Not a constant because it can change
     */
    public static final String getUserName() {
        return System.getProperty("user.name");
    }

    /**
     * User's home directory.  Not a constant because it can change
     */
    public static final String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * User's current working directory.  Not a constant because it can change
     */
    public static final String getUserDir() {
        return System.getProperty("user.dir");
    }
}
