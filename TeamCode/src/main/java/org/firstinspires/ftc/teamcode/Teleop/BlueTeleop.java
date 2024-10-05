/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Auto.Vision.Pipeline;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Extendo;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Slides;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.List;


@TeleOp
public class BlueTeleop extends LinearOpMode {

    private enum ExtendoState {EXTENDOSTART, EXTENDOEXTEND, EXTENDOINTAKE, EXTENDORETRACT}
    private ExtendoState extendoState = ExtendoState.EXTENDOSTART;

    private enum LiftState {LIFTSTART, SAMPLEEXTEND, SAMPLEFLIP, SAMPLEDEPOSIT, SAMPLEFLOP, SAMPLERETRACT, SPECIMANEXTEND, SPECIMANFLIP, SPECIMANGRAB, SPECIMANPUTUP, SPECIMANPUTDOWN}
    private LiftState liftState = LiftState.LIFTSTART;

    private final double EXTENDOLENGTH = 100;
    private final double HIGHBASKET = 100;
    private final double LOWBASKET = 50;
    private final double WALLHEIGHT = 100;
    private final double SPECIMANHIGH = 100;
    private final double SPECIMANLOW = 50;
    private boolean baskethigh = true;
    private boolean specimanhigh = true;

    Pipeline vision = new Pipeline(telemetry);
    OpenCvWebcam webcam1 = null;

    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();

    public static boolean liftOn = false;
    public void drivetrain(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR){
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        FL.setPower(frontLeftPower);
        BL.setPower(backLeftPower);
        FR.setPower(frontRightPower);
        BR.setPower(backRightPower);
    }

    public void buttonpress(Extendo extendo, Intake intake, Slides slides, Claw claw) {
        double y = gamepad2.left_stick_y;

        //TODO: change values to the min/max of how far extendo extends
        if (extendo.getPos() > 0 && extendo.getPos() < 10000) {
            extendo.extendoLeft.setPower(y);
            extendo.extendoRight.setPower(y);
        }
        //TODO: change value to ~1/3 of max extendo length or smth like that
        if (extendo.getPos() > 200) {
            if (!Intake.flipped) {
                runningActions.add(intake.flip());
            }
            if (vision.colorDetected().equals("Blue") || vision.colorDetected().equals("Yellow")) {
                intake.intakeMotor.setPower(0);
                runningActions.add(new SequentialAction(
                        intake.flop(),
                        extendo.retract()
                ));
            } else if (vision.colorDetected().equals("Red")) {
                intake.intakeMotor.setPower(-1);
            } else {
                intake.intakeMotor.setPower(1);
            }
        }

        if (gamepad2.x) {
            if (!liftOn) {
                liftOn = true;
                runningActions.add(new SequentialAction(
                        claw.close(),
                        slides.slideTopBasket(),
                        claw.flip()
                ));
            } else {
                runningActions.add(new SequentialAction(
                        claw.open(),
                        claw.flop(),
                        slides.retract()
                ));
            }

        }
        /*
        if (extendo extends far enough) {
            flip intake
            run intake
            >sensor input false<
            reverse intake
            >sensor input true<
            stop intake
            flop intake
            retract extendo
        }

        if x {
            close claw
            extend slides
            flip claw
            >user input<
            open claw
            flop claw
            retract slides
        }
        if y {
            extend slides to wall
            close claw
            extend slides to high
            >user input<
            retract slides
       }
         */
    }
    @Override
    public void runOpMode() {

        DcMotor FL = hardwareMap.get(DcMotor.class, "FL");
        DcMotor FR = hardwareMap.get(DcMotor.class, "FR");
        DcMotor BL = hardwareMap.get(DcMotor.class, "BL");
        DcMotor BR = hardwareMap.get(DcMotor.class, "BR");

        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        Extendo extendo = new Extendo(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Slides slides = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam1");
        int cameraMoniterViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMoniterViewId", "id", hardwareMap.appContext.getPackageName());
        webcam1 = OpenCvCameraFactory.getInstance().createWebcam(webcamName,cameraMoniterViewId);
        webcam1.setPipeline(new Pipeline(telemetry));
        webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
            public void onOpened(){
                webcam1.startStreaming(640,480, OpenCvCameraRotation.UPRIGHT);

            }
            public void onError(int errorCode){

            }

        });

        waitForStart();

        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();

            drivetrain(FL, FR, BL, BR);
            buttonpress(extendo, intake, slides, claw);

            List<Action> newActions = new ArrayList<>();
            for (Action action : runningActions) {
                action.preview(packet.fieldOverlay());
                if (action.run(packet)) {
                    newActions.add(action);
                }
            }
            runningActions = newActions;

            dash.sendTelemetryPacket(packet);

            telemetry.addData("intakeCamera", vision.colorDetected());
            telemetry.update();
        }
    }
}