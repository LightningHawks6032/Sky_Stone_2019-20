package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TestOpmodes.ServoTester;

@TeleOp(name="Capstone Servo Test", group="Test Opmode")
@Disabled

public class CapStonerTester extends OpMode {
    private ServoTester capStonerTester;

    public void init(){
        capStonerTester = new ServoTester(this, hardwareMap.get(Servo.class, "cs"), "capStoner", gamepad1);
    }

    public void loop(){
        capStonerTester.run();
        telemetry.update();
    }
}
