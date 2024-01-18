// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.closedloop;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.TestMotorConstants;
import frc.robot.subsystems.TestMotors;

public class PIDAngleControl extends Command {
  /** Creates a new PIDAngleControl. */
  private final TestMotors m_Shooter;
  private final PIDController m_AnglePIDController;
  private double setpoint;
  public PIDAngleControl(TestMotors shooter, double setpoint) {
    m_Shooter = shooter;
    this.setpoint = setpoint;
    m_AnglePIDController = new PIDController(TestMotorConstants.kAngleP, TestMotorConstants.kAngleI, TestMotorConstants.kAngleD);
    m_AnglePIDController.disableContinuousInput();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double feedforward = 0.0; // just to have TODO: maybe do characterization??
    double speed = m_AnglePIDController.calculate(m_Shooter.getAngleEncoder().getPosition(), setpoint);
    m_Shooter.getAngleMotor().set(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Shooter.getAngleMotor().set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    return m_AnglePIDController.atSetpoint();
  }
}
