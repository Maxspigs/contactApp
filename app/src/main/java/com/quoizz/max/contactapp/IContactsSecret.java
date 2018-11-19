package com.quoizz.max.contactapp;

public interface IContactsSecret {
    void addContact(String nom, String numero);
    void validatePassword(String password);
}
