package com.rocklobstre.causalarm;

import com.rocklobstre.causalarm.settings.SettingsContract;
import com.rocklobstre.causalarm.settings.SettingsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by Ryan on 09/03/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class SettingsPresenterTest {

    @Mock
    private SettingsContract.View view;

    private SettingsPresenter presenter;

    @Before
    public void SetUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new SettingsPresenter(
                view
        );

    }

    @Test
    public void whenContactButtonClicked () {
        presenter.onBackButtonPress();
        //verify(view).startCreateAccountActivity();
    }

}