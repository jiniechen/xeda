package com.xrosstools.xeda.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DepartmentNode implements XedaConstants, IPropertySource {
	private String id;
	private String description;

	private List<BaseNode> nodes = new ArrayList<BaseNode>();
	private List<MessageType> events = new ArrayList<MessageType>();
	private XedaHelper helper = new XedaHelper(this);
	
	private Rectangle constrain;
	private Dimension size;
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	protected void firePropertyChange(String propertyName){
		listeners.firePropertyChange(propertyName, null, null);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(PROP_ID, PROP_ID),
				new TextPropertyDescriptor(PROP_DESRIPTION, PROP_DESRIPTION),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_ID.equals(propName))
			return getValue(id);
		if (PROP_DESRIPTION.equals(propName))
			return getValue(description);
		
		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_ID.equals(propName))
			setId((String)value);
		if (PROP_DESRIPTION.equals(propName))
			setDescription((String)value);
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}

	public void resetPropertyValue(Object propName){
	}
		
	private String getValue(String value) {
		return value == null? "" : value;
	}
	
	public void validate(List<String> errorMessages) {
	    if(id == null || id.trim().length() == 0) {
	        errorMessages.add("Department Id is empty");
	    }
	                
        Set<String> ids = new HashSet<>();
	    for(BaseNode node: nodes) {
            if(ids.contains(node.getId())) {
                errorMessages.add(String.format("Duplicate node id in department %s is detected: %s", id, node.getId()));
            }
            
            node.validate(errorMessages);
        }
	}

	public void removeNode(BaseNode node){
		nodes.remove(node);
		firePropertyChange(STATE_NODE);
	}

	public void addNode(BaseNode node){
		nodes.add(node);
		node.setDepartmentId(id);
		firePropertyChange(STATE_NODE);
	}

	public void setConstrain(Rectangle constrain) {
		this.constrain = constrain;
		listeners.firePropertyChange(PROP_LOCATION, null, constrain);
	}

	public Rectangle getConstrain() {
		return constrain;
	}
	
	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
		listeners.firePropertyChange(PROP_LOCATION, null, constrain);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		
		for(BaseNode n: nodes) 
		    n.setDepartmentId(id);
		
		firePropertyChange(PROP_ID);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		firePropertyChange(PROP_DESRIPTION);
	}

	public List<BaseNode> getNodes() {
		return nodes;
	}

    public BaseNode getNodeById(String id) {
        for(BaseNode n: nodes)
            if(id.equals(n.getId()))
                return n;

        return null;
    }

	public void setNodes(List<BaseNode> newNodes) {
		this.nodes.clear();
		
		for(BaseNode n: newNodes) 
		    addNode(n);
		
		firePropertyChange(STATE_NODE);
	}
	
	public List<MessageType> getEvents() {
		return events;
	}
	
	public XedaHelper getHelper() {
		return helper;
	}
}
