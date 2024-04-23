
/**
 * Write a description of class Horse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distance;
    private boolean fallen;
    private double confidence;
    
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        symbol = horseSymbol;
        name = horseName;
        if(horseConfidence <= 1.0 && horseConfidence >= 0.0){
            this.confidence = horseConfidence;
        } else {
            this.confidence = 0.5;
        }
        distance = 0;
        fallen = false;
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        this.fallen = true;
    }

    public void resetfall()
    {
        this.fallen = false;
    }
    
    public double getConfidence()
    {
        return(this.confidence);
    }
    
    public int getDistanceTravelled()
    {
        return(this.distance);
    }
    
    public String getName()
    {
        return(this.name);
    }
    
    public char getSymbol()
    {
        return(this.symbol);
    }
    
    public void goBackToStart()
    {
        this.distance = 0;
    }
    
    public boolean hasFallen()
    {
        return(this.fallen);
    }

    public void moveForward()
    {
        this.distance++;
    }

    public void setConfidence(double newConfidence)
    {
        if(newConfidence <= 1.0 && newConfidence >= 0.0){
            this.confidence = newConfidence;
        }
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
    
}
