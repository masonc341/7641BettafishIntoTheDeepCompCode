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

    public static double apreloadX = -19.35;
    public static double apreloadY = 15.3;
    public static double apreloadH = 65;
    public static double bfirstsampleX = -8; //-18.5;
    public static double bfirstsampleY = 12; //20;
    public static double bfirstsampleH = 90; //76;
    public static double dfirstsampledepositX = -14.75;
    public static double dfirstsampledepositY = 15;
    public static double dfirstsampledepositH = 58;
    public static double cfirstsampleintakeH = 90;
    public static double cfirstsampleintakex = -8;
    public static double cfirstsampleintakey = 21;
    public static double esecondsampleH = 90;
    public static double esecondsamplex = -18.27;
    public static double esecondsampley = 13;
    public static double fsecondsampleintakeh = 90;
    public static double fsecondsampleintakex = -19.27;
    public static double fsecondsampleintakey = 24;
    public static double gsecondsampledepositX = -14.75;
    public static double gsecondsampledepositY = 15;
    public static double gsecondsampledepositH = 58;



    @Override
    public void runOpMode() {

        Pose2d StartPose1 = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, StartPose1);
        SlidesV2 slides = new SlidesV2(hardwareMap, true);
        ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Intaker intake = new Intaker(hardwareMap);


        TrajectoryActionBuilder pathT = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(apreloadX, apreloadY), Math.toRadians(apreloadH))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(bfirstsampleX, bfirstsampleY), Math.toRadians(bfirstsampleH))
                .waitSeconds(0.5)
                .strafeTo(new Vector2d(cfirstsampleintakex, cfirstsampleintakey))
                .waitSeconds(3.6)
                .strafeToLinearHeading(new Vector2d(dfirstsampledepositX, dfirstsampledepositY), Math.toRadians(dfirstsampledepositH))
                .waitSeconds(1.9)
                .strafeToLinearHeading(new Vector2d(esecondsamplex, esecondsampley), Math.toRadians(esecondsampleH))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(esecondsamplex, esecondsampley+1), Math.toRadians(esecondsampleH))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(fsecondsampleintakex, fsecondsampleintakey), Math.toRadians(fsecondsampleintakeh))
                .waitSeconds(3)
                .strafeToLinearHeading(new Vector2d(gsecondsampledepositX, gsecondsampledepositY), Math.toRadians(gsecondsampledepositH))
                .waitSeconds(100)
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
                                        //new SleepAction(1),
                                        intake.intake(),
                                        new SleepAction(3),
                                        intake.flop(),
                                        new SleepAction(0.2),
                                        intake.creep(),
                                        new SleepAction(0.1),
                                        extendo.retract(),
                                        new SleepAction(1.1),
                                        intake.extake()
                                )
                        ),
                        new SleepAction(1.2),
                        intake.off(),
                        claw.up(),
                        slides.slideTopBasket(),
                        new SleepAction(0.5),
                        new ParallelAction(
                                new SequentialAction(
                                        claw.flip(),
                                        new SleepAction(0.7),
                                        claw.flop(),
                                        new SleepAction(0.8),
                                        slides.retract()
                                ),
                                new SequentialAction(
                                        new SleepAction(2),
                                        extendo.extend(),
                                        new SleepAction(0.15),
                                        intake.flip(),
                                        //new SleepAction(1),
                                        intake.intake(),
                                        new SleepAction(1.8),
                                        intake.flop(),
                                        new SleepAction(0.15),
                                        intake.creep(),
                                        new SleepAction(0.15),
                                        extendo.retract(),
                                        new SleepAction(1.1),
                                        intake.extake()
                                )
                        ),
                        new SleepAction(1.2),
                        intake.off(),
                        claw.up(),
                        slides.slideTopBasket(),
                        new SleepAction(0.5),
                        new ParallelAction(
                                new SequentialAction(
                                        claw.flip(),
                                        new SleepAction(0.7),
                                        claw.flop(),
                                        new SleepAction(0.5),
                                        slides.retract()
                                ),
                                new SequentialAction(
                                        new SleepAction(1),
                                        extendo.extend(),
                                        new SleepAction(0.15),
                                        intake.flip(),
                                        new SleepAction(1),
                                        intake.intake(),
                                        new SleepAction(3),
                                        intake.flop(),
                                        new SleepAction(0.15),
                                        intake.creep(),
                                        extendo.retract(),
                                        new SleepAction(1.1),
                                        intake.extake()
                                )
                        )
                ),
                path
        ));
    }
}