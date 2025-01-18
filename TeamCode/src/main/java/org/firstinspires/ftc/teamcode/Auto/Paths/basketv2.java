package org.firstinspires.ftc.teamcode.Auto.Paths;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous
public class basketv2 extends LinearOpMode {

    @Override
    public void runOpMode() {

        Pose2d StartPose1 = new Pose2d(0,0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, StartPose1);

        TrajectoryActionBuilder path = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(-15.75, 6.99), Math.toRadians(45))
                .waitSeconds(1.5);
        Action path2 = path.fresh()
                .strafeToLinearHeading(new Vector2d(-9.95, 8.97), Math.toRadians(90))
                .waitSeconds(1.5)
                .build();
        Action path3 = path.fresh()
                .strafeToLinearHeading(new Vector2d(-15.75, 6.99), Math.toRadians(45))

                .build();

        Action pathh = path.build();
        waitForStart();
        Actions.runBlocking(new SequentialAction(
                pathh,
                path2,
                path3)
        );


    }

}
