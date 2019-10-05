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

    public Servo clamper;
    public Servo flipper;

    private Gamepad gamepad;

    //Servo/limit Constants
    public final double CLAMPER_OPEN = 1, CLAMPER_CLOSED = 0,
                        FLIPPER_IN = 1, FLIPPER_OUT = 0;
    public final double UPPER_LIMIT = 3500, LOWER_LIMIT = 0; //TBD

    //TeleOp vairables
    boolean open = true;
    boolean in = true;

    // AUTO BASED VARIABLES
    private LinearOpMode autonomous = null; // stays null unless used in an auto
    private long startTime;

    protected  WeekendBot_Outtake (DcMotor upMotor, Servo clampie, Servo flippie, Gamepad manipsGamepad){
        raiseMotor = upMotor;
        flipper = flippie;
        clamper = clampie;
        raiseEncoder = new Encoder(upMotor, AutonomousData.NEVEREST_20_ENCODER, 1.8);;
    }

    public void initHardware(){
        raiseMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        raiseEncoder.runWith();
        clamper.setPosition(CLAMPER_CLOSED);
        flipper.setPosition(FLIPPER_IN);
    }



    //tele-op functions

    public void manageTeleOp(){
        manageClamp();
        manageFlip();
        manageRaise();
    }

    private void manageRaise(){

        double pow = 0; // Control TBD

        if (pow > 0 && raiseEncoder.getEncoderCount() < UPPER_LIMIT || pow < 0 && raiseEncoder.getEncoderCount() > LOWER_LIMIT) {
            raiseMotor.setPower(pow);
        }else{
            raiseMotor.setPower(0);
        }
    }

    private void manageClamp(){
        if (false/*control TBD*/ && open){
            clamper.setPosition(CLAMPER_CLOSED);
            open = false;
        }else if (false/*control TBD*/ && !open){
            clamper.setPosition(CLAMPER_OPEN);
            open = true;
        }
    }

    private void manageFlip(){
        if (false/*control TBD*/ && in){
            clamper.setPosition(FLIPPER_OUT);
            in = false;
        }else if (false/*control TBD*/ && !in){
            clamper.setPosition(FLIPPER_IN);
            in = true;
        }
    }


    private boolean autoRunning() {
        return System.currentTimeMillis() - startTime <= AutonomousData.TIME_LIMIT && !autonomous.isStopRequested();
    }
}
