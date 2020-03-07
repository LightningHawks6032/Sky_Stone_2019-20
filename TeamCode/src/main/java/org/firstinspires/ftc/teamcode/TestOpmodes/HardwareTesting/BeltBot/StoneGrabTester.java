package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TestOpmodes.ServoTester;

@TeleOp(name="Stone Grabber Servo Test", group="Test Opmode")
@Disabled
public class StoneGrabTester extends OpMode {
    private ServoTester stoneGrabTester;

    public void init(){
        stoneGrabTester = new ServoTester(this, hardwareMap.get(Servo.class, "sg"), "stoneGrab", gamepad1);
    }

    public void loop(){
        stoneGrabTester.run();
        telemetry.update();
    }
}
