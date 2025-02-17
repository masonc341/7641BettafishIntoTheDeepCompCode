

package org.firstinspires.ftc.teamcode.Auto.Paths;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Auto.Vision.Limelight;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.MecanumDriveSWBot;

import java.util.concurrent.Callable;

@Autonomous
public class SpeciPathWithLimelight extends LinearOpMode{


    @Override
    public void runOpMode() {
        Limelight limelight = new Limelight(hardwareMap);
        Pose2d StartPose1 = new Pose2d(0,0, Math.toRadians(0));
        MecanumDriveSWBot drive = new MecanumDriveSWBot(hardwareMap, StartPose1);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.clear();


        waitForStart();

        while(opModeIsActive()) {
            Actions.runBlocking(
                    limelight.align(drive, telemetry)

                    // extendo.runToDistance(limelight.getDistance(cameraHeight); 13 in this instance? Implement once tuned
                    // extendo.retract();
                    // back up
            );
            telemetry.addData("Test", limelight.getForwardDistance(13));
            telemetry.update();

            double tx;

        /*while (true){

            tx = limelight.getTx();
            telemetry.addData("tx", tx);
            telemetry.update();
        }*/

            //new Vector2d(1,1);

        }
    }

}