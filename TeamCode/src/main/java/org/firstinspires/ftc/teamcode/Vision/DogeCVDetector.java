package org.firstinspires.ftc.teamcode.Vision;

import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class DogeCVDetector {
    // DogeCV/OpenCV declarations
    private OpenCvCamera phoneCam;
    private SkystoneDetector skyStoneDetector;
    private double robotCamX, robotCamY;

    private final double LEFT_MARGIN = 0, RIGHT_MARGIN = 100, CONVERSION_FACTOR = .5;


    public DogeCVDetector(double centerX, double centerY, HardwareMap hardwareMap){
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


    /*
     * Returns an int based off the alignment of the skystone:
     * 0 (left), 1 (center), or 2 (right)
     * Alliance parameter: 1 (red), 2 (blue). These are the same values as the AutonomousData class
     */
    double debugLocation = 0;

    public int detectSkyStoneAlign(int alliance) throws InterruptedException{
        int result = 1; //will default to center if no value is detected
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
        Thread.sleep(4000);

        double location = skyStoneDetector.getScreenPosition().x + robotCamX;
        debugLocation = location;

        if(location > RIGHT_MARGIN) result = 2;
        else if(location < LEFT_MARGIN) result = 0;

        return result;
    }
}
