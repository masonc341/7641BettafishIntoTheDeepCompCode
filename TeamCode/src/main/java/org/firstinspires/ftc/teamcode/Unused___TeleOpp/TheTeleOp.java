package org.firstinspires.ftc.teamcode.Unused___TeleOpp;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class TheTeleOp extends LinearOpMode {


    @Override
    public void runOpMode() {

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));

        imu.initialize(parameters);

        DIP extendoMotorDIP = new DIP(1, 1, 1);
        DcMotor extendoMotor = hardwareMap.dcMotor.get("extendoMotor");
        enum ExtendoState {EXTENDOSTART, EXTENDOEXTEND, EXTENDOINTAKE, EXTENDORETRACT}
        ExtendoState extendoState = ExtendoState.EXTENDOSTART;


        Servo extendoLeftServo = hardwareMap.servo.get("extendoLeftServo");
        Servo extendoRightServo = hardwareMap.servo.get("extendoRightServo");

        DcMotor intakeMotor = hardwareMap.dcMotor.get("intakeMotor");

        DcMotor liftLeftMotor = hardwareMap.dcMotor.get("liftLeftMotor");
        DcMotor liftRightMotor = hardwareMap.dcMotor.get("liftRightMotor");
        Servo armLeftServo = hardwareMap.servo.get("armLeftServo");
        Servo armRightServo = hardwareMap.servo.get("armRightServo");
        DIP liftLeftDIP = new DIP(1, 1, 1);
        DIP liftRightDIP = new DIP(1, 1, 1);
        enum LiftState {LIFTSTART, LIFTEXTEND, LIFTARM, LIFTRELEASE, LIFTRETRACT}
        LiftState liftState = LiftState.LIFTSTART;

        NormalizedColorSensor colorSensor;
        View relativeLayout;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        float gain = 50;

        final float[] hsvValues = new float[3];

        boolean xButtonPreviouslyPressed = false;
        boolean xButtonCurrentlyPressed = false;

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }



        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if (gamepad1.options) {
                imu.resetYaw();
            }


            telemetry.addData("Gain", gain);
            colorSensor.setGain(gain);

            xButtonCurrentlyPressed = gamepad1.x;

            if (xButtonCurrentlyPressed != xButtonPreviouslyPressed) {
                if (xButtonCurrentlyPressed) {
                    if (colorSensor instanceof SwitchableLight) {
                        SwitchableLight light = (SwitchableLight)colorSensor;
                        light.enableLight(!light.isLightOn());
                    }
                }
            }
            xButtonPreviouslyPressed = xButtonCurrentlyPressed;

            NormalizedRGBA colors = colorSensor.getNormalizedColors();

            Color.colorToHSV(colors.toColor(), hsvValues);

            telemetry.addLine()
                    .addData("Red", "%.3f", colors.red)
                    .addData("Green", "%.3f", colors.green)
                    .addData("Blue", "%.3f", colors.blue);
            telemetry.addLine()
                    .addData("Hue", "%.3f", hsvValues[0])
                    .addData("Saturation", "%.3f", hsvValues[1])
                    .addData("Value", "%.3f", hsvValues[2]);
            telemetry.addData("Alpha", "%.3f", colors.alpha);

            if (colors.red > 225 && colors.green > 225 && colors.blue < 30) {
                telemetry.addData("Block Color", "yellow");
            } else if (colors.red > 225 && colors.green < 30 && colors.blue < 30) {
                telemetry.addData("Block Color", "red");
            } else if (colors.red < 30 && colors.green < 30 && colors.blue > 225) {
                telemetry.addData("Block Color", "blue");
            } else {
                telemetry.addData("Block Color", "none");
            }


            switch (liftState) {
                case LIFTSTART:
                    if (gamepad1.x) {
                        liftLeftDIP.goal = 100;
                        liftLeftDIP.position = liftLeftMotor.getCurrentPosition();
                        liftRightDIP.goal = 100;
                        liftRightDIP.position = liftLeftMotor.getCurrentPosition();
                        liftState = LiftState.LIFTEXTEND;
                    }
                    break;
                case LIFTEXTEND:
                    if (Math.abs(liftLeftDIP.goal - liftLeftDIP.position) < 10) {
                        armLeftServo.setPosition(100);
                        armRightServo.setPosition(100);
                        liftState = LiftState.LIFTARM;
                    } else {
                        liftLeftDIP.position = liftLeftMotor.getCurrentPosition();
                        liftLeftMotor.setPower(liftLeftDIP.moveSomeIdk(timer.seconds()));
                        liftRightDIP.position = liftRightMotor.getCurrentPosition();
                        liftRightMotor.setPower(liftRightDIP.moveSomeIdk(timer.seconds()));
                    }
                    break;
                case LIFTARM:
                    liftState = LiftState.LIFTRELEASE;
                    break;
                case LIFTRELEASE:
                    liftState = LiftState.LIFTRETRACT;
                    break;
                case LIFTRETRACT:
                    liftState = LiftState.LIFTSTART;
                    break;
                default:
                    liftState = LiftState.LIFTSTART;
                    break;
            }


            switch (extendoState) {
                case EXTENDOSTART:
                    if (gamepad1.a) {
                        extendoMotorDIP.goal = 100;
                        extendoMotorDIP.position = extendoMotor.getCurrentPosition();
                        extendoState = ExtendoState.EXTENDOEXTEND;
                    }
                    break;
                case EXTENDOEXTEND:
                    if (Math.abs(extendoMotorDIP.goal - extendoMotorDIP.position) < 10) {
                        extendoLeftServo.setPosition(100);
                        extendoRightServo.setPosition(100);
                        intakeMotor.setPower(1.0);
                        extendoState = ExtendoState.EXTENDOINTAKE;
                    } else {
                        extendoMotorDIP.position = extendoMotor.getCurrentPosition();
                        extendoMotor.setPower(extendoMotorDIP.moveSomeIdk(timer.seconds()));
                    }
                    break;
                case EXTENDOINTAKE:
                    if (gamepad1.a) { //change to be based on sensor input
                        extendoMotorDIP.goal = -100;
                        extendoMotorDIP.position = extendoMotor.getCurrentPosition();
                        extendoLeftServo.setPosition(100);
                        extendoRightServo.setPosition(0);
                        extendoState = ExtendoState.EXTENDORETRACT;
                    }
                    break;
                case EXTENDORETRACT:
                    if (Math.abs(extendoMotorDIP.goal - extendoMotorDIP.position) < 10) {
                        extendoState = ExtendoState.EXTENDOSTART;
                    } else {
                        extendoMotorDIP.position = extendoMotor.getCurrentPosition();
                        extendoMotor.setPower(extendoMotorDIP.moveSomeIdk(timer.seconds()));
                    }

                    break;
                default:
                    extendoState = ExtendoState.EXTENDOSTART;
                    break;
            }


            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);


            timer.reset();
            telemetry.update();

            relativeLayout.post(new Runnable() {
                                    public void run() {
                                        relativeLayout.setBackgroundColor(Color.HSVToColor(hsvValues));
                                    }
                                });
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
}
}