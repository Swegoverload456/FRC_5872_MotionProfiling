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
import edu.wpi.first.wpilibj.Joystick;
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
	
	Joystick _joy = new Joystick(0);
	
	//InterpreterL left = new InterpreterL(frontLeft);
	//InterpreterR right = new InterpreterR(frontRight);
	
	//MotionProfileExample test = new MotionProfileExample(frontLeft);

	boolean[] _btnsLast = {false, false, false, false, false, false, false, false, false, false};
	
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
		frontLeft.setInverted(true);
		frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 30);
		//frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 30);
		frontLeft.configNominalOutputForward(0, 30);
		frontLeft.configNominalOutputReverse(0, 30);
		frontLeft.configPeakOutputForward(0.3, 30);
		frontLeft.configPeakOutputReverse(-0.3, 30);
		frontLeft.selectProfileSlot(0, 0);
		frontLeft.config_kF(0, 2, 30);
		frontLeft.config_kP(0, 0, 30);
		frontLeft.config_kI(0, 0, 30);
		frontLeft.config_kD(0, 0, 30);
		
		frontLeft.configMotionProfileTrajectoryPeriod(10, Constants.kTimeoutMs); 
		
		frontLeft.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		frontLeft.configMotionCruiseVelocity(15000, 30);
		frontLeft.configMotionAcceleration(6000, 30);
		
		frontLeft.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 0); 
		frontLeft.configVelocityMeasurementWindow(64, 0);     
		
		backLeft.follow(frontLeft);
		/* zero the sensor */
		frontLeft.setSelectedSensorPosition(0, 0, 30);
		
		frontRight.setSensorPhase(true);
		frontRight.setInverted(true);
		frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 30);
		frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 30);
		frontRight.configNominalOutputForward(0, 30);
		frontRight.configNominalOutputReverse(0, 30);
		frontRight.configPeakOutputForward(0.3, 30);
		frontRight.configPeakOutputReverse(-0.3, 30);
		frontRight.selectProfileSlot(0, 0);
		frontRight.config_kF(0, 2, 30);
		frontRight.config_kP(0, 0, 30);
		frontRight.config_kI(0, 0, 30);
		frontRight.config_kD(0, 0, 30);
		
		frontRight.configMotionProfileTrajectoryPeriod(10, Constants.kTimeoutMs); 
		
		frontRight.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		frontRight.configMotionCruiseVelocity(15000, 30);
		frontRight.configMotionAcceleration(6000, 30);
		backRight.setInverted(true);
		backRight.follow(frontRight);
		/* zero the sensor */
		frontRight.setSelectedSensorPosition(0, 0, 30);
		
		frontRight.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 0); 
		frontRight.configVelocityMeasurementWindow(64, 0);     
		//left.control();
		//right.control();
		//test.reset();
		//test.startMotionProfile();
		//test.control();
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
		
		
		SmartDashboard.putNumber("Speed: ", frontLeft.getMotorOutputPercent());
		SmartDashboard.putNumber("frontLeft Enc: ", frontLeft.getSelectedSensorPosition(0));
		//double c = 814.873308631;
		
		
		//frontLeft.set(ControlMode.MotionMagic, c * 15);
		//delay(50);
		
		//delay(50);
		//frontLeft.setSelectedSensorPosition(0, 0, 30);
		//frontLeft.set(ControlMode.MotionMagic, c* -25);
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		
		while(true) {
		
			double c = 814.873308631;
			
			//test.startMotionProfile();
			
			//left.start();
			//SetValueMotionProfile lsetOutput = test.getSetValue();
			
			//right.start();
			//SetValueMotionProfile rsetOutput = right.getSetValue();
			
			dumpPoints(frontLeft, frontRight);
			//dumpPointsL(frontLeft);
			//dumpPointsR(frontRight);
			
			//frontLeft.set(ControlMode.MotionProfile, lsetOutput.value);
			
			//frontRight.set(ControlMode.MotionProfile, rsetOutput.value);
			
			//test.start();
			
			//SetValueMotionProfile setOutput = test.getSetValue();
			
			SmartDashboard.putNumber("Speed: ", frontLeft.getMotorOutputPercent());
			SmartDashboard.putNumber("frontLeft Enc: ", frontLeft.getSelectedSensorPosition(0));
			SmartDashboard.putNumber("frontRight Enc: ", frontRight.getSelectedSensorPosition(0));
			
			//frontLeft.set(ControlMode.MotionProfile, setOutput);
			
			//SmartDashboard.putNumber("Right Enc:  ", right.getSelectedSensorPosition(0));
		
			//frontLeft.set(ControlMode.PercentOutput, 1.0);
			
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	public void dumpPoints(TalonSRX left, TalonSRX right) {
		
		for(int i = 0; i < TestFile.TotalPoints; i++) {
			
			while((left.getSelectedSensorPosition(0)) < (TestFile.leftPoints[i][0] * Constants.kSensorUnitsPerRotation * 12) && (right.getSelectedSensorPosition(0)) < (TestFile.rightPoints[i][0] * Constants.kSensorUnitsPerRotation * 12)) {
				
				if(left.getSelectedSensorPosition(0) >= (TestFile.leftPoints[i][0] * Constants.kSensorUnitsPerRotation * 12)) {
					
					left.set(ControlMode.PercentOutput, 0);
					
				}
				else {
					
					left.set(ControlMode.Velocity, TestFile.leftPoints[i][1] * 12 * (Constants.kSensorUnitsPerRotation/200));
					
				}
				
				if(right.getSelectedSensorPosition(0) >= (TestFile.rightPoints[i][0] * Constants.kSensorUnitsPerRotation * 12)) {
					
					right.set(ControlMode.PercentOutput, 0);
					
				}
				else {
					
					right.set(ControlMode.Velocity, TestFile.rightPoints[i][1] * 12 * (Constants.kSensorUnitsPerRotation/200));
					
				}
				
				SmartDashboard.putNumber("frontLeft Enc: ", frontLeft.getSelectedSensorPosition(0));
				SmartDashboard.putNumber("Left MP Position: ", TestFile.leftPoints[i][0] * Constants.kSensorUnitsPerRotation * 12);
				SmartDashboard.putNumber("frontLeft Vel: ", TestFile.leftPoints[i][1] * 12 * (Constants.kSensorUnitsPerRotation/200));
				SmartDashboard.putNumber("frontRight Enc: ", frontRight.getSelectedSensorPosition(0));
				SmartDashboard.putNumber("Right MP Position: ", TestFile.rightPoints[i][0] * Constants.kSensorUnitsPerRotation * 12);
				SmartDashboard.putNumber("frontRight Vel: ", TestFile.rightPoints[i][1] * 12 * (Constants.kSensorUnitsPerRotation/200));
				
			}
			
			left.set(ControlMode.PercentOutput, 0);
			right.set(ControlMode.PercentOutput, 0);
			//delay(50);
			
		}
		
	}
	
	public void dumpPointsL(TalonSRX talon) {
			
			for(int i = 0; i < TestFile.TotalPoints; i++) {
				
				while((talon.getSelectedSensorPosition(0)) < (TestFile.leftPoints[i][0] * Constants.kSensorUnitsPerRotation * 12)) {
					
					talon.set(ControlMode.Velocity, TestFile.leftPoints[i][1] * 12 * (Constants.kSensorUnitsPerRotation/200));
					SmartDashboard.putNumber("frontLeft Enc: ", frontLeft.getSelectedSensorPosition(0));
					SmartDashboard.putNumber("Left MP Position: ", TestFile.leftPoints[i][0] * Constants.kSensorUnitsPerRotation * 12);
					SmartDashboard.putNumber("frontLeft Vel: ", TestFile.leftPoints[i][1] * 12 * (Constants.kSensorUnitsPerRotation/200));
					
				}
				
				talon.set(ControlMode.PercentOutput, 0);
				//delay(50);
				
			}
		
	}

	public void dumpPointsR(TalonSRX talon) {
		
		for(int i = 0; i < TestFile.TotalPoints; i++) {
			
			while((talon.getSelectedSensorPosition(0)) < (TestFile.rightPoints[i][0] * Constants.kSensorUnitsPerRotation * 12)) {
				
				talon.set(ControlMode.Velocity, TestFile.rightPoints[i][1] * 12 * (Constants.kSensorUnitsPerRotation/200));
				SmartDashboard.putNumber("frontRight Enc: ", frontRight.getSelectedSensorPosition(0));
				SmartDashboard.putNumber("Right MP Position: ", TestFile.rightPoints[i][0] * Constants.kSensorUnitsPerRotation * 12);
				SmartDashboard.putNumber("frontRight Vel: ", TestFile.rightPoints[i][1] * 12 * (Constants.kSensorUnitsPerRotation/200));
				
			}
			
			talon.set(ControlMode.PercentOutput, 0);
			//delay(50);
			
		}
		
	}
	
	public static void delay(int milliseconds) {
		
		try {
			
			Thread.sleep(milliseconds);
			
		}
		catch(Exception e1){
			
			e1.printStackTrace();
			
		}
		
	}
}
