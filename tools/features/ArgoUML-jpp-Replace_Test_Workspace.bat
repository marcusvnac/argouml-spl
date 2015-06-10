@echo off 
if exist .\workspace-argouml-spl\argouml-app\staging\org (
	echo Substituindo argouml-app
	
	echo Apagando \workspace-argouml-javapp-test\argouml-app\src\org	
	rem del .\workspace-argouml-javapp-test\argouml-app\src\org\ /s /q /f
	rmdir .\workspace-argouml-javapp-test\argouml-app\src\org\ /s /q
	
	echo Copiando .\workspace-argouml-spl\argouml-app\staging\org
	xcopy .\workspace-argouml-spl\argouml-app\staging\org .\workspace-argouml-javapp-test\argouml-app\src\org\ /e /y /q
	xcopy .\workspace-argouml-spl\argouml-app\src\org\argouml\Images .\workspace-argouml-javapp-test\argouml-app\src\org\argouml\Images\ /e /y /q /exclude:exclude-file-to-xcopy.txt
	xcopy .\workspace-argouml-spl\argouml-app\src\org\argouml\i18n .\workspace-argouml-javapp-test\argouml-app\src\org\argouml\i18n\ /e /y /q /exclude:exclude-file-to-xcopy.txt
	xcopy .\workspace-argouml-spl\argouml-app\src\org\argouml\resource .\workspace-argouml-javapp-test\argouml-app\src\org\argouml\resource\ /e /y /q /exclude:exclude-file-to-xcopy.txt
	xcopy .\workspace-argouml-spl\argouml-app\src\org\argouml\persistence .\workspace-argouml-javapp-test\argouml-app\src\org\argouml\persistence\ /e /y /q /exclude:exclude-file-to-xcopy.txt
	xcopy .\workspace-argouml-spl\argouml-app\src\org\argouml\profile\profiles .\workspace-argouml-javapp-test\argouml-app\src\org\argouml\profile\profiles\ /e /y /q /exclude:exclude-file-to-xcopy.txt
	copy .\workspace-argouml-spl\argouml-app\src\org\argouml\*argo.ini .\workspace-argouml-javapp-test\argouml-app\src\org\argouml\
)

if exist .\workspace-argouml-spl\argouml-core-diagrams-sequence2\staging\org (
	echo.
	echo Substituindo argouml-core-diagrams-sequence2
	
	echo Apagando \workspace-argouml-javapp-test\argouml-core-diagrams-sequence2\src\org	
	rmdir .\workspace-argouml-javapp-test\argouml-core-diagrams-sequence2\src\org\ /s /q
	
	echo Copiando .\workspace-argouml-spl\argouml-core-diagrams-sequence2\staging\org
	xcopy .\workspace-argouml-spl\argouml-core-diagrams-sequence2\staging\org .\workspace-argouml-javapp-test\argouml-core-diagrams-sequence2\src\org\ /e /y /q	
)

if exist .\workspace-argouml-spl\argouml-core-model\staging\org (
	echo.
	echo Substituindo argouml-core-model
	
	echo Apagando \workspace-argouml-javapp-test\argouml-core-model\src\org	
	rmdir .\workspace-argouml-javapp-test\argouml-core-model\src\org\ /s /q
	
	echo Copiando .\workspace-argouml-spl\argouml-core-model\staging\org
	xcopy .\workspace-argouml-spl\argouml-core-model\staging\org .\workspace-argouml-javapp-test\argouml-core-model\src\org\ /e /y /q	
)

if exist .\workspace-argouml-spl\argouml-core-model-euml\staging\org (
	echo.
	echo Substituindo argouml-core-model-euml
	
	echo Apagando \workspace-argouml-javapp-test\argouml-core-model-euml\src\org	
	rmdir .\workspace-argouml-javapp-test\argouml-core-model-euml\src\org\ /s /q
	
	echo Copiando .\workspace-argouml-spl\argouml-core-model-euml\staging\org
	xcopy .\workspace-argouml-spl\argouml-core-model-euml\staging\org .\workspace-argouml-javapp-test\argouml-core-model-euml\src\org\ /e /y /q	
)

if exist .\workspace-argouml-spl\argouml-core-model-mdr\staging\org (
	echo.
	echo Substituindo argouml-core-model-mdr
	
	echo Apagando \workspace-argouml-javapp-test\argouml-core-model-mdr\src\org	
	rmdir .\workspace-argouml-javapp-test\argouml-core-model-mdr\src\org\ /s /q
	
	echo Copiando .\workspace-argouml-spl\argouml-core-model-mdr\staging\org
	xcopy .\workspace-argouml-spl\argouml-core-model-mdr\staging\org .\workspace-argouml-javapp-test\argouml-core-model-mdr\src\org\ /e /y /q	
	xcopy .\workspace-argouml-spl\argouml-core-model-mdr\src\org\argouml\model\mdr\mof .\workspace-argouml-javapp-test\argouml-core-model-mdr\src\org\argouml\model\mdr\mof\ /e /y /q /exclude:exclude-file-to-xcopy.txt	
	xcopy .\workspace-argouml-spl\argouml-core-model-mdr\build\java .\workspace-argouml-javapp-test\argouml-core-model-mdr\build\java /e /y /q /exclude:exclude-file-to-xcopy.txt	
	copy .\workspace-argouml-spl\argouml-core-model-mdr\build\UML14.dtd .\workspace-argouml-javapp-test\argouml-core-model-mdr\build\
)

pause