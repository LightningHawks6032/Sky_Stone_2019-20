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

    public Encoder leftLiftEncoder;
    public Encoder rightLiftEncoder;

    private Gamepad gamepad;

    private LinearOpMode autonomous = null; // stays null unless used in an auto
    private long startTime;

    private final double FRONT_CLAW_IN = 0, FRONT_CLAW_OUT = 1,
                         BACK_CLAW_IN = 0, BACK_CLAW_OUT = 1;

    //Encoder positions
    private final double LEFT_LIFT_UPPER_ENCODER = 1000;
    private final double LEFT_LIFT_LOWER_ENCODER = 0;
    private final double RIGHT_LIFT_UPPER_ENCODER = 1000;
    private final double RIGHT_LIFT_LOWER_ENCODER = 0;


    public BeltBot_Outtake (CRServo fc, CRServo bc, CRServo lb, CRServo rb, DcMotor ll, DcMotor rl, Gamepad manipsGamepad){
        frontClaw = fc;
        backClaw = bc;
        leftBelt = lb;
        rightBelt = rb;
        leftLift = ll;
        rightLift = rl;
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
    public void manageTeleOp(){
        manageLift();
        manageHorizontalSlide();
        manageOGrabMe();
    }

    // Uses left stick vert to control lift
    // Uses slide limiting
    private void manageLift(){
        double pow = gamepad.left_stick_y*0.3;

        /*

         //condition for when going up and the encoder count allows to go up more
        if(pow > 0 && leftLiftEncoder.getEncoderCount() < LEFT_LIFT_UPPER_ENCODER){
            leftLift.setPower(pow);
            rightLift.setPower(pow);
        }//condition for when going down and the encoder count allows to go down more
        else if (pow < 0 && leftLiftEncoder.getEncoderCount() > LEFT_LIFT_LOWER_ENCODER){
            leftLift.setPower(pow);
            rightLift.setPower(pow);
        }//when it isn't supposed to move
        else {
            leftLift.setPower(0);
            rightLift.setPower(0);
        }

         */

        leftLift.setPower(-pow);
        rightLift.setPower(pow);
    }

    // Uses right stick vert to control horizontal slide
    // Uses slide limiting
    private void manageHorizontalSlide(){
        double pow = gamepad.right_stick_y*0.6;

        leftBelt.setPower(pow);
        rightBelt.setPower(pow);

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
        backClaw.setPower(pow);
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
}
