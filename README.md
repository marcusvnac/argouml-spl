# argouml-spl

ArgoUML-SPL is a project that aims to extract a SPL from the current ArgoUML codebase.

At the moment, we have already modularized the following features:

<OL>

<LI> Design Critics: "Simple agents that continuously execute in a background thread of control. They analyze the design as the designer is working and suggest possible improvements. These suggestions range from indications of syntax errors, to reminders to return to parts of the design that need finishing, to style guidelines, to the advice of expert designers."

<LI> State Diagrams: "State diagrams are used to describe the behavior of a system. State diagrams describe all of the possible states of an object as events occur. Each diagram usually represents objects of a single class and track the different states of its objects through the system."

<LI> Activity Diagrams: "Activity diagrams describe the workflow behavior of a system. Activity diagrams are similar to state diagrams because activities are the state of doing something. The diagrams describe the state of activities by showing the sequence of activities performed."

<LI> Sequence Diagrams: "Interaction diagrams model the behavior of use cases by describing the way groups of objects interact to complete the task. Sequence Diagram displays the time sequence of the objects participating in the interaction. This consists of the vertical dimension (time) and horizontal dimension (different objects)."

<LI> Use Case Diagrams: "A use case is a set of scenarios that describing an interaction between a user and a system. A use case diagram displays the relationship among actors and use cases. The two main components of a use case diagram are use cases and actors."

<LI> Collaboration Diagrams: "Collaboration diagrams are used to show how objects interact to perform the behavior of a particular use case, or a part of a use case. Along with sequence diagrams, collaborations are used by designers to define and clarify the roles of the objects that perform a particular flow of events of a use case. They are the primary source of information used to determining class responsibilities and interfaces."

<LI> Deployment Diagrams: "A deployment diagram models the run-time architecture of a system. It shows the configuration of the hardware elements (nodes) and shows how software elements and artifacts are mapped onto those nodes."

<LI> Logging: "The purpose of debug log and trace messages is to provide a mechanism that allows the developer to enable output of minor events focused on a specific problem area and to follow what is going on inside ArgoUML."

</OL>

<P>
<B>Feature Model</B>
<P>

<img align="middle" alt="ArgoUML-SPL Feature Model" src="https://github.com/ASERG-UFMG/argouml-spl/blob/master/ArgoUML-SPL_FeatureModel.png"></center>

<P>
<B> Research Paper</B>
<P>

Marcus Vinicius Couto, Marco Tulio Valente, and Eduardo Figueiredo. <A HREF="http://homepages.dcc.ufmg.br/~mtov/pub/2011_csmr_argouml.pdf"> Extracting Software Product Lines: A Case Study Using Conditional Compilation</A>. In 15th European Conference on Software Maintenance and Reengineering (CSMR), pages 191-200, 2011.

<p>
<B>Feature Extraction Mechanism</B>

<p>At the moment, we are using pre-processor directives in order to modularize and delimit our feature's code. Currently, our implementation is based on the <a href="http://www.slashdev.ca/javapp/">javapp</a> pre-processor.</p>

<P>
<B>Examples of Products</B>

<ul>

<li><a href="https://github.com/ASERG-UFMG/argouml-spl/blob/master/products/argouml-spl_full.zip?raw=true">Product 1: Full ArgoUML application</a> (including the four mentioned features) 

<li><a href="https://github.com/ASERG-UFMG/argouml-spl/blob/master/products/argouml-spl_no-features.zip?raw=true">Product 2: ArgoUML without the  mentioned features</a> 
		
<li><a href="https://github.com/ASERG-UFMG/argouml-spl/blob/master/products/argouml-spl_no-cognitive.zip?raw=true">Product 3: ArgoUML without Design Critics feature</a> 

</ul>
