
package org.firstinspires.ftc.teamcode.Auto;

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

@Autonomous
public class SpeciAutoModel extends LinearOpMode {
    public static double speciDropX = -28.42;
    public static double speciDropY = -6.61;
    public static double speciDropH = 0;
    public static double block1X = -21.85;
    public static double block1Y = 18.47;
    public static double block1H = 125;
    public static double depositHumanH = 90;
    public static double block2X = -22.65;
    public static double block2Y = 27.62;
    public static double block2H = 125;
    public static double depositHumanH2 = 90;
    public static double pickUpX = -8;
    public static double pickUpY = 30;
    public static double pickUpH = 180;
    public static double dropSpecimenX = -28.42;
    public static double dropSpecimenY =-6.61 ;
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
                .strafeToLinearHeading(new Vector2d(speciDropX, speciDropY), Math.toRadians(speciDropH))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(block1X, block1Y), Math.toRadians(block1H))
                .waitSeconds(0.5)
                .turn(depositHumanH)
                .waitSeconds(1.9)
                .strafeToLinearHeading(new Vector2d(block2X, block2Y), Math.toRadians(block2H))
                .waitSeconds(1.9)
                .turn(depositHumanH2)
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

        /*
        Actions.runBlocking(new SequentialAction(
                firstSpeciDropOff,
                slides.slideTopBar(),
                slides.slideTopBarClip(),
                new SleepAction(0.25),
                claw.open(),
                new SleepAction(0.2),
                slides.retract(),
                new ParallelAction(
                        block1,
                        extendo.extend()
                ),
                intake.flat(),
                deposit1,
                block2,
                deposit2,
                new ParallelAction(
                        extendo.retract(),
                        intake.flop(),
                        pickUp1
                ),
                claw.close(),
                new ParallelAction(
                        slides.slideTopBar(),
                        secondSpeciDropOff
                ),
                slides.slideTopBarClip(),
                new SleepAction(0.25),
                claw.open(),
                new SleepAction(0.2),
                slides.retract(),
                pickUp2,
                claw.close(),
                new ParallelAction(
                        slides.slideTopBar(),
                        thirdSpeciDropOff
                ),
                slides.slideTopBarClip(),
                //new SleepAction(0.25),
                claw.open(),
                //new SleepAction(0.2),
                slides.retract(),
                pickUp3,
                claw.close(),
                new ParallelAction(
                        slides.slideTopBar(),
                        fourthSpeciDropOff
                ),
                slides.slideTopBarClip(),
                //new SleepAction(0.25),
                claw.open(),
                //new SleepAction(0.2),
                slides.retract(),
                park

        ));

         */


    }
}
