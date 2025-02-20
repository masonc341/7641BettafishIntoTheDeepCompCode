

package org.firstinspires.ftc.teamcode.Auto.Paths;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Auto.Vision.Limelight;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.ExtendoV2;
import org.firstinspires.ftc.teamcode.mechanisms.Intaker;

@Autonomous
public class SpeciPathWithLimelightWWW extends LinearOpMode{


    @Override
    public void runOpMode() {
        Limelight limelight = new Limelight(hardwareMap);
        Pose2d StartPose1 = new Pose2d(0,0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, StartPose1);
        ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        Intaker intake = new Intaker(hardwareMap);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.clear();


        waitForStart();

            Actions.runBlocking(
                    new SequentialAction(
                            limelight.align(drive, telemetry),
                            extendo.extend(),
                            new SleepAction(1),
                            intake.flop()
                    )
            );
    }

}