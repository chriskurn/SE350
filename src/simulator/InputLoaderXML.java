package simulator;
/*
Description:    Object-Oriented Software Development
                Quarter Programming Project
Authors:        Chris Kurn and Patrick Stein
Class:          SE-350
Date:           Spring Quarter 2014
*/
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class InputLoaderXML implements InputLoader {
    private String fileName;
    private File xmlFile;
    private Document xmlDoc;
    
    private int numElevators;
    private int numFloors;
    
    private final int MIN_ELEVATORS = 1;
    private final int MIN_FLOORS = 1;

    /**
     * A basic constructor that takes a xml file name with its full path.
     * @param fn A String containing the full path and name of an xml file.
     */
    public InputLoaderXML(String fn) {
        // TODO Auto-generated constructor stub
        this.fileName = fn;
    }

    @Override
    public void loadInput() throws Exception{
        /* This method will load the input contained within the filename provided at construction time.
         * TODO Create new exception to be more general. Use of general exception probably bad!
         */
        //Create new file object
        File myFile = new File(this.getFileName());
        //Create parsing objects
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(myFile);
        
        //doc.getDocumentElement().normalize();
        //Get a list of the simulation input elements
        NodeList nList = doc.getElementsByTagName("input");
        //Get the first node of the input
        Node firstNode =  nList.item(1);
        
        //Only do if it is of type ELEMENT_NODE
        // TODO null pointer error. Must figure out why!
        if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
            
            Element element = (Element) firstNode;
            //Get the number of floors and elevators
            this.setNumElevators(element.getAttribute("numelevators"));
            this.setNumFloors(element.getAttribute("numfloors"));
        }
       
        
    }

    @Override
    public int getNumElevators() {
        // TODO Auto-generated method stub
        return this.numElevators;
    }

    @Override
    public int getNumFloors() {
        // TODO Auto-generated method stub
        return this.numFloors;
    }
    /**
     * Private method for setting the number of elevators.
     * @param numE takes a string that corresponds to a number that must be greater than 0.
     */
    private void setNumElevators(String numE){
        int n = Integer.parseInt(numE);
        if(n >= this.MIN_ELEVATORS){
            this.numElevators = n;
        }else {
            //throw exception
        }
    }
    /**
     * Private method for setting the number of elevators.
     * @param numF takes a string that corresponds to a number that must be greater than 0.
     */
    private void setNumFloors(String numF){
        int n = Integer.parseInt(numF);
        if(n >= this.MIN_FLOORS){
            this.numFloors = n;
        }else {
            //throw exception
        }
        
    }
    /**
     * Private method for returning the xml file name.
     * @return returns a string containing the xml file
     */
    private String getFileName(){
        return this.fileName;
    }

}
