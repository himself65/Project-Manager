package com.coms3091mc3.projectmanager.store;

import androidx.databinding.ObservableField;

public class User {
    public final ObservableField<String> name;
    public User(String name) {
        this.name = new ObservableField<>(name);
    }
}
