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
import org.firstinspires.ftc.teamcode.Misc.Sounds;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

public class BeltBot_Hardware implements RobotHardware{
    // Constants for phone position for nav targets *THESE WILL NEED TO CHANGE AS THEY ARE CURRENTLY PULLED FROM QUALBOT*
    private final double CAMERA_FORWARD_POSITION = 3.5, // eg: Camera is 0 inches in front of robot center
            CAMERA_LEFT_POSITION = 0; // eg: Camera is 0 inches left of the robot's center line

    // X-position pixel value for center of robot (for mineral sampling)
    private final int ROBOT_CENTER_X = 225;

    public MecanumWheelDrive drivetrain;
    public BeltBot_Outtake outtake;
    public BeltBot_Intake intake;
    public GeneralDetector detector;
    public Sounds sounds;

    public BeltBot_Hardware(HardwareMap hardwareMap, Gamepad driveGamepad, Gamepad manipsGamepad, boolean calibrateSensors){
        drivetrain = new MecanumWheelDrive(
                hardwareMap.get(DcMotor.class, "fl"), //front left motor
                hardwareMap.get(DcMotor.class, "fr"), //front right motor
                hardwareMap.get(DcMotor.class, "bl"), //back left motor
                hardwareMap.get(DcMotor.class, "br"),
                new ExpansionHubIMU(hardwareMap.get(BNO055IMU.class, "imu"), calibrateSensors),
                new MRGyro(hardwareMap.get(GyroSensor.class, "gs"), calibrateSensors),
                driveGamepad
        );
        intake = new BeltBot_Intake(
                hardwareMap.get(DcMotor.class, "li"), //left intake
                hardwareMap.get(DcMotor.class, "ri"), //right intake
                hardwareMap.get(Servo.class, "lf"), //left flipper
                hardwareMap.get(Servo.class, "rf"), //right flipper
                manipsGamepad,
                hardwareMap.get(CRServo.class, "lie"), //left intake extender
                hardwareMap.get(CRServo.class, "rie") //right intake extender
        );
        outtake = new BeltBot_Outtake(
                hardwareMap.get(CRServo.class, "fc"), //front claw
                hardwareMap.get(CRServo.class, "bc"), //back claw
                hardwareMap.get(CRServo.class, "lb"), //left horizontal belt
                hardwareMap.get(CRServo.class, "rb"), //right horiz. belt
                hardwareMap.get(DcMotor.class, "ll"), //left lifter
                hardwareMap.get(DcMotor.class, "rl"), //right lifter
                hardwareMap.get(Servo.class, "cs"), //capStoner
                manipsGamepad
        );
        detector = new GeneralDetector(hardwareMap, CAMERA_FORWARD_POSITION, CAMERA_LEFT_POSITION);
        sounds = new Sounds(hardwareMap);
    }

    public void initHardware(){
        outtake.initHardware();
        intake.initHardware();
        drivetrain.initHardware();
    }

    public void manageTeleOp(boolean slideLimiting){
        outtake.manageTeleOp(slideLimiting);
        intake.manageTeleOp();
        intake.manageFoundationClamp(drivetrain.gamepad);
        drivetrain.manageTeleOp();
    }
}
