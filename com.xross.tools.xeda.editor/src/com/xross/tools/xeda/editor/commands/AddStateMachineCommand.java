package com.xross.tools.xeda.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xeda.editor.model.DepartmentNode;
import com.xross.tools.xeda.editor.model.XedaDiagram;

public class AddStateMachineCommand extends Command {
	private XedaDiagram parent;
	private DepartmentNode amchine;
	private int oldIndex;
	private int index;
	
	public AddStateMachineCommand(XedaDiagram parent, DepartmentNode unit, int index){
		this.parent = parent;
		this.amchine = unit;
		oldIndex = parent.indexOf(unit);
		this.index = index;
	}
	
	public void execute() {
		parent.move(index, amchine);
	}

    public String getLabel() {
        return "Move Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	parent.move(oldIndex, amchine);
    }
}