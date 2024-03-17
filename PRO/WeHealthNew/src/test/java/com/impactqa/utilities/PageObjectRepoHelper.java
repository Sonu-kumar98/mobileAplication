package com.impactqa.utilities;

import io.appium.java_client.MobileBy;
import org.apache.commons.io.FileUtils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.Map;
import java.util.Set;


/**
 * @author Sonu Kumar
 * @version 1.0
 * @since   22020-11-17
 * @description This class is Util class to read and resolve the page objects from page object repository files
 * repository files should be maintained in the location src/test/resources/PageReporties
 */
public class PageObjectRepoHelper extends CommonUtil{

    private String pageObjectRepoFilePath;
    private PLATFORM platform;
    private Document doc;
    public enum PLATFORM {
        IOS,
        ANDROID,
        WEB
    }

    private static final Logger LOGGER = LogManager.getLogger(PageObjectRepoHelper.class.getName());

    public PageObjectRepoHelper(String pageObjectRepoFileName, PLATFORM platform)
    {
        this.platform = platform;
        if(platform == PLATFORM.WEB)
            pageObjectRepoFilePath = System.getProperty("user.dir")
                                        +"/"+ FrameworkConfig.getStringEnvProperty("PageObjectRepoDirectoryPathWeb")
                                                +"/"+pageObjectRepoFileName;
        else
            pageObjectRepoFilePath = System.getProperty("user.dir")
                    +"/"+ FrameworkConfig.getStringEnvProperty("PageObjectRepoDirectoryPathMobile")
                    +"/"+pageObjectRepoFileName;

        try{
            this.doc = parseXML();
        }
        catch (Exception e) {
            Assert.fail("error occured while reading the Page Object with path: "+pageObjectRepoFilePath, e);
        }
    }



    private Document parseXML() throws Exception
    {
        DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(pageObjectRepoFilePath);
        doc.getDocumentElement().normalize();
        return doc;
    }

    /**
     * return By locator object for given element object name
     * @param name
     * @return  By locator object
     */
    public By getObject(String name) {
        return getByLocatorFromString(getObjectLocatorAsString(name));
    }

    /**
     * return By locator object for given element object name
     * This methods is used when dynamic locator is required ( like //*[text()='$replace$'] )
     * before constructing the BY object, it will replace the replaceKeys with replaceValues ( provided in the replaceKepValMap )
     * @param fieldName
     * @param replaceKepValMap
     * @return  By locator object
     */
    public By getObject(String fieldName, Map<String,String> replaceKepValMap)
    {
        String finalStrObj = getObjectLocatorAsString(fieldName);

        Set<String> keyset = replaceKepValMap.keySet();
        for (String replaceKey: keyset)
            finalStrObj = finalStrObj.replace(replaceKey, replaceKepValMap.get(replaceKey));

        return getByLocatorFromString(finalStrObj);
    }

    /**
     * return By locator object resolved from string property value
     * @param locatorStr
     * @return  By locator object
     */
    private static By getByLocatorFromString(String locatorStr) {
        int indexOfDelimit = locatorStr.indexOf(':');
        By by = null;
        if (indexOfDelimit > -1) {
            String locatorType = locatorStr.substring(0, locatorStr.indexOf(':'));
            String locatorValue = locatorStr.substring((locatorStr.indexOf(':') + 1), locatorStr.length());
            switch (locatorType.toLowerCase()) {
                case "xpath":
                    by = By.xpath(locatorValue);
                    break;
                case "id":
                    by = By.id(locatorValue);
                    break;
                case "name":
                    by = By.name(locatorValue);
                    break;
                case "classname":
                    by = By.className(locatorValue);
                    break;
                case "css":
                    by = By.cssSelector(locatorValue);
                    break;
                case "linktext":
                    by = By.linkText(locatorValue);
                    break;
                case "partiallinktext":
                    by = By.partialLinkText(locatorValue);
                    break;
                case "tagname":
                    by = By.tagName(locatorValue);
                    break;
                default:
                    break;
            }
        } else
            Assert.fail("Improper locator String - " + locatorStr);
        return by;
    }

    private String getObjectLocatorAsString(String objectName){
        String locatorType = null,locatorValue=null;
        try {
            XPath xPath =  XPathFactory.newInstance().newXPath();
            String expression = "/objects/object[@name='"+objectName+"']";
            NodeList nList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            Node nodeElement = null;
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String nodeType = getNodeType(nNode);
                nodeElement = eElement.getElementsByTagName(nodeType).item(0);
                if(nodeElement.getAttributes().getNamedItem("locatorType")==null || nodeElement.getAttributes().getNamedItem("locatorValue")==null )
                {
                    LOGGER.log(Level.ERROR, "locatorType and/or locatorValue are missing for '"+nodeType+"' tag");
                    throw new RuntimeException("locatorType and/or locatorValue are missing for '"+nodeType+"' tag");
                }
                locatorType = nodeElement.getAttributes().getNamedItem("locatorType").getNodeValue().toString();
                locatorValue = nodeElement.getAttributes().getNamedItem("locatorValue").getNodeValue().toString();

            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error occured while getting object "+objectName+". RepoFile: "+pageObjectRepoFilePath+". "+e);
            throw new RuntimeException("Error occured while getting object "+objectName+". RepoFile: "+pageObjectRepoFilePath+". "+e);
        }
        return locatorType+":"+locatorValue;
    }

    private String getNodeType(Node node) throws Exception {
        String nodeType = null;
        if(platform == PLATFORM.IOS && getChildNode(node, "ios")!=null)
            nodeType = "ios";
        else
        if(platform == PLATFORM.ANDROID && getChildNode(node, "android")!=null)
            nodeType = "android";
        else
        if(platform == PLATFORM.WEB && getChildNode(node, "web")!=null)
            nodeType = "web";
        else
        {
            LOGGER.error("Please provide atleast one property detail. IOS, Andriod or web  for object: "+node.getAttributes().getNamedItem("name"));
            throw new Exception("Please provide atleast one property detail. IOS, Andriod or web  for object: "+node.getAttributes().getNamedItem("name"));
        }

        return nodeType;
    }


    private Node getChildNode(Node node, String childName) throws Exception {
        Node child =null;

        if(((Element) node).getElementsByTagName(childName).getLength()>0) {
            child= ((Element) node).getElementsByTagName(childName).item(0);
        }else{
            LOGGER.error("child tag '"+childName+"' is missing for object: "+node.getAttributes().getNamedItem("name"));
            throw new Exception("child tag '"+childName+"' is missing for object: "+node.getAttributes().getNamedItem("name"));
        }
        return child;
    }

    public static void main(String[] args) {
//        PageObjectRepoHelper p = new PageObjectRepoHelper("_SampleMobile.xml", PLATFORM.ANDROID);
//      By by = p.getObject("playStorePopup", Map.of("as","as"));
//       System.out.println(by);
    }
}
