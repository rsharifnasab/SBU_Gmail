
client:	compile_uiux compile_client
	@echo "running client"
	@java -cp ".:./src" ClientAPP.ClientEXE
compile_client:
	@echo "compiling client"
	@javac -cp ".:./src" src/ClientAPP/ClientEXE.java
compile_uiux:
	@echo "compiling ui/ux "
	@javac -cp ".:./src:./src/ClientAPP/UIUX/FXMLs" src/ClientAPP/Controllers/*.java

server: compile_server
	@echo "server is running"
	@java -cp ".:./src" ServerScript.ServerEXE


test: compile_server compile_test
	@echo "running tests"
	@java -cp ./src/tests/junit.jar:./src:./src/tests/j2.jar   org.junit.runner.JUnitCore tests.ServerTest
	@echo "tests complete, deleting class files"
	@find . -type f -name '*.class' -delete

compile_test:
	@echo "compiling tests"
	@javac -cp .:./src:./src/tests/junit.jar src/tests/ServerTest.java


compile_server:
	@echo "compiling server.."
	@javac -cp ".:./src" src/ServerScript/ServerEXE.java


clean:
	@echo "cleaning .class files"
	@find . -type f -name '*.class' -delete

clean_DB:
	@echo "cleaning DB files"
	@rm src/ServerScript/DB/MailDB
	@rm src/ServerScript/DB/ProfilesDB
	@echo "creating empty DB files"
	@touch src/ServerScript/DB/MailDB
	@touch src/ServerScript/DB/ProfilesDB
