
client:	compile_uiux compile_client
	java -cp ".:./src" NetworkStuff.ClientSide.Client
compile_client:
	javac -cp ".:./src" src/NetworkStuff/ClientSide/Client.java
compile_uiux:
	javac -cp ".:./src" src/UIUX/Controllers/*.java

server: compile_server
	java -cp ".:./src" NetworkStuff.ServerSide.Server
compile_server:
	javac -cp ".:./src" src/NetworkStuff/ServerSide/Server.java

clean:
	find . -type f -name '*.class' -delete
