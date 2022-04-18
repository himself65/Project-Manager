package com.coms3091mc3.projectmanager.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The type Settings view model.
 */
public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Instantiates a new Settings view model.
     */
    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public LiveData<String> getText() {
        return mText;
    }
}