Here's the changes that I really recall.

# Commit 1:

- Moved the ToF sensor to the index, since it's generally more applicable to index-related jobs.
- Simplified needless DoubleSuppliers.
- Added new **(untested)** auton commands
  - IncrementIndexCommand: Runs the index until the Time of Flight reads false.
  - RevShooterCommand Revs the shooter to the maximum. Stops when it reaches this max.
  - RunIndexContCommand: Literally runs the index continuously
  - RevShooterCommand: Revs the shooter until it gets to the proper point.
  - RevAndShootCommand: Gets the cargo out of the shooter's way, revs until it reaches the desired velocity, then indexes. Is finished half a second after.
- Removed ShooterSubsystem's RunShooter
  Reason: Obsolete, and subsystems running their own commands in P6 hurts my senile brain.
- Added new constants in reference to the velocity mode of the shooter motors.

# Commit 2:

- Arm Subsystem Changes:
  - Factory Default
  - Smartdash -> Shuffleboard

# Commit 3:

- "Reworked" comments. Some documentation/notes were moved, some fossils were eradicated, etc. Nothing else happened here, so that it is easier to dissect the changes.