package com.rocklobstre.causalarm.dependencyinjection.modules;

import android.app.AlarmManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;

import com.rocklobstre.causalarm.data.alarmdatabase.AlarmDatabase;
import com.rocklobstre.causalarm.data.alarmdatabase.AlarmSource;
import com.rocklobstre.causalarm.data.alarmservice.AlarmService;
import com.rocklobstre.causalarm.util.BaseSchedulerProvider;
import com.rocklobstre.causalarm.util.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.POWER_SERVICE;

/**
 * This all made more sense after watching
 * <a href="https://www.youtube.com/watch?v=plK0zyRLIP8&t=92s">This talk.</a>
 * <p>
 * This is a Module. It provides dependencies via @Provides annotated methods below.
 * Module's are designed to be partitioned in a way that makes sense.
 * Created by Ryan on 10/03/2017.
 */
@Module
public final class ApplicationModule {
    private final Context applicationContext;
    private final PowerManager.WakeLock wakeLock;
    private final Vibrator vibrator;
    private final AudioManager audioManager;
    private final AlarmManager alarmManager;
    private final MediaPlayer mediaPlayer;

    //This objects are necessary for creation of other objects within this Module, hence making them
    //variables
    public ApplicationModule(Context application) {
        this.applicationContext = application;
        this.wakeLock = ((PowerManager) applicationContext
                .getSystemService(POWER_SERVICE))
                .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Alarm");
        this.audioManager = ((AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE));
        this.vibrator = ((Vibrator) applicationContext.getSystemService(Context.VIBRATOR_SERVICE));
        this.alarmManager = ((AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE));
        this.mediaPlayer = MediaPlayer.create(application, Settings.System.DEFAULT_ALARM_ALERT_URI);
    }

    @Provides
    @Singleton
    Context provideContext() {
        return applicationContext;
    }

    @Singleton
    @Provides
    PowerManager.WakeLock provideWakeLock() {
        return wakeLock;
    }

    @Singleton
    @Provides
    AudioManager provideAudioManager() {
        return audioManager;
    }

    @Singleton
    @Provides
    MediaPlayer provideMediaPlayer() {
        return mediaPlayer;
    }

    @Singleton
    @Provides
    Vibrator provideVibrator() {
        return vibrator;
    }

    @Singleton
    @Provides
    AlarmManager provideAndroidAlarmManager() {
        return alarmManager;
    }

    @Singleton
    @Provides
    BaseSchedulerProvider provideScheduler() {
        return new SchedulerProvider();
    }

    @Singleton
    @Provides
    com.rocklobstre.causalarm.data.alarmservice.AlarmManager provideAlarmManager() {
        return new AlarmService(
                wakeLock,
                mediaPlayer,
                audioManager,
                vibrator,
                alarmManager,
                applicationContext
        );
    }

    @Singleton
    @Provides
    AlarmSource provideAlarmSource() {
        return new AlarmDatabase();
    }

}
