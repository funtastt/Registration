package com.example.stocks.ui.top_up;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TopUpViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TopUpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is top up fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}