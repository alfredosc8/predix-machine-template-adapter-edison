/*
 * Copyright (c) 2014 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.ge.predix.solsvc.workshop.types;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import com.ge.dspmicro.machinegateway.types.PDataNode;
import com.ge.predix.solsvc.workshop.config.JsonDataNode;
import com.ge.predix.solsvc.workshop.config.TriggerNode;

import mraa.Aio;
import mraa.Gpio;
import upm_grove.GroveButton;
import upm_grove.GroveLed;
import upm_grove.GroveLight;
import upm_grove.GroveRotary;
import upm_grove.GroveTemp;

/**
 * 
 * 
 * @author Predix Machine Sample
 */
public class WorkshopDataNodeIntel extends PDataNode
{
	//private static final Logger _logger = LoggerFactory.getLogger(WorkshopDataNodeIntel.class);
	private Gpio gpioPin;

	
	private Aio aioPin;
	
	
	private String nodeType;

	private String nodePinType;
	
	private String nodePinDir;
	
	private String expression;
	
	private String nodeClass;
		
    private GroveLight groveLight;
    
    private GroveTemp groveTemp;
    
    private GroveRotary groveRotary;
    
    private GroveButton groveButton;
    
    private GroveLed groveLED;
    
	private List<TriggerNode> triggerNodes;
	
	/**
	 * @param machineAdapterId -
	 * @param node -
	 */
	public WorkshopDataNodeIntel(UUID machineAdapterId, JsonDataNode node) {
		super(machineAdapterId, node.getNodeName());
		this.nodeType = node.getNodeType();
		this.triggerNodes = node.getTriggerNodes();
		this.nodePinType = node.getNodePinType();
		this.nodePinDir = node.getNodePinDir();
		this.expression = node.getExpression();
		this.nodeClass = node.getNodeClass();	
		switch (this.nodeType) {
	        case "Light": //$NON-NLS-1$
				this.groveLight = new GroveLight(node.getNodePin());
				break;
	        case "Temperature": //$NON-NLS-1$
	        	this.groveTemp = new GroveTemp(node.getNodePin());
	        	break;
	        case "RotaryAngle": //$NON-NLS-1$
	        	this.groveRotary = new GroveRotary(node.getNodePin());
	        	break;
	        case "Button": //$NON-NLS-1$
	        	this.groveButton = new GroveButton(node.getNodePin());
	        	break;
	        case "LED" :
	        	this.setGroveLED(new GroveLed(node.getNodePin()));
			default:
				break;
		}
	}

	public Float readValue() {
		float fvalue = 0.0f;
		
		switch (this.nodeType) {
			case "Light": //$NON-NLS-1$
				fvalue = this.groveLight.value();
				break;
			case "Temperature": //$NON-NLS-1$
				fvalue = this.groveTemp.value();
				break;
			case "RotaryAngle": //$NON-NLS-1$
				float sensorValue = this.groveRotary.abs_value();
				float calculatedValue = Math.round((sensorValue) * 5 / 1023);
				DecimalFormat df = new DecimalFormat("####.##"); //$NON-NLS-1$
				fvalue = new Float(df.format(calculatedValue));
				break;
			case "Button": //$NON-NLS-1$
				int value = this.groveButton.value();
				fvalue = new Float(value);
				break;
			default:
				fvalue = getAioPin().readFloat();
				break;
		}
		//_logger.info("Node Name : "+this.getName()+" : NodeType : "+this.nodeType+" : Value : "+fvalue);
		return new Float(fvalue);
	}
	/**
	 * Node address to uniquely identify the node.
	 */
	@Override
	public URI getAddress() {
		try {
			URI address = new URI("sample.subscription.adapter", null, "localhost", -1, "/" + getName(), null, null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return address;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	/**
	 * @return -
	 */
	public Aio getAioPin() {
		return this.aioPin;
	}

	/**
	 * @param aioPin
	 *            -
	 */
	public void setAioPin(Aio aioPin) {
		this.aioPin = aioPin;
	}

	/**
	 * @return -
	 */
	public Gpio getGpioPin() {
		return this.gpioPin;
	}

	/**
	 * @param gpioPin
	 *            -
	 */
	public void setGpioPin(Gpio gpioPin) {
		this.gpioPin = gpioPin;
	}

	/**
	 * @return -
	 */
	public String getNodeType() {
		return this.nodeType;
	}

	/**
	 * @param nodeType
	 *            -
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * @return -
	 */
	public String getNodePinType() {
		return this.nodePinType;
	}

	/**
	 * @param nodePinType -
	 */
	public void setNodePinType(String nodePinType) {
		this.nodePinType = nodePinType;
	}

	/**
	 * @return -
	 */
	public List<TriggerNode> getTriggerNodes() {
		return this.triggerNodes;
	}

	/**
	 * @param triggerNodes -
	 */
	public void setTriggerNodes(List<TriggerNode> triggerNodes) {
		this.triggerNodes = triggerNodes;
	}

	/**
	 * @return -
	 */
	public String getNodePinDir() {
		return this.nodePinDir;
	}

	/**
	 * @param nodePinDir -
	 */
	public void setNodePinDir(String nodePinDir) {
		this.nodePinDir = nodePinDir;
	}


	/**
	 * @return the expression
	 */
	public String getExpression() {
		return this.expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getNodeClass() {
		return nodeClass;
	}

	public void setNodeClass(String nodeClass) {
		this.nodeClass = nodeClass;
	}

	public GroveLed getGroveLED() {
		return groveLED;
	}

	public void setGroveLED(GroveLed groveLED) {
		this.groveLED = groveLED;
	}
}
