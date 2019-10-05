package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

public class WeekendBot_Intake implements RobotHardware{

    public DcMotor leftIntake; //REV Core Hex Motor
    public DcMotor rightIntake; //NeverRest Motor

    private Gamepad gamepad;


    //Final powers
    public final double INTAKE_POWER = 1, POWER_RATIO = 1;


    public WeekendBot_Intake(DcMotor leftIn, DcMotor rightIn, Gamepad manipsGamepad){
        leftIntake = leftIn;
        rightIntake = rightIn;
        gamepad = manipsGamepad;
    }

    public void initHardware(){
        leftIntake.setDirection(DcMotorSimple.Direction.FORWARD);
        rightIntake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    //tele op function
    public void manageTeleOp(){
        manageIntake();
    }

    private void manageIntake(){

        if (gamepad.a){
            //inwards
            setIntakePower(1);
        } else if (gamepad.y){
            //outwards
        } else{

        }
    }

    //in/out function, use in everything else
    public void setIntakePower(double pow){
        leftIntake.setPower(pow);
        rightIntake.setPower(pow*POWER_RATIO);
    }
}
