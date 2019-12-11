package org.firstinspires.ftc.teamcode.Hardware.BeltBot;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

    private LinearOpMode autonomous = null; // stays null unless used in an auto
    private long startTime;

    private final double LEFT_FOUNDATION_UP = 0.25,
                         LEFT_FOUNDATION_DOWN = 0.4,
                         RIGHT_FOUNDATION_UP = 0.65,
                         RIGHT_FOUNDATION_DOWN = 0.47;


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

    //General Methods

    public void clampersUp(){
        leftFoundation.setPosition(LEFT_FOUNDATION_UP);
        rightFoundation.setPosition(RIGHT_FOUNDATION_UP);
    }

    public void clampersDown(){
        leftFoundation.setPosition(LEFT_FOUNDATION_DOWN);
        rightFoundation.setPosition(RIGHT_FOUNDATION_DOWN);
    }

    public void setStartTime(long time) {
        startTime = time;
    }
    public void setAuto(LinearOpMode auto) {
        autonomous = auto;
    }

    //Tele-op Methods
    public void manageTeleOp(){
        manageFoundationClamp();
        manageIntake();
    }

    // Booleans to manage clamping foundation for tele-op
    private boolean flippingIn = true; // Should the flipper be flipping inward? (i.e. was the last command to flip inward?)
    private boolean togglePressed = false; // Is the toggle button currently pressed?
    private boolean toggleLastPressed = false; // Was the toggle button pressed last iteration of loop()?

    private void manageFoundationClamp(){
        //right bumper controls the toggle
        togglePressed = gamepad.right_bumper;

        if (togglePressed && !toggleLastPressed) // Only change flipper if toggle button wasn't pressed last iteration of loop()
            flippingIn = !flippingIn;
        toggleLastPressed = togglePressed; // toggleLastPressed updated for the next iteration of loop()

        if(flippingIn){
            clampersUp();
        }else{
            clampersDown();
        }

    }

    private void manageIntake(){
        if (gamepad.a){
            intakePowers(1);
        }else if (gamepad.y){
            intakePowers(-1);
        }else{
            intakePowers(0);
        }
    }

    public void intakePowers(double pow){
        leftIntake.setPower(pow);
        rightIntake.setPower(pow);
    }


    //Auto methods

}