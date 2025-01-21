
package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDriveSWBot;

@Autonomous
@Config
public class ImGay extends LinearOpMode {

    public static double speciDropX = -29.3;
    public static double speciDropY = -7.74;
    public static double wayPointX = -20.9;
    public static double wayPointY = 18;
    public static double speciDropH = 0;
    public static double block1X = -45.89;
    public static double block1Y = 41.6;
    public static double block1H = -90;
    public static double depositHumanH = -90;
    public static double block2X = -45.9;
    public static double block2Y = 51.2;
    public static double block2H = -90;
    public static double depositHumanH2 = -90;
    public static double pickUpX = -3.8;
    public static double pickUpY = 42;
    public static double pickUpH = -180;
    public static double dropSpecimenX = -29.3;
    public static double dropSpecimenY =-7.74 ;
    public static double dropSpecimenH =0;
    public static double parkX = -6;
    public static double parkY = 32;
    public static double parkH = 180;

    @Override
    public void runOpMode() {

        Pose2d StartPose1 = new Pose2d(0,0, (0));
        MecanumDriveSWBot drive = new MecanumDriveSWBot(hardwareMap, StartPose1);
        //SlidesV2 slides = new SlidesV2(hardwareMap, true);
        //ExtendoV2 extendo = new ExtendoV2(hardwareMap);
        //Claw claw = new Claw(hardwareMap);
        //Intaker intake = new Intaker(hardwareMap);


        TrajectoryActionBuilder pathT = drive.actionBuilder(StartPose1)
                .strafeToLinearHeading(new Vector2d(-30.72287017878197, -9.562962522543964), (0.06132301555397507))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-26.479441359175397, -9.475201765826624), (0.03782197965305058))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-25.047427759905084, 25.87502412796529), (-0.11677077213271886))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-49.30420822798732, 28.23786342583621), (-0.10428584681035245))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-48.79209937373229, 38.05792718286376), (-1.7177788428832055))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-8.623125341079648, 30.75857652842694), (-1.901747889545131))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-48.281950508625584, 39.511963181084376), (-1.7544992114784002))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-46.831640678012654, 49.51186731710842), (-1.7339358050650906))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-6.798456800852759, 40.63632359776796), (-1.914232814867496))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-14.772039873083317, 43.822915907074005), (2.8487292324710314))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-0, 30.230117726825952), (2.7936486795782387))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-36.165556412736315, -6.584657434682271), (-0.44141579365840883))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-47.012919166066006, -0), (-0.4333373125674656))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-41.33126578317133, -4.027548780331957), (-0.43443892362532066))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-9.05868621478093, 30.360255790619938), (2.669864074268263))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(0, 25.534336731185483), (2.644894223623532))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-41.47782926428844, -0.8075237115847632), (-0.7109802622913061))
                .waitSeconds(0.25)
                .strafeToLinearHeading(new Vector2d(-49.32896532509381, 5.933501816734894), (-0.6349690992992525))
                .waitSeconds(0.25);



        waitForStart();

        Action path = pathT.build();

        Actions.runBlocking(new ParallelAction(
                path

//                new SequentialAction(
//                        slides.slideTopBar(),
//                        slides.slideTopBarClip(),
//                        new SleepAction(0.25),
//                        claw.open(),
//                        new SleepAction(0.2),
//                        slides.retract(),
//                        new ParallelAction(
//
//                                extendo.extend()
//                        ),
//                        intake.flat(),
//                        new ParallelAction(
//                                extendo.retract(),
//                                intake.flop()
//                        ),
//                        claw.close(),
//                        new ParallelAction(
//                                slides.slideTopBar()
//                        ),
//                        slides.slideTopBarClip(),
//                        new SleepAction(0.25),
//                        claw.open(),
//                        new SleepAction(0.2),
//                        slides.retract(),
//                        claw.close(),
//                        new ParallelAction(
//                                slides.slideTopBar()
//                        ),
//                        slides.slideTopBarClip(),
//                        //new SleepAction(0.25),
//                        claw.open(),
//                        //new SleepAction(0.2),
//                        slides.retract(),
//                        claw.close(),
//                        new ParallelAction(
//                                slides.slideTopBar()
//                        ),
//                        slides.slideTopBarClip(),
//                        //new SleepAction(0.25),
//                        claw.open(),
//                        //new SleepAction(0.2),
//                        slides.retract()
//
//
//                )

        ));


    }
}
