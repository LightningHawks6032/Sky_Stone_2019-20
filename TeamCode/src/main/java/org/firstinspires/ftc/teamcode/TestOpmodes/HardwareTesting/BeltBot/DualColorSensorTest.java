package org.firstinspires.ftc.teamcode.TestOpmodes.HardwareTesting.BeltBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

@TeleOp (name = "Color Sensor Test lol", group = "Hardware Tests")
@Disabled
public class DualColorSensorTest extends OpMode {
    public ColorSensor leftCS = null;
    public ColorSensor rightCS = null;

    public void init(){
        leftCS = hardwareMap.get(ColorSensor.class, "lcs");
        rightCS = hardwareMap.get(ColorSensor.class, "rcs");

        leftCS.setI2cAddress(I2cAddr.create8bit(0x3c));
        rightCS.setI2cAddress(I2cAddr.create8bit(0x3c));

        leftCS.enableLed(true);
        rightCS.enableLed(false);


    }

    public void loop(){
        telemetry.addData("Debug", leftCS.getI2cAddress());

        telemetry.addData("Left Alpha Amount", leftCS.alpha());
        telemetry.addData("Right Alpha Amount", rightCS.alpha());

        telemetry.addData("Left Blue Amount", leftCS.blue());
        telemetry.addData("Right Blue Amount", rightCS.blue());

        telemetry.addData("Left Red Amount", leftCS.red());
        telemetry.addData("Right Red Amount", rightCS.red());

        telemetry.update();
    }
}
