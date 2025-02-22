package org.firstinspires.ftc.teamcode.AutoArchive;

import android.graphics.Color;

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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;


import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.ExtendoV2;
import org.firstinspires.ftc.teamcode.mechanisms.Intaker;
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV3;
import org.firstinspires.ftc.teamcode.mechanisms.SweeperSample;

@Disabled
@Config
@Autonomous(preselectTeleOp = "ABlueTeleop")
public class SpeciAuto extends LinearOpMode {

    public static double apreloadX = -17.97;
    public static double apreloadY = 12.644;
    public static double apreloadH = 68.3;
    public static double bfirstsampleX = -8; //-18.5;
    public static double bfirstsampleY = 12.5; //20;
    public static double bfirstsampleH = 90; //76;
    public static double cfirstsampleintakeH = 69.8;
    public static double cfirstsampleintakex = -15;
    public static double cfirstsampleintakey = 20;
    public static double dfirstsampledepositX = -16.5;
    public static double dfirstsampledepositY = 12;
    public static double dfirstsampledepositH = 58;
    public static double esecondsampleH = 90;
    public static double esecondsamplex = -17;
    public static double esecondsampley = 13.3;
    public static double fsecondsampleintakeh = 92.5;
    public static double fsecondsampleintakex = -16.94;
    public static double fsecondsampleintakey = 21;
    public static double gsecondsampledepositX = -16.75;
    public static double gsecondsampledepositY = 13;
    public static double gsecondsampledepositH = 58;
    public static double hthirdsampleH = 140;
    public static double hthirdsamplex = -7.5;
    public static double hthirdsampley = 23.3;
    public static double ithirdsamplealignH = 123.35;
    public static double ithirdsamplealignx = -15.75;
    public static double ithirdsamplealigny = 15.9;
    public static double jthirdsampleintakeh = 129;
    public static double jthirdsampleintakex = -18;
    public static double jthirdsampleintakey = 24.6;
    public static double ksubmersibleintakex = 15;
    public static double ksubmersibleintakey = 55;
    public static double ksubmersibleintakeh = 0;
    public static double parkX = 10;
    public static double kyes = 65;

    public static double parkY = 67.07;

    public static double parkHead1 = 180;

    public static double parkHead2 = 0;

    public String intakeColor;


    @Override
    public void runOpMode() {


        Pose2d StartPose1 = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, StartPose1);
        SlidesV3 slides = new SlidesV3(hardwareMap, true);
        ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Intaker intake = new Intaker(hardwareMap);
        SweeperSample sampleSweeper = new SweeperSample(hardwareMap);
        NormalizedColorSensor colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");


        final float[] hsvValues = new float[3];

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }

        NormalizedRGBA colors = null;

        colorSensor.setGain(120);

        TrajectoryActionBuilder pathT = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(apreloadX, apreloadY), Math.toRadians(apreloadH))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(cfirstsampleintakex, cfirstsampleintakey), Math.toRadians(cfirstsampleintakeH))
                .waitSeconds(1.8)
                .strafeToLinearHeading(new Vector2d(dfirstsampledepositX, dfirstsampledepositY), Math.toRadians(dfirstsampledepositH))
                .waitSeconds(0.7)
//                .strafeToLinearHeading(new Vector2d(esecondsamplex, esecondsampley), Math.toRadians(esecondsampleH))
                .strafeToLinearHeading(new Vector2d(fsecondsampleintakex, fsecondsampleintakey), Math.toRadians(fsecondsampleintakeh), null, new ProfileAccelConstraint(-25.0, 35.0))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(gsecondsampledepositX, gsecondsampledepositY), Math.toRadians(gsecondsampledepositH))
                .waitSeconds(0.9)
                .splineToLinearHeading(new Pose2d(jthirdsampleintakex, jthirdsampleintakey, Math.toRadians(jthirdsampleintakeh)), Math.toRadians(jthirdsampleintakeh), null, new ProfileAccelConstraint(-25, 35.0))
                .waitSeconds(1.1)
                .strafeToLinearHeading(new Vector2d(apreloadX + 1, apreloadY + 1), Math.toRadians(apreloadH - 7.5))
                .waitSeconds(0.9)
                .splineToLinearHeading(new Pose2d(ksubmersibleintakex - 5, ksubmersibleintakey, Math.toRadians(ksubmersibleintakeh)), Math.toRadians(0))
                .waitSeconds(0.1)
                .strafeToLinearHeading(new Vector2d(ksubmersibleintakex + 9, ksubmersibleintakey), Math.toRadians(ksubmersibleintakeh - 10))
                .waitSeconds(0.1)
                .strafeToLinearHeading(new Vector2d(ksubmersibleintakex, ksubmersibleintakey + 5), Math.toRadians(kyes), null, new ProfileAccelConstraint(-25.0, 25.0))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(apreloadX, apreloadY), Math.toRadians(apreloadH))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(apreloadX + 10, apreloadY + 10), Math.toRadians(apreloadH));


        Action path = pathT.build();

        waitForStart();


        Actions.runBlocking(new ParallelAction(
                new SequentialAction(
                        extendo.extend(),
                        intake.flip(),
                        new SleepAction(1.),
                        intake.intake(),
                        new SleepAction(3),
                        extakeOrNot(colors, hsvValues, colorSensor, intake, extendo),
                        new SleepAction(10),
                        intake.creep(),
                        new SleepAction(5)
                )
                //path
        ));


    }

    public Action extakeOrNot(NormalizedRGBA colors, float[] hsvValues, NormalizedColorSensor colorSensor, Intaker intake, ExtendoV2 extendo) {
        colors = colorSensor.getNormalizedColors(); // Update sensor values in the loop
        Color.colorToHSV(colors.toColor(), hsvValues);


        if (hsvValues[0] >= 60 && hsvValues[0] < 100 && hsvValues[1] > 0.5) {
            intakeColor = "yellow";
        } else if ((hsvValues[0] > 10 && hsvValues[0] < 70 && hsvValues[1] >= 0.3) ||
                (hsvValues[0] > 60 && hsvValues[0] < 140 && hsvValues[1] < 0.4)) {
            intakeColor = "red";
        } else if (hsvValues[0] > 155 && hsvValues[0] < 240) {
            intakeColor = "blue";
        } else {
            intakeColor = "none";
        }

        telemetry.addData("colar", intakeColor);
        telemetry.addData("h", hsvValues[0]);
        telemetry.addData("s", hsvValues[1]);
        telemetry.addData("v", hsvValues[2]);
        telemetry.update();

        if (intakeColor.equals("yellow") || intakeColor.equals("blue") || intakeColor.equals("none")) {
            return new SequentialAction(
                    intake.flop(),
                    new SleepAction(0.15),
                    intake.creep(),
                    new SleepAction(0.15),
                    extendo.retract()
            );
        } else {
            return new SequentialAction(
                    intake.extake(),
                    new SleepAction(0.2),
                    intake.flop(),
                    new SleepAction(0.15),
                    intake.creep(),
                    new SleepAction(0.15),
                    extendo.retract()
            );
        }
    }
}