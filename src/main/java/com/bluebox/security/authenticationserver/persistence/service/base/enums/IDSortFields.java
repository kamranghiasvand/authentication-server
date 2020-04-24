package com.bluebox.security.authenticationserver.persistence.service.base.enums;

import com.bluebox.security.authenticationserver.common.viewModel.SortField;

public enum IDSortFields implements SortField {
    ID("id");
    private String fieldName;

    IDSortFields(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getName() {
        return fieldName;
    }

}
