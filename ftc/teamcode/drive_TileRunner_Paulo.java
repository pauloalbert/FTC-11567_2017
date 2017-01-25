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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

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

@TeleOp(name="Driving: paulo", group="Iterative Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class drive_TileRunner_Paulo extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor1 = null;
    private DcMotor leftMotor2 = null;
    private DcMotor rightMotor1 = null;
    private DcMotor rightMotor2 = null;
    Gamepad gamepad;

    double turnRatio[] = { 1.0, 0.1  };
    double maxSpeed = 0.75;
    double StartRatio = 0.4;

    boolean frozen = false;
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
        leftMotor1 = hardwareMap.dcMotor.get("left_drive_1");
        leftMotor2 = hardwareMap.dcMotor.get("left_drive_2");
        rightMotor1 = hardwareMap.dcMotor.get("right_drive_1");
        rightMotor2 = hardwareMap.dcMotor.get("right_drive_2");
        gamepad = gamepad1;
        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        leftMotor1.setDirection(DcMotor.Direction.REVERSE);
        leftMotor2.setDirection(DcMotor.Direction.REVERSE);// Set to REVERSE if using AndyMark motors
        rightMotor2.setDirection(DcMotor.Direction.FORWARD);
        rightMotor1.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
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
        if (gamepad.b)
            maxSpeed = 1;
        else{
            maxSpeed = 0.75;


    }
        leftMotor1.setPower(((gamepad.left_trigger > 0.78f && gamepad.right_trigger > 0.78f && !gamepad.b) ? (gamepad.left_stick_x) : (((gamepad.left_trigger > gamepad.right_trigger) ? -gamepad.left_trigger : gamepad.right_trigger) * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x) * (Math.abs(gamepad.left_stick_x) + StartRatio) / (1 + StartRatio)) * (turnRatio[(int) Math.ceil((Math.signum(-gamepad.left_stick_x) + 1) / 2)] - maxSpeed)))));
        leftMotor2.setPower(((gamepad.left_trigger > 0.78f && gamepad.right_trigger > 0.78f && !gamepad.b) ? (gamepad.left_stick_x) : (((gamepad.left_trigger > gamepad.right_trigger) ? -gamepad.left_trigger : gamepad.right_trigger) * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x) * (Math.abs(gamepad.left_stick_x) + StartRatio) / (1 + StartRatio)) * (turnRatio[(int) Math.ceil((Math.signum(-gamepad.left_stick_x) + 1) / 2)] - maxSpeed)))));
        rightMotor1.setPower(((gamepad.left_trigger>0.78f && gamepad.right_trigger>0.78f && !gamepad.b) ? (-gamepad.left_stick_x) : (((gamepad.left_trigger > gamepad.right_trigger) ? -gamepad.left_trigger : gamepad.right_trigger) * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(Math.abs(gamepad.left_stick_x)+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(gamepad.left_stick_x)+1)/2)] - maxSpeed)))));
        rightMotor2.setPower(((gamepad.left_trigger>0.78f && gamepad.right_trigger>0.78f && !gamepad.b) ? (-gamepad.left_stick_x) : (((gamepad.left_trigger > gamepad.right_trigger) ? -gamepad.left_trigger : gamepad.right_trigger) * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(Math.abs(gamepad.left_stick_x)+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(gamepad.left_stick_x)+1)/2)] - maxSpeed)))));

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
    // robot hardware file
    // robot utilities.
    //turn to Range.clip
    //try joystick drives
    //

}
/* OLD MAINSTREAM CODE
else if(gamepad.left_trigger>gamepad.right_trigger){

                leftMotor1.setPower(-gamepad.left_trigger * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(-gamepad.left_stick_x)+1)/2)] - maxSpeed)));
                leftMotor2.setPower(-gamepad.left_trigger * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(-gamepad.left_stick_x)+1)/2)] - maxSpeed)));
                rightMotor1.setPower(-gamepad.left_trigger * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(gamepad.left_stick_x)+1)/2)] - maxSpeed)));
                rightMotor2.setPower(-gamepad.left_trigger * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(gamepad.left_stick_x)+1)/2)] - maxSpeed)));

        }
        else if(gamepad.right_trigger>=gamepad.left_trigger){
            leftMotor1.setPower(gamepad.right_trigger*(maxSpeed+Math.abs(Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio))*(turnRatio[(int)Math.ceil((Math.signum(-gamepad.left_stick_x)+1)/2)]-maxSpeed)));
            leftMotor2.setPower(gamepad.right_trigger*(maxSpeed+Math.abs(Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio))*(turnRatio[(int)Math.ceil((Math.signum(-gamepad.left_stick_x)+1)/2)]-maxSpeed)));
            rightMotor1.setPower(gamepad.right_trigger*(maxSpeed+Math.abs(Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio))*(turnRatio[(int)Math.ceil((Math.signum(gamepad.left_stick_x)+1)/2)]-maxSpeed)));
            rightMotor2.setPower(gamepad.right_trigger*(maxSpeed+Math.abs(Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio))*(turnRatio[(int)Math.ceil((Math.signum(gamepad.left_stick_x)+1)/2)]-maxSpeed)));
        }
        else{
            leftMotor1.setPower(0);
            leftMotor2.setPower(0);
            rightMotor1.setPower(0);
            rightMotor2.setPower(0);
        }
        Range.clip();
 */




//k, so heres a little lesson, in this mystery
//very hard to read but that's history,
//if you want to be a programmer number one,
//you gotta make hykus that sound very dumb. ~rotten programmer, rob robbie.
// sry

/*leftMotor1.setPower(gamepad.right_trigger*(maxSpeed+Math.abs(Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio))*(turnRatio[(int)Math.ceil((Math.signum(-gamepad.left_stick_x)+1)/2)]-maxSpeed)));
* so this looks very fucking difficult at first glance. thats only because it fucking is. and being so it is so fucking impossible to understand, ill try to explain.
*
* at the most basic level, i made a scale. making a scale is very usefull and if you do not know how fuck you you just made this a lot harder. UHHHHHHH.
* so the starting scale looked like dis.
*     power = trigger * max_speed;
* this is just drivin forwards. it needs to steer too.
*      power = trigger * (max_speed + joystick.x(maxTurn[0*] - max_speed));    *0 being of this motor, 1 being of the other one.
* this is basically nested scales, the inner scale being joysticks 0 to 1 into the difference the motor needs between its normal state and its maximum turn.
* this however doesn't work. when the joystick is minus the turn ratio has to be that of the opposite motor, and not be negitive.
* so i changed maxTurn[0] to maxTurn[  (int)Math.ceil((Math.signum(-gamepad.left_stick_x)+1)/2) ]
* same shit, but the 0 changes to a 1 when negitive. (i could have just as easily done if statements but fuck the mainstream.

* now a second problem came from the robot that being that it only reacts to turning when the difference is really big.
* that i change by adding a variable StartRatio.
* and instead of 'joystick.x', i turned it into 'Math.signum(gamepad.left_stick_x)*(gamepad.left_stick_x+StartRatio)/(1+StartRatio)'.  basically another scale with a sign num incase its 0.
* and thats it BIATCH */

// oh shit whats that i added more crap. k so now, instead of having 2 else ifs and an else, i condenced it all into one by having an else if in code.
//fuckin dumb i know
//((gamepad.left_trigger > gamepad.right_trigger) ? -gamepad.left_trigger : gamepad.right_trigger)
// this asks if left trigger is bigger than rt. if so,  - left trigger   else, right trigger.
// and the else never happened anyways in my last code so fuck it.
// if you guys want i could put a much more tanginable, less retarded version, and explain it in drawing instead of full text speeches with snippets of "comedy" in em.

/*
        leftMotor1.setPower((gamepad.left_trigger>0.78f && gamepad.right_trigger>0.78f) ? (gamepad.left_stick_x) : (((gamepad.left_trigger > gamepad.right_trigger) ? -gamepad.left_trigger : gamepad.right_trigger) * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(Math.abs(gamepad.left_stick_x)+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(-gamepad.left_stick_x)+1)/2)] - maxSpeed))));
        leftMotor2.setPower((gamepad.left_trigger>0.78f && gamepad.right_trigger>0.78f) ? (gamepad.left_stick_x) : (((gamepad.left_trigger > gamepad.right_trigger) ? -gamepad.left_trigger : gamepad.right_trigger) * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(Math.abs(gamepad.left_stick_x)+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(-gamepad.left_stick_x)+1)/2)] - maxSpeed))));
        rightMotor1.setPower((gamepad.left_trigger>0.78f && gamepad.right_trigger>0.78f) ? (gamepad.left_stick_x) : (((gamepad.left_trigger > gamepad.right_trigger) ? -gamepad.left_trigger : gamepad.right_trigger) * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(Math.abs(gamepad.left_stick_x)+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(gamepad.left_stick_x)+1)/2)] - maxSpeed))));
        rightMotor2.setPower((gamepad.left_trigger>0.78f && gamepad.right_trigger>0.78f) ? (gamepad.left_stick_x) : (((gamepad.left_trigger > gamepad.right_trigger) ? -gamepad.left_trigger : gamepad.right_trigger) * (maxSpeed + Math.abs(Math.signum(gamepad.left_stick_x)*(Math.abs(gamepad.left_stick_x)+StartRatio)/(1+StartRatio)) * (turnRatio[(int)Math.ceil((Math.signum(gamepad.left_stick_x)+1)/2)] - maxSpeed))));
 */