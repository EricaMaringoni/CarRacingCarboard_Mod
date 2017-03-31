package it.unibg.p3d4amb.carracingcarboard.Manager;

/**
 * Created by matteo on 22/01/2015.
 */
public class EnemiesManager implements EnemiesManagerInterface{

    private final int lane=3;  //we have 3 lane
    private final int numberOfCar=4; //we have 10 different picture of car

    private int selectedLane; //what's the selected lane
    private int selectedCar; //what's the enemy

    private int previousLane=0;// use this to manipulate the random algorithm
    private int countPrevious=0;// use this to manipulate the random algorithm

    /**
     * chooses lane and enemy
     */
    public void randomFunction(){
        selectedLane=(int)(lane*Math.random())+1;
        if(previousLane==selectedLane){
            countPrevious++;
            if(countPrevious>3){
                selectedLane++;
                if(selectedLane>3){
                    selectedLane=1;
                }
            }
        }
        previousLane=selectedLane;

        selectedCar=(int)(numberOfCar*Math.random())+1;

    }
    public void setLane(){
        selectedLane=2; //at first the car is on the center lane
    }

    /**
     *
     * @return the selected lane
     */
    public int getSelectedLane(){
        return selectedLane;
    }

    /**
     *
     * @return the selected enemies
     */
    public int getNumberOfCar(){
        return  selectedCar;
    }

    /* FOR TEST ONLY
    public static void main(String [ ] args)
    {
        EnemiesManager e= new EnemiesManager();
        e.randomFunction();
        System.out.println("corsia "+ e.getSelectedLane());
        System.out.println("nemico "+ e.getNumberOfCar());
        e.randomFunction();
        System.out.println("corsia "+ e.getSelectedLane());
        System.out.println("nemico "+ e.getNumberOfCar());
        e.randomFunction();
        System.out.println("corsia "+ e.getSelectedLane());
        System.out.println("nemico "+ e.getNumberOfCar());
        e.randomFunction();
        System.out.println("corsia "+ e.getSelectedLane());
        System.out.println("nemico "+ e.getNumberOfCar());
        e.randomFunction();
        System.out.println("corsia "+ e.getSelectedLane());
        System.out.println("nemico "+ e.getNumberOfCar());
        e.randomFunction();
        System.out.println("corsia "+ e.getSelectedLane());
        System.out.println("nemico "+ e.getNumberOfCar());

    }*/

}

