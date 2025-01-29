package org.firstinspires.ftc.teamcode.Testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Slides;
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV2;
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV3;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class TestHang extends LinearOpMode {

    private SlidesV3 slides;

    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();
    private Telemetry tele = dash.getTelemetry();
    @Override
    public void runOpMode() {


        slides = new SlidesV3(hardwareMap, true);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();


        waitForStart();


        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();


            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);


            if (currentGamepad1.x && !previousGamepad1.x) {
                runningActions.add(slides.retract());
            }

            if (currentGamepad1.a && !previousGamepad1.a) {
                runningActions.add(slides.slideTopBasket());
            }

            if (currentGamepad1.b && !previousGamepad1.b) {
                slides.slidesLeftMotor.setPower(0);
                slides.slidesRightMotor.setPower(0);
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


            tele.addData("slidesLeftMotor", slides.slidesLeftMotor.getCurrentPosition());
            tele.addData("slidesRightMotor", slides.slidesRightMotor.getCurrentPosition());
            tele.addData("slidesLeftMotor power", slides.slidesLeftMotor.getPower());
            tele.addData("slidesRightMotor power", slides.slidesRightMotor.getPower());
            tele.addData("avg", slides.getPos());
            tele.update();
        }

    }
}