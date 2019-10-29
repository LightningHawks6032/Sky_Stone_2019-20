package org.firstinspires.ftc.teamcode.Misc;

import android.content.Context;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Sounds {
    HardwareMap hardwareMap;
    Context myApp;


    public Sounds (HardwareMap hardwareMa){
        hardwareMap = hardwareMa;
        myApp = hardwareMap.appContext;
        SoundPlayer.PlaySoundParams params = new SoundPlayer.PlaySoundParams();
        params.loopControl = 0;
        params.waitForNonLoopingSoundsToFinish = true;
    }



    public void playSound(){

    }
}
