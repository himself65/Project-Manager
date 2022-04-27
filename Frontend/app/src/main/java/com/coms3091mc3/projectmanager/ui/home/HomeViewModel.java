package com.coms3091mc3.projectmanager.ui.home;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The type Home view model.
 */
public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    /**
     * The Btn dropdown menu.
     */
    Button btnDropdownMenu;

    /**
     * Instantiates a new Home view model.
     */
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
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