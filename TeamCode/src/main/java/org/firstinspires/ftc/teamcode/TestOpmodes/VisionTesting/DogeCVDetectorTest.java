package org.firstinspires.ftc.teamcode.TestOpmodes.VisionTesting;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Vision.DogeCVDetectorMethods;

@Autonomous (name = "Detector Auto Test", group = "Autonomous Testing")
public class DogeCVDetectorTest extends LinearOpMode {

    private DogeCVDetectorMethods detect;


    public void runOpMode() throws InterruptedException{
        detect  = new DogeCVDetectorMethods(20, 10, hardwareMap);
        detect.initCameraConnection();
        int location;

        waitForStart();
        location = detect.detectSkyStoneAlign();

        if(location == 0) telemetry.addLine("Left");
        else if(location == 1) telemetry.addLine("Center");
        else if(location == 2) telemetry.addLine("Right");
        telemetry.update();

        Thread.sleep(10000);
    }
}
