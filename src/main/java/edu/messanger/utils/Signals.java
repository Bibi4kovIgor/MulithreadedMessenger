package edu.messanger.utils;

public enum Signals {
    EXIT("exit");

    private final String value;
    Signals(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
