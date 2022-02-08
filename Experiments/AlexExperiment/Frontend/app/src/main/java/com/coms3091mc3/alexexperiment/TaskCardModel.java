package com.coms3091mc3.alexexperiment;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskCardModel extends ViewModel {
    @NonNull
    private final MutableLiveData<List<TaskCard>> itemList = new MutableLiveData<>(
            new ArrayList<>()
    );

    public void addTaskCard(TaskCard taskCard) {
        List<TaskCard> list = this.itemList.getValue();

        assert list != null;
        if (list.add(taskCard)) {
            itemList.setValue(list);
        }
    }

    public MutableLiveData<List<TaskCard>> getItems() {
        return itemList;
    }
}
