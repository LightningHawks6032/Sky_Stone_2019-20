package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.WeekendBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TestOpmodes.ServoTester;


@TeleOp(name="Flipper Servo Test", group="Test Opmode")
public class FlipperTest extends OpMode {

    private ServoTester flipTester;

    public void init() {
        flipTester = new ServoTester(this, hardwareMap.get(Servo.class, "f"), "Flipper Servo", gamepad1);
    }

    public void loop(){
        flipTester.run();
        telemetry.update();
    }
}
