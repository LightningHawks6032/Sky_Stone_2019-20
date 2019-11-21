package org.firstinspires.ftc.teamcode.Hardware.BeltBot;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.ExpansionHubIMU;
import org.firstinspires.ftc.teamcode.Hardware.MRGyro;
import org.firstinspires.ftc.teamcode.Hardware.MecanumWheelDrive;
import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

public class BeltBot_Intake {

    public DcMotor leftIntake;
    public DcMotor rightIntake;
    public Servo leftFoundation;
    public Servo rightFoundation;
    private Gamepad gamepad;

    private final double LEFT_FOUNDATION_UP = 0,
                         LEFT_FOUNDATION_DOWN = 90,
                         RIGHT_FOUNDATION_UP = 0,
                         RIGHT_FOUNDATION_DOWN = 90;


    public BeltBot_Intake (DcMotor li, DcMotor ri, Servo lf, Servo rf, Gamepad manipsGamepad){
        leftIntake = li;
        rightIntake = ri;
        leftFoundation = lf;
        rightFoundation = rf;
        gamepad = manipsGamepad;
    }

    public void initHardware(){
        leftIntake.setDirection(DcMotor.Direction.FORWARD);
        rightIntake.setDirection(DcMotor.Direction.FORWARD);
        rightFoundation.setPosition(RIGHT_FOUNDATION_UP);
        leftFoundation.setPosition(LEFT_FOUNDATION_UP);
    }

    //Tele-op Methods
    public void manageTeleOp(){

    }


    //Auto methods

}
