package org.firstinspires.ftc.teamcode.Vision;

import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.FieldMapping.Vector;
import org.openftc.easyopencv.OpenCvCamera;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class GeneralDetector {

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private static final boolean PHONE_IS_PORTRAIT = true;


    private boolean stoneDetected = false;

    //Vuforia code
    private static final String VUFORIA_KEY =
            "AdwaKe7/////AAAAmVQWX/gUQE/gnK+olEmSWA5FCaxNrdY/EyKFLO2afR1IQD4gbnThc6LcCHIJ64hyC2i3n5VRiIRAMGxtKqjI7meHCphQAPrXpH9GomENr/fSXjVUhQao+Zw0/MLQEuTaqNYnp5EI/4oo6LTm/YPgYKOSPaP+tijaydiwNQn4A8zXPfDhkD/q6RTYMzS3UtpOR7WBZJPUBxW9XKim5ekHbYd1Hk2cFTTFAsL0XwycIWhuvHYpVlnZMqWwEnkTqp0o+5TE1FLkAfJ4OOUEfB8sP9kMEcged2/tczAh3GOcjOudp1S9F5xjPFZQX00OLV+QUCPzmT5kkqFBwiS30YR6L8urW2mJG4quq6NnrNYwzn47";

    //Constants
    private static final float mmPerInch = 25.4f;
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField = 36 * mmPerInch;

    // Class Members
    //private OpenGLMatrix lastLocation = null;
    //private VuforiaLocalizer vuforia = null;
    //private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;


    // Instance data: hardwareMap and displacements that indicate phone camera location on robot
    private HardwareMap hardwareMap;
    private double camForwardDisplacement; // eg: Camera is ___ inches in front of robot center
    private double camLeftDisplacement; // eg: Camera is ___ inches to left of robot center

    // Setup tracking data
    private VuforiaLocalizer vuforia;
    private ArrayList<VuforiaTrackable> navigationTargets;

    // For returning to telemetry
    private boolean targetVisible;
    private String whichTargetVisible;
    private static final String[] targetNames = {"Stone Target", "Blue Rear Bridge", "Red Rear Bridge",
            "Red Front Bridge", "Blue Front Bridge", "Red Perimeter 1", "Red Perimeter 2",
            "Front Perimeter 1", "Front Perimeter 2", "Blue Perimeter 1", "Blue Perimeter 2", "Rear Perimeter 1", "Rear Perimeter 2"};
    private OpenGLMatrix lastLocation = null; // the last location of a nav target we've seen
    private VectorF camPos;
    public Orientation robotRotation;

    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();

    public GeneralDetector(HardwareMap hwMap, double camForwardDisplacement, double camLeftDisplacement) {
        // Hardware
        this.hardwareMap = hwMap;
        this.camForwardDisplacement = camForwardDisplacement;
        this.camLeftDisplacement = camLeftDisplacement;

        // Tracker results
        targetVisible = false; // by default, we assume we don't see a target
        whichTargetVisible = null; // by default, we assume we don't see a target

        // Data for tracker
        navigationTargets = new ArrayList<VuforiaTrackable>();
        camPos = null;
        robotRotation = null;
    }

    public void setupTracker() {


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey =VUFORIA_KEY;
        parameters.cameraDirection   =CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia =ClassFactory.getInstance().

        createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
            stoneTarget.setName(targetNames[0]);
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
            blueRearBridge.setName(targetNames[1]);
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
            redRearBridge.setName(targetNames[2]);
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
            redFrontBridge.setName(targetNames[3]);
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
            blueFrontBridge.setName(targetNames[4]);
        VuforiaTrackable red1 = targetsSkyStone.get(5);
            red1.setName(targetNames[5]);
        VuforiaTrackable red2 = targetsSkyStone.get(6);
            red2.setName(targetNames[6]);
        VuforiaTrackable front1 = targetsSkyStone.get(7);
            front1.setName(targetNames[7]);
        VuforiaTrackable front2 = targetsSkyStone.get(8);
            front2.setName(targetNames[8]);
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
            blue1.setName(targetNames[9]);
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
            blue2.setName(targetNames[10]);
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
            rear1.setName(targetNames[11]);
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
            rear2.setName(targetNames[12]);

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        //List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.

        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = (float) camForwardDisplacement * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = (float) camLeftDisplacement * mmPerInch;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        targetsSkyStone.activate();

        //Turn flash on

        com.vuforia.CameraDevice.getInstance().setFlashTorchMode(true);

        //Zoom in

        com.vuforia.CameraDevice.getInstance().setField("opti-zoom", "opti-zoom-on");
        com.vuforia.CameraDevice.getInstance().setField("zoom", "30");
    }

    public void setZoom(int zoom){
        com.vuforia.CameraDevice.getInstance().setField("zoom", zoom+"");
    }

    public void lookForTargets() {
        stoneDetected = false;
        for (VuforiaTrackable trackable : allTrackables) {

            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                targetVisible = true;
                whichTargetVisible = trackable.getName();
                if(whichTargetVisible.equals(targetNames[0])) {
                    stoneDetected = true;
                }


                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }


                break; // Exit the for loop if we've found one of the nav targets
            } else {
                targetVisible = false;
            }
        }

        // If we've seen a target and know where the robot is
        if (targetVisible && lastLocation != null) {
            // express position (translation) of robot in inches.
            camPos = lastLocation.getTranslation();

            // Express the rotation of the robot in degrees. Extrinsic = Roll, XYZ = Pitch, DEGREES = Yaw
            robotRotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
        }
    }

    // The following methods check for the visibility of a specific target by inputting its index in navigationTargets or target name
    public boolean specificTargetVisible(int targetIndex) {
        return ((VuforiaTrackableDefaultListener) navigationTargets.get(targetIndex).getListener()).isVisible();
    }

    // Return true if the robot sees any target at all
    public boolean isTargetVisible() {
        return targetVisible;
    }

    // Return which target the robot sees
    public String visibleTarget() {
        return whichTargetVisible;
    }

    // Returns vector of camera's position in inches
    public Vector getCamPosition() {
        double x = camPos.get(0) / mmPerInch, y = camPos.get(1) / mmPerInch;

        // Craters
        if (specificTargetVisible(2)) {
            return new Vector(y, -x);
        }

        // Footprint
        else if (specificTargetVisible(1)) {
            return new Vector(-x, -y);
        }

        // Space
        else if (specificTargetVisible(3)) {
            return new Vector(-y, x);
        }

        // Rover
        return new Vector(x, y);
    }

    // Converts camera position into robot center's position using camForwardDisplacement
    // THIS WILL WORK IF ROBOT IS IN CENTER OF FRONT SIDE (camLeftDisplacement = 0)

    public Vector getRobotPosition() {
        VectorF translation = lastLocation.getTranslation();
        double x = translation.get(0) / mmPerInch; // Camera Position X
        double y = translation.get(1) / mmPerInch; // Camera Position Y
        return new Vector(x,y);
    }

    public boolean stoneVisible(){
        return stoneDetected;
    }


    /*
    public Vector getRobotPosition() {

        VectorF translation = lastLocation.getTranslation();

        double yawR = -(robotRotation.thirdAngle) * Math.PI / 180; // draw line from nav target to robot, this is the angle (in radians) the line makes with nearest axis

        double x = translation.get(0) / mmPerInch; // Camera Position X
        double y = translation.get(1) / mmPerInch; // Camera Position Y

        //double x = getCamPosition().getX(); // Camera Position X
        //double y = getCamPosition().getY(); // Camera Position Y
        double disp = camForwardDisplacement; // How far forward from robot center is the camera?

        /*
        // Craters
        if (specificTargetVisible(2)) {
            return new Vector(x - disp * Math.cos(yawR), y + disp * Math.sin(yawR));
        }

        // Rover
        else if (specificTargetVisible(0)) {
            return new Vector(x - disp * Math.sin(yawR), y - disp * Math.cos(yawR));
        }

        // Space
        else if (specificTargetVisible(3)) {
            return new Vector(x + disp * Math.cos(yawR), y - disp * Math.sin(yawR));
        }

        // Footprint
        else if (specificTargetVisible(1)) {
            return new Vector(x + disp * Math.sin(yawR), y + disp * Math.cos(yawR));
        }
        */

/*
        // If everything fails, just return original phone position
        return getCamPosition();
    }
    */





}

