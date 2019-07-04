
client:
	@echo "running client"
	@java -cp "target/gmail-1.0.jar:taget/classes"  sbu.clientAPP.ClientEXE

server:
	@echo "running server"
	@java -cp target/gmail-1.0.jar  sbu.serverscript.ServerEXE


clean_DB:
	@echo "cleaning DB files"
	@rm ./db/MailDB
	@rm ./db/ProfilesDB
	@echo "creating empty DB files"
	@touch ./db/MailDB
	@touch ./db/ProfilesDB

clean_mails:
	@echo "deleting mail db file"
	@rm ./db/MailDB
	@echo "creating empty mail DB file"
	@touch ./db/MailDB
