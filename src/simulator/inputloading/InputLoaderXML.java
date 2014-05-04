package simulator.inputloading;

/**
 * Description: InputLoaderXML
 * @author Chris Kurn, Patrick Stein
 * @since Version 1.0 - Spring Quarter 2014
 * @see simulator.inputloading
 */

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import simulator.common.FileReadException;
import simulator.common.IllegalParamException;
import simulator.common.NullFileException;

public class InputLoaderXML implements InputLoader {
    private String fileName;
    private File xmlFile;
    private Document xmlDoc;
    
    private int numElevators;
    private int numFloors;
    private int numExpressElevators;
    private int numPersonsPerElevators;
    private int floorTime;
    private int doorTime;
    private int elevatorSleepTime;
    
    
    private final int MIN_ELEVATORS = 1;
    private final int MIN_FLOORS = 1;

    /**
     * A basic constructor that takes a xml file name with its full path.
     * @param fn A String containing the full path and name of an xml file.
     * @throws NullFileNameException 
     */
    public InputLoaderXML(String fn) throws NullFileException {
        this.setFileName(fn);
        this.createNewFile();
    }

    @Override
    public void loadInput() throws IllegalParamException, FileReadException, NullFileException{
        // This method will load the input contained within the filename provided at construction time.
        
        //Create parsing objects
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        //Make null members
        DocumentBuilder dBuilder = null;
        Document doc = null;
        try {
             dBuilder = dbFactory.newDocumentBuilder();
             doc = dBuilder.parse(this.getXmlFile());
        }catch(Exception e){
            //Unable to handle this file. Throwing new custom error.
            // TODO Review all relevant exceptions and determine unique behavior
            throw new FileReadException(String.format("Unable to process and parse the file:" +
            		" %s. Review file and try again.",this.getFileName()));
            
        }
        
        doc.getDocumentElement().normalize();
        //Set document to member
        this.setXmlDoc(doc);
    }
    
    @Override
    public void readInput() throws NullFileException, IllegalParamException{
        
        Document doc = this.getXmlDoc();
        NodeList nList = null;
        if(doc != null)
            //Get a list of the simulation input elements
            nList = doc.getElementsByTagName("input");
            //Get the first node of the input
            Node firstNode =  nList.item(1);
            
            //Only do if it is of type ELEMENT_NODE
            // TODO null pointer error. Must figure out why!
            if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
                
                Element element = (Element) firstNode;
                //Get the number of floors and elevators
                // TODO Maybe I should catch this illegal param exception and throw a different one?
                this.setNumElevators(element.getAttribute("numElevators"));
                this.setNumFloors(element.getAttribute("numFloors"));
                this.setNumExpressElevators(element.getAttribute("numExpressElevators"));
                this.setNumPersonsPerElevators(element.getAttribute("numPersonsPerElevators"));
                this.setFloorTime(element.getAttribute("floorTime"));
                this.setDoorTime(element.getAttribute("doorTime"));
                this.setElevatorSleepTime(element.getAttribute("elevatorSleepTime"));
        }else {
            throw new NullFileException("The XML document member you are trying to read from is currently null.");
        }
    }
    /**
     * Creates a new file object based on the filename member.
     * @throws NullFileNameException The filename belonging to this member cannot be null
     */
    private void createNewFile() throws NullFileException{
        //Get the file name
        String fn = this.getFileName();
        File newFile = null;
        //Make sure there is a valid filename
        //If null, then throw a new exception
        if(fn != null){
            newFile = new File(this.getFileName());
        } else{
            throw new NullFileException("The filename cannot be null.");
        }
        this.setXmlFile(newFile);
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
    
    @Override
    public int numExpressElevators() {
        // TODO Auto-generated method stub
        return this.numExpressElevators;
    }

    @Override
    public int numPersonsPerElevators() {
        // TODO Auto-generated method stub
        return this.numPersonsPerElevators;
    }
    
    
    @Override
    public int floorTime() {
        // TODO Auto-generated method stub
        return this.floorTime;
    }

    @Override
    public int doorTime() {
        // TODO Auto-generated method stub
        return this.doorTime;
    }
    
    @Override
    public int elevatorSleepTime() {
        // TODO Auto-generated method stub
        return this.elevatorSleepTime;
    }    
    
    
    
    /**
     * Private method for setting the number of elevators.
     * @param numE takes a string that corresponds to a number that must be greater than 0.
     * @throws IllegalParamException The number of elevators must be greater or equal to 1.
     */
    private void setNumElevators(String numE) throws IllegalParamException{
        int n = Integer.parseInt(numE);
        if(n >= this.MIN_ELEVATORS){
            this.numElevators = n;
        }else {
            throw new IllegalParamException(String.format("Invalid value in file: %s " +
            		"Number of elevators must be greater or equal to 1.",this.getFileName()));
        }
    }
    /**
     * Private method for setting the number of elevators.
     * @param numF takes a string that corresponds to a number that must be greater than 0.
     * @throws IllegalParamException The number of floors must be greater or equal to 1.
     */
    private void setNumFloors(String numF) throws IllegalParamException{
        int n = Integer.parseInt(numF);
        if(n >= this.MIN_FLOORS){
            this.numFloors = n;
        }else {
            throw new IllegalParamException(String.format("Invalid value in file: %s " +
                    "Number of floors must be greater or equal to 1.",this.getFileName()));
        }
        
    }
    /**
     * Private set method for the xml file name.
     * @param fn Takes a string that must correspond to an xml file. It also cannot be null.
     * @throws NullFileNameException The file name must not be null
     */
    private void setFileName(String fn) throws NullFileException{
        //Make sure the file name exists
        if(fn != null){
            this.fileName = fn;
        }else{
            throw new NullFileException("The filename cannot be null.");
        }
    }
    /**
     * Simple private get method for acquiring the xmlFile object.
     * @return returns the xmlFile object
     */
    private File getXmlFile(){
        return this.xmlFile;
    }
    /**
     * Private set method for setting the xmlFile member.
     * @param xmlF Give it a non-null File object corresponding to the filename
     * @throws NullFileException The parameter xmlF must not be null.
     */
    private void setXmlFile(File xmlF) throws NullFileException {
        if(xmlF != null){
            this.xmlFile = xmlF;
        }else{
            throw new NullFileException("Cannot set the file member to a null reference.");
        }
    }
    /**
     * Private set method for xmlDoc member.
     * @param doc Must be a document object corresponding the xmlFile member.
     * @throws NullFileException The new document cannot be null.
     */
    private void setXmlDoc(Document doc) throws NullFileException{
        if(doc != null){
            this.xmlDoc = doc;
        }else{
            throw new NullFileException("Cannot set the document member to a null reference.");
        }
    }
    /**
     * private get method for the xmlDoc member.
     * @return Returns the reference to the xmlDoc member.
     */
    private Document getXmlDoc(){
        return this.xmlDoc;
    }
    
    /**
     * Private method for returning the xml file name.
     * @return returns a string containing the xml file
     */
    private String getFileName(){
        return this.fileName;
    }
}
