import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.lang.Math; 

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse[] horses;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, int numLanes)
    {
        // initialise instance variables
        raceLength = distance;
        horses = new Horse[numLanes];
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {

        if (laneNumber > 0 && laneNumber <= horses.length)
        {
            horses[laneNumber - 1] = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {

        Scanner scanner = new Scanner(System.in);

        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        boolean keepgoing = true;

        while(keepgoing){
        
            //reset all the lanes (all horses not fallen and back to 0). 
            for(Horse horse : horses)
            {
                if(horse != null)
                {
                    horse.goBackToStart();
                }
            }
                        
            while (!finished)
            {
                //move each horse
                for(Horse horse : horses)
                {
                    if(horse != null)
                    {
                        moveHorse(horse);
                    }
                }
                            
                //print the race positions
                printRace();
                
                //if any of the three horses has won the race is finished

                int numHorses = 0;

                for(Horse horse : horses)
                {
                    if(horse != null)
                    {
                        numHorses++;
                    }
                }

                int fallen = 0;

                for(Horse horse : horses)
                {
                    if(horse != null)
                    {
                        if (horse.hasFallen())
                        {
                            fallen++;
                        }
                    }
                }

                if(fallen == numHorses)
                {
                    finished = true;
                    System.out.println("All horses have fallen!");
                }

                Horse[] winners = {};

                for(Horse horse : horses)
                {
                    if(horse != null)
                    {
                        if (raceWonBy(horse))
                        {
                            finished = true;
                            int index = winners.length;
                            winners = Arrays.copyOf(winners, winners.length + 1);
                            winners[index] = horse;
                        }
                    }
                }

                if(winners.length > 0)
                {

                    System.out.println((winners.length > 1) ? "The winners are:" : "The winner is:");

                    for(Horse horse : winners)
                    {
                        System.out.println(horse.getName());
                    }

                }

                //wait for 100 milliseconds
                try{ 
                    TimeUnit.MILLISECONDS.sleep(100);
                }catch(Exception e){}
            }

            System.out.println("Race again? Y/N");
            String input = scanner.nextLine();

            while(!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"))
            {
                System.out.println("Invalid input. Please enter Y or N.");
                input = scanner.nextLine();
            }

            if(input.equalsIgnoreCase("N"))
            {
                keepgoing = false;
            }
            else{

                finished = false;

                for(Horse horse : horses)
                {
                    if(horse != null)
                    {
                        horse.resetfall();
                    }
                }

            }

        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
                theHorse.setConfidence(theHorse.getConfidence() - 0.1); 
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            if(!theHorse.hasFallen())
            {
                theHorse.setConfidence(theHorse.getConfidence() + 0.1);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        //System.out.print('\u000C');  //clear the terminal window

        System.out.print("\033[H\033[2J"); //EDIT TO CLEAR CONSOLE ------------------------------------------------------------------------
        System.out.flush();
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        for(Horse horse : horses)
        {
            if(horse != null)
            {
                printLane(horse);
                System.out.println();
            }
            else
            {
                System.out.print('|');
                multiplePrint(' ',raceLength+1);
                System.out.print('|');
                System.out.println("  Empty Lane");
            }
        }
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u274C'); //EDIT TO MAKE CROSS ------------------------------------------------------------------------
            spacesAfter = spacesAfter - 1;
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');

        //print the horse's name and confidence
        System.out.print("  " + theHorse.getName() + " (Current Confidence: " + theHorse.getConfidence() + ")"); //EDIT TO ADD NAME AND CONFIDENCE ------------------------------------------------------------------------
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

}
