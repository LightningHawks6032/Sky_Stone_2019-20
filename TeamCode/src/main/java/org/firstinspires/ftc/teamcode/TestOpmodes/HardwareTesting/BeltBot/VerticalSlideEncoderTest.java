package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.BeltBot.BeltBot_Hardware;
import org.firstinspires.ftc.teamcode.Hardware.Encoder;

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
    }

    public void loop(){
        
    }
}
