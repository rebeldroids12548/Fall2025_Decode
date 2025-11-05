package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Simple Lift TeleOp
 * Left joystick Y controls the left lift motor
 * Right joystick Y controls the right lift motor
 */
@TeleOp(name = "Lift Only TeleOp - 12548 (Joystick Control)", group = "TeleOp")
public class LiftOnlyTeleOp extends OpMode {

    private DcMotor leftLift = null;
    private DcMotor rightLift = null;
    private final double UP_LIFT_POWER = 1.0;
    private final double DOWN_LIFT_POWER = 1.5;
    private String direction = "Stopped";

    @Override
    public void init() {
        // Map lift motors from configuration (match these names to your Robot Configuration)
        leftLift = hardwareMap.get(DcMotorEx.class, "left_lift");
        rightLift = hardwareMap.get(DcMotorEx.class, "right_lift");

        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("✅ Lift Only TeleOp Initialized");
        telemetry.addLine("Controls:");
        telemetry.addLine("🎮 Left Stick Y = Left Lift Up/Down");
        telemetry.addLine("🎮 Right Stick Y = Right Lift Up/Down");
        telemetry.update();
    }

    @Override
    public void loop() {
        // --- Independent Lift Control via Joysticks ---
        double leftStickY = -gamepad1.left_stick_y;   // Up on joystick = positive
        double rightStickY = -gamepad1.right_stick_y; // Up on joystick = positive

        // Apply joystick values directly as power
        leftLift.setPower(leftStickY);
        rightLift.setPower(rightStickY);

        // Determine direction for telemetry
        if (leftStickY > 0.1 || rightStickY > 0.1) {
            direction = "Up";
        } else if (leftStickY < -0.1 || rightStickY < -0.1) {
            direction = "Down";
        } else {
            direction = "Stopped";
        }

        // --- Telemetry Feedback ---
        telemetry.addData("Lift Direction", direction);
        telemetry.addData("Left Lift Power", leftStickY);
        telemetry.addData("Right Lift Power", rightStickY);
        telemetry.addData("Left Lift Pos", getLeftPosition());
        telemetry.addData("Right Lift Pos", getRightPosition());
        telemetry.update();
    }

    /** Get direction for telemetry */
    private String getDirection() {
        return direction;
    }

    /** Encoder positions */
    private int getLeftPosition() {
        return leftLift.getCurrentPosition();
    }

    private int getRightPosition() {
        return rightLift.getCurrentPosition();
    }
}
