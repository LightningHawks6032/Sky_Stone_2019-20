package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.WeekendBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TestOpmodes.ServoTester;


@TeleOp(name="Grabber Servo Test", group="Test Opmode")
@Disabled
public class ClamperTest extends OpMode {

    private ServoTester clamp;

    public void init() {
        clamp = new ServoTester(this, hardwareMap.get(Servo.class, "g"), "Grabber Servo", gamepad1);
    }

    public void loop(){
        clamp.run();
        telemetry.update();
    }
}