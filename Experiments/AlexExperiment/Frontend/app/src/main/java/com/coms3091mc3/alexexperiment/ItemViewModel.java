package com.coms3091mc3.alexexperiment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class Item {
    private final String title;

    public Item(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<List<Item>> itemList = new MutableLiveData<>(
            new ArrayList<>()
    );

    public void addItem(Item item) {
        List<Item> list = itemList.getValue();
        list.add(item);
        itemList.setValue(list);
    }

    public MutableLiveData<List<Item>> getItems() {
        return itemList;
    }
}
