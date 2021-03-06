package com.bluebox.security.authenticationserver.common.util;


import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static com.bluebox.security.authenticationserver.common.Constants.*;

/**
 * @author kamran ghiasvand
 */
public class ExceptionUtil {
    private static final Map<String, String> UNIQUE_CONST = new HashMap<>();
    private static final Map<String, String> FK_CONST = new HashMap<>();
    private static final Map<String, String> MANY_TO_MANY = new HashMap<>();


    static {
        UNIQUE_CONST.put(UNIQUE_PERMISSION_NAME, FIELD_NAME);
        UNIQUE_CONST.put(UNIQUE_USER_PHONE, FIELD_USER_PHONE);
        UNIQUE_CONST.put(UNIQUE_ADMIN_EMAIL, FIELD_ADMIN_EMAIL);
        UNIQUE_CONST.put(UNIQUE_CLIENT_ID, FIELD_CLIENT_ID);

        FK_CONST.put(FK_APE_TO_PERMISSION, FIELD_APE_PERMISSION);
        FK_CONST.put(FK_APE_TO_ADMIN_USER, FIELD_APE_USER);
        FK_CONST.put(FK_ROLE_TO_PERMISSION, FIELD_ROLE_PERMISSIONS);
        FK_CONST.put(FK_PERMISSION_TO_ROLE, FIELD_ROLES);
    }

    private ExceptionUtil() {
    }


    public static boolean isConstraintViolationExp(Throwable ex) {
        return ex.getCause() != null &&
                ex.getCause() instanceof ConstraintViolationException &&
                ex.getCause().getCause() != null;
    }

    public static boolean isDuplicateExp(Throwable ex) {
        return ex.getCause() != null &&
                ex.getCause().getCause() != null &&
                ex.getCause().getCause().getMessage() != null &&
                ex.getCause().getCause().getMessage().contains("Duplicate entry");
    }

    public static String findMessage(Throwable ex) {
        if (ex == null || ex.getMessage() == null || "".equals(ex.getMessage())) {
            return "";
        }
        String filedName = checkMessage(ex.getMessage());
        if ("".equals(filedName) && ex.getCause() != null) {
            filedName = checkMessage(ex.getCause().getMessage());
            if ("".equals(filedName) && ex.getCause().getCause() != null)
                filedName = checkMessage(ex.getCause().getCause().getMessage());
        }
        if ("".equals(filedName))
            return ex.getMessage();
        return MessageFormat.format(DUPLICATE_MSG, filedName);
    }

    private static String checkMessage(String mes) {
        if (mes == null)
            return "";
        for (String uniqueName : UNIQUE_CONST.keySet()) {
            if (mes.contains(uniqueName)) {
                return UNIQUE_CONST.get(uniqueName);
            }
        }
        return OTHER_MSG;
    }


    private static String hasFkException(Throwable d) {
        if (d.getCause() == null || !ConstraintViolationException.class.equals(d.getCause().getClass())) {
            return "";
        }
        Throwable cause = d.getCause().getCause();
        if (cause == null || !SQLIntegrityConstraintViolationException.class.equals(cause.getClass()) || cause.getMessage() == null) {
            return "";
        }
        for (Map.Entry<String, String> entry : FK_CONST.entrySet()) {
            if (cause.getMessage().contains(entry.getKey()))
                return entry.getKey();
        }
        return "";
    }

    public static String fkException(Throwable d) {
        String key = hasFkException(d);
        if (key != null && !"".equals(key))
            return MessageFormat.format(VIOLATION_OF_MSG, FK_CONST.get(key));
        return "";
    }

    public static String fkManyToManyException(Throwable d) {
        for (Map.Entry<String, String> entry : MANY_TO_MANY.entrySet()) {
            if (d.getMessage() != null && d.getMessage().contains(entry.getKey())) {
                String with_id = "with id";
                int withId = d.getMessage().indexOf(with_id);
                int withIdEnd = d.getMessage().indexOf(";");
                if (withId > -1)
                    return MessageFormat.format(entry.getValue(), d.getMessage().substring(withId, withIdEnd).replaceAll(with_id, ""));
                else
                    return MessageFormat.format(entry.getValue(), "");
            }
        }
        return "";

    }

}
