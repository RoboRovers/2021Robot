/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.lang.ModuleLayer.Controller;
import java.sql.Driver;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


/*
 * Your best friend: https://first.wpi.edu/FRC/roborio/release/docs/java/
 * the link above is the documentation for the classes and library we use
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * TimedRobot: It provides control of the robot program through a collection of init() and periodic() methods,
 * which are called by WPILib during specific robot states (e.g. autonomous or teleoperated).
 * By default, periodic methods are called every 20ms; this can be changed by overriding the getPeriod() method.
 * 
 * Any method with @Overide above it is a function in the class TimedRobot which is what our code inherits from. We override them with
 * the functionality we need from out robot and shouldnt be deleted. Deleting them wont do anything and you can reimplment them later
 * but I recommend not removing them for simplicity. The other methods are ones we defined completly for ourselves and I prefer to put
 * them near the top of the code. These are used to simplify code and make code snippets reusable. An example being the Launcher()
 * method that should set the launcher to the speed you pass in and make sures they spin in oppisite directions. That way we just need
 * to call Luancher when we need it instead of rewriting it everytime we need to set the speed of the launcher. 
 * 
 * Here is the explanation for all the methods we overide from the TimedRobot class:
 * Note: If you want more info on any of these or see the other ones we havent implemented you can find it in the documentation below: 
 * https://first.wpi.edu/FRC/roborio/release/docs/java/
 * 
 * robotInit() is the function run when the robot starts up. Use this for code you need to run when the robot is enabled
 * 
 * teleopInit() is the function run when the robot starts into tele-operated mode (when you control the robot remotly).
 * 
 * teleopPeriodic() is the function run consantly when in tele-operated mode. A very simplified explanation is while the robot is in 
 * tele-operated mode it will do the instructions in this function every 20ms. So this is where stuff like your drive and shooting go
 * 
 * autonomousInit() is the function run when the robot starts into autonomous mode (when the robot moves on its own).
 * 
 * autonomousPeriodic() is the function run consantly when in autonomous mode. A very simplified explanation is while the robot is in 
 * autonomous mode it will do the instructions in this function every 20ms. So this is where stuff like drive and shooting go
 */
public class Robot extends TimedRobot {

 // private final Encoder           m_encoder     = new Encoder(0,1 );                    //encoder stuff, it good
  private final PWMVictorSPX      m_left        = new PWMVictorSPX(0);                  //Sets up left side of drive motors
  private final PWMVictorSPX      m_right       = new PWMVictorSPX(1);                  //Sets up right side of drive motors
  private final DifferentialDrive m_robotDrive  = new DifferentialDrive(m_left,m_right);//Initializes the drive function to use both sets of drive motors
  private final PWMVictorSPX      m_intake      = new PWMVictorSPX(9);                  //for intake/outtake
  private final PWMVictorSPX      m_launcher1   = new PWMVictorSPX(4);    //bottom      //for The Launching Mechanism
  private final PWMVictorSPX      m_launcher2   = new PWMVictorSPX(5);                  //top Motor
  private final PWMSparkMax       m_light       = new PWMSparkMax(3);                   //for the lights
  private final Joystick          m_stick       = new Joystick(0);                      //Joystick Controller
  private final Timer             m_timer       = new Timer();                          //Timer object
  private final PWMSparkMax       m_climber     = new PWMSparkMax(6);                    //Climbers

  public static boolean controlsReversed;
  public static boolean driveSwitch;
  public static int autonomousTestCount;

  public void Launcher(double speed)
  {
    m_launcher1.setSpeed(speed);             //Bottom
    m_launcher2.setSpeed(-(speed - 0.1));    //Top
  }

  @Override
  public void robotInit() {
    controlsReversed = false;
    driveSwitch = false;
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
//99-103=intake motor foward and reverse motion
    if (m_stick.getTriggerPressed()) {
      m_intake.setSpeed(0.5);
    }
    if (m_stick.getTriggerReleased()) {
      m_intake.setSpeed(0);
    }
    if (m_stick.getRawButtonPressed(4)) {
      m_intake.setSpeed(-0.5);
    }
    if (m_stick.getRawButtonReleased(4)) {
      m_intake.setSpeed(0);
    }
//106-109=Reverse controls on button 2
    if (m_stick.getRawButtonReleased(2))
    {
      controlsReversed = !controlsReversed;
    }
    
//111-116=launcher set speeds
    if (m_stick.getRawButtonPressed(3)) {
      Launcher(0.5);
    }
    if (m_stick.getRawButtonReleased(3)) {
      m_launcher1.setSpeed(0);
      m_launcher2.setSpeed(0);
    }
    
    if (m_stick.getRawButtonPressed(5)) {
      Launcher(0.9);
    }
    if (m_stick.getRawButtonReleased(5)) {
      m_launcher1.setSpeed(0);
      m_launcher2.setSpeed(0);
    }
//118-123=Stop all motors button 6
    if (m_stick.getRawButtonReleased(6)) 
    {
      m_launcher1.stopMotor();
      m_launcher2.stopMotor();
      m_intake.stopMotor();
    }

    
//127-135=Driving controls and reverse
    if (controlsReversed)
    {
      m_robotDrive.arcadeDrive((m_stick.getY()),-m_stick.getX(), true);
    }
    else
    {
      m_robotDrive.arcadeDrive(-(m_stick.getY()), (m_stick.getX()), true);
    }
  }
  //136-156=old autonomous mode (WIP)
  @Override
  public void autonomousInit() {
    //m_timer.reset();
    //m_timer.start();
   // m_encoder.reset(); //resets encoder to 0
   // m_encoder.setDistancePerPulse(1.0/360.0);
   autonomousTestCount = 0;
  }

  @Override
  public void autonomousPeriodic() {
    if (autonomousTestCount < 100) {
      m_robotDrive.arcadeDrive(0.5, 0);
      autonomousTestCount += 1;
    }
    else {
      m_robotDrive.arcadeDrive(0, 0);
    }

  }

}