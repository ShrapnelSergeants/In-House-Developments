/* Copyright (c) 2022 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * This file works in conjunction with the External Hardware Class sample called: ConceptExternalHardwareClass.java
 * Please read the explanations in that Sample about how to use this class definition.
 *
 * This file defines a Java Class that performs all the setup and configuration for a sample robot's hardware (motors and sensors).
 * It assumes three motors (left_drive, right_drive and arm) and two servos (left_hand and right_hand)
 *
 * This one file/class can be used by ALL of your OpModes without having to cut & paste the code each time.
 *
 * Where possible, the actual hardware objects are "abstracted" (or hidden) so the OpMode code just makes calls into the class,
 * rather than accessing the internal hardware directly. This is why the objects are declared "private".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with *exactly the same name*.
 *
 * Or.. In OnBot Java, add a new file named RobotHardware.java, drawing from this Sample; select Not an OpMode.
 * Also add a new OpMode, drawing from the Sample ConceptExternalHardwareClass.java; select TeleOp.
 *
 */

public class RobotHardware {

    /* Declare OpMode members. */
    private LinearOpMode MecanumTeleop = null;

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    private DcMotor frontLeftDrive   = null;
    private DcMotor frontRightDrive  = null;
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor armMotor = null;
    private Servo   leftHand = null;
    private Servo   rightHand = null;

    // Define Drive constants.  Make them public so they CAN be used by the calling OpMode
    public static final double MID_SERVO       =  0.5 ;
    public static final double HAND_SPEED      =  0.02 ;  // sets rate to move servo
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public RobotHardware(LinearOpMode opmode) { MecanumTeleop = opmode; }

    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     *
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    public void init()    {
        // Define and Initialize Motors (note: need to use reference to actual OpMode).
        frontLeftDrive   = MecanumTeleop.hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRightDrive  = MecanumTeleop.hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive    = MecanumTeleop.hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive   = MecanumTeleop.hardwareMap.get(DcMotor.class, "back_right_drive");
        //armMotor   = myOpMode.hardwareMap.get(DcMotor.class, "arm");

        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        MecanumTeleop.telemetry.addData(">", "Hardware Initialized");
        MecanumTeleop.telemetry.update();
    }
    
    public void turnMecanum(double Speed, int Point){
        switch (Point){
            case 1:
                if (Speed < 0) {
                    backLeftDrive.setPower(Speed);
                    backRightDrive.setPower(-Speed);
                } else {
                    backLeftDrive.setPower(-Speed);
                    backRightDrive.setPower(Speed);
                }
            break;
            case 2:
                frontRightDrive.setPower(Speed);
                backRightDrive.setPower(Speed);
            break;
            case 3:
                if (Speed < 0) {
                    frontLeftDrive.setPower(Speed);
                    frontRightDrive.setPower(-Speed);
                } else {
                    frontLeftDrive.setPower(-Speed);
                    frontRightDrive.setPower(Speed);
                }
            break;
            case 4:
                frontLeftDrive.setPower(Speed);
                backLeftDrive.setPower(Speed);
            break;
        }
    }
    public void spinMecanum(double Speed){
        if(Speed < 0) {
            frontLeftDrive.setPower(Speed);
            backLeftDrive.setPower(Speed);
            frontRightDrive.setPower(-Speed);
            backRightDrive.setPower(-Speed);
        } else if(Speed > 0){
            frontLeftDrive.setPower(-Speed);
            backLeftDrive.setPower(-Speed);
            frontRightDrive.setPower(Speed);
            backRightDrive.setPower(Speed);
        }
    }
    public void strafeMecanum(double Speed){
        if(Speed < 0){
            frontLeftDrive.setPower(-Speed);
            backLeftDrive.setPower(Speed);
            frontRightDrive.setPower(Speed);
            backRightDrive.setPower(-Speed);
        } else if(Speed > 0){
            frontLeftDrive.setPower(Speed);
            backLeftDrive.setPower(-Speed);
            frontRightDrive.setPower(-Speed);
            backRightDrive.setPower(Speed);
        }
    }
    public void diagonalMecanum(double Speed, String Direction){
        if(Direction == "NESW"){
            frontLeftDrive.setPower(Speed);
            backRightDrive.setPower(Speed);
        } else if(Direction == "NWSE"){
            frontRightDrive.setPower(Speed);
            backLeftDrive.setPower(Speed);
        }

    }
    public void driveMecanum(double Speed){
        frontLeftDrive.setPower(Speed);
        backLeftDrive.setPower(Speed);
        frontRightDrive.setPower(Speed);
        backRightDrive.setPower(Speed);
    }
}
