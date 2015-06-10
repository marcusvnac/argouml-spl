// $Id: UMLConditionExpressionModel.java 127 2010-09-25 22:23:13Z marcusvnac $
// Copyright (c) 2003-2006 The Regents of the University of California. All
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

//#if defined(LOGGING)
//@#$LPS-LOGGING:GranularityType:Import
import org.apache.log4j.Logger;
//#endif
import org.argouml.model.Model;
import org.argouml.ui.targetmanager.TargetManager;

/**
 *
 * @author mkl
 *
 */
public class UMLConditionExpressionModel extends UMLExpressionModel2 {
    //#if defined(LOGGING)
    //@#$LPS-LOGGING:GranularityType:Field
    private static final Logger LOG =
        Logger.getLogger(UMLConditionExpressionModel.class);
    //#endif
    /**
     * The constructor.
     *
     * @param container the container of UML user interface components
     * @param propertyName the name of the property
     */
    public UMLConditionExpressionModel(UMLUserInterfaceContainer container,
            String propertyName) {
        super(container, propertyName);
    }

    /*
     * @see org.argouml.uml.ui.UMLExpressionModel2#getExpression()
     */
    public Object getExpression() {
        return Model.getFacade().getCondition(
                TargetManager.getInstance().getTarget());
    }

    /*
     * @see org.argouml.uml.ui.UMLExpressionModel2#setExpression(java.lang.Object)
     */
    public void setExpression(Object expression) {
        Object target = TargetManager.getInstance().getTarget();

        if (target == null) {
            throw new IllegalStateException("There is no target for "
                    + getContainer());
        }
        //#if defined(USECASEDIAGRAM)
        //@#$LPS-USECASEDIAGRAM:GranularityType:Statement
        //@#$LPS-USECASEDIAGRAM:Localization:EndMethod
        Model.getUseCasesHelper().setCondition(target, expression);
        //#endif
    }

    /*
     * @see org.argouml.uml.ui.UMLExpressionModel2#newExpression()
     */
    public Object newExpression() {
        //#if defined(LOGGING)
        //@#$LPS-LOGGING:GranularityType:Statement
        //@#$LPS-LOGGING:Localization:StartMethod
        //@#$LPS-LOGGING:Localization:BeforeReturn
        LOG.debug("new boolean expression");
        //#endif
        return Model.getDataTypesFactory().createBooleanExpression("", "");
    }

}
