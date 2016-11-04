package com._604robotics.robotnik.data;

public interface DataRecipient {
    public abstract void sendData (String fieldName, double dataValue);
}
