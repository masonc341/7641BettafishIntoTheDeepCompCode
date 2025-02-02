package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
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
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV3;

@Config
@Autonomous(preselectTeleOp = "ABlueTeleop")
public class BlueBasketAuto extends LinearOpMode {

    public static double apreloadX = -18.35;
    public static double apreloadY = 14;
    public static double apreloadH = 65;
    public static double bfirstsampleX = -8; //-18.5;
    public static double bfirstsampleY = 11.5; //20;
    public static double bfirstsampleH = 90; //76;
    public static double dfirstsampledepositX = -16.5;
    public static double dfirstsampledepositY = 12;
    public static double dfirstsampledepositH = 58;
    public static double cfirstsampleintakeH = 90;
    public static double cfirstsampleintakex = -8;
    public static double cfirstsampleintakey = 25;
    public static double esecondsampleH = 90;
    public static double esecondsamplex = -18.27;
    public static double esecondsampley = 15.5;
    public static double fsecondsampleintakeh = 95;
    public static double fsecondsampleintakex = -18.27;
    public static double fsecondsampleintakey = 25;
    public static double gsecondsampledepositX = -15.75;
    public static double gsecondsampledepositY = 14;
    public static double gsecondsampledepositH = 58;
    public static double hthirdsampleH = 140;
    public static double hthirdsamplex = -7.5;
    public static double hthirdsampley = 23.3;
    public static double ithirdsamplealignH = 140;
    public static double ithirdsamplealignx = -6.4;
    public static double ithirdsamplealigny = 22.3;
    public static double jthirdsampleintakeh = 140;
    public static double jthirdsampleintakex = -12.76;
    public static double jthirdsampleintakey = 27.8;
    public static double parkX = 10;

    public static double parkY = 67.07;

    public static double parkHead1 = 180;

    public static double parkHead2 = 0;




    @Override
    public void runOpMode() {

        Pose2d StartPose1 = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, StartPose1);
        SlidesV3 slides = new SlidesV3(hardwareMap, true);
        ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Intaker intake = new Intaker(hardwareMap);


        TrajectoryActionBuilder pathT = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(apreloadX, apreloadY), Math.toRadians(apreloadH))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(bfirstsampleX, bfirstsampleY), Math.toRadians(bfirstsampleH))
                .waitSeconds(0.3)
                .strafeTo(new Vector2d(cfirstsampleintakex, cfirstsampleintakey), null, new ProfileAccelConstraint(-25.0, 35.0))
                .waitSeconds(2.7)
                .strafeToLinearHeading(new Vector2d(dfirstsampledepositX, dfirstsampledepositY), Math.toRadians(dfirstsampledepositH))
                .waitSeconds(1.9)
                .strafeToLinearHeading(new Vector2d(esecondsamplex, esecondsampley-0.5), Math.toRadians(esecondsampleH))
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(esecondsamplex, esecondsampley+0.5), Math.toRadians(esecondsampleH+1))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(fsecondsampleintakex, fsecondsampleintakey), Math.toRadians(fsecondsampleintakeh), null, new ProfileAccelConstraint(-25.0, 35.0))
                .waitSeconds(3)
                .strafeToLinearHeading(new Vector2d(gsecondsampledepositX, gsecondsampledepositY), Math.toRadians(gsecondsampledepositH))
                .waitSeconds(1.9)
                //.strafeToLinearHeading(new Vector2d(hthirdsamplex, hthirdsampley-1.5), Math.toRadians(hthirdsampleH))
                //.waitSeconds(0.2)
                .strafeToLinearHeading(new Vector2d(ithirdsamplealignx, ithirdsamplealigny+0.25), Math.toRadians(ithirdsamplealignH+3), null, new ProfileAccelConstraint(-25.0, 35.0))
                .waitSeconds(0.2)
                .strafeToLinearHeading(new Vector2d(jthirdsampleintakex, jthirdsampleintakey), Math.toRadians(jthirdsampleintakeh))
                .waitSeconds(3.4)
                //.strafeToLinearHeading(new Vector2d(apreloadX+1, apreloadY+1), Math.toRadians(apreloadH), null, new ProfileAccelConstraint(-25.0, 40.0))
                //.waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(apreloadX, apreloadY), Math.toRadians(apreloadH+4), null, new ProfileAccelConstraint(-25.0, 40.0))
                .waitSeconds(0.95)
                .splineToLinearHeading(new Pose2d(parkX, parkY, Math.toRadians(parkHead1)), Math.toRadians(parkHead2));




        Action path = pathT.build();

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                new SequentialAction(
                        extendo.retract(),
                        claw.up(),
                        claw.open(),
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
                                        new SleepAction(0.7),
                                        slides.retract()
                                ),
                                new SequentialAction(
                                        new SleepAction(0.8),
                                        extendo.extend(),
                                        new SleepAction(0.1),
                                        intake.flip(),
                                        new SleepAction(1.5),
                                        intake.intake(),
                                        new SleepAction(1.25),
                                        intake.flop(),
                                        new SleepAction(0.2),
                                        intake.creep(),
                                        new SleepAction(0.1),
                                        extendo.retract(),
                                        new SleepAction(1.1),
                                        intake.extake()
                                )
                        ),
                        new SleepAction(0.5),
                        intake.off(),
                        claw.up(),
                        slides.slideTopBasket(),
                        new SleepAction(0.5),
                        new ParallelAction(
                                new SequentialAction(
                                        claw.flip(),
                                        new SleepAction(0.7),
                                        claw.flop(),
                                        new SleepAction(1.35),
                                        slides.retract()
                                ),
                                new SequentialAction(
                                        new SleepAction(2),
                                        extendo.extend(),
                                        new SleepAction(0.1),
                                        intake.flip(),
                                        //new SleepAction(1),
                                        intake.intake(),
                                        new SleepAction(1.95),
                                        intake.flop(),
                                        new SleepAction(0.15),
                                        intake.creep(),
                                        new SleepAction(0.15),
                                        extendo.retract(),
                                        new SleepAction(1.1),
                                        intake.extake()
                                )
                        ),
                        new SleepAction(0.5),
                        intake.off(),
                        claw.up(),
                        slides.slideTopBasket(),
                        new SleepAction(0.5),
                        new ParallelAction(
                                new SequentialAction(
                                        claw.flip(),
                                        new SleepAction(0.7),
                                        claw.flop(),
                                        new SleepAction(0.7),
                                        slides.retract()
                                ),
                                new SequentialAction(
                                        new SleepAction(1.5),     //4th start here
                                        extendo.extend(),
                                        new SleepAction(0.3),
                                        intake.flip(),
                                        new SleepAction(0.8),
                                        intake.intake(),
                                        new SleepAction(2),
                                        intake.flop(),
                                        new SleepAction(0.15),
                                        intake.creep(),
                                        extendo.retract(),
                                        new SleepAction(1.1),
                                        intake.extake()
                                )
                        ),
                        new SleepAction(0.5),
                        intake.off(),
                        claw.up(),
                        slides.slideTopBasket(),
                        new SleepAction(0.75),
                        new ParallelAction(
                                new SequentialAction(
                                        claw.flip(),
                                        new SleepAction(0.7),
                                        claw.flop(),
                                        new SleepAction(0.45),
                                        slides.slidePark()
                                )
                        )

                ),
                path
        ));
    }
}