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

    public static double X1 = -18.55;
    public static double Y1 = 14.5;
    public static double H1 = 55;
    public static double X2 = -16.5;
    public static double Y2 = 18;
    public static double H2 = 76;

    @Override
    public void runOpMode() {

        Pose2d StartPose1 = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, StartPose1);
        SlidesV2 slides = new SlidesV2(hardwareMap, true);
        ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Intaker intake = new Intaker(hardwareMap);


        TrajectoryActionBuilder pathT = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(X1, Y1), Math.toRadians(H1))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(X2, Y2), Math.toRadians(H2))
                .waitSeconds(4)
                .strafeToLinearHeading(new Vector2d(-20.75, 14.78), Math.toRadians(79))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-15.47, 20.08), Math.toRadians(121))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(-14.68, 8.01), Math.toRadians(45))
                .waitSeconds(1.5)
                .splineToLinearHeading(new Pose2d(11.87, 67.07, Math.toRadians(180)), Math.toRadians(0));


        Action path = pathT.build();

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                new SequentialAction(
                        extendo.retract(),
                        claw.up(),
                        intake.flop(),
                        new ParallelAction(
                                slides.slideTopBasket(),
                                new SleepAction(2)
                        ),
                        new ParallelAction(
                                new SequentialAction(
                                        claw.flip(),
                                        new SleepAction(0.7),
                                        claw.flop(),
                                        slides.retract()
                                ),
                                new SequentialAction(
                                        extendo.extend(),
                                        new SleepAction(0.15),
                                        intake.flip(),
                                        new SleepAction(1),
                                        intake.intake(),
                                        new SleepAction(1),
                                        intake.flop(),
                                        intake.creep(),
                                        extendo.retract(),
                                        new SleepAction(1.1),
                                        intake.extake()
                                )
                        ),
                        new SleepAction(1),
                        slides.slideTopBasket(),
                        intake.off(),
                        claw.flip(),
                        new SleepAction(0.5),
                        claw.flop(),
                        slides.retract()


                ),
                path
        ));
    }
}