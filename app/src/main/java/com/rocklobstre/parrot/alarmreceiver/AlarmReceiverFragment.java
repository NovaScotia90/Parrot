package com.rocklobstre.parrot.alarmreceiver;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.rocklobstre.parrot.alarmreceiver.DaggerAlarmReceiverComponent;
import com.rocklobstre.parrot.PostrainerApplication;
import com.rocklobstre.parrot.R;
import com.rocklobstre.parrot.data.viewmodel.Alarm;

import javax.inject.Inject;

/**
 * Created by Ryan on 05/03/2017.
 */

public class AlarmReceiverFragment extends Fragment implements AlarmReceiverContract.View {

    private static final String ALARM_ID = "ALARM_ID";
    private String alarmId;

    @Inject
    AlarmReceiverPresenter presenter;

    public AlarmReceiverFragment() {

    }

    public static AlarmReceiverFragment newInstance(String alarmId) {
        AlarmReceiverFragment fragment = new AlarmReceiverFragment();
        Bundle args = new Bundle();
        args.putString(ALARM_ID, alarmId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.alarmId = getArguments().getString(ALARM_ID);
        setRetainInstance(true);

        DaggerAlarmReceiverComponent.builder()
                .alarmReceiverPresenterModule(new AlarmReceiverPresenterModule(this))
                .applicationComponent(
                        ((PostrainerApplication) getActivity().getApplication())
                                .getApplicationComponent()
                )
                .build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);

        Button stopAlarm = (Button) v.findViewById(R.id.btn_alarm_dismiss);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAlarmDismissClick();
            }
        });
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void makeToast(@StringRes int message) {
        Toast.makeText(getActivity(),
                message,
                Toast.LENGTH_SHORT)
                .show();
    }


    @Override
    public void onResume() {
        super.onResume();
        /*
                In order to set up the Presenter properly, it must be supplied with the Id of the
                Alarm which just went off.
                 */
        presenter.start();
    }

    public String getAlarmId() {
        return this.alarmId;
    }

    @Override
    public Alarm getViewModel() {
       return new Alarm(
                this.alarmId,
                getString(R.string.def_alarm_name),
               getString(R.string.def_alarm_message),
                false,
                false,
                true,
                12,
                30
        );
    }

    @Override
    public void finishActivity() {
        Activity activity = getActivity();

        //null check to avoid cases where Act is destroyed. Not sure if necessary at this point.
        if (activity != null) {
            activity.finish();
        }
    }
}
