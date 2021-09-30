/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
//package org.usfirst.frc.team1403.robot;

import java.lang.ModuleLayer.Controller;
import java.sql.Driver;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMTalonFX;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;

//import edu.wpi.first.wpilibj.Encoder;
//import cougarecho.record.Recorder;
//import sun.jvm.hotspot.ci.ciMethod;
//import sun.security.provider.CtrDrbg;






/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
/**
 * TimedRobot: It provides control of the robot program through a collection of init() and periodic() methods,
 * which are called by WPILib during specific robot states (e.g. autonomous or teleoperated).
 * By default, periodic methods are called every 20ms; this can be changed by overriding the getPeriod() method.
 * 
 * 
 * 
 * 
 */
public class Robot extends TimedRobot {

 // private final Encoder           m_encoder     = new Encoder(0,1 );                    //encoder stuff, it good
  private final PWMVictorSPX      m_left        = new PWMVictorSPX(0);                  //Sets up left side of drive motors
  private final PWMVictorSPX      m_right       = new PWMVictorSPX(1);                  //Sets up right side of drive motors
  private final DifferentialDrive m_robotDrive  = new DifferentialDrive(m_left,m_right);//Initializes the drive function to use both sets of drive motors
  private final PWMVictorSPX      m_intake      = new PWMVictorSPX(2);                  //for intake/outtake
  private final PWMVictorSPX      m_launcher1   = new PWMVictorSPX(4);    //bottom      //for The Launching Mechanism
  private final PWMVictorSPX      m_launcher2   = new PWMVictorSPX(5);                  //top Motor
  private final PWMSparkMax       m_light       = new PWMSparkMax(3);                   //for the lights
  private final Joystick          m_stick       = new Joystick(0);                      //Joystick Controller right
  private final Joystick          m_stick_left  = new Joystick(1);                      //Joystick Controller left
  private final Timer             m_timer       = new Timer();                          //Timer object
  private final XboxController    m_controller  = new XboxController(2);                //XBox Controller
  private final UsbCamera         camera        = new UsbCamera("Camera", 0);            //Camera
  private final MjpegServer       jpegServer1   = new MjpegServer("serve_USB Camera", 25); //mJpegServer
  private final CvSink            cvSink        = new CvSink("opencv_USB Camera 0");    //CvSink

  

    
  public static boolean toggleOn;
  public static boolean Truetoggle;
  public static boolean togglePressed;
  public static boolean driveSwitch;
  public static boolean togglePressed2;


  /*
  private int numpaths;
  private String path;
  public static Recorder recorder;
  public static boolean record;
  public static boolean store;*(/)

  

  
  //private final AnalogInput       m_distance    = new AnalogInput(0);                   //Distance Sensor
    /*
    @Override
    public void enable() {
      // TODO Auto-generated method stub
      
    }
  
    @Override
    public void disable() {
      // TODO Auto-generated method stub
      
    }
  }

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  
  @Override
  public void robotInit() {
  
    /*
  jpegServer1.setSource(camera);
  cvSink.setSource(camera);
  */
  
  

  toggleOn = false;

  Truetoggle = false;
  togglePressed2 = false;

  togglePressed = false;
  driveSwitch = false;
  togglePressed2 = false;
  /*recorder = new Recorder(10000);
  boolean toggleOn = false;
  boolean togglePressed = false;

  numpaths = 0;
  init();
  recorder.setCurrentWritefile(1);
  recorder.setCurrentReadfile(0);
  Recorder.initWriter();
  Recorder.initReader();*/
  
  }
  /**
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();

   // m_encoder.reset(); //resets encoder to 0
   // m_encoder.setDistancePerPulse(1.0/360.0);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

   
    //m_left.setSpeed(0.50);
    //m_right.setSpeed(-0.50);


    /*if (m_timer.get() < 3.0) {
      m_robotDrive.arcadeDrive(0.4, 0.0); // drive forwards half speed
    }
    else if (m_timer.get() > 3.0 && m_timer.get() < 6.0)
    {
      m_robotDrive.arcadeDrive(-0.4, 0.0); //drive backwards half speed
    }
    else if (m_timer.get() > 6.0 && m_timer.get() < 9.0)
    {
      m_robotDrive.arcadeDrive(0.5, -0.45);
    }
    else if (m_timer.get() > 9.0 && m_timer.get() < 12.0)
    {
      m_intake.setSpeed(-0.4); //intake
      m_light.set(0.61);
      m_robotDrive.arcadeDrive(0.4, 0.0);
    }
    else if (m_timer.get() > 12.0 && m_timer.get() < 15.0)
    {
      m_intake.setSpeed(0.4); //outtake
      m_light.set(0.69);
      m_robotDrive.arcadeDrive(-0.4, 0.0);
    } 
    if (m_timer.get() < 15.0)
    {
      m_robotDrive.arcadeDrive(-0.4, 0.0);
    }
    else
    {
      m_light.stopMotor();
      m_intake.setSpeed(0);
      m_robotDrive.stopMotor(); // stop robot
    }*/ 
    //Forward(0.3,0.0);
    //m_robotDrive.arcadeDrive(0.3,0.0);
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    

   

    

  /*if (m_stick.getY() < 0.2 && m_stick.getY() > -0.2)
    {
      if (m_stick.getRawButton(1))
      {
        updateToggle();
      }
      if (togglePressed == true)
      {
        m_robotDrive.stopMotor();
        change = -1;
      }
      if (togglePressed == false)
      {
        m_robotDrive.stopMotor();
        change = 1;
      }
    }

    //Drive(-(m_stick.getY()), m_stick.getX());
    m_robotDrive.arcadeDrive(change * -(m_stick.getY()),change * m_stick.getX());*/


    if (m_stick.getRawButton(2))
    {
      ReverseControls();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        //TODO: handle exception
      }
    }
    if (togglePressed == true)
    {
      System.out.println("Button has been toggled");
      if (!driveSwitch) //Joystick
      {
          m_robotDrive.arcadeDrive((m_stick.getY()),-m_stick.getX(), true);
      }
      else 
      {
          //m_robotDrive.tankDrive(m_controller.getY(Hand.kLeft),m_controller.getY(Hand.kRight));
          m_robotDrive.tankDrive(m_stick.getY(),m_stick_left.getY());
      }
    }
    else
    {
      System.out.println("Button has not been toggled");
      if (!driveSwitch) //Joystick
      {
          m_robotDrive.arcadeDrive(-(m_stick.getY()), (m_stick.getX()), true);
      }
      else
      {
        //m_robotDrive.tankDrive(-m_controller.getY(Hand.kLeft),-m_controller.getY(Hand.kRight))
        m_robotDrive.tankDrive(-(m_stick_left.getY()),-(m_stick.getY()));
      }
    }


    //This controls the intake and outtake motor (tank drive)
    if (m_controller.getY() > 0.69) //If the controller's joystick y axis is greater than 0.69
    {
      //m_intake.setSpeed(0.70); //speed is set to 0.70
    }
    else if(m_controller.getY() < -0.69) //If the controller's joystick y axis is less than 0.69
    {
      //m_intake.setSpeed(-0.70); //speed is set to -0.70
    }
    else if(m_controller.getY() > 0.1 || m_controller.getY() < -0.1) //if the controlller's joystick y axis is between 0.1 and -0.1
    {
      //m_intake.setSpeed(- (m_controller.getY())); //User controlled speed
    }
    else if (m_controller.getRawButton(1)) //if button A is pressed
    {
      Intake();  //Turn on intake motor
    }
    else if (m_controller.getRawButton(3)) //if button B is pressed
    {
      Outtake(); //Turn on outtake motor
    }
    else
    {
      m_intake.stopMotor(); //Stop intake motor
    }

     

    
    if (m_controller.getRawButton(2)) 
    {
      
      m_launcher1.setSpeed(0.6);  //Bottom
      m_launcher2.setSpeed(-0.5); //Top
    }
    else if (m_controller.getRawButton(4))
    {
      Launcher(-0.3);
    }
    else 
    {
      m_launcher1.stopMotor();
      m_launcher2.stopMotor();
    }

    if(m_controller.getRawButtonPressed(7))
    {
      SwitchDrive();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        //TODO: handle exception
      }
    }


    
    if(m_stick.getRawButtonPressed(7))
    {
      SwitchDrive();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        //TODO: handle exception
      }
    }

                                  // sensor code. Might work? Probably not.
    /*double gapLength = 60.96;

    double currentdistance = (m_distance.getValue() / 9.77);

    if (currentdistance < gapLength)
    {
      Drive(0.2,0.0); 
    }
    else Stop();*/






    /*if (m_stick.getDirectionDegrees() <= 20  || (m_stick.getDirectionDegrees() >=  70 && m_stick.getDirectionDegrees() <= 110))
    {
      m_robotDrive.arcadeDrive(-(m_stick.getY()), m_stick.getX());
    }
    else m_robotDrive.stopMotor();*/

    //Spins m_intake
    /*if(m_stick.getRawButton(3)) //Intake
    {
      m_light.set(0.61);
      m_intake.setSpeed(-0.4);
    }
    else if(m_stick.getRawButton(4)) //Outtake
    {
      m_light.set(0.69);
      m_intake.setSpeed(0.4);
    }
    else
    {
      m_light.stopMotor();
      m_intake.stopMotor();
    }

    if (m_controller.getRawButton(1))
    {
      m_light.set(0.77); //Green Light
    }
    else if (m_controller.getRawButton(2))
    {
      m_light.set(0.61); //Red Light
    }
    else if (m_controller.getRawButton(4))
    {
      m_light.set(0.69); //Yellow Light
    }
    else if (m_controller.getRawButton(3))
    {
      m_light.set(0.87); //Blue Light
    }
    else 
    {
      m_light.stopMotor(); //No Light
    }

    
    m_robotDrive.arcadeDrive(-(m_stick.getY()), m_stick.getX());
   */ 
    //m_robotDrive.tankDrive(-m_controller.getY(Hand.kLeft) / 2, -m_controller.getY(Hand.kRight) / 2 );
    

    //m_left.setSpeed(m_stick.getY()/8);
   // m_right.setSpeed(-(m_stick.getY()/8)); //Testing to see if running both of them also makes it pop.
    /*if(m_stick.getRawButton(4)) 
    {
      m_light.set(0.61);
      m_left.setSpeed(-0.4);
    }
    
    
    m_intake.setSpeed(-(m_stick.getY() / 2));
*/
  }
  /*
  
  public void ReverseControls()
  {
      if (!togglePressed)
      {
        toggleOn = !toggleOn;
        togglePressed = true;
      }
      else 
      {
        togglePressed = false;
      }
  }
  public void SwitchDrive()
  {
    if (!togglePressed2)
    {
       driveSwitch = true;
       togglePressed2 = true;
    }
    else
    {
      driveSwitch = false;
      togglePressed2 = false;
    }
  }

  public void Drive(double Mspeed, double Mrotation)
  {
    if ((Mspeed <= 1 && Mspeed >= -1) && (Mrotation <= 1 && Mrotation >= -1))
    {
      m_robotDrive.arcadeDrive(Mspeed,Mrotation);
    }
  }
  
  public void Intake()
  {
    //m_light.set(0.61);
    m_intake.setSpeed(0.7);
  }

  public void Outtake()
  {
    //m_light.set(0.69);
    m_intake.setSpeed(-0.7);
  }

  public void Launcher(double speed)
  {
    m_launcher1.setSpeed(speed);             //Bottom
    m_launcher2.setSpeed(-(speed - 0.1));    //Top
  }

  public void Stop()
  {
    m_robotDrive.stopMotor();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }
}


//

