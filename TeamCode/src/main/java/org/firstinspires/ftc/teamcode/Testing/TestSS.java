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
import org.firstinspires.ftc.teamcode.mechanisms.SweeperSample;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class TestSS extends LinearOpMode {

    private SweeperSample sampleSweeper;

    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();
    private Telemetry tele = dash.getTelemetry();
    @Override
    public void runOpMode() {


        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        sampleSweeper = new SweeperSample(hardwareMap);

        waitForStart();


        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();


            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);

            if (currentGamepad1.a && !previousGamepad1.a) {
                runningActions.add(sampleSweeper.sweepSample());
            }

            if (currentGamepad1.b && !previousGamepad1.b) {
                runningActions.add(sampleSweeper.sampleSweep());
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

        }

    }
}