package com.example.frontendexperiment.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.frontendexperiment.MainActivity;
import com.example.frontendexperiment.MainActivity3;
import com.example.frontendexperiment.Utility;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> welcomeText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        welcomeText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        welcomeText.setValue(Utility.welcomeText);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getWelcomeText() {return welcomeText;}

}