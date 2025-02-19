
package org.firstinspires.ftc.teamcode.Auto.Vision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.telecom.Call;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.MecanumDriveSWBot;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.ExtendoV2;
import org.firstinspires.ftc.teamcode.mechanisms.Intaker;
import org.firstinspires.ftc.teamcode.mechanisms.SlidesV3;

import java.nio.file.Paths;
import java.util.List;
import java.lang.Math;
import java.util.concurrent.Callable;

public class Limelight{
    public Limelight3A limelight;


    public Limelight(HardwareMap HWMap) {
        limelight = HWMap.get(Limelight3A.class, "bob");
        limelight.pipelineSwitch(0);
        limelight.setPollRateHz(100);
        limelight.start();

    }

    double limelightmountangledown = 15;//degrees pointing downward
    double limelightmountheight = 13.6; // inches above ground (CHANGE THIS!!!! add drivetrain height or smth)

    //remove the swbot part of this later
//need limeight to move until degree = 0
    public class Align implements Action {
        private MecanumDriveSWBot drive;
        private Telemetry telem;
        private double forwarddistance;
        private double sidedistance;
        public Align(MecanumDriveSWBot drive, Telemetry telemetry, double forwarddistance) {
            this.drive = drive;
            this.telem = telemetry;
            this.forwarddistance = forwarddistance;
            this.sidedistance = sidedistance;
        }


        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            boolean oop;

            oop = true;



            double tx = getTx();

//            telem.addData("tx", tx);
//            telem.update();
//


            if (tx > 17) { //Pls dont change the RANGE of values plsplspls cuz then ull break it o noes
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, 0.3),
                        0));
            } else if (tx < 3) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, -0.3),
                        0
                ));
            }
            else {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, 0),
                        0
                ));
            }

            this.forwarddistance = getForwardDistance(limelightmountheight);
            if (forwarddistance >= 17.4 && forwarddistance <= 18.2){
                return true; //right position to extend
            }

            else if (forwarddistance <= 17.4){
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(-0.3, 0),
                        0
                ));
            }
//            else {
//                drive.setDrivePowers(new PoseVelocity2d(
//                        new Vector2d(0, 0.3),//change maybe?
//                        0
//                ));
//            }
            return true;
        }

    }

    public Action align(MecanumDriveSWBot drive, Telemetry telemetry) {
        return new Align(drive, telemetry, getForwardDistance(limelightmountheight));

    }

    public double getTx() {
        LLResult result = limelight.getLatestResult();
        double tx;

        if (result != null && result.isValid()) {
            tx = result.getTx(); // How far left or right the target is (degrees)
            //ty = result.getTy() * -1; // How far up or down the target is (degrees)

        }
        else {

            return 0;
        }

        return tx;
    }

    public double getForwardDistance(double cameraHeight){
        LLResult result = limelight.getLatestResult();


        double ty;

        if (result != null && result.isValid()){

            ty = result.getTy();

           double downradians = Math.toRadians(90 - Math.abs(ty)-limelightmountangledown);
           // double downradians = Math.toRadians(ty);
            double forwarddistance = limelightmountheight * Math.tan(downradians);

            return forwarddistance;


        } else{
            return 0;
        }
    }

//    public double getSideDistance(double cameraHeight){
//        LLResult result = limelight.getLatestResult();
//
//        double tx;
//        double ty;
//
//        if (result != null && result.isValid()){
//            tx = result.getTx();
//            ty = result.getTy();
//
//            double downradians = Math.toRadians(limelightmountangledown + ty);
//            double sideradians = Math.toRadians(limelightmountangletointake + tx);//edit tx
//            double forwarddistance = limelightmountheight * Math.tan(downradians);
//            double sidedistance = forwarddistance / Math.tan(sideradians);
//
//            return sidedistance;
//
//        } else{
//            return 0;
//        }
//    }



    public double StrafeAmount() {
        return 0;
    }

    //move along wall = y pos changes
    //take in position
    //put into method/atcition
    //then strafe

    public int testW(){
        return 21;
    }



}






