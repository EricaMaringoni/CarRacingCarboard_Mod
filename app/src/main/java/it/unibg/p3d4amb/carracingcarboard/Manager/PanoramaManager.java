package it.unibg.p3d4amb.carracingcarboard.Manager;

import it.unibg.p3d4amb.carracingcarboard.Manager.Enum.Side;

import java.util.Random;

/**
 * Created by matteo on 23/01/2015.
 */
public class PanoramaManager implements PanoramaManagerInterface
{


    private Side selectedSide;
    private final int numberOfSubject=3;
    private int idSubject;


    /**
     * chooses the side and what subject send(never like the previous)
     */
    public void randomPanorama() {

        int pick = new Random().nextInt(Side.values().length);
        selectedSide= Side.values()[pick];
        idSubject= (int)(numberOfSubject*Math.random())+1;

    }


    /**
     *
     * @return the selected side (LEFT or RIGHT)
     */
    public Side getSelectedSide(){
        return selectedSide;
    }

    /**
     *
     * @return the selected subject for our panorama view
     */
    public int getIdSubject(){
        return idSubject;
    }


  /*FOR TEST ONLY  public static void main(String [ ] args)
    {

        PanoramaManager p = new PanoramaManager();
        p.randomPanorama();
        System.out.println("lato: "+ p.getSelectedSide());
        System.out.println("id: "+ p.getIdSubject());
        p.randomPanorama();
        System.out.println("lato: "+ p.getSelectedSide());
        System.out.println("id: "+ p.getIdSubject());
        p.randomPanorama();
        System.out.println("lato: "+ p.getSelectedSide());
        System.out.println("id: "+ p.getIdSubject());
        p.randomPanorama();
        System.out.println("lato: "+ p.getSelectedSide());
        System.out.println("id: "+ p.getIdSubject());
        p.randomPanorama();
        System.out.println("lato: "+ p.getSelectedSide());
        System.out.println("id: "+ p.getIdSubject());
        p.randomPanorama();
        System.out.println("lato: "+ p.getSelectedSide());
        System.out.println("id: "+ p.getIdSubject());
        p.randomPanorama();
        System.out.println("lato: "+ p.getSelectedSide());
        System.out.println("id: "+ p.getIdSubject());
    }*/

}
