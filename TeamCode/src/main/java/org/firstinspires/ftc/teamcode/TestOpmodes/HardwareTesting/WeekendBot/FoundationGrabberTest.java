package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.WeekendBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TestOpmodes.ServoTester;


@TeleOp(name="Foundation Servo Test", group="Test Opmode")
public class FoundationGrabberTest extends OpMode {

    private ServoTester leftTester;
    private ServoTester rightTester;

    public void init() {
        //leftTester = new ServoTester(this, hardwareMap.get(Servo.class, "lc"), "Left Clamper Servo", gamepad1);
        rightTester = new ServoTester(this, hardwareMap.get(Servo.class, "rc"), "Right Clamper Servo", gamepad1);
    }

    public void loop(){
        //leftTester.run();
        rightTester.run();
        telemetry.update();
    }
}
