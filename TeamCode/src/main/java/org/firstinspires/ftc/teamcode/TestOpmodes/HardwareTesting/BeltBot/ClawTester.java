package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TestOpmodes.ServoTester;

@TeleOp(name="Claw Servo Test", group="Test Opmode")
//@Disabled

//(for BeltBot)\\
public class ClawTester extends OpMode{
    private CRServo frontClaw;
    private CRServo backClaw;

    public void init() {
        frontClaw = hardwareMap.get(CRServo.class, "fc");
        backClaw = hardwareMap.get(CRServo.class, "bc");
        //frontClaw.setDirection(CRServo.Direction.FORWARD);
        //backClaw.setDirection(CRServo.Direction.FORWARD);
    }

    public void loop(){
        double pow1 = gamepad1.left_stick_y * 0.4;
        double pow2 = -gamepad1.right_stick_y * 0.4;

        frontClaw.setPower(pow1);
        backClaw.setPower(pow2);
    }
}



