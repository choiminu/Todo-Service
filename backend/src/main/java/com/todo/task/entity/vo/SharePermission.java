package com.todo.task.entity.vo;

public enum SharePermission {
    VIEW, EDIT;

    public boolean allowsEdit() {
        return this == EDIT;
    }

    public static SharePermission from(String value) {
        if (EDIT.name().equalsIgnoreCase(value)) {
            return SharePermission.EDIT;
        }
        return SharePermission.VIEW;
    }
}
