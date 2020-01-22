package org.firstinspires.ftc.teamcode.Vision;

import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class DogeCVDetectorMethods {
    // DogeCV/OpenCV declarations
    private OpenCvCamera phoneCam;
    private SkystoneDetector skyStoneDetector;
    private double robotCamX, robotCamY;

    private final double RIGHT_MARGIN = 0, LEFT_MARGIN = 100, CONVERSION_FACTOR = .5;


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
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        Thread.sleep(4000);

        double location = skyStoneDetector.getScreenPosition().x + robotCamX;
        debugLocation = location;

        if(location > LEFT_MARGIN) result = 0;
        else if(location < RIGHT_MARGIN) result = 2;

        return result;
    }
}
