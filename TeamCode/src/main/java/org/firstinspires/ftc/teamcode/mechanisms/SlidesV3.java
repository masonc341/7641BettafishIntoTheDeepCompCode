package org.firstinspires.ftc.teamcode.mechanisms;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PIDFController;

@Config
public class SlidesV3 {
    public DcMotor slidesLeftMotor;
    public DcMotor slidesRightMotor;


    public static int topBasketTarget = 3600;
    public static int bottomBasketTarget = 1000;
    public static int wallTarget = 5;
    public static int topBarTarget = 1360;
    public static int topBarClipTarget = 1030;
    public static int bottomBarTarget = 700;
    public static int bottomBarClipTarget = 500;
    public static int hangTarget = 2770;
    public static int hangRetractTarget = 1570;
    public static int retractTarget = 0;
    public static int parkTarget = 960;
    public static int startTarget = 1030;

    private int target = 0;

    public SlidesV3(HardwareMap HWMap, boolean reset) {
        slidesLeftMotor = HWMap.get(DcMotor.class, "leftSlidesMotor");
        slidesRightMotor = HWMap.get(DcMotor.class, "rightSlidesMotor");

        if (reset) {
            slidesLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slidesRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        slidesLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slidesRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        slidesLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slidesRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slidesRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public class SlideTopBasket implements Action {
        private boolean init = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!init) {
                target = topBasketTarget;
                setMotorPow(1);
                init = true;
            }


            if (target - getPos() <  60) {
                setMotorPow(0.12);
                return false;
            }
            return true;
        }
    }
    public Action slideTopBasket() {
        return new SlideTopBasket();
    }

    public class SlideBottomBasket implements Action {
        private boolean init = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!init) {
                target = bottomBasketTarget;
                setMotorPow(1);
                init = true;
            }


            if (target - getPos() <  60) {
                setMotorPow(0.12);
                return false;
            }
            return true;
        }
    }
    public Action slideBottomBasket() {
        return new SlideBottomBasket();
    }

    public class SlideHang implements Action {
        private boolean init = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!init) {
                target = hangTarget;
                setMotorPow(1);
                init = true;
            }


            if (target - getPos() <  60) {
                setMotorPow(0.12);
                return false;
            }
            return true;
        }
    }
    public Action slideHang() {
        return new SlideHang();
    }


    public class SlidePark implements Action {
        private boolean init = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!init) {
                target = parkTarget;
                setMotorPow(-1);
                init = true;
            }


            if (getPos() - target <  70) {
                setMotorPow(-0.12);
                return false;
            }
            return true;
        }
    }
    public Action slidePark() {
        return new SlidePark();
    }


    public class Start implements Action {
        private boolean init = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!init) {
                target = startTarget;
                setMotorPow(1);
                init = true;
            }


            if (target - getPos() <  70) {
                setMotorPow(0.12);
                return false;
            }
            return true;
        }
    }
    public Action start() {
        return new Start();
    }



    public class SlideRetractHang implements Action {
        private boolean init = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!init) {
                target = hangRetractTarget;
                setMotorPow(-1);
                init = true;
            }


            if (getPos() - target <  60) {
                setMotorPow(-0.12);
                return false;
            }
            return true;
        }
    }
    public Action slideRetractHang() {
        return new SlideRetractHang();
    }

    public class Retract implements Action {
        private boolean init = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!init) {
                target = retractTarget;
                setMotorPow(-1);
                init = true;
            }


            if (getPos() - target <  70) {
                setMotorPow(-0.07);
                return false;
            }
            return true;
        }
    }
    public Action retract() {
        return new Retract();
    }


    public double getPos() {
        return (double) (slidesLeftMotor.getCurrentPosition() + slidesRightMotor.getCurrentPosition()) / 2;
    }

    public void setMotorPow(double pow) {
        slidesLeftMotor.setPower(pow);
        slidesRightMotor.setPower(pow);
    }

    public int getTarget() {
        return target;
    }


}
