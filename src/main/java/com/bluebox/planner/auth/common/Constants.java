package com.bluebox.planner.auth.common;

/**
 * @author by kamran ghiasvand
 */
public class Constants {
    /*.................... FIELDS .............................................................*/
    public static final String FIELD_ID = "id";
    public static final String FIELD_PERMISSION_NAME = "name";
    public static final String FIELD_USER_EMAIL = "email";
    public static final String FIELD_APE_PERMISSION = "permission";
    public static final String FIELD_APE_USER = "user";
    public static final String FIELD_ROLE_PERMISSIONS = "permissions";
    public static final String FIELD_PERMISSION_ROLES = "roles";
    public static final String FIELD_REGULAR_USER_EMAIL = "email";
    public static final String FIELD_REGULAR_USER_FIRST_NAME = "firstName";
    public static final String FIELD_REGULAR_USER_LAST_NAME = "lastName";
    public static final String FIELD_REGULAR_USER_PASSWORD = "password";
    public static final String FIELD_REGULAR_USER_MATCHING_PASSWORD = "matchingPassword";

    /*.................... MESSAGES ............................................................*/
    public static final String DUPLICATE_MSG = "Duplicate {}";
    public static final String OTHER_MSG = "other";
    public static final String VIOLATION_OF_MSG = "Violation of {}";
    public static final String VALIDATION_ARGUMENT_MSG = "{} is not valid for {}";

    /*.................... FOREIGN KEY .........................................................*/
    public static final String FK_APE_TO_ADMIN_USER = "fk_admin_user_permission_to_admin_user";
    public static final String FK_APE_TO_PERMISSION = "fk_admin_user_permission_to_permission";
    public static final String FK_ADMIN_USER_TO_PARENT = "fk_admin_user_to_parent";
    public static final String FK_ROLE_TO_PERMISSION = "fk_role_to_permission";
    public static final String FK_PERMISSION_TO_ROLE = "fk_permission_to_role";


    /*.................... UNIQUE ..............................................................*/
    public static final String UNIQUE_USER_EMAIL = "unique_user_email";
    public static final String UNIQUE_PERMISSION_NAME = "unique_permission_name";

    /*.................... ERROR ...............................................................*/
    public static final String ERROR_NOT_FOUND = "not_found_error";
    public static final String ERROR_VALIDATION = "validation_error";
    public static final String ERROR_CONVERTER = "converter_error";
    public static final String ERROR_CLIENT = "client_error";
    public static final String ERROR_UNHANDLED = "unhandled_error";
    public static final String ERROR_DUPLICATE = "duplicate_error";


    /*.................... VALIDATION .........................................................*/
    public static final String VALIDATION_IS_NULL_OR_NEGATIVE_MSG = "{} is null or negative";
    public static final String VALIDATION_NOT_FOUND_MSG = "{} with id {} not found";
    public static final String VALIDATION_REGEX_NOT_VALID1_MSG = "{} pattern incorrect";
    public static final String VALIDATION_IS_NULL_MSG = "{} is null";
    public static final String VALIDATION_MULTI_IS_NULL_MSG = "{} are null together";
    public static final String VALIDATION_SIZE_MSG = "The {} length must be {}";
    public static final String VALIDATION_IS_EMPTY_MSG = "{} is empty";
    public static final String VALIDATION_LENGTH_MAX_MSG = "The {} max length is {}";
    public static final String VALIDATION_LENGTH_MIN_MSG = "The {} min length is {}";
    public static final String VALIDATION_IS_NEGATIVE_MSG = "{} must be greater than zero";
    public static final String VALIDATION_IS_ZERO_MSG = "{} is zero";

    /*.................... SERVICE & CONTROLLERS NAMES .........................................*/
    public static final String USER_SERVICE = "userService";
    public static final String REGULAR_USER = "RegularUser";


}
