package org.firstinspires.ftc.teamcode.Misc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "Memes", group = "Memes")
//@Disabled
public class MemingTesting extends OpMode {
    private Sounds sounds;

    public void init(){
        sounds = new Sounds(hardwareMap);
    }

    public void loop(){
        if (gamepad1.a){
            sounds.playMegalovenia();
        }
    }
}
