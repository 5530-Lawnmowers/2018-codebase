package org.usfirst.frc.team5530.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.DriverStation;

import org.usfirst.frc.team5530.robot.subsystems.ArmSS;
import org.usfirst.frc.team5530.robot.subsystems.DrivetrainSS;
import org.usfirst.frc.team5530.robot.subsystems.ElevatorSS;
import org.usfirst.frc.team5530.robot.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
//This command turns the motors to move the arm to the top of the elevator
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class SimpleTurn extends Command{
	
	String side;
	boolean flag = false;
	double rightMulti = 1;
	double leftMulti = 1.05;
	double ticks;
	double startDistanceR;
	double startDistanceL;
	double errorCounter;
	double minErrorTime = 10;
	double turnCounter;
	double maxTurnTime;
	
//	double maxError; //Original was 200
	
	public SimpleTurn(String side, double radians, double time) {
		requires(Robot.drivetrainSS);
		maxTurnTime = time * 50;
		this.side = side;
//		maxError = error;
		if(side.equalsIgnoreCase("l")) {
			ticks = -((radians*22.75)/(6*Math.PI))*1024/2;
			ticks = ticks * leftMulti;
		} else if(side.equalsIgnoreCase("r")) {
			ticks = ((radians*22.75)/(6*Math.PI))*1024/2;
			ticks = ticks * rightMulti;
		}
	}
	
	protected void initialize() {
		DrivetrainSS.frontLeft.selectProfileSlot(1, 0); //TODO set it back somewhere else
		DrivetrainSS.frontRight.selectProfileSlot(1, 0);
		startDistanceR = DrivetrainSS.frontRight.getSelectedSensorPosition(0);
		startDistanceL = DrivetrainSS.frontLeft.getSelectedSensorPosition(0);
		errorCounter = 0;
		turnCounter = 0;
	}

	protected void execute() {
		DrivetrainSS.frontRight.set(ControlMode.Position, startDistanceR + ticks);
		DrivetrainSS.frontLeft.set(ControlMode.Position, startDistanceL + ticks);
		turnCounter++;
		System.out.println("Left TARGET: " + startDistanceL + ticks);
		System.out.println("Right TARGET: " + startDistanceR + ticks);
	}
	protected boolean isFinished() {
		if ((Math.abs(((startDistanceR + ticks) - DrivetrainSS.frontRight.getSelectedSensorPosition(0))) < 200) && (Math.abs(((startDistanceL + ticks) - DrivetrainSS.frontLeft.getSelectedSensorPosition(0))) < 200)) {
			if (errorCounter >= 10) return true;
			else {
				errorCounter++;
				return false;
			}	
		} else if (turnCounter >= maxTurnTime) return true;
		else {
			errorCounter = 0;
			return false;
		}
	}
	protected void end() {
	}
	protected void interrupted() {
	}
	
	
	
}

