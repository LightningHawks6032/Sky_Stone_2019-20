package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TestOpmodes.ServoTester;

//(for BeltBot)\\

@TeleOp(name="Foundation Servo Test", group="Test Opmode")
//@Disabled
public class FoundationGrabberTester extends OpMode {
    private ServoTester leftTester;
    private ServoTester rightTester;

    public void init() {
        leftTester = new ServoTester(this, hardwareMap.get(Servo.class, "lf"), "Left Foundation Servo", gamepad1);
        //rightTester = new ServoTester(this, hardwareMap.get(Servo.class, "rf"), "Right Foundation Servo", gamepad1);
    }

    public void loop(){
        leftTester.run();
        //rightTester.run();
        telemetry.update();
    }
}
