package com.bluebox.planner.auth.common;

/**
 * @author by kamran ghiasvand
 */
public class Constants {
    /*.................... FOREIGN KEY..........................................................*/
    public static final String FK_CATEGORY_TO_DOMAIN = "fk_category_to_domain";
    public static final String FK_ROLE_TO_CATEGORY = "fk_role_to_category";
    public static final String FK_ROLE_TO_ROLE_PERMISSION = "fk_role_to_rp";
    public static final String FK_PERMISSION_TO_ROLE_PERMISSION = "fk_permission_to_rp";
    public static final String FK_PERMISSION_TO_DOMAIN = "fk_permission_to_domain";

    /*.................... UNIQUE...............................................................*/
    public static final String UNIQUE_NAME_DOMAIN = "unique_name_domain";

    /*.................... ERROR................................................................*/
    public static final String ERROR_NOT_FOUND = "not_found_error";


    /*.................... VALIDATION..........................................................*/
    public static final String VALIDATION_IS_NULL_OR_NEGATIVE_MSG = "%s is null or negative";
    public static final String VALIDATION_NOT_FOUND_MSG = "%s with id %s not found";

    /*.................... SERVICE NAMES.......................................................*/
    public static final String USER_SERVICE = "userService";
}
