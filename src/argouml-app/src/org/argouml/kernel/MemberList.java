// $Id: MemberList.java 150 2014-06-14 16:07:47Z benjamin_klatt $
// Copyright (c) 2004-2008 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
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

package org.argouml.kernel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//#if defined(LOGGING)
//@#$LPS-LOGGING:GranularityType:Import
import org.apache.log4j.Logger;
//#endif
import org.argouml.uml.ProjectMemberModel;
//#if defined(COGNITIVE)
//@#$LPS-COGNITIVE:GranularityType:Import
import org.argouml.uml.cognitive.ProjectMemberTodoList;
//#endif
import org.argouml.uml.diagram.ArgoDiagram;
import org.argouml.uml.diagram.ProjectMemberDiagram;

/**
 * List of ProjectMembers. <p>
 *
 * <p>The project members are grouped into 4 categories:
 * model, diagrams, the todo item list and the profile configuration. <p>
 *
 * <p>The purpose of these categories is to make sure that members are read
 * and written in the correct order.
 *
 * <p>When reading the todo items it will fail if the diagrams elements or model
 * elements have not yet been read that they refer to. When reading diagrams
 * that will fail if the model elements don't yet exist that they refer to.
 * When loading the model that may fail if the correct profile has not been
 * loaded.
 *
 * <p>Hence, the save (and therefore load) order is profile, model, diagrams,
 * todo items.
 *
 * <p>This implementation supports only one profile configuration, one model
 * member, multiple diagram members, one todo list member.
 *
 * <p>Comments by mvw: <p>
 * This class should be reworked to be independent
 * of the org.argouml.uml package. That can be done by extending the
 * ProjectMember interface with functions returning the sorting order,
 * and if multiple entries of the same type are allowed. <p>
 *
 * In preparation, this class is made simpler by deprecating
 * all operations that are not part of the List interface.
 *
 * @author Bob Tarling
 */
class MemberList implements List<ProjectMember> {
    //#if defined(LOGGING)
    //@#$LPS-LOGGING:GranularityType:Field
    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(MemberList.class);
    //#endif
    private AbstractProjectMember model;

    private List<ProjectMemberDiagram> diagramMembers =
        new ArrayList<ProjectMemberDiagram>(10);

    //#if defined(COGNITIVE)
    //@#$LPS-COGNITIVE:GranularityType:Field
    private AbstractProjectMember todoList;
    //#endif
    private AbstractProjectMember profileConfiguration;

    /**
     * The constructor.
     */
    public MemberList() {
        //#if defined(LOGGING)
        //@#$LPS-LOGGING:GranularityType:MethodBody
        //@#$LPS-LOGGING:Localization:EntireMethod
        LOG.info("Creating a member list");
        //#endif
    }

    public synchronized boolean add(ProjectMember member) {

        if (member instanceof ProjectMemberModel) {
            // Always put the model at the top
            model = (AbstractProjectMember) member;
            return true;
        }
        //#if defined(COGNITIVE)
        //@#$LPS-COGNITIVE:GranularityType:Statement
        else if (member instanceof ProjectMemberTodoList) {
            // otherwise add the diagram at the start
            setTodoList((AbstractProjectMember) member);
            return true;
        }
        //#endif
        else if (member instanceof ProfileConfiguration) {
            profileConfiguration = (AbstractProjectMember) member;
            return true;
        } else if (member instanceof ProjectMemberDiagram) {
            // otherwise add the diagram at the start
            return diagramMembers.add((ProjectMemberDiagram) member);
        }
        return false;
    }

    public synchronized boolean remove(Object member) {
        //#if defined(LOGGING)
        //@#$LPS-LOGGING:GranularityType:Statement
        //@#$LPS-LOGGING:Localization:StartMethod
        LOG.info("Removing a member");
        //#endif
        if (member instanceof ArgoDiagram) {
            return removeDiagram((ArgoDiagram) member);
        }
        ((AbstractProjectMember) member).remove();
        if (model == member) {
            model = null;
            return true;
        }
        //#if defined(COGNITIVE)
        //@#$LPS-COGNITIVE:GranularityType:Statement
        else if (todoList == member) {
            //#if defined(LOGGING)
            //@#$LPS-LOGGING:GranularityType:Statement
            //@#$LPS-LOGGING:Localization:NestedStatement
            LOG.info("Removing todo list");
            //#endif
            setTodoList(null);
            return true;
        }
        //#endif
        else if (profileConfiguration == member) {
            //#if defined(LOGGING)
            //@#$LPS-LOGGING:GranularityType:Statement
            //@#$LPS-LOGGING:Localization:NestedStatement
            LOG.info("Removing profile configuration");
            //#endif
            profileConfiguration = null;
            return true;
        } else {
            final boolean removed = diagramMembers.remove(member);
            //#if defined(LOGGING)
            //@#$LPS-LOGGING:GranularityType:Statement
            //@#$LPS-LOGGING:Localization:NestedStatement
            //@#$LPS-LOGGING:Localization:BeforeReturn
            if (!removed) {
                LOG.warn("Failed to remove diagram member " + member);
            }
            //#endif
            return removed;
        }
    }

    public synchronized Iterator<ProjectMember> iterator() {
        return buildOrderedMemberList().iterator();
    }

    public synchronized ListIterator<ProjectMember> listIterator() {
        return buildOrderedMemberList().listIterator();
    }

    public synchronized ListIterator<ProjectMember> listIterator(int arg0) {
        return buildOrderedMemberList().listIterator(arg0);
    }

    /**
     * @return the list of members in the order that they need to be written
     *         out in.
     */
    private List<ProjectMember> buildOrderedMemberList() {
        List<ProjectMember> temp =
            new ArrayList<ProjectMember>(size());
        if (profileConfiguration != null) {
            temp.add(profileConfiguration);
        }
        if (model != null) {
            temp.add(model);
        }
        temp.addAll(diagramMembers);
        //#if defined(COGNITIVE)
        //@#$LPS-COGNITIVE:GranularityType:Statement
        if (todoList != null) {
            temp.add(todoList);
        }
        //#endif
        return temp;
    }

    private boolean removeDiagram(ArgoDiagram d) {
        for (ProjectMemberDiagram pmd : diagramMembers) {
            if (pmd.getDiagram() == d) {
                pmd.remove();
                diagramMembers.remove(pmd);
                return true;
            }
        }
        //#if defined(LOGGING)
        //@#$LPS-LOGGING:GranularityType:Statement
        //@#$LPS-LOGGING:Localization:BeforeReturn
        LOG.debug("Failed to remove diagram " + d);
        //#endif
        return false;
    }

    public synchronized int size() {
        int size = diagramMembers.size();
        if (model != null) {
            ++size;
        }
        //#if defined(COGNITIVE)
        //@#$LPS-COGNITIVE:GranularityType:Statement
        if (todoList != null) {
            ++size;
        }
        //#endif
        if (profileConfiguration != null) {
            ++size;
        }
        return size;
    }

    public synchronized boolean contains(Object member) {
        //#if defined(COGNITIVE)
        //@#$LPS-COGNITIVE:GranularityType:Statement
        if (todoList == member) {
            return true;
        }
        //#endif
        if (model == member) {
            return true;
        }
        if (profileConfiguration == member) {
            return true;
        }
        return diagramMembers.contains(member);
    }

    public synchronized void clear() {
        //#if defined(LOGGING)
        //@#$LPS-LOGGING:GranularityType:Statement
        //@#$LPS-LOGGING:Localization:StartMethod
        LOG.info("Clearing members");
        //#endif
        if (model != null) {
            model.remove();
        }
        //#if defined(COGNITIVE)
        //@#$LPS-COGNITIVE:GranularityType:Statement
        if (todoList != null) {
            todoList.remove();
        }
        //#endif
        if (profileConfiguration != null) {
            profileConfiguration.remove();
        }
        Iterator membersIt = diagramMembers.iterator();
        while (membersIt.hasNext()) {
            ((AbstractProjectMember) membersIt.next()).remove();
        }
        diagramMembers.clear();
    }

    public synchronized ProjectMember get(int i) {
        if (model != null) {
            if (i == 0) {
                return model;
            }
            --i;
        }

        if (i == diagramMembers.size()) {
            //#if defined(COGNITIVE)
            //@#$LPS-COGNITIVE:GranularityType:Statement
            if (todoList != null) {
                return todoList;
            } else {
            //#endif
                return profileConfiguration;
            //#if defined(COGNITIVE)
            //@#$LPS-COGNITIVE:GranularityType:Statement
            }
            //#endif
        }

        if (i == (diagramMembers.size() + 1)) {
            return profileConfiguration;
        }

        return diagramMembers.get(i);
    }

    public synchronized boolean isEmpty() {
        return size() == 0;
    }

    public synchronized ProjectMember[] toArray() {
        ProjectMember[] temp = new ProjectMember[size()];
        int pos = 0;
        if (model != null) {
            temp[pos++] = model;
        }
        for (ProjectMemberDiagram d : diagramMembers) {
            temp[pos++] = d;
        }
        //#if defined(COGNITIVE)
        //@#$LPS-COGNITIVE:GranularityType:Statement
        if (todoList != null) {
            temp[pos++] = todoList;
        }
        //#endif
        if (profileConfiguration != null) {
            temp[pos++] = profileConfiguration;
        }
        return temp;
    }

    //#if defined(COGNITIVE)
    //@#$LPS-COGNITIVE:GranularityType:Method
    private void setTodoList(AbstractProjectMember member) {
        //#if defined(LOGGING)
        //@#$LPS-LOGGING:GranularityType:Statement
        //@#$LPS-LOGGING:Localization:StartMethod
        LOG.info("Setting todoList to " + member);
        //#endif
        todoList = member;
    }
    //#endif

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection< ? > arg0) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection< ? extends ProjectMember> arg0) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int arg0, Collection< ? extends ProjectMember> arg1) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection< ? > arg0) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection< ? > arg0) {
        throw new UnsupportedOperationException();
    }

    public ProjectMember set(int arg0, ProjectMember arg1) {
        throw new UnsupportedOperationException();
    }

    public void add(int arg0, ProjectMember arg1) {
        throw new UnsupportedOperationException();
    }

    public ProjectMember remove(int arg0) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object arg0) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object arg0) {
        throw new UnsupportedOperationException();
    }

    public List<ProjectMember> subList(int arg0, int arg1) {
        throw new UnsupportedOperationException();
    }


}
