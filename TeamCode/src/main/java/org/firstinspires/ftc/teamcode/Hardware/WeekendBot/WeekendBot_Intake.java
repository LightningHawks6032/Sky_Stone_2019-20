package org.firstinspires.ftc.teamcode.Hardware.WeekendBot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;

public class WeekendBot_Intake implements RobotHardware {

    public DcMotor leftIntake; //
    public DcMotor rightIntake; //

    public Servo leftClamper;
    public Servo rightClamper;

    private Gamepad gamepad;


    //booleans
    public boolean clampersUp = true;

    // AUTO BASED VARIABLES
    private LinearOpMode autonomous = null; // stays null unless used in an auto
    private long startTime;

    //Final powers
    public final double INTAKE_POWER = 1, POWER_RATIO = 1;

    //Final servo pos's
    public final double LEFT_CLAMP_UP = .05, LEFT_CLAMP_DOWN = .55, RIGHT_CLAMP_UP = .15, RIGHT_CLAMP_DOWN = .7;


    public WeekendBot_Intake(DcMotor leftIn, DcMotor rightIn, Servo clampL, Servo clampR, Gamepad manipsGamepad){
        leftIntake = leftIn;
        rightIntake = rightIn;
        gamepad = manipsGamepad;
        leftClamper = clampL;
        rightClamper = clampR;
    }

    public void initHardware(){
        leftIntake.setDirection(DcMotorSimple.Direction.FORWARD);
        rightIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setStartTime(long time) {
        startTime = time;
    }
    public void setAuto(LinearOpMode auto) {
        autonomous = auto;
    }

    //tele op function
    public void manageTeleOp(){
        manageIntake();
        manageClampers();
    }

    private void manageIntake(){

        if (gamepad.a){
            //inwards
            setIntakePower(1);
        } else if (gamepad.y){
            //outwards
            setIntakePower(-1);
        } else{
            setIntakePower(0);
        }
    }

    // Booleans to manage clamping foundation for tele-op
    private boolean flippingIn = true; // Should the flipper be flipping inward? (i.e. was the last command to flip inward?)
    private boolean togglePressed = false; // Is the toggle button currently pressed?
    private boolean toggleLastPressed = false; // Was the toggle button pressed last iteration of loop()?

    private void manageClampers(){
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

    public void clampersDown(){
        leftClamper.setPosition(LEFT_CLAMP_DOWN);
        rightClamper.setPosition(RIGHT_CLAMP_DOWN);
    }

    public void clampersUp(){
        leftClamper.setPosition(LEFT_CLAMP_UP);
        rightClamper.setPosition(RIGHT_CLAMP_UP);
    }

    //in/out function, use in everything else
    public void setIntakePower(double pow){
        leftIntake.setPower(pow);
        rightIntake.setPower(pow*POWER_RATIO);
    }
}
