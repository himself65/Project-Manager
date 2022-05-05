package com.coms3091mc3.projectmanager;

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
                .create().resume().get();
    }
}