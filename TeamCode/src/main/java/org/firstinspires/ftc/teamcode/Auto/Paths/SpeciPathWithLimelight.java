<<<<<<< HEAD

package org.firstinspires.ftc.teamcode.Auto.Paths;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Auto.Vision.Limelight;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.MecanumDriveSWBot;

@Autonomous
public class SpeciPathWithLimelight extends LinearOpMode{
    @Override
    public void runOpMode() {
        Limelight limelight = new Limelight(hardwareMap);
        Pose2d StartPose1 = new Pose2d(0,0, Math.toRadians(0));
        MecanumDriveSWBot drive = new MecanumDriveSWBot(hardwareMap, StartPose1);



        waitForStart();
        Actions.runBlocking(limelight.align(drive));
        new Vector2d(1,1);





        }

}
=======
//
//package org.firstinspires.ftc.teamcode.Auto.Paths;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.hardware.limelightvision.Limelight3A;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.Auto.Vision.Limelight;
//import org.firstinspires.ftc.teamcode.MecanumDrive;
//import org.firstinspires.ftc.teamcode.MecanumDriveSWBot;
//
//@Autonomous
//public class SpeciPathWithLimelight extends LinearOpMode{
//    @Override
//    public void runOpMode() {
//        Limelight limelight = new Limelight(hardwareMap);
//        Pose2d StartPose1 = new Pose2d(0,0, Math.toRadians(0));
//        MecanumDriveSWBot drive = new MecanumDriveSWBot(hardwareMap, StartPose1);
//
//
//
//        waitForStart();
//        Actions.runBlocking(limelight.align(drive));
//        new Vector2d(1,1);
//
//    }
//
//
//            waitForStart();
//            Actions.runBlocking(limelight.align(drive));
//            new Vector2d(1,1);
//
//        }
//
//}
>>>>>>> 89beaa6c17cd3d60c8bc9abe51cba50dc07377c4
