package com.salesianostriana.psp.contactsretrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Profesor on 30/11/2015.
 */
public class Contacts {

    @SerializedName("contacts")
    List<Contact> contacts;

    public Contacts() {
    }

    public Contacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "contacts=" + contacts +
                '}';
    }
}
