package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class ExampleThread extends Thread{

    OpMode ln;
    public ExampleThread(OpMode linear){
        ln = linear;
    }
    public void run(){
        while(true/*condition*/){

        }
    }
}
