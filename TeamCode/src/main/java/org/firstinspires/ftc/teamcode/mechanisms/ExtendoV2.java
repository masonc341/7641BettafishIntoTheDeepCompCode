package org.firstinspires.ftc.teamcode.mechanisms;



import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PIDFController;

@Config
public class ExtendoV2 {
    public Servo extendoLeftServo;
    public Servo extendoRightServo;
    public AnalogInput extendoAnalog;
    public double pos;

    public static double extendTarget = 0.5;
    public static double retractTarget = 0.2;
    public static double balanceTarget = 0.36;

    public ExtendoV2(HardwareMap HWMap) {
        extendoLeftServo = HWMap.get(Servo.class, "extendoLeftServo");
        extendoRightServo = HWMap.get(Servo.class, "extendoRightServo");
        extendoAnalog = HWMap.get(AnalogInput.class, "extendoAnalog");
    }

    public class Retract implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pos = retractTarget;
            extendoLeftServo.setPosition(1 - pos);
            extendoRightServo.setPosition(pos);
//            if (Math.abs(getPos()) < 20) {
//                return false;
//            }
//            return true;
            return false;
        }

    }

    public Action retract() {
        return new Retract();
    }

    public class Extend implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pos = extendTarget;
            extendoLeftServo.setPosition(1 - pos);
            extendoRightServo.setPosition(pos);
//            if (Math.abs(getPos()) < 20) {
//                return false;
//            }
//            return true;
            return false;
        }
    }

    public Action extend() {
        return new Extend();
    }

    public class Balance implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pos = balanceTarget;
            extendoLeftServo.setPosition(1 - pos);
            extendoRightServo.setPosition(pos);
//            if (Math.abs(getPos()) < 20) {
//                return false;
//            }
//            return true;
            return false;
        }
    }

    public Action balance() {
        return new Balance();
    }

    public void move(double d) {
        pos += d;
        if (pos < 0.2) {
            pos = 0.2;
        } else if (pos > 0.6) {
            pos = 0.6;
        }
        extendoLeftServo.setPosition(1 - pos);
        extendoRightServo.setPosition(pos);
    }

    public double getPos() {
        return extendoAnalog.getVoltage() / 3.3 + 360;
    }
}



