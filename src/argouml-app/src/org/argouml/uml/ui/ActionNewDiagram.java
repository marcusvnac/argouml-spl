// $Id: ActionNewDiagram.java 132 2010-09-26 23:32:33Z marcusvnac $
// Copyright (c) 2006-2008 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.uml.ui;

import java.awt.event.ActionEvent;

import javax.swing.Action;

//#if defined(LOGGING)
//@#$LPS-LOGGING:GranularityType:Import
import org.apache.log4j.Logger;
//#endif
import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;
import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.ui.explorer.ExplorerEventAdaptor;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.diagram.ArgoDiagram;
import org.tigris.gef.undo.UndoableAction;

/**
 * Abstract action to trigger creation of a new diagram. <p>
 * 
 * ArgoUML shall never create a diagram for a read-only modelelement.
 * <p>
 * TODO: Bobs says, can we merge ActionAddDiagram with this class?
 * 
 * @author michiel
 */
public abstract class ActionNewDiagram extends UndoableAction {
    //#if defined(LOGGING)
    //@#$LPS-LOGGING:GranularityType:Field
    /**
     * Logger.
     */
    private static final Logger LOG =
        Logger.getLogger(ActionNewDiagram.class);
    //#endif
    /**
     * The constructor.
     * @param name the i18n key for this action name.
     */
    protected ActionNewDiagram(String name) {
        super(Translator.localize(name),
                ResourceLoaderWrapper.lookupIcon(name));
        // Set the tooltip string:
        putValue(Action.SHORT_DESCRIPTION, 
                Translator.localize(name));
    }

    /*
     * @see java.awt.event.ActionListener#actionPerformed(
     *      java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        // TODO: Since there may be multiple top level elements in
        // a project, this should be using the default Namespace (currently
        // undefined) or something similar 
        Project p = ProjectManager.getManager().getCurrentProject();
        Object ns = findNamespace();
        
        if (ns != null && isValidNamespace(ns)) {
            ArgoDiagram diagram = createDiagram(ns);
            assert (diagram != null)
            : "No diagram was returned by the concrete class";

            p.addMember(diagram);
            //TODO: make the explorer listen to project member property
            //changes...  to eliminate coupling on gui.
            ExplorerEventAdaptor.getInstance().modelElementAdded(
                    diagram.getNamespace());
            TargetManager.getInstance().setTarget(diagram);
        } else {
            //#if defined(LOGGING)
            //@#$LPS-LOGGING:Localization:NestedStatement
            //@#$LPS-LOGGING:GranularityType:Statement
            LOG.error("No valid namespace found");
            //#endif
            throw new IllegalStateException("No valid namespace found");
        }
    }

    /**
     * Find the right namespace for the diagram.
     *
     * @return the namespace or null
     */
    protected Object findNamespace() {
        Project p = ProjectManager.getManager().getCurrentProject();
        return p.getRoot();
    }

    /**
     * @param namespace the namespace in which to create the diagram
     * @return the new diagram
     */
    protected abstract ArgoDiagram createDiagram(Object namespace);

    /**
     * Test if the given namespace is a valid namespace to add the diagram to.
     * TODO: This method was created to facilitate the merge 
     * of this class with ActionAddDiagram.
     *
     * @param ns the namespace to check
     * @return Returns <code>true</code> if valid.
     */
    public boolean isValidNamespace(Object ns) {
        return true;
    }
    //#if defined(COLLABORATIONDIAGRAM) or defined(SEQUENCEDIAGRAM)
    //@#$LPS-COLLABORATIONDIAGRAM:GranularityType:Method
    //@#$LPS-SEQUENCEDIAGRAM:GranularityType:Method
    /**
     * Utility function to create a collaboration.
     * 
     * @return a new collaboration
     * @param namespace the back-up namespace to put the collaboration in
     */
    protected static Object createCollaboration(Object namespace) {
        Object target = TargetManager.getInstance().getModelTarget();
        if (Model.getFacade().isAUMLElement(target) 
                && Model.getModelManagementHelper().isReadOnly(target)) {
            target = namespace;
        }
        
        Object collaboration = null;
        if (Model.getFacade().isAOperation(target)) {
            Object ns = Model.getFacade().getNamespace(
                    Model.getFacade().getOwner(target));
            collaboration =
                Model.getCollaborationsFactory().buildCollaboration(ns, target);
        } else if (Model.getFacade().isAClassifier(target)) {
            Object ns = Model.getFacade().getNamespace(target);
            collaboration =
                Model.getCollaborationsFactory().buildCollaboration(ns, target);
        } else {
            collaboration =
                Model.getCollaborationsFactory().createCollaboration();
            if (Model.getFacade().isANamespace(target)) {
                /* TODO: Not all namespaces are useful here - any WFRs? */
                namespace = target;
            } else {
                if (Model.getFacade().isAModelElement(target)) {
                    Object ns = Model.getFacade().getNamespace(target);
                    if (Model.getFacade().isANamespace(ns)) {
                        namespace = ns;
                    }
                }
            }
            Model.getCoreHelper().setNamespace(collaboration, namespace);
            Model.getCoreHelper().setName(collaboration, 
                    "unattachedCollaboration");
        }
        return collaboration;
    }
    //#endif
}
