package com.xrosstools.xeda.editor.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.xrosstools.xeda.editor.model.BaseNode;
import com.xrosstools.xeda.editor.model.DepartmentNode;

public class AddNodeCommand extends Command {
	private DepartmentNode parent;
	private BaseNode node;
	private DepartmentNode oldParent;
	private Point oldPosition;
	private Point newPosition;
	
	public AddNodeCommand(DepartmentNode parent, BaseNode node, DepartmentNode oldParent, Point newPosition){
		this.parent = parent;
		this.node = node;
		this.oldParent = oldParent;
		oldPosition = new Point(node.getLocation());
		this.newPosition = newPosition;
	}
	
	public void execute() {
		oldParent.removeNode(node);
		parent.addNode(node);
		node.setLocation(newPosition);
	}
	
    public String getLabel() {
        return "Add node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	parent.removeNode(node);
    	oldParent.addNode(node);
		node.setLocation(oldPosition);
	}
}
