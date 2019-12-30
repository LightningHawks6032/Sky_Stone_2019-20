package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;
import org.firstinspires.ftc.teamcode.Hardware.Encoder;

@TeleOp (name = "Vertical Slider Encoder Test", group = "Tests")
public class VerticalSlideEncoderTest extends OpMode {
    public DcMotor leftLift;
    public DcMotor rightLift;

    public Encoder leftLiftEncoder;
    public Encoder rightLiftEncoder;

    public void init(){
        leftLift = hardwareMap.get(DcMotor.class, "ll");
        rightLift = hardwareMap.get(DcMotor.class, "rl");

        leftLiftEncoder = new Encoder(leftLift, AutonomousData.NEVEREST_20_ENCODER, 0);
        rightLiftEncoder = new Encoder(rightLift, AutonomousData.NEVEREST_20_ENCODER, 0);
        leftLiftEncoder.runWith();
        rightLiftEncoder.runWith();
    }

    public void loop(){
        double pow1 = gamepad1.left_stick_y * 0.4;
        double pow2 = -gamepad1.right_stick_y * 0.4;

        leftLift.setPower(pow1);
        rightLift.setPower(pow2);

        if(gamepad1.b){leftLiftEncoder.reset();
        rightLiftEncoder.reset();}

        telemetry.addData("Left Encoder Pos", leftLiftEncoder.getEncoderCount());
        telemetry.addData("Right Encoder Pos", rightLiftEncoder.getEncoderCount());
        telemetry.update();
    }
}
