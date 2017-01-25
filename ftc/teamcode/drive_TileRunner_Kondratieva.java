/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Driving: maria", group="Iterative Opmode")   // @Autonomous(...) is the other common choice
@Disabled
public class drive_TileRunner_Kondratieva extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor2 = null;

    Gamepad gamepad;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        leftMotor1  = hardwareMap.dcMotor.get("left_drive_1");
        rightMotor1 = hardwareMap.dcMotor.get("right_drive_1");
        leftMotor2 = hardwareMap.dcMotor.get("left_drive_2");
        rightMotor2 = hardwareMap.dcMotor.get("right_drive_2");
        gamepad = gamepad1;
        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        leftMotor1.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightMotor1.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        leftMotor2.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightMotor2.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        // telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        telemetry.addData("Status", "A butt on: " + gamepad.a);
        telemetry.addData("Status", "Left motor: " + leftMotor1.getPower() + "\tRight motor " + rightMotor1.getPower());
        telemetry.addData("Status", "Left stick x axis: " + gamepad.left_stick_x);
        telemetry.update();
        // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        // leftMotor.setPower((gamepad.left_trigger+gamepad.left_stick_x)/2);  //left_triggered (trump rally)
        // rightMotor.setPower((gamepad.left_trigger-gamepad.left_stick_x)/2);  //right_triggered (hilary dabs)
        // rightMotor.setPower(-gamepad1.right_stick_y);
        if (gamepad.left_trigger>0.78f && gamepad.right_trigger>0.78f) { // emergency break
            leftMotor1.setPower(-gamepad.left_stick_x);
            rightMotor1.setPower(gamepad.left_stick_x);
            leftMotor2.setPower(-gamepad.left_stick_x);
            rightMotor2.setPower(gamepad.left_stick_x);
        }
        else if (gamepad.left_trigger>=gamepad.right_trigger) { //driving forward
            if (gamepad.left_stick_x>0) { // driving left i think
                leftMotor1.setPower(-gamepad.left_trigger);
                rightMotor1.setPower(-gamepad.left_trigger+(gamepad.left_trigger*gamepad.left_stick_x));
                leftMotor2.setPower(-gamepad.left_trigger);
                rightMotor2.setPower(-gamepad.left_trigger+(gamepad.left_trigger*gamepad.left_stick_x));
            }
            else if (gamepad.left_stick_x<0) { // driving right i think
                leftMotor1.setPower(-gamepad.left_trigger-(gamepad.left_trigger*gamepad.left_stick_x));
                rightMotor1.setPower(-gamepad.left_trigger);
                leftMotor2.setPower(-gamepad.left_trigger-(gamepad.left_trigger*gamepad.left_stick_x));
                rightMotor2.setPower(-gamepad.left_trigger);
            }
            else if (gamepad.left_stick_x==0) { // driving straight
                leftMotor1.setPower(-gamepad.left_trigger);
                rightMotor1.setPower(-gamepad.left_trigger);
                leftMotor2.setPower(-gamepad.left_trigger);
                rightMotor2.setPower(-gamepad.left_trigger);
            }
        }
        else if (gamepad.right_trigger>gamepad.left_trigger) { // driving backwards
            if (gamepad.left_stick_x>0) { // driving... right?
                leftMotor1.setPower(gamepad.right_trigger);
                rightMotor1.setPower(gamepad.right_trigger-(gamepad.right_trigger*gamepad.left_stick_x));
                leftMotor2.setPower(gamepad.right_trigger);
                rightMotor2.setPower(gamepad.right_trigger-(gamepad.right_trigger*gamepad.left_stick_x));
            }
            else if (gamepad.left_stick_x<0) { // driving... left?
                leftMotor1.setPower(gamepad.right_trigger+(gamepad.right_trigger*gamepad.left_stick_x));
                rightMotor1.setPower(gamepad.right_trigger);
                leftMotor2.setPower(gamepad.right_trigger+(gamepad.right_trigger*gamepad.left_stick_x));
                rightMotor2.setPower(gamepad.right_trigger);
            }
            else if (gamepad.left_stick_x==0) { // driving straight
                leftMotor1.setPower(gamepad.right_trigger);
                rightMotor1.setPower(gamepad.right_trigger);
                leftMotor2.setPower(gamepad.right_trigger);
                rightMotor2.setPower(gamepad.right_trigger);
            }
        }

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
