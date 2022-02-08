package com.coms3091mc3.alexexperiment;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskCard extends BaseObservable {
    private String title;

    public TaskCard(String title) {
        this.title = title;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Logger.getLogger("Item").log(Level.INFO, "new title " + title);
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
}