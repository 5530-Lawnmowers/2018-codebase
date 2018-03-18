package org.usfirst.frc.team5530.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class G_RightTurnAuton extends CommandGroup {
	
	public G_RightTurnAuton() {
		addSequential(new InitializeMotors());
		addSequential(new TurnArc(52, Math.PI/2, Math.PI/5, .05, "right"));
		addSequential(new TurnOL("left"));
		addSequential(new InitializeMotors());
		addSequential(new DriveForward(49));
	}

}
 