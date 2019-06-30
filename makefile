
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
