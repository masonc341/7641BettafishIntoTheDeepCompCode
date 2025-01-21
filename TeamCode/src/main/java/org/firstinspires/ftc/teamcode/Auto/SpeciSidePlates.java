
package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.MecanumDriveSWBot;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.ExtendoV2;
import org.firstinspires.ftc.teamcode.mechanisms.Intaker;
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV2;

@Autonomous
@Config
public class SpeciSidePlates extends LinearOpMode {

    public static double speciDropX = -29.3;
    public static double speciDropY = -7.74;
    public static double wayPointX = -20.9;
    public static double wayPointY = 18;
    public static double speciDropH = 0;
    public static double block1X = -45.89;
    public static double block1Y = 41.6;
    public static double block1H = -90;
    public static double depositHumanH = -90;
    public static double block2X = -45.9;
    public static double block2Y = 51.2;
    public static double block2H = -90;
    public static double depositHumanH2 = -90;
    public static double pickUpX = -3.8;
    public static double pickUpY = 42;
    public static double pickUpH = -180;
    public static double dropSpecimenX = -29.3;
    public static double dropSpecimenY =-7.74 ;
    public static double dropSpecimenH =0;
    public static double parkX = -6;
    public static double parkY = 32;
    public static double parkH = 180;

    @Override
    public void runOpMode() {

        Pose2d StartPose1 = new Pose2d(0,0, Math.toRadians(0));
        MecanumDriveSWBot drive = new MecanumDriveSWBot(hardwareMap, StartPose1);
        //SlidesV2 slides = new SlidesV2(hardwareMap, true);
        //ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        //Claw claw = new Claw(hardwareMap);
        //Intaker intake = new Intaker(hardwareMap);


        TrajectoryActionBuilder pathT = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(speciDropX, speciDropY), Math.toRadians(speciDropH))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(wayPointX, wayPointY), Math.toRadians(-90))
                .strafeToLinearHeading(new Vector2d(block1X, block1Y), Math.toRadians(block1H))
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(block1X+40, block1Y), Math.toRadians(depositHumanH))
                .waitSeconds(1.9)
                .strafeToLinearHeading(new Vector2d(block2X, block2Y), Math.toRadians(block2H))
                .waitSeconds(1.9)
                .strafeToLinearHeading(new Vector2d(block1X+40, block2Y), Math.toRadians(depositHumanH2))
                .strafeToLinearHeading(new Vector2d(pickUpX, pickUpY), Math.toRadians(pickUpH))
                .waitSeconds(0.7)
                .strafeToLinearHeading(new Vector2d(dropSpecimenX, dropSpecimenY), Math.toRadians(dropSpecimenH))
                .waitSeconds(3)
                .strafeToLinearHeading(new Vector2d(pickUpX, pickUpY), Math.toRadians(pickUpH))
                .waitSeconds(0.7)
                .strafeToLinearHeading(new Vector2d(dropSpecimenX, dropSpecimenY), Math.toRadians(dropSpecimenH))
                .waitSeconds(3)
                .strafeToLinearHeading(new Vector2d(pickUpX, pickUpY), Math.toRadians(pickUpH))
                .waitSeconds(0.7)
                .strafeToLinearHeading(new Vector2d(dropSpecimenX, dropSpecimenY), Math.toRadians(dropSpecimenH))
                .waitSeconds(0.7)
                .strafeToLinearHeading(new Vector2d(parkX, parkY), Math.toRadians(parkH));



        waitForStart();

        Action path = pathT.build();

        Actions.runBlocking(new ParallelAction(
                path

//                new SequentialAction(
//                        slides.slideTopBar(),
//                        slides.slideTopBarClip(),
//                        new SleepAction(0.25),
//                        claw.open(),
//                        new SleepAction(0.2),
//                        slides.retract(),
//                        new ParallelAction(
//
//                                extendo.extend()
//                        ),
//                        intake.flat(),
//                        new ParallelAction(
//                                extendo.retract(),
//                                intake.flop()
//                        ),
//                        claw.close(),
//                        new ParallelAction(
//                                slides.slideTopBar()
//                        ),
//                        slides.slideTopBarClip(),
//                        new SleepAction(0.25),
//                        claw.open(),
//                        new SleepAction(0.2),
//                        slides.retract(),
//                        claw.close(),
//                        new ParallelAction(
//                                slides.slideTopBar()
//                        ),
//                        slides.slideTopBarClip(),
//                        //new SleepAction(0.25),
//                        claw.open(),
//                        //new SleepAction(0.2),
//                        slides.retract(),
//                        claw.close(),
//                        new ParallelAction(
//                                slides.slideTopBar()
//                        ),
//                        slides.slideTopBarClip(),
//                        //new SleepAction(0.25),
//                        claw.open(),
//                        //new SleepAction(0.2),
//                        slides.retract()
//
//
//                )

        ));


    }
}
