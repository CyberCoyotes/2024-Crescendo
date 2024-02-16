# Autonomous Options
## Tested
- `Auto1`: Line up center or side, shoots a note, drives forward 3 meters.
- `Auto2`: This autonomous mode focuses on scoring points by navigating through a specific path.
- `Auto3`: This autonomous mode is designed for defensive maneuvers, blocking opponent robots.

## Dev and Calibrations
- `FailSafe`: Drives forward 2 meters and stops
- `Shoot1Only`: Score preloaded note from center or side. Do not move
- `1Load1`: Score preloaded note, drive to center note
- `1Score1`: Score preloaded note, drive to center note, score second note
- `1Score1Score1`:

/* STARTING POSITIONS & NOTE POSITIONS ***********
/*
    |-------------------------------------------|-------------|----
    |                                           
    |                                           4                                           
    | Start.1               3                   
    |\                                          |
    | \                                         |
    |  \                                        
    |   |                                       5
    |   |Start.2            2          /           
    |  /                             /          |
    | /                            /            |
    |/                           /               
    | Start.3               1  |                6
    |                            \            
    |                              \            |
    |                                \          |
    |                                  \             
    |                                           7
    |
    |                                           |
    |                                           |
    |                                           
    |                                           8
    |
    |-------------------------------------------|---------------|--

*/

/* S for Starting position */

|   Starting Position   |   Notes           |
| --------------------- | ----------------- |
|   S1                  | 0, 3, 4, 5        |
|   S1                  | 0, 3, 4, 5, 6     |
|   S1                  | 0, 3, 4, 5, 6, 2  |
|   S3                  | 0, 8, 7           |
|   S3                  | 0, 8, 7, 6        |
|   S2                  | 0, 2              |
|   S2                  | 0, 4, 5


|   Note                | Pose (x,y)        |
| --------------------  | ------------------|
|   1                   | (0.00,0.00)       |
|   2                   |                   |
|   3                   |                   |
|   4                   |                   |
|   5                   |                   |
|   6                   |                   |
|   7                   |                   |
|   8                   |                   |