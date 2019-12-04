package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.ExpansionHubIMU;
import org.firstinspires.ftc.teamcode.Hardware.MRGyro;
import org.firstinspires.ftc.teamcode.Hardware.MecanumWheelDrive;

@TeleOp(name = "Drivtrain TeleOp", group = "Iterative Opmode")
public class DrivetrainTeleOp extends OpMode {
    private MecanumWheelDrive drive;

    //////////////////////////////
    public Servo leftFoundation;
    public Servo rightFoundation;
    ///////////////////////////////
    public void init(){
        drive = new MecanumWheelDrive(
                hardwareMap.get(DcMotor.class, "fl"), //front left motor
                hardwareMap.get(DcMotor.class, "fr"), //front right motor
                hardwareMap.get(DcMotor.class, "bl"), //back left motor
                hardwareMap.get(DcMotor.class, "br"),
                new ExpansionHubIMU(hardwareMap.get(BNO055IMU.class, "imu"), false),
                //new MRGyro(hardwareMap.get(GyroSensor.class, "gs"), false),
                gamepad1
        );
        leftFoundation = hardwareMap.get(Servo.class, "lf");
        rightFoundation = hardwareMap.get(Servo.class, "rf");
    }

    //The grounds for testing other things with this program


    public void loop(){
        drive.manageTeleOp();

        ///////////////////////
        if(gamepad1.x){
            leftFoundation.setPosition(0.2);
            rightFoundation.setPosition(0.4);
        }else if(gamepad1.y){
            leftFoundation.setPosition(0.4);
            rightFoundation.setPosition(0.7);
        }

        //////////////////////
    }
}
