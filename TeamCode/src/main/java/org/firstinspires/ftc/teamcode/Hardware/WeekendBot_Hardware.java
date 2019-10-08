package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;


public class WeekendBot_Hardware implements RobotHardware{

    public MecanumWheelDrive drivetrain;
    public WeekendBot_Outtake outtake;
    public WeekendBot_Intake intake;


    public WeekendBot_Hardware(HardwareMap hardwareMap, Gamepad driveGamepad, Gamepad manipsGamepad, boolean calibrateSensors){
        drivetrain = new MecanumWheelDrive(
              hardwareMap.get(DcMotor.class, "fl"), //front left motor
              hardwareMap.get(DcMotor.class, "fr"), //front right motor
              hardwareMap.get(DcMotor.class, "bl"), //back left motor
              hardwareMap.get(DcMotor.class, "br"),
              new ExpansionHubIMU(hardwareMap.get(BNO055IMU.class, "imu"), calibrateSensors),
              new MRGyro(hardwareMap.get(GyroSensor.class, "gs"), calibrateSensors),
              driveGamepad
        );
        intake = new WeekendBot_Intake(
              hardwareMap.get(DcMotor.class, "li"), //left intake
              hardwareMap.get(DcMotor.class, "ri"), //right intake
              hardwareMap.get(Servo.class, "lc"),
                hardwareMap.get(Servo.class, "rc"),
                manipsGamepad
        );
        outtake = new WeekendBot_Outtake(
              hardwareMap.get(DcMotor.class, "rm"), //raise motor
              hardwareMap.get(Servo.class, "f"), //flipper
              hardwareMap.get(Servo.class, "g"), //grabber
              manipsGamepad
        );
    }


    public void initHardware(){
        drivetrain.initHardware();
        outtake.initHardware();
        intake.initHardware();
    }
}
