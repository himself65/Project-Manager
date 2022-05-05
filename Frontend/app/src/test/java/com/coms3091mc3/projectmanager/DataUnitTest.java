package com.coms3091mc3.projectmanager;

import static org.junit.Assert.assertEquals;

import com.coms3091mc3.projectmanager.data.User;
import com.coms3091mc3.projectmanager.utils.Const;

import org.junit.Test;

public class DataUnitTest {
    @Test
    public void should_pass() {
        Const.user = new User(1, "zeyu", "Zeyu Yang");
        assertEquals(Const.user.getUsername(), "zeyu");
        assertEquals(Const.user.getFullname(), "Zeyu Yang");
        assertEquals(Const.user.getUserID(), 1);

        Const.user.setUserID(2);
        Const.user.setUsername("brandon");
        Const.user.setFullname("Brandon");

        assertEquals(Const.user.getUsername(), "brandon");
        assertEquals(Const.user.getFullname(), "Brandon");
        assertEquals(Const.user.getUserID(), 2);
    }
}
