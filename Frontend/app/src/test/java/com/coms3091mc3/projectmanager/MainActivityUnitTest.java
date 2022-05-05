package com.coms3091mc3.projectmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
public class MainActivityUnitTest {
    @Before
    public void setUpMockito() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test() {
        LoginActivity activity = Robolectric.buildActivity(LoginActivity.class)
                .setup().get();
        Button button = activity.findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button.performClick();
    }
}