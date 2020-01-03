package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Horizontal Slide Test", group = "Tests")
@Disabled
public class HorizontalSlideCRServoTest extends OpMode {
    public CRServo leftSlide;
    public CRServo rightSlide;

    public void init(){
        leftSlide = hardwareMap.get(CRServo.class, "lb");
        rightSlide = hardwareMap.get(CRServo.class, "rb");
    }

    public void loop(){
        double pow1 = gamepad1.left_stick_y * 0.4;
        double pow2 = -gamepad1.right_stick_y * 0.4;

        leftSlide.setPower(pow1);
        rightSlide.setPower(pow2);
    }
}
