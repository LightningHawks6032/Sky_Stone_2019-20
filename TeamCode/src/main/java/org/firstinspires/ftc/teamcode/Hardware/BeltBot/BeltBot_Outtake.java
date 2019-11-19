package org.firstinspires.ftc.teamcode.Hardware.BeltBot;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.ExpansionHubIMU;
import org.firstinspires.ftc.teamcode.Hardware.MRGyro;
import org.firstinspires.ftc.teamcode.Hardware.MecanumWheelDrive;
import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

public class BeltBot_Outtake {

    public CRServo frontClaw;
    public CRServo backClaw;
    public Servo turner;
    public CRServo leftBelt;
    public CRServo rightBelt;
    public DcMotor leftLift;
    public DcMotor rightLift;

    public BeltBot_Outtake (CRServo fc, CRServo bc, Servo t, CRServo lb, CRServo rb, DcMotor ll, DcMotor rl){
        frontClaw = fc;
        backClaw = bc;
        turner = t;
        leftBelt = lb;
        rightBelt = rb;
        leftLift = ll;
        rightLift = rl;
    }
}
