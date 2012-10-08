/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054
 *
 * Comp 4104 Assignment 1 
 */
 
 -Java 1.7 is required to run this assignment. Note that Java 1.7 is installed on the Windows 7 machines in the HP labs.
 
 -Windows 7 is required to run this assignment (specifically the HP lab computers) as the tests are automated with batch
 files.
 
 -Eclipse is necessary to run this assignment (with the 'Build Automatically' option checked in the 'Project' menu from
 the menu bar) as we didn't provide a separate Java makefile.
 
 -The Eclipse project java compiler settings need to be set to use 1.7 (it is already set up that way but if you wanted
 to just import the source files only and not the entire project then make sure you're using 1.7). 
 
 -----------------------------------------------
 Running the automated tests:
 -There are two options to run the provided automated tests. The first is to run 'A1Tests.bat'. You will be prompted before the tests 
 to make sure you're ready to move on. The second way is to run 'Question*Test.bat' where * is the question number you want
 to test. The 'A1Tests.bat' batch file calls these 'Question*Test.bat' batch files. Of course, these scripts can either be
 run by double-clicking on them or from the command prompt (e.g. Entering "Z:\4104A1>Question1Test.bat" will run Question1Test.bat).
 
 -We assume it's OK to alter the title of the command prompt window. Be aware that the scripts may add to the Path variable, but
 it should be local to the command prompt instance and it shouldn't be necessary on lab computers in HP.
 
 -----------------------------------------------
 Assumptions:
 -We assume that using java.util.random for getting random numbers is sufficient and that we don't need to keep a history
 of chosen numbers to try to guarantee randomness.
 
 -We assume that catching exceptions and handling it in Eclipse's default manner (printing out the stack trace) is sufficient
 for the guideline that states that we must have error processing and exception handling.
 
 Q 1:
 
 Q 2:
 
 Q 3:
 -We assume that since the question says we need to use a monitor object and only call synchronized methods for synchronizing,
 we can't use barriers or anything in the Agent or Eater class. We also assume this in Q5, except we assume an exception (which
 you'll find below in the Q5 section). The difference between this and Q 5 is that we wouldn't need to poll here once we return
 to the Eater thread like we do in Q5 to see if we're done our haircut.
 
 -We assume a start latch is allowed.
 
 -We assume that the passed-in runtime is the time at which the program should start gracefully exiting. That is to say when the timer
 goes off, we don't abruptly interrupt all of the threads and then exit. Because of this, we end up running a little longer than the 
 time passed-in, but we exit gracefully.
 
 -We assume printed output should be orderly (a sentence isn't interrupted half-way through with another sentence). That is only one
 thread prints out at a time.
 
 Q 4:
 
 Q 5:
 -Like Q3, this question also says that we can only use the monitor object for synchronization which would mean that, like in Q3,
 we wouldn't be able to use barriers. We thought that we could make an exception here though because otherwise we would have to 
 continuously poll the Salon in the Customer's loop to see if the Customer is still getting their hair cut which is very ugly.
 If we aren't allowed barriers here, then we would continuously check the Salon's barberChairs variable in the Customer's loop to 
 see if it contains 'this' and while it did contain 'this' we would stay in the loop (if we put a sleep in the loop then we might stay 
 in the loop too long).    
 
 -We assume a start latch is allowed.
 
 -We assume that while we can have no customers, we can't have no barbers and we can't have no waiting room chairs (otherwise how could
 the salon be open if no one's there to cut hair and there isn't enough room for a customer between the door and the barber's chair? )
 
 -We assume that the input runtime, max growing time, and max cutting time are all specified in seconds.
 
 -We assume that the passed-in runtime is the time at which the program should start gracefully exiting. That is to say when the timer
 goes off, we don't abruptly interrupt all of the threads and then exit. Because of this, we end up running a little longer than the 
 time passed-in, but we exit gracefully.
 
 -We assume printed output should be orderly (a sentence isn't interrupted half-way through with another sentence). That is only one
 thread prints out at a time.