package com.coms3091mc3.projectmanager.store;

import androidx.databinding.ObservableField;

public class UserStore {
    public ObservableField<String> name;

    public UserStore() {
        this.name = new ObservableField<>("UNKNOWN");
    }
}
