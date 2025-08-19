package com.todo.task.entity;

public enum TaskStatus {
    NONE, PROGRESS, DONE;


    public static TaskStatus from(String status) {
        for (TaskStatus value : TaskStatus.values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return value;
            }
        }
        return null;
    }
}
