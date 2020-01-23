package org.firstinspires.ftc.teamcode.Vision;

import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AutonomousData;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class DogeCVDetectorMethods {
    // DogeCV/OpenCV declarations
    private OpenCvCamera phoneCam;
    private LinearOpMode autonomous;
    private SkystoneDetector skyStoneDetector;
    private double robotCamX, robotCamY;
    private long startTime;

    private final double RIGHT_MARGIN = 150, LEFT_MARGIN = 100, CONVERSION_FACTOR = .5;


    //centerX: how far right the phone camera is from the center of the robot with regards to the robot looking at the skystones
    public DogeCVDetectorMethods(double centerX, double centerY, HardwareMap hardwareMap){
        robotCamX = centerX*CONVERSION_FACTOR;
        robotCamY = centerY*CONVERSION_FACTOR;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
    }

    public void initCameraConnection(){
        phoneCam.openCameraDevice();

        skyStoneDetector = new SkystoneDetector();
        phoneCam.setPipeline(skyStoneDetector);

        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    public void setStartTime(long time){
        startTime = time;
    }

    public void closeCameraConnection(){
        phoneCam.closeCameraDevice();
    }


    /*
     * Returns an int based off the alignment of the skystone:
     * 0 (left), 1 (center), or 2 (right)
     */
    double debugLocation = 0;

    public int detectSkyStoneAlign() throws InterruptedException{
        int result = 1; //will default to center if no value is detected

        //Thread.sleep(4000);

        double initTime = System.currentTimeMillis();

        int numLeft = 0;
        int numCenter = 0;
        int numRight = 0;

        while(System.currentTimeMillis() - initTime < 4000){
            double location = skyStoneDetector.getScreenPosition().x + robotCamX;
            debugLocation = location;

            if(location < LEFT_MARGIN) numLeft++;
            else if(location > RIGHT_MARGIN) numRight++;
            else numCenter++;
        }

        if (numLeft > numRight){
            if(numLeft > numCenter) result = 0;
            else result = 1;
        }else if (numRight > numLeft){
            if(numRight > numCenter) result = 2;
            else result = 1;
        }


        return result;
    }

    public boolean autoRunning() {
        return System.currentTimeMillis() - startTime <= AutonomousData.TIME_LIMIT && !autonomous.isStopRequested();
    }
}
