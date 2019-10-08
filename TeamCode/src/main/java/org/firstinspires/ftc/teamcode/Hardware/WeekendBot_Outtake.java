package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AutonomousData;

public class WeekendBot_Outtake implements RobotHardware{

    public DcMotor raiseMotor;
    public Encoder raiseEncoder;

    public Servo grabber;
    public Servo flipper;

    private Gamepad gamepad;

    //Servo/limit Constants
    public final double GRABBER_OPEN = 1, GRABBER_CLOSED = 0,
                        FLIPPER_IN = 1, FLIPPER_OUT = 0;
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
        //manageRaise();
    }

    private void manageRaise(){


        double pow = gamepad.left_stick_y*0.1; // Control TBD


        if ((gamepad.left_stick_y > 0 && raiseEncoder.getEncoderCount() < UPPER_LIMIT) || (gamepad.left_stick_y < 0 && raiseEncoder.getEncoderCount() > LOWER_LIMIT)) {
            raiseMotor.setPower(pow);
        }else{
            raiseMotor.setPower(0);
        }


    }

    private void manageClamp(){
        if (false/*control TBD*/ && open){
            grabber.setPosition(GRABBER_CLOSED);
            open = false;
        }else if (false/*control TBD*/ && !open){
            grabber.setPosition(GRABBER_OPEN);
            open = true;
        }
    }

    private void manageFlip(){
        if (false/*control TBD*/ && in){
            grabber.setPosition(FLIPPER_OUT);
            in = false;
        }else if (false/*control TBD*/ && !in){
            grabber.setPosition(FLIPPER_IN);
            in = true;
        }
    }


    private boolean autoRunning() {
        return System.currentTimeMillis() - startTime <= AutonomousData.TIME_LIMIT && !autonomous.isStopRequested();
    }
}
