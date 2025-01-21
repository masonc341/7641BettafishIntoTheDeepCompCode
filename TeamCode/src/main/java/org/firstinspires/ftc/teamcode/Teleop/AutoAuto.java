package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDriveSWBot;


import org.firstinspires.ftc.teamcode.MecanumDriveSWBot;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


@TeleOp
public class AutoAuto extends LinearOpMode {

    @Override
    public void runOpMode() {




        Pose2d StartPose = new Pose2d(0, 0, 0); // Replaced with 0 radians instead of Math.toRadians(0);
        MecanumDriveSWBot drive = new MecanumDriveSWBot(hardwareMap, StartPose);

        Gamepad currentGamepad1 = gamepad1; // Changed from new Gamepad() to gamepad1, same thing with gamepad 2
        Gamepad currentGamepad2 = gamepad2;


        String finalFormatting = "  .strafeToLinearHeading(new Vector2d(%dx, %dy), (%heading))\n  .waitSeconds(1.5)";

        String codeHeader = "TrajectoryActionBuilder path = drive.actionBuilder(StartPosition)\n";

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.clear();

        boolean mappingState = false; // Switch to make sure no infinite execution, need to test, might need previous state check with controller
        boolean buttonState = false; // Will implement for each individual button in the future, make sure to not click more than one button at a time

        Map<String, Double> mappingTable = new HashMap<>();


        double mapping_x = 0.00; // Place holder for last X pos
        double mapping_y = 0.00; // Place holder for last Y pos
        double mapping_heading = 0.00;

        int count = 0; // Count variable for dict/hashmap for data storage, temporary solution? Hopefully better in the future
        int action_count = 0; // Count variable for actions when mapping out auto

        List<String> storedActions = new ArrayList<String>(); // List for holding actions along with positioning


        WebSocketClient webSocket = null;

        int webCount = 0;

        try {
            webSocket = new WebSocketClient(new URI("ws://192.168.43.148:8765")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    telemetry.addData("WebSocket", "Connected");
                    telemetry.update();
                }

                @Override
                public void onMessage(String message) {
                    telemetry.addData("WebSocket Received", message);
                    telemetry.update();
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    telemetry.addData("WebSocket", "Connection closed: " + reason);
                    telemetry.update();
                }

                @Override
                public void onError(Exception ex) {
                    telemetry.addData("WebSocket Error", ex.getMessage());
                    telemetry.update();
                }
            };

            webSocket.connect();
        } catch (URISyntaxException e) {
            telemetry.addData("WebSocket Error", "Invalid URI: " + e.getMessage());
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {


            double x = drive.pose.position.x; // drive pos x
            double y = drive.pose.position.y; // drive pos y
            double heading = drive.pose.heading.toDouble(); // heading in radians


            if (currentGamepad1.a && !mappingState) {
                mapping_x = x; // Currently not in use, meant for debugging
                mapping_y = y;
                mapping_heading = heading;

                // Currently not in use, might reimplement in the future for more features or options, using a simple array for now
                // Not in use from [START]

                String x_key = "x_pos_" + count;
                String y_key = "y_pos_" + count;
                String heading_key = "heading_" + count;

                mappingTable.put(x_key, x); // Insert values into a dict with x_pos_{count} and y_pos_{count}
                mappingTable.put(y_key, y);
                mappingTable.put(heading_key, heading);

                // [END]

                String storageLine = finalFormatting.replace("%dx", "" + x).replace("%dy", "" + y).replace("%heading", "" + heading);
                storedActions.add(storageLine); // Append mapping stop point with count for final parsing/mapping result

                action_count++;
                count++;

                mappingState = true;
            } else if (!currentGamepad1.a) {
                mappingState = false;
            }


            // Checks meant for registering events from button clicks other than position mapping
            // Ex: If button x is clicked, "event_x" is appended to the list of stored actions, allowing you to later replace it with an action

            if (currentGamepad1.x && !buttonState) {
                storedActions.add("event_x\n");
                action_count++;
                buttonState = true;
            } else if (currentGamepad1.b && !buttonState) {
                storedActions.add("event_b\n");
                action_count++;
                buttonState = true;
            } else if (currentGamepad1.y && !buttonState) {
                storedActions.add("event_y\n");
                action_count++;
                buttonState = true;
            } else if (!currentGamepad1.x && !currentGamepad1.b && !currentGamepad1.y) {
                buttonState = false;
            }

            drive.updatePoseEstimate(); // Update drive position estimate, essential for script to run

            // Current telemetry meant for copy-and-pasting, still WIP

            String codeData = codeHeader + String.join("\n", storedActions);

            if (webSocket != null && webSocket.isOpen()) {
                webSocket.send(codeData);
                webCount++;
                telemetry.addData("WebSocket", "Message sent: Test new @decorator");
                telemetry.addData("Count", webCount);
            } else {
                telemetry.addData("WebSocket", "Not connected");
            }

            telemetry.addData("Copy and Paste", codeHeader + String.join("\n", storedActions));


            telemetry.update();


            /* telemetry.addData("X Coord", x);      // Debugging values, disabled once program is debugged
            telemetry.addData("Y Coord", y);
            telemetry.addData("Heading", heading);
            telemetry.addData("Last X", mapping_x);
            telemetry.addData("Last Y", mapping_y);
            telemetry.addData("Last Heading", mapping_heading); */


        }
    }
}