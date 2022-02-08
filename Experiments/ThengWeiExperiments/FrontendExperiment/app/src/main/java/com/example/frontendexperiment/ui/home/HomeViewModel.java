package com.example.frontendexperiment.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.frontendexperiment.Utility;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> welcomeText;
    private MutableLiveData<String> newUser;
    private MutableLiveData<String> newPassword;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        welcomeText = new MutableLiveData<>();
        newUser = new MutableLiveData<>();
        newPassword = new MutableLiveData<>();


        mText.setValue("This is home fragment");
        welcomeText.setValue(Utility.welcomeText);


    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getWelcomeText() {return welcomeText;}
}