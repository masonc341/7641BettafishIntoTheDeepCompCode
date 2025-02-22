package org.firstinspires.ftc.teamcode.Testing;

import android.graphics.Color;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.Extendo;
import org.firstinspires.ftc.teamcode.mechanisms.ExtendoV2;
import org.firstinspires.ftc.teamcode.mechanisms.Intaker;

import java.util.ArrayList;
import java.util.List;

@Config
@TeleOp
public class TestColorSensor extends LinearOpMode {
    public static double yellowHt = 100;
    public static double yellowHb = 70;
    public static double redHt = 60;
    public static double redHb = 10;
    public static double blueHt = 240;
    public static double blueHb = 160;
    public static float gain = 100;

    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();
    private Telemetry tele = dash.getTelemetry();

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        NormalizedColorSensor colorSensor;

        ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        Intaker intake = new Intaker(hardwareMap);


        final float[] hsvValues = new float[3];

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();

            colorSensor.setGain(gain);

            NormalizedRGBA colors = colorSensor.getNormalizedColors();

            Color.colorToHSV(colors.toColor(), hsvValues);

            if (hsvValues[0] > yellowHb && hsvValues[0] < yellowHt) {

            } else if (hsvValues[0] > redHb && hsvValues[0] < redHt) {

            } else if (hsvValues[0] > blueHb && hsvValues[0] < blueHt) {

            } else {

            }

            if (hsvValues[0] >= 60 && hsvValues[0] < 100 && hsvValues[1] > 0.5) {
                telemetry.addData("color","yellow");
            } else if ((hsvValues[0] > 10 && hsvValues[0] < 60 && hsvValues[1] >= 0.3) || (hsvValues[0] > 60 && hsvValues[0] < 100 && hsvValues[1] < 0.3)) {
                telemetry.addData("color","red");
            } else if (hsvValues[0] > 155 && hsvValues[0] < 240) {
                telemetry.addData("color","blue");
            } else {
                telemetry.addData("color","none");
            }


            if (gamepad1.a) {
                runningActions.add(new SequentialAction(
                        extendo.extend(),
                        intake.flip(),
                        intake.intake()
                ));
            }

            if (gamepad1.b) {
                runningActions.add(new SequentialAction(
                        extendo.retract(),
                        intake.flop(),
                        intake.off()
                ));
            }

            if (gamepad1.x) {
                runningActions.add(intake.off());
            }

            if (gamepad1.y) {
                runningActions.add(intake.intake());
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

            telemetry.addData("redv", colors.red);
            telemetry.addData("bluev", colors.blue);
            telemetry.addData("greenv", colors.green);
            telemetry.addData("H!", hsvValues[0]);
            telemetry.addData("S!", hsvValues[1]);
            telemetry.addData("V!", hsvValues[2]);
            telemetry.update();
        }
    }

}