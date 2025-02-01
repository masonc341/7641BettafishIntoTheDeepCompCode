package org.firstinspires.ftc.teamcode.TestArchive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Extendo;
import org.firstinspires.ftc.teamcode.mechanisms.ExtendoV2;
import org.firstinspires.ftc.teamcode.mechanisms.Intaker;
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV3;

@TeleOp
public class BResetPos extends LinearOpMode {
    @Override
    public void runOpMode() {
        Claw claw = new Claw(hardwareMap);
        ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        SlidesV3 slides = new SlidesV3(hardwareMap, true);
        Intaker intake = new Intaker(hardwareMap);

        waitForStart();
        
    }
}
