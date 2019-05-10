package ru.bmstu.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty about;

    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        this.about = new SimpleStringProperty("It's just a game");
    }

    public Person() {
        this(null, null);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getAbout() {
        return about.get();
    }

    public StringProperty aboutProperty() {
        return about;
    }

    public void setAbout(String about) {
        this.about.set(about);
    }
}
