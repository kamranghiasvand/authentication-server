package com.bluebox.security.authenticationserver.common;

import java.text.MessageFormat;

/**
 * @author by kamran ghiasvand
 */
public class Constants {
    /*.................... FIELDS .............................................................*/
    public static final String FIELD_ID = "id";
    public static final String FIELD_VERIFICATION_CODE = "code";
    public static final String FIELD_USER_PHONE = "phone";

    public static final String FIELD_ADMIN_EMAIL = "email";

    public static final String FIELD_APE_PERMISSION = "permission";
    public static final String FIELD_APE_USER = "user";

    public static final String FIELD_REGULAR_USER_EMAIL = "email";
    public static final String FIELD_REGULAR_USER_FIRST_NAME = "firstName";
    public static final String FIELD_REGULAR_USER_LAST_NAME = "lastName";
    public static final String FIELD_REGULAR_USER_PASSWORD = "password";
    public static final String FIELD_REGULAR_USER_MATCHING_PASSWORD = "matchingPassword";

    public static final String FIELD_PERMISSION_METHOD = "method";
    public static final String FIELD_PERMISSION_NAME = "name";
    public static final String FIELD_PERMISSION_URL = "url";
    public static final String FIELD_PERMISSION_ROLES = "roles";

    public static final String FIELD_ROLE_PERMISSIONS = "permissions";
    public static final String FIELD_ROLE_NAME = "name";
    public static final String FIELD_ROLE_PERMISSIONS_ID = "permissions[{0}].id";

    public static final String FIELD_CLIENT_ID = "clientId";
    /*.................... MESSAGES ............................................................*/
    public static final String DUPLICATE_MSG = "Duplicate {0}";
    public static final String OTHER_MSG = "other";
    public static final String VIOLATION_OF_MSG = "Violation of {0}";
    public static final String IS_NULL_MSG = "{0} is null";
    public static final String VALIDATION_ARGUMENT_MSG = "{0} is not valid for {1}";
    public static final String FIRST_IS_NOT_EQUAL_TO_SECOND_MSG = "{0} is not equal to {1}";
    public static final String FIELD_ROLE_PERMISSION_ID_IS_NULL_MSG = MessageFormat.format(IS_NULL_MSG, FIELD_ROLE_PERMISSIONS_ID);
    public static final String CODE_OR_PHONE_IS_NOT_VALID_MSG = "code or phoneNumber is not valid";
    public static final String CODE_ALREADY_SENT_MSG = "code already sent";
    /*.................... FOREIGN KEY .........................................................*/
    public static final String FK_APE_TO_ADMIN_USER = "fk_admin_user_permission_to_admin_user";
    public static final String FK_APE_TO_PERMISSION = "fk_admin_user_permission_to_permission";
    public static final String FK_ADMIN_USER_TO_PARENT = "fk_admin_user_to_parent";
    public static final String FK_ROLE_TO_PERMISSION = "fk_role_to_permission";
    public static final String FK_PERMISSION_TO_ROLE = "fk_permission_to_role";


    /*.................... UNIQUE ..............................................................*/
    public static final String UNIQUE_ADMIN_EMAIL = "unique_admin_email";
    public static final String UNIQUE_USER_PHONE = "unique_user_phone";
    public static final String UNIQUE_PERMISSION_NAME = "unique_permission_name";
    public static final String UNIQUE_CLIENT_ID = "unique_client_id";

    /*.................... ERROR ...............................................................*/
    public static final String ERROR_NOT_FOUND = "not_found_error";
    public static final String ERROR_VALIDATION = "validation_error";
    public static final String ERROR_PHONE_VERIFICATION = "phone_verification_error";
    public static final String ERROR_CONVERTER = "converter_error";
    public static final String ERROR_CLIENT = "client_error";
    public static final String ERROR_UNHANDLED = "unhandled_error";
    public static final String ERROR_DUPLICATE = "duplicate_error";
    public static final String ERROR_UN_AUTHORIZED = "unAuthorize_error";


    /*.................... VALIDATION .........................................................*/
    public static final String VALIDATION_IS_NULL_OR_NEGATIVE_MSG = "{0} is null or negative";
    public static final String VALIDATION_NOT_FOUND_MSG = "{0} with id {1} not found";
    public static final String VALIDATION_REGEX_NOT_VALID_MSG = "{0} pattern incorrect";
    public static final String VALIDATION_IS_NULL_MSG = "{0} is null";
    public static final String VALIDATION_MULTI_IS_NULL_MSG = "{0} are null together";
    public static final String VALIDATION_SIZE_MSG = "The {0} length must be {1}";
    public static final String VALIDATION_IS_EMPTY_MSG = "{0} is empty";
    public static final String VALIDATION_LENGTH_MAX_MSG = "The {0} max length is {1}";
    public static final String VALIDATION_LENGTH_MIN_MSG = "The {0} min length is {1}";
    public static final String VALIDATION_IS_NEGATIVE_MSG = "{0} must be greater than zero";
    public static final String VALIDATION_IS_ZERO_MSG = "{0} is zero";

    /*.................... SERVICE & CONTROLLERS NAMES .........................................*/
    public static final String USER_SERVICE = "userService";
    public static final String PERMISSION_SERVICE = "permissionService";
    public static final String ROLE_SERVICE = "roleService";
    public static final String PHONE_VERIFICATION_SERVICE = "phoneVerificationService";

    public static final String REGULAR_USER = "RegularUser";
    public static final String PERMISSION = "permission";
    public static final String ROLE = "role";
    public static final String USER = "user";


}
