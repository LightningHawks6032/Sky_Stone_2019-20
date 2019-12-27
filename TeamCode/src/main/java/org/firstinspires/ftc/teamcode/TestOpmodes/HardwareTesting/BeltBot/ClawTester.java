package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TestOpmodes.ServoTester;

@TeleOp(name="Claw Servo Test", group="Test Opmode")
//@Disabled

//(for BeltBot)\\
public class ClawTester extends OpMode{
    private ServoTester frontTester;
    private ServoTester backTester;

    public void init() {
        frontTester = new ServoTester(this, hardwareMap.get(Servo.class, "fc"), "Front Claw Servo", gamepad2);
        backTester = new ServoTester(this, hardwareMap.get(Servo.class, "bc"), "Back Claw Servo", gamepad1);
        backTester.setServoPos(0.5);
    }

    public void loop(){
        frontTester.run();
        backTester.run();
        telemetry.update();
    }
}



