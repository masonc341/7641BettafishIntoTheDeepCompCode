
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
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.ExtendoV2;
import org.firstinspires.ftc.teamcode.mechanisms.Intaker;
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV2;

@Autonomous
@Config
public class SpeciAuto extends LinearOpMode {

    public static double c1speciDropX = -29.3;
    public static double c1speciDropY = -7.74;
    public static double c1speciDropH = 0;
    public static double c2block1X = -29;
    public static double c2block1Y = 28.26;
    public static double c2block1H = 130;
    public static double c3depositblock1X = -10.91;
    public static double c3depositblock1Y = 26.42;
    public static double c3depositblock1H = 83;
    public static double c4block2X = -31.34;
    public static double c4block2Y = 36.5;
    public static double c4block2H = 122;
    public static double c5depositblock2X = -11.99;
    public static double c5depositblock2Y = 29.87;
    public static double c5depositblock2H = 63;
    public static double pickUpX = -12.8;
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
        MecanumDrive drive = new MecanumDrive(hardwareMap, StartPose1);
        SlidesV2 slides = new SlidesV2(hardwareMap, true);
        ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Intaker intake = new Intaker(hardwareMap);


        TrajectoryActionBuilder pathT = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(c1speciDropX, c1speciDropY), Math.toRadians(c1speciDropH))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(c2block1X, c2block1Y), Math.toRadians(c2block1H))
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(c3depositblock1X, c3depositblock1Y), Math.toRadians(c3depositblock1H))
                .waitSeconds(1.4)
                .strafeToLinearHeading(new Vector2d(c4block2X, c4block2Y), Math.toRadians(c4block2H), null, new ProfileAccelConstraint(-25.0, 40.0))
                .waitSeconds(1.4)
                .strafeToLinearHeading(new Vector2d(c5depositblock2X, c5depositblock2Y), Math.toRadians(c5depositblock2H));
//                .strafeToLinearHeading(new Vector2d(pickUpX, pickUpY), Math.toRadians(pickUpH))
//                .waitSeconds(0.7)
//                .strafeToLinearHeading(new Vector2d(dropSpecimenX, dropSpecimenY), Math.toRadians(dropSpecimenH), null, new ProfileAccelConstraint(-80.0, 100.0))
//                .waitSeconds(1.5)
//                .strafeToLinearHeading(new Vector2d(pickUpX, pickUpY), Math.toRadians(pickUpH), null, new ProfileAccelConstraint(-80.0, 100.0))
//                .waitSeconds(0.7)
//                .strafeToLinearHeading(new Vector2d(dropSpecimenX, dropSpecimenY), Math.toRadians(dropSpecimenH))
//                .waitSeconds(1.5)
//                .strafeToLinearHeading(new Vector2d(pickUpX, pickUpY), Math.toRadians(pickUpH))
//                .waitSeconds(0.7)
//                .strafeToLinearHeading(new Vector2d(dropSpecimenX, dropSpecimenY), Math.toRadians(dropSpecimenH))
//                .waitSeconds(0.7)
//                .strafeToLinearHeading(new Vector2d(parkX, parkY), Math.toRadians(parkH));



        waitForStart();

        Action path = pathT.build();

        Actions.runBlocking(new ParallelAction(
                path,
                new SequentialAction(
                        extendo.retract(),
                        intake.flop(),
                        claw.flop(),
                        claw.open(),
                        slides.slideTopBar(),
                        slides.slideTopBarClip(),
                        new SleepAction(0.25),
                        claw.open(),
                        new SleepAction(0.2),
                        slides.retract(),
                        new SleepAction(1.2),
                        extendo.extend(),
                        intake.flip(),
                        new SleepAction(10),
                        new ParallelAction(
                                extendo.retract(),
                                intake.flop()
                        )
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


                )

        ));


    }
}
