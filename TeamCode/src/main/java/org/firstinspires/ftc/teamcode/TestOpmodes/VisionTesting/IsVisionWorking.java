package org.firstinspires.ftc.teamcode.TestOpmodes.VisionTesting;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.WeekendBot.WeekendBot_Hardware;
import org.firstinspires.ftc.teamcode.Misc.Sounds;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

@TeleOp (name = "General vision tester", group = "Test")
@Disabled
public class IsVisionWorking extends OpMode {
    //private GeneralDetector detector;
    private Sounds sounds;

    /*
    public void init(){
        detector = new GeneralDetector(hardwareMap, 0, 0);
        sounds = new Sounds(hardwareMap);
        detector.setupTracker();
        sounds.playMegalovenia();
    }

    public void loop(){
        //telemetry data on robot/phone position
    }
    */


    // Detector object
    private GeneralDetector detector;
    private WeekendBot_Hardware hardware;

    @Override
    public void init() {
        // Set up detector
        hardware = new WeekendBot_Hardware(hardwareMap, gamepad1, gamepad2, false);
        hardware.initHardware();
        detector = hardware.detector; // Create detector
        detector.setupTracker();
        sounds = new Sounds(hardwareMap);
        sounds.playMegalovenia();
    }

    /*
     * Code to run REPEATEDLY when the driver hits INIT
     */
    @Override
    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    @Override
    public void loop() {
        hardware.drivetrain.manageTeleOp();
        detector.lookForTargets();

        /*
        telemetry.addData("Target Visible: ", detector.isTargetVisible());
        if (detector.isTargetVisible()) {
            telemetry.addData("The robot sees", detector.visibleTarget());
            telemetry.addData("Cam Pos", detector.getCamPosition().toString());
            telemetry.addData("Robot Pos", detector.getRobotPosition().toString());
            telemetry.addData("Quadrant", detector.getRobotPosition().quadrant());
            //telemetry.addData("Robot Rotation", Math.round(detector.getRobotRotation()));
            telemetry.addData("Detector FirstAngle", detector.robotRotation.firstAngle);
            telemetry.addData("Detector SecondAngle", detector.robotRotation.secondAngle);
            telemetry.addData("Detector ThirdAngle", detector.robotRotation.thirdAngle);
        } else
            telemetry.addLine("The robot sees: No Target");
        telemetry.update();
        */
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
