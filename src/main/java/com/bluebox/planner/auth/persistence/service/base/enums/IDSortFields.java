package com.bluebox.planner.auth.persistence.service.base.enums;

import com.bluebox.planner.auth.common.dto.SortField;

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
