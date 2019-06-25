
client:	compile_uiux compile_client
	@echo "running client"
	@java -cp ".:./src" NetworkStuff.ClientSide.Client
compile_client:
	@echo "compiling client"
	@javac -cp ".:./src" src/NetworkStuff/ClientSide/Client.java
compile_uiux:
	@echo "compiling ui/ux "
	@javac -cp ".:./src" src/UIUX/Controllers/*.java

server: compile_server
	@echo "server is running"
	@java -cp ".:./src" NetworkStuff.ServerSide.Server
compile_server:
	@echo "compiling server.."
	@javac -cp ".:./src" src/NetworkStuff/ServerSide/Server.java

clean:
	@echo "cleaning .class files"
	@find . -type f -name '*.class' -delete
