package org.firstinspires.ftc.teamcode.Hardware.BeltBot;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
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
    public CRServo leftIntakeExtender;
    public CRServo rightIntakeExtender;
    public Servo leftFoundation;
    public Servo rightFoundation;
    public Servo stoneGrab;
    private Gamepad gamepad;

    private LinearOpMode autonomous = null; // stays null unless used in an auto
    private long startTime;

    private final double LEFT_FOUNDATION_UP = 0,
                         LEFT_FOUNDATION_DOWN = 0.25,
                         RIGHT_FOUNDATION_UP = 0.95,
                         RIGHT_FOUNDATION_DOWN = 0.75,
                         STONEGRAB_UP = 0.025,
                         STONEGRAB_DOWN = 0.7;



    public BeltBot_Intake (DcMotor li, DcMotor ri, Servo lf, Servo rf, Gamepad manipsGamepad, CRServo lie, CRServo rie, Servo sg){
        leftIntake = li;
        rightIntake = ri;
        leftFoundation = lf;
        rightFoundation = rf;
        gamepad = manipsGamepad;
        leftIntakeExtender = lie;
        rightIntakeExtender = rie;
        stoneGrab = sg;
    }

    public void initHardware(){
        leftIntake.setDirection(DcMotor.Direction.REVERSE);
        rightIntake.setDirection(DcMotor.Direction.REVERSE);
        //rightFoundation.setPosition(RIGHT_FOUNDATION_UP);
        //leftFoundation.setPosition(LEFT_FOUNDATION_UP);
        leftIntakeExtender.setDirection(CRServo.Direction.FORWARD);
        rightIntakeExtender.setDirection(CRServo.Direction.FORWARD);
        stoneGrab.setPosition(STONEGRAB_UP);
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

    public void grabStone(){
        stoneGrab.setPosition(STONEGRAB_DOWN);
    }

    public void ungrabStone(){
        stoneGrab.setPosition(STONEGRAB_UP);
    }

    public void setStartTime(long time) {
        startTime = time;
    }
    public void setAuto(LinearOpMode auto) {
        autonomous = auto;
    }

    //Tele-op Methods
    public void manageTeleOp(){
        manageIntake();
    }

    // Booleans to manage clamping foundation for tele-op
    private boolean flippingIn = true; // Should the flipper be flipping inward? (i.e. was the last command to flip inward?)
    private boolean togglePressed = false; // Is the toggle button currently pressed?
    private boolean toggleLastPressed = false; // Was the toggle button pressed last iteration of loop()?

    public boolean manageFoundationClamp(Gamepad gp){
        boolean down = false;
        //right bumper controls the toggle
        togglePressed = gp.y;

        if (togglePressed && !toggleLastPressed) // Only change flipper if toggle button wasn't pressed last iteration of loop()
            flippingIn = !flippingIn;
        toggleLastPressed = togglePressed; // toggleLastPressed updated for the next iteration of loop()

        if(flippingIn){
            clampersUp();
        }else{
            clampersDown();
            down = true;
        }

        return down;

    }

    private boolean grabingIn  = true;
    private boolean grabPressed = false;
    private boolean grabLastPressed = false;

    private void manageStoneGrab(){
        grabPressed = gamepad.x;

        if(grabPressed && !grabLastPressed) grabingIn = !grabingIn;

        grabLastPressed = grabPressed;

        if(grabingIn) {grabStone();} else {ungrabStone();}
    }

    private void manageIntake(){
        if (gamepad.a){
            //in
            intakePowers(0.5);
        }else if (gamepad.y){
            //out
            intakePowers(-0.5);
        }else{
            intakePowers(0);
        }
    }

    public void intakePowers(double pow){
        leftIntake.setPower(-pow);
        rightIntake.setPower(pow);
        leftIntakeExtender.setPower(pow);
        rightIntakeExtender.setPower(-pow);
    }


    //Auto methods

    //direction: 1 for in, -1 for out
    public void intakeForTime (double secs, int direction) throws InterruptedException{
        intakePowers(1*direction);
        Thread.sleep((long) secs*1000);
        intakePowers(0);
    }
}
