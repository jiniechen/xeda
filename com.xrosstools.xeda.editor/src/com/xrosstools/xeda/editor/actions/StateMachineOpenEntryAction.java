package com.xrosstools.xeda.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xeda.editor.commands.ChangeExitActionCommand;
import com.xrosstools.xeda.editor.model.ActorNode;
import com.xrosstools.xeda.editor.parts.ImplementationFinder;
import com.xrosstools.xeda.editor.parts.ImplementationSource;

public class StateMachineOpenEntryAction extends WorkbenchPartAction implements XedaActionConstants, XedaMessages, ImplementationSource{
	private ActorNode node;
	private ImplementationFinder finder;
	public StateMachineOpenEntryAction(IWorkbenchPart part, ActorNode node, ImplementationFinder finder){
		super(part);
		setId(ID_PREFIX + OPEN_ENTRY_ACTION);
		setText(OPEN_ENTRY_ACTION_MSG);
		this.node = node;
		this.finder = finder;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		finder.openImpl(this);
	}

	@Override
	public String getImplementation() {
		return node.getActorClassName();
	}

	@Override
	public void implChanged(String newImpl) {
		execute(new ChangeExitActionCommand(node, newImpl));
	}
}
