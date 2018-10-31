/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5872.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motorcontrol.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	TalonSRX frontLeft = new TalonSRX(0);
	TalonSRX frontRight = new TalonSRX(3);
	TalonSRX backLeft = new TalonSRX(1);
	TalonSRX backRight = new TalonSRX(2);
	
	MotionProfileExample test = new MotionProfileExample(frontLeft);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
		backRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
		backLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
		
		frontLeft.setSensorPhase(false);
		frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 30);
		frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 30);
		frontLeft.configNominalOutputForward(0, 30);
		frontLeft.configNominalOutputReverse(0, 30);
		frontLeft.configPeakOutputForward(0.3, 30);
		frontLeft.configPeakOutputReverse(-0.3, 30);
		frontLeft.selectProfileSlot(0, 0);
		frontLeft.config_kF(0, 0.2, 30);
		frontLeft.config_kP(0, 0.2, 30);
		frontLeft.config_kI(0, 0, 30);
		frontLeft.config_kD(0, 0, 30);
		/* set acceleration and vcruise velocity - see documentation */
		frontLeft.configMotionCruiseVelocity(15000, 30);
		frontLeft.configMotionAcceleration(6000, 30);
		/* zero the sensor */
		frontLeft.setSelectedSensorPosition(0, 0, 30);

		//right.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		
		while(true) {
		
			double c = 814.873308631;
			
			test.control();
			
			
			SetValueMotionProfile setOutput = test.getSetValue();
			
			test.startMotionProfile();
			SmartDashboard.putNumber("Speed: ", frontLeft.getMotorOutputPercent());
			SmartDashboard.putNumber("frontLeft Enc: ", frontLeft.getSelectedSensorPosition(0));
			frontLeft.set(ControlMode.MotionProfile, setOutput.value);
			//frontLeft.set(ControlMode.MotionMagic, c * 40);
			
			//SmartDashboard.putNumber("Right Enc:  ", right.getSelectedSensorPosition(0));
		
			//left.set(ControlMode.PercentOutput, 0.4);
			
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
