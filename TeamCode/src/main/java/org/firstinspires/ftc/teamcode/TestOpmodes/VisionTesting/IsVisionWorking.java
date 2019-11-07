package org.firstinspires.ftc.teamcode.TestOpmodes.VisionTesting;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Misc.Sounds;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

@TeleOp (name = "General vision tester", group = "Test")
public class IsVisionWorking extends OpMode {
    private GeneralDetector detector;
    private Sounds sounds;

    public void init(){
        detector = new GeneralDetector(hardwareMap, 0, 0);
        sounds = new Sounds(hardwareMap);
        detector.setupTracker();
        sounds.playMegalovenia();
    }

    public void loop(){
        //telemetry data on robot/phone position
    }
}
