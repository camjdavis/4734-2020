/*
FRC 2021: Infant Recharge
The Iron Plainsmen
4734
*/



//Controller 1
//Sticks = drive
//a = elevtor down
//y = elevator up
//b = limelight

//Controller 2
//left Stick = into shooter
//right Stick = into robot
//a = intake out
//y = intake in 
//b = turret x right
//x = turret x left
//rightBump = shooter
//leftBump = front cannon




//imports
package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.networktables.*;
import edu.wpi.first.cameraserver.CameraServer;



//public class
public class Robot extends TimedRobot {
  //CAN Device ID declaration and initialization
  private static final int leftDeviceID = 1;
  private static final int rightDeviceID = 2;
  private static final int elevatorID = 3;
  private static final int intakeID = 4;
  private static final int intoRobotID = 5;
  private static final int frontCannonID = 6;
  private static final int shooterID = 7;
  private static final int turretXID = 8;
  //private static final int turretYID = 9;
  private static final int intoShooterID= 10;
  private static final int preShooterID = 11;
  //declaration of motors
  private DifferentialDrive m_myRobot;
  private CANSparkMax m_leftMotor;
  private CANSparkMax m_rightMotor;
  private VictorSPX m_elevator;
  private VictorSPX m_intake;
  private TalonSRX m_intoRobot;
  private CANSparkMax m_frontCannon;
  private VictorSPX m_shooter;
  private VictorSPX m_turretX;
  private VictorSPX m_intoShooter;
  private TalonSRX m_preShooter;



  //initialization of controller one
  private final XboxController m_driverController = new XboxController(0);
  //initialization of controller two
  private final XboxController m_secondController = new XboxController(1);



  @Override
  public void robotInit() {
    //initialization of motors
    m_leftMotor = new CANSparkMax(leftDeviceID, MotorType.kBrushless);
    m_rightMotor = new CANSparkMax(rightDeviceID, MotorType.kBrushless);
    m_elevator = new VictorSPX(elevatorID);
    m_intoRobot = new TalonSRX(intoRobotID);
    m_frontCannon = new CANSparkMax(frontCannonID, MotorType.kBrushless);
    m_shooter = new VictorSPX(shooterID);
    m_turretX = new VictorSPX(turretXID);
    m_intoShooter = new VictorSPX(intoShooterID);
    m_intake = new VictorSPX(intakeID);
    m_turretX = new VictorSPX(turretXID);
    m_preShooter = new TalonSRX(preShooterID);



    //restoring factory defaults and turning motors off
    m_leftMotor.restoreFactoryDefaults();
    m_leftMotor.set(0);
    m_rightMotor.restoreFactoryDefaults();
    m_rightMotor.set(0);
    m_frontCannon.restoreFactoryDefaults();
    m_frontCannon.set(0);
    m_elevator.configFactoryDefault();
    m_elevator.set(ControlMode.PercentOutput, 0);
    m_intake.configFactoryDefault();
    m_intake.set(ControlMode.PercentOutput, 0);
    m_intoRobot.configFactoryDefault();
    m_intoRobot.set(ControlMode.PercentOutput,0);
    m_shooter.configFactoryDefault();
    m_shooter.set(ControlMode.PercentOutput, 0);
    m_turretX.configFactoryDefault();
    m_turretX.set(ControlMode.PercentOutput, 0);
    m_intoShooter.configFactoryDefault();
    m_intoShooter.set(ControlMode.PercentOutput, 0);
    m_preShooter.configFactoryDefault();
    m_preShooter.set(ControlMode.PercentOutput, 0);
    //setting up diff. drive
    m_myRobot = new DifferentialDrive(m_leftMotor, m_rightMotor);
    m_myRobot.setRightSideInverted(true);
    


    //camera cool stuff
    setResolution();
    startAutomaticCapture();
    getVideo();
    CameraServer server = CameraServer.getInstance();
    server.startAutomaticCapture("frontCam", 0);
  }



  private void getVideo() 
  {}
  private void startAutomaticCapture() 
  {}
  private void setResolution() 
  {}



  @Override

  public void autonomousInit()
  {
    m_myRobot.tankDrive(.4, .4); //drive forward
    Timer.delay(.5);
    m_myRobot.tankDrive(0, 0);

    while(limelightX < -2.5 || limelightX > 2.5) //aim might not work
    {
      if(limelightX > 2.5)
      {
        m_turretX.set(ControlMode.PercentOutput, .25);
        limelightX = tx.getDouble(0.0);
      }
      else if(limelightX < -2.5)
      {
        m_turretX.set(ControlMode.PercentOutput, -.25);
        limelightX = tx.getDouble(0.0);
      }
      else
      {
        m_turretX.set(ControlMode.PercentOutput, 0);
        limelightX = tx.getDouble(0.0);
      }
      limelightX = tx.getDouble(0.0);
    }
    m_turretX.set(ControlMode.PercentOutput, 0); //aim might not work

    m_myRobot.setSafetyEnabled(false); //shoot
    m_myRobot.tankDrive(0.0, 0.0);
    m_intake.set(ControlMode.PercentOutput, 0);
    m_intoRobot.set(ControlMode.PercentOutput, 0);
    m_elevator.set(ControlMode.PercentOutput, 0);
    m_frontCannon.set(0);
    m_turretX.set(ControlMode.PercentOutput, 0);
    m_shooter.set(ControlMode.PercentOutput, 1);
    Timer.delay(.75);
    m_preShooter.set(ControlMode.PercentOutput, -1);
    m_intoShooter.set(ControlMode.PercentOutput, .5);
    Timer.delay(1.5);
    m_shooter.set(ControlMode.PercentOutput, 0);
    m_preShooter.set(ControlMode.PercentOutput, 0);
    m_intoShooter.set(ControlMode.PercentOutput, 0); //shoot

  }

  public void teleopPeriodic() 
  {
    //driving control driver controller left and right stick
    m_myRobot.tankDrive(-(m_driverController.getY(Hand.kLeft)), -(m_driverController.getY(Hand.kRight)));
    
    //limelight driver controller b
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    //NetworkTableEntry ty = table.getEntry("ty");
    //NetworkTableEntry ta = table.getEntry("ta");
    double limelightX = tx.getDouble(0.0);
    //double limelightY = ty.getDouble(0.0);
    //double area = ta.getDouble(0.0);
    Boolean bButton = m_driverController.getRawButton(2);
    if(bButton)
    {
      while(limelightX < -2.5 || limelightX > 2.5)
      {
        if(limelightX > 2.5)
        {
          m_turretX.set(ControlMode.PercentOutput, .25);
          limelightX = tx.getDouble(0.0);
        }
        else if(limelightX < -2.5)
        {
          m_turretX.set(ControlMode.PercentOutput, -.25);
          limelightX = tx.getDouble(0.0);
        }
        else
        {
          m_turretX.set(ControlMode.PercentOutput, 0);
          limelightX = tx.getDouble(0.0);
        }
        limelightX = tx.getDouble(0.0);
      }
      m_turretX.set(ControlMode.PercentOutput, 0);
    }



  //elevator driver controller a and y
  while(m_driverController.getRawButton(1))
  {
    m_elevator.set(ControlMode.PercentOutput, -1);
  }
  if(!m_driverController.getRawButton(1))
    {
      m_elevator.set(ControlMode.PercentOutput, 0);
    }
  while(m_driverController.getRawButton(4))
  {
    m_elevator.set(ControlMode.PercentOutput, 1);
  }
  if(!m_driverController.getRawButton(4))
  {
    m_elevator.set(ControlMode.PercentOutput, 0);
  }



  //intake second controller a and y
  while(m_secondController.getRawButton(4))
  {
    m_intake.set(ControlMode.PercentOutput, .75);
  }
  if(!m_secondController.getRawButton(4))
  {
    m_intake.set(ControlMode.PercentOutput, 0);
  }
  while(m_secondController.getRawButton(1))
  {
    m_intake.set(ControlMode.PercentOutput, -.75);
  }
  if(!m_secondController.getRawButton(1))
  {
    m_intake.set(ControlMode.PercentOutput, 0);
  }
  


  //into robot second controller right stick 
  m_intoRobot.set(ControlMode.PercentOutput, (m_secondController.getY(Hand.kRight))/2);
  //into robot second controller left stick 
  m_intoShooter.set(ControlMode.PercentOutput, (-(m_secondController.getY(Hand.kLeft))/4));



  //front cannon second controller Left Bumper
  while(m_secondController.getRawButton(5))
   {
     m_frontCannon.set(-.75);
   }
   if(!m_secondController.getRawButton(5))
   {
     m_frontCannon.set(0);
   }



  //shooter second controller right bumper
  if (m_secondController.getRawButton(6)) 
  {
    m_myRobot.setSafetyEnabled(false);
    m_myRobot.tankDrive(0.0, 0.0);
    m_intake.set(ControlMode.PercentOutput, 0);
    m_intoRobot.set(ControlMode.PercentOutput, 0);
    m_elevator.set(ControlMode.PercentOutput, 0);
    m_frontCannon.set(0);
    m_turretX.set(ControlMode.PercentOutput, 0);
    m_shooter.set(ControlMode.PercentOutput, 1);
    Timer.delay(.75);
    m_preShooter.set(ControlMode.PercentOutput, -1);
    m_intoShooter.set(ControlMode.PercentOutput, .5);
    Timer.delay(1.5);
    m_shooter.set(ControlMode.PercentOutput, 0);
    m_preShooter.set(ControlMode.PercentOutput, 0);
    m_intoShooter.set(ControlMode.PercentOutput, 0);
  }



  //manual control of turret x, second controller x and b
  while(m_secondController.getRawButton(2))
    {
      m_turretX.set(ControlMode.PercentOutput, .25);
    }
  if(!m_secondController.getRawButton(2))
    {
      m_turretX.set(ControlMode.PercentOutput, 0);
    }
  while(m_secondController.getRawButton(3))
    {
      m_turretX.set(ControlMode.PercentOutput, -.25);
    }
  if(!m_secondController.getRawButton(3))
    {
      m_turretX.set(ControlMode.PercentOutput, 0);
    }

  m_myRobot.close();
  }
}
