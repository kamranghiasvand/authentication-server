package com.bluebox.security.authenticationserver.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by kamran ghiasvand
 */
public class PathConstant {
    public static final String REGISTRATION_BASE = "/api/registration";
    public static final String SEND_PHONE_VERIFICATION_CODE = "/send-phone-verification-code";
    public static final String REGISTER_WITH_PHONE = "/with-code";
    public static final String LOGIN_BASE = "/api/login";
    public static final String PERMISSION_BASE = "/api/permission";
    public static final String ROLE_BASE = "/api/role";
    public static final String ASSIGN_BASE = "/api/assign";
    public static final String ROLE_PERMISSION = "/role-permission";
    public static final String USER_ROLE = "/user-role";

    public static List<String> PERMIT_ALL = new ArrayList<>();

    static {
        PERMIT_ALL.add(REGISTRATION_BASE + "**");
        PERMIT_ALL.add("/**/*swagger*/**");
        PERMIT_ALL.add("/api-docs*");
    }
}
