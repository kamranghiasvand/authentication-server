package com.bluebox.planner.auth.rest.controller.regular;

import com.bluebox.planner.auth.common.config.InputProperties;
import com.bluebox.planner.auth.common.viewModel.regular.RegularUserDto;
import com.bluebox.planner.auth.rest.validation.ValidationFactory;
import com.bluebox.planner.auth.validators.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;

import static com.bluebox.planner.auth.common.Constants.*;
import static com.bluebox.planner.auth.validators.RuleFactory.*;
import static java.text.MessageFormat.format;

/**
 * @author by kamran ghiasvand
 */
@Service
public class RegularValidationFactory extends ValidationFactory<RegularUserDto, Long> {

    @Autowired
    public RegularValidationFactory(InputProperties inputProperties) {
        super(inputProperties);

    }

    @Override
    protected void init() {
        var nullEmail = nullRule(RegularUserDto::getEmail, FIELD_REGULAR_USER_EMAIL);
        var validEmail = validEmail(RegularUserDto::getEmail, FIELD_REGULAR_USER_EMAIL);
        var firstName = nullEmptyLenRule(RegularUserDto::getFirstName,
                FIELD_REGULAR_USER_FIRST_NAME, inputProps.getTextMaxLength());
        var lastName = nullEmptyLenRule(RegularUserDto::getLastName,
                FIELD_REGULAR_USER_LAST_NAME, inputProps.getTextMaxLength());
        var password = nullEmptyLenRule(RegularUserDto::getPassword, FIELD_REGULAR_USER_PASSWORD, inputProps.getTextMaxLength());
        var matchingPassword = nullEmptyLenRule(RegularUserDto::getPassword, FIELD_REGULAR_USER_PASSWORD, inputProps.getTextMaxLength());
        Rule<RegularUserDto> samePassword= dto->{
            if(dto.getPassword()==null)
                return null;
          if (!dto.getPassword().equals(dto.getMatchingPassword()))
              return Collections.singletonList(format("{} is not equal to {}", FIELD_REGULAR_USER_PASSWORD,FIELD_REGULAR_USER_MATCHING_PASSWORD));
          return null;
        };

        createCtx.addAll(nullEmail, validEmail,
                firstName.getNullRule(), firstName.getEmptyRule(), firstName.getLenRule(),
                lastName.getNullRule(), lastName.getEmptyRule(), lastName.getLenRule(),
                password.getNullRule(), password.getEmptyRule(), password.getLenRule(),
                matchingPassword.getNullRule(), matchingPassword.getEmptyRule(), matchingPassword.getLenRule(),
                samePassword);
        updateCtx.addAll(validEmail,
                firstName.getEmptyRule(), firstName.getLenRule(),
                lastName.getEmptyRule(), lastName.getLenRule(),
                password.getEmptyRule(), password.getLenRule(),
                samePassword);
    }
}
