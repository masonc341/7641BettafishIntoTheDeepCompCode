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
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.ExtendoV2;
import org.firstinspires.ftc.teamcode.mechanisms.Intaker;
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV2;

@Config
@Autonomous
public class BasketAuto extends LinearOpMode {

    public static double depositPreloadX = -10.64;
    public static double depositPreloadY = 7.05;
    public static double depositFirstX = -10.64;
    public static double depositFirstY = 7.05;
    public static double depositSecondX = -10.64;
    public static double depositSecondY = 7.05;
    public static double depositThirdX = -10.64;
    public static double depositThirdY = 7.05;
    public static double firstSampleX = -0.7;
    public static double firstSampleY = 13.27;
    public static double secondSampleX = -8.9;
    public static double secondSampleY = 13.27;
    public static double thirdSampleX = -4.62;
    public static double thirdSampleY = 23.9;
    public static double park1X = -16.31;
    public static double park1Y = 52.08;
    public static double park2X = 12.99;
    public static double park2Y = 55.52;
    @Override
    public void runOpMode() {

        Pose2d StartPose1 = new Pose2d(0,0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, StartPose1);
        SlidesV2 slides = new SlidesV2(hardwareMap, true);
        ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Intaker intake = new Intaker(hardwareMap);

        TrajectoryActionBuilder fresh = drive.actionBuilder(StartPose1);

        Action depositPreload = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(-10.64, 7.05), Math.toRadians(48))
                .build();
        Action firstSample = fresh.fresh()
                .strafeToLinearHeading(new Vector2d(-0.7, 13.27), Math.toRadians(105))
                .build();
        Action firstSampleSweep = fresh.fresh()
                .strafeToLinearHeading(new Vector2d(firstSampleX, firstSampleY + 3), Math.toRadians(90))
                .build();
        Action depositFirst = fresh.fresh()
                .strafeToLinearHeading(new Vector2d(depositFirstX, depositFirstY), Math.toRadians(45))
                .build();
        Action secondSample = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(secondSampleX, secondSampleY), Math.toRadians(90))
                .build();
        Action secondSampleSweep = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(secondSampleX, secondSampleY + 3), Math.toRadians(90))
                .build();
        Action depositSecond = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(depositSecondX, depositSecondY), Math.toRadians(45))
                .build();

        Action thirdSample = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(thirdSampleX, thirdSampleY), Math.toRadians(125))
                .build();
        Action thirdSampleSweep = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(thirdSampleX, thirdSampleY + 3), Math.toRadians(125))
                .build();
        Action depositThird = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(depositThirdX, depositThirdY), Math.toRadians(45))
                .build();
        Action park = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(park1X, park1Y), Math.toRadians(-180))
                .strafeToLinearHeading(new Vector2d(park2X, park2Y), Math.toRadians(-180))
                .build();

        TrajectoryActionBuilder path = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(-10.64, 7.05), Math.toRadians(48))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-9.95, 8.97), Math.toRadians(90))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-15.75, 6.99), Math.toRadians(45))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-15.60, 9.45), Math.toRadians(90))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-15.75, 6.99), Math.toRadians(45))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-14.92, 10.38), Math.toRadians(125))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-15.75, 6.99), Math.toRadians(45))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-16.31, 52.08), Math.toRadians(-180))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(12.99, 55.52), Math.toRadians(-180));

        waitForStart();
        Actions.runBlocking(new SequentialAction(
                //extendo.retract(),
                //claw.flop(),
                //intake.flop(),
                //preload
                //new ParallelAction(
                        depositPreload,
                        //slides.slideTopBasket(),
                        //extendo.balance()
                //),
                //claw.flip(),
                new SleepAction(0.7),
                //claw.flop(),
                //0-1
                //new ParallelAction(
                        firstSample,
                        //extendo.extend(),
                        //intake.flip(),
                        //intake.intake()
                //),
                new SleepAction(0.7),
                //new ParallelAction(
                        //slides.retract(),
                        firstSampleSweep
                //),
//                new ParallelAction(
//                        //intake.creep(),
//                        //extendo.retract()
//                ),
                //depositFirst,
                //intake.extake(),
//                new SleepAction(0.7),
//                //claw.up(),
//                new ParallelAction(
//                        //slides.slideTopBasket(),
//                        //extendo.balance()
//                ),
//                //claw.flip(),
//                new SleepAction(0.7)
                //claw.flop()
//                //0-2
//                new ParallelAction(
//                        secondSample,
//                        slides.retract(),
//                        extendo.extend(),
//                        intake.flip(),
//                        intake.intake()
//                ),
//                secondSampleSweep,
//                new ParallelAction(
//                        intake.creep(),
//                        extendo.retract()
//                ),
//                depositSecond,
//                intake.extake(),
//                new SleepAction(0.7),
//                claw.up(),
//                new ParallelAction(
//                        slides.slideTopBasket(),
//                        extendo.balance()
//                ),
//                claw.flip(),
//                new SleepAction(0.7),
//                claw.flop(),
//                //0-3
//                new ParallelAction(
//                        thirdSample,
//                        slides.retract(),
//                        extendo.extend(),
//                        intake.flip(),
//                        intake.intake()
//                ),
//                thirdSampleSweep,
//                new ParallelAction(
//                        intake.creep(),
//                        extendo.retract()
//                ),
//                depositThird,
//                intake.extake(),
//                new SleepAction(0.7),
//                claw.up(),
//                new ParallelAction(
//                        slides.slideTopBasket()
//                ),
//                claw.flip(),
//                new SleepAction(0.7),
//                claw.flop(),
//                new ParallelAction(
//                        park,
//                        slides.slideBottomBar()
//                )
        ));


    }

}
