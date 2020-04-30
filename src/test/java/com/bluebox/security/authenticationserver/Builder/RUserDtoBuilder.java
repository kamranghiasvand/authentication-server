package com.bluebox.security.authenticationserver.Builder;


import com.bluebox.security.authenticationserver.common.viewModel.regular.RUserDto;

/**
 * @author by kamran ghiasvand
 */
public class RUserDtoBuilder {
    private RUserDto dto = new RUserDto();

    public static RUserDtoBuilder builder() {
        return new RUserDtoBuilder();
    }

    public RUserDtoBuilder password(String password) {
        dto.setPassword(password);
        return this;
    }

    public RUserDtoBuilder email(String email) {
        dto.setEmail(email);
        return this;
    }

    public RUserDtoBuilder firstName(String firstName) {
        dto.setFirstName(firstName);
        return this;
    }

    public RUserDtoBuilder lastName(String lastName) {
        dto.setLastName(lastName);
        return this;
    }

    public RUserDtoBuilder phone(String phone) {
        dto.setPhone(phone);
        return this;
    }

    public RUserDtoBuilder matchingPassword(String matchingPassword) {
        dto.setMatchingPassword(matchingPassword);
        return this;
    }

    public RUserDto build() {
        return dto;
    }
}
