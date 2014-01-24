How to install
--------------

### VisualStudio 2010, VisualStudio 2008

- copy dhtmlx.debug.js in the same folder where dhtmlx.js located
- if you are using separate component (not dhtmlx.js) - rename dhtmlx.debug.js 
  to {component}.debug.js and place in the same folder where js file of component is placed

### PHPStorm, WebStorm (JetBrain IDEs)

- drop dhtmlx3.sdoc.js in the project
- you may need to restart IDE after that to force auto-complete reindexing. 

### NetBeans 7

- drop dhtlmx3.sdoc in the project
- select in top menu "Source|Scan for external changes"

### Aptana 2.x

- copy jar file in plugins folder of IDE
- restart IDE
- select "References|Global references"
- check "DHTMLX 3.0" item

### Aptana 3.x

- drop dhtmlx3.vsdoc.js in the project
- restart IDE


### Eclipse

- install Eclipse JavaScript Development Tools
- go to project properties – JavaScript – Include Path
- click "Add JavaScript Library" – "User Library" – "Configure User Library" – "New"
- enter "dhtmlx3" as library name
- select dhtmlx3 from the list and press "Add .js file", select dhtmlx3.sdoc.js 
  from the download package
- press "OK" and "Finish" in all dialogs

### Komodo IDE and Komodo Editor

- extract dhtmlx3.sdoc.js from the package to some folder
- open project properties – Languages – Javascript
- add the above folder to the list of JavaScript directories