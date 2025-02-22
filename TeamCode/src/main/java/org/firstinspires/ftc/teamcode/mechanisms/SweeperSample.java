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
public class SweeperSample {
    public Servo sampleSweeper;
    public double pos;

    public static double extendTarget = 0.82;
    public static double retractTarget = 0.45;


    public SweeperSample(HardwareMap HWMap) {
        sampleSweeper = HWMap.get(Servo.class, "sampleSweeper");
    }

    public class SampleSweep implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pos = retractTarget;
            sampleSweeper.setPosition(pos);
            return false;
        }

    }

    public Action sampleSweep() {
        return new SampleSweep();
    }

    public class SweepSample implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            pos = extendTarget;
            sampleSweeper.setPosition(pos);
            return false;
        }
    }

    public Action sweepSample() {
        return new SweepSample();
    }

}



