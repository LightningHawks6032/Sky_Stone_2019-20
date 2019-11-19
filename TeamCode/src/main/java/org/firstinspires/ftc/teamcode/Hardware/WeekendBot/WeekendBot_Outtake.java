package org.firstinspires.ftc.teamcode.Hardware.WeekendBot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.Encoder;
import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;

public class WeekendBot_Outtake implements RobotHardware {

    public DcMotor raiseMotor;
    public Encoder raiseEncoder;

    public Servo grabber;
    public Servo flipper;

    private Gamepad gamepad;

    //Servo/limit Constants
    public final double GRABBER_OPEN = 0, GRABBER_CLOSED = 0.6,
                        FLIPPER_IN = 0, FLIPPER_OUT = 1;
    public final double UPPER_LIMIT = 385, LOWER_LIMIT = 85; //TBD

    //TeleOp vairables
    boolean open = true;
    boolean in = true;

    // AUTO BASED VARIABLES
    private LinearOpMode autonomous = null; // stays null unless used in an auto
    private long startTime;

    protected  WeekendBot_Outtake (DcMotor upMotor, Servo grabbie, Servo flippie, Gamepad manipsGamepad){
        raiseMotor = upMotor;
        flipper = flippie;
        grabber = grabbie;
        raiseEncoder = new Encoder(upMotor, AutonomousData.NEVEREST_20_ENCODER, 1.8);;
        gamepad = manipsGamepad;
    }

    public void initHardware(){
        raiseMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        raiseEncoder.runWith();
        raiseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabber.setPosition(GRABBER_CLOSED);
        flipper.setPosition(FLIPPER_IN);
    }

    public void setStartTime(long time) {
        startTime = time;
    }
    public void setAuto(LinearOpMode auto) {
        autonomous = auto;
    }



    //tele-op functions

    public void manageTeleOp(){
        manageClamp();
        manageFlip();
        manageRaise();
    }

    private void manageRaise(){


        double pow = gamepad.left_stick_y*.4; // Control TBD


        if ((gamepad.left_stick_y > 0 && raiseEncoder.getEncoderCount() < UPPER_LIMIT) || (gamepad.left_stick_y < 0 && raiseEncoder.getEncoderCount() > LOWER_LIMIT)) {
            raiseMotor.setPower(pow);
        }else{
            raiseMotor.setPower(0);
        }




    }

    //Booleans for managing the toggle for clamping
    private boolean clampingIn = true; // Should the flipper be flipping inward? (i.e. was the last command to flip inward?)
    private boolean clampTogglePressed = false; // Is the toggle button currently pressed?
    private boolean clampToggleLastPressed = false; // Was the toggle button pressed last iteration of loop()?


    private void manageClamp(){

        clampTogglePressed = gamepad.b;

        if (clampTogglePressed && !clampToggleLastPressed) // Only change clamper if toggle button wasn't pressed last iteration of loop()
            clampingIn = !clampingIn;
        clampToggleLastPressed = clampTogglePressed; // toggleLastPressed updated for the next iteration of loop()

        if(clampingIn){
            grabber.setPosition(FLIPPER_IN);
        }else {
            grabber.setPosition(FLIPPER_OUT);
        }
    }

    // Booleans to manage flipping for tele-op
    private boolean flippingIn = true; // Should the flipper be flipping inward? (i.e. was the last command to flip inward?)
    private boolean togglePressed = false; // Is the toggle button currently pressed?
    private boolean toggleLastPressed = false; // Was the toggle button pressed last iteration of loop()?

    private void manageFlip(){

        togglePressed = gamepad.x;

        if (togglePressed && !toggleLastPressed) // Only change flipper if toggle button wasn't pressed last iteration of loop()
            flippingIn = !flippingIn;
        toggleLastPressed = togglePressed; // toggleLastPressed updated for the next iteration of loop()

        if(flippingIn){
            flipper.setPosition(FLIPPER_IN);
        }else {
            flipper.setPosition(FLIPPER_OUT);
        }

    }


    private boolean autoRunning() {
        return System.currentTimeMillis() - startTime <= AutonomousData.TIME_LIMIT && !autonomous.isStopRequested();
    }
}
