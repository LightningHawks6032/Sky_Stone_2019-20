package org.firstinspires.ftc.teamcode.Vision;

import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class DogeCVDetector {
    // DogeCV/OpenCV declarations
    private OpenCvCamera phoneCam;
    private SkystoneDetector skyStoneDetector;

    private double robotCenterX, robotCenterY;


    public DogeCVDetector(double centerX, double centerY, HardwareMap hardwareMap){
        robotCenterX = centerX;
        robotCenterY = centerY;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
    }

    public void initCameraConnection(){
        phoneCam.openCameraDevice();
    }


    /*
     * Returns an int based off the alignment of the skystone:
     * 0 (left), 1 (center), or 2 (right)
     * Alliance parameter: 1 (red), 2 (blue). These are the same values as the AutonomousData class
     */
    public int detectSkyStoneAlign(int alliance){
        int result = 0; //will default to center if no value is detected





        return result;
    }
}
