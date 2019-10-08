package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public class WeekendBot_Intake implements RobotHardware{

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
    public final double LEFT_CLAMP_UP = 0, LEFT_CLAMP_DOWN = 1, RIGHT_CLAMP_UP = 1, RIGHT_CLAMP_DOWN = 0;


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

    private void manageClampers(){
        if(gamepad.right_bumper && clampersUp){
            clampersDown();
            clampersUp = false;
        }else if(gamepad.right_bumper && !clampersUp){
            clampersUp();
            clampersUp = true;
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
