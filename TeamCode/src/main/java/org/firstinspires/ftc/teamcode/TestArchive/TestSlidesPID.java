package org.firstinspires.ftc.teamcode.TestArchive;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV2;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class TestSlidesPID extends LinearOpMode {

    private SlidesV2 slides;

    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();
    private Telemetry tele = dash.getTelemetry();
    @Override
    public void runOpMode() {


        slides = new SlidesV2(hardwareMap, true);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        runningActions.add(slides.slideWallLevel());

        waitForStart();


        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();

            slides.updateMotors();

            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);

            if (currentGamepad1.b && !previousGamepad1.b) {
                runningActions.add(slides.retract());
            }

            if (currentGamepad1.y && !previousGamepad1.y) {
                runningActions.add(slides.slideTopBasket());
            }

            if (currentGamepad1.a && !previousGamepad1.a) {
                runningActions.add(slides.slideWallLevel());
            }


            List<Action> newActions = new ArrayList<>();
            for (Action action : runningActions) {
                action.preview(packet.fieldOverlay());
                if (action.run(packet)) {
                    newActions.add(action);
                }
            }
            runningActions = newActions;
            dash.sendTelemetryPacket(packet);

            telemetry.addData("PID", slides.getPID());
            telemetry.addData("Slides Left", slides.slidesLeftMotor.getCurrentPosition());
            telemetry.addData("Slides Right", slides.slidesRightMotor.getCurrentPosition());
            telemetry.update();

            tele.addData("PID", slides.getPID());
            tele.addData("Slides Left", slides.slidesLeftMotor.getCurrentPosition());
            tele.addData("Slides Right", slides.slidesRightMotor.getCurrentPosition());
            tele.addData("Slides Left Power", slides.slidesLeftMotor.getPower());
            tele.addData("Slides Right Power", slides.slidesRightMotor.getPower());
            tele.update();
        }

    }
}
