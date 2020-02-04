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

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.firstinspires.ftc.teamcode.Hardware.Encoder;
import org.firstinspires.ftc.teamcode.Hardware.ExpansionHubIMU;
import org.firstinspires.ftc.teamcode.Hardware.MRGyro;
import org.firstinspires.ftc.teamcode.Hardware.MecanumWheelDrive;
import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.Vision.GeneralDetector;

public class BeltBot_Outtake {

    public CRServo frontClaw;
    public CRServo backClaw;
    // public Servo turner;
    public CRServo leftBelt;
    public CRServo rightBelt;
    public DcMotor leftLift;
    public DcMotor rightLift;

    public Servo capStoner;

    public Encoder leftLiftEncoder;
    public Encoder rightLiftEncoder;

    private Gamepad gamepad;

    private LinearOpMode autonomous = null; // stays null unless used in an auto
    private long startTime;

    //Encoder positions
    private final double LEFT_LIFT_UPPER_ENCODER = 1800, LEFT_LIFT_LOWER_ENCODER = 0,
    RIGHT_LIFT_UPPER_ENCODER = -1800, RIGHT_LIFT_LOWER_ENCODER = 0, ENCODER_MARGIN_OF_ERROR = 50, TOP_AUTO_EXTEND = 601,
    CAPPED_STONE = 0.4, UNCAPPED_STONE = 0.0;


    public BeltBot_Outtake (CRServo fc, CRServo bc, CRServo lb, CRServo rb, DcMotor ll, DcMotor rl, Servo cs, Gamepad manipsGamepad){
        frontClaw = fc;
        backClaw = bc;
        leftBelt = lb;
        rightBelt = rb;
        leftLift = ll;
        rightLift = rl;
        capStoner = cs;
        gamepad = manipsGamepad;

        leftLiftEncoder = new Encoder(ll, AutonomousData.NEVEREST_20_ENCODER, 0);
        rightLiftEncoder = new Encoder(rl, AutonomousData.NEVEREST_20_ENCODER, 0);

    }


    public void initHardware(){
        frontClaw.setDirection(CRServo.Direction.FORWARD);
        backClaw.setDirection(CRServo.Direction.FORWARD);
        leftBelt.setDirection(CRServo.Direction.FORWARD);
        rightBelt.setDirection(CRServo.Direction.FORWARD);

        leftLift.setDirection(DcMotor.Direction.FORWARD);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift.setDirection(DcMotor.Direction.FORWARD);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLiftEncoder.runWith();
        rightLiftEncoder.runWith();
    }

    public void setStartTime(long time) {
        startTime = time;
    }
    public void setAuto(LinearOpMode auto) {
        autonomous = auto;
    }

    //tele-op methods
    public void manageTeleOp(boolean slideLimiting){
        manageLift(slideLimiting);
        manageHorizontalSlide();
        manageOGrabMe();
        cap();
    }

    // Conditions for auto retract/extend
    public double debugPow = 0;
    public boolean debugLift = false;
    public boolean debugLow = false;
    public double debugEncoder = 0;
    public boolean condition1 = false;
    public boolean condition2 = false;
    public String testLocation = "";
    private boolean autoRetract = false;
    private boolean autoExtend = false;
    private double restingEncoder, prevRestingEncoder = 0;

    // Uses left stick vert to control lift
    // Uses slide limiting
    private void manageLift(boolean slideLimiting){
        double pow = -gamepad.left_stick_y*0.6;

        debugPow = pow;
        debugLift = autoExtend;
        debugLow = autoRetract;

        double averageEncoder = -(leftLiftEncoder.getEncoderCount() + -(rightLiftEncoder.getEncoderCount()))/2;
        //double averageEncoder = leftLiftEncoder.getEncoderCount();
        debugEncoder = averageEncoder;
        restingEncoder = averageEncoder;

        if(slideLimiting) {

            condition1 = (pow > 0 && averageEncoder <= LEFT_LIFT_UPPER_ENCODER+ENCODER_MARGIN_OF_ERROR);
            condition2 = (pow < 0 && averageEncoder >= LEFT_LIFT_LOWER_ENCODER-ENCODER_MARGIN_OF_ERROR);
            if ((pow > 0 && averageEncoder <= LEFT_LIFT_UPPER_ENCODER+ENCODER_MARGIN_OF_ERROR) || (pow < 0 && averageEncoder >= LEFT_LIFT_LOWER_ENCODER-ENCODER_MARGIN_OF_ERROR)) {
                //condition for when going up and the encoder count allows to go up more or going down and the encoder count allows to go down more

                //autoExtend = false;
                //autoRetract = false;
                leftLift.setPower(-pow);
                rightLift.setPower(pow);
                testLocation = "reached";
            }
            else if (autoExtend && averageEncoder < /*LEFT_LIFT_UPPER_ENCODER*/ TOP_AUTO_EXTEND){ //move up automatically
                //autoRetract = false;
                testLocation = "not reached";
                leftLift.setPower(-0.4);
                rightLift.setPower(0.4);
            }else if (autoRetract && averageEncoder > LEFT_LIFT_LOWER_ENCODER){
                //autoExtend = false;
                testLocation = "not reached";
                leftLift.setPower(0.4);
                rightLift.setPower(-0.4);
            }
            else {//when it isn't supposed to move
                testLocation = "not reached";
                autoRetract = false;
                autoExtend = false;
                if(restingEncoder < prevRestingEncoder){
                    leftLift.setPower(-0.2);
                    rightLift.setPower(0.2);
                }else {
                    leftLift.setPower(0);
                    rightLift.setPower(0);
                }

            }


        } else {
            leftLift.setPower(pow);
            rightLift.setPower(-pow);
        }

        if(gamepad.dpad_down) {
            autoRetract = true;
            autoExtend = false;
        }
        if(gamepad.dpad_up) {
            autoExtend = true;
            autoRetract = false;
        }
        prevRestingEncoder = restingEncoder;
    }

    // Uses right stick vert to control horizontal slide
    // Uses slide limiting
    private void manageHorizontalSlide(){
        double pow = gamepad.right_stick_y*2;

        leftBelt.setPower(pow);
        rightBelt.setPower(-pow);

        /*

        //condition for when extending and the position count allows to extend more
        if(pow > 0 && leftBelt.){
            leftBelt.setPower(pow);
            rightBelt.setPower(pow);
        }//condition for when retracting and the position count allows to retract more
        else if (pow < 0 && leftLiftEncoder.getEncoderCount() > LEFT_LIFT_LOWER_ENCODER){
            leftBelt.setPower(pow);
            rightBelt.setPower(pow);
        }//when it isn't supposed to move
        else {
            leftBelt.setPower(0);
            rightBelt.setPower(0);
        }


         */
    }



    // A toggles between open and closed using B (control TBD)
        // Booleans to manage grabbing for tele-op
    /*
    private boolean flippingIn = true;
    private boolean togglePressed = false;
    private boolean toggleLastPressed = false;
    */
        //
    private void manageOGrabMe() {
        /*
        togglePressed = gamepad.b;

        if (togglePressed && !toggleLastPressed) // Only change flipper if toggle button wasn't pressed last iteration of loop()
            flippingIn = !flippingIn;
        toggleLastPressed = togglePressed; // toggleLastPressed updated for the next iteration of loop()

        if(flippingIn) grab(); else unGrab();
        */

        double pow = gamepad.right_trigger - gamepad.left_trigger;

        frontClaw.setPower(pow);
        backClaw.setPower(-pow);
    }

    //general methods
    /*
    public void grab(){
        frontClaw.setPosition(FRONT_CLAW_IN);
        backClaw.setPosition(BACK_CLAW_IN);
    }

    public void unGrab(){
        frontClaw.setPosition(FRONT_CLAW_OUT);
        backClaw.setPosition(BACK_CLAW_OUT);
    }
    */

    public void horizontalSlidePowers(double pow){
        leftBelt.setPower(pow);
        rightBelt.setPower(-pow);
    }

    public void cap(){
        if(gamepad.dpad_left) capStoner.setPosition(CAPPED_STONE);
        else if (gamepad.dpad_right) capStoner.setPosition(UNCAPPED_STONE);
    }
}
