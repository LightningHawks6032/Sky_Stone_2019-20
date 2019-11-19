package org.firstinspires.ftc.teamcode.Hardware.WeekendBot;

import com.qualcomm.hardware.bosch.BNO055IMU;
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


public class WeekendBot_Hardware implements RobotHardware {

    // Constants for phone position for nav targets *THESE WILL NEED TO CHANGE AS THEY ARE CURRENTLY PULLED FROM QUALBOT*
    private final double CAMERA_FORWARD_POSITION = 3.5, // eg: Camera is 0 inches in front of robot center
            CAMERA_LEFT_POSITION = 0; // eg: Camera is 0 inches left of the robot's center line

    // X-position pixel value for center of robot (for mineral sampling)
    private final int ROBOT_CENTER_X = 225;

    // Range Sensor Distance from Robot Center (inches)
    public final int RANGE_SENSOR_DISPLACEMENT = 3;

    public MecanumWheelDrive drivetrain;
    public WeekendBot_Outtake outtake;
    public WeekendBot_Intake intake;
    public GeneralDetector detector;


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
        detector = new GeneralDetector(hardwareMap, CAMERA_FORWARD_POSITION, CAMERA_LEFT_POSITION);
    }


    public void initHardware(){
        drivetrain.initHardware();
        outtake.initHardware();
        intake.initHardware();
    }
}
