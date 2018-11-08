FROM payara/server-full:5.183

MAINTAINER Ludovic Delafontaine <ludovic.delafontaine@heig-vd.ch>

ADD drivers/mysql-connector-java-5.1.39-bin.jar ./glassfish/lib/

RUN echo "AS_ADMIN_PASSWORD=admin" > /tmp/gfpwd.txt && \
    ./bin/asadmin start-domain domain1 && \
    ./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/gfpwd.txt create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=adminpw:DatabaseName=wp1:ServerName=db:port=3306 pool_wp1 && \
    ./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/gfpwd.txt create-jdbc-resource --connectionpoolid pool_wp1 jdbc/wp1 && \
	./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/gfpwd.txt create-javamail-resource --mailhost smtp.gmail.com --mailuser heig.amt.project --fromaddress heig.amt.project@gmail.com --storeprotocol=imap --storeprotocolclass=com.sun.mail.imap.IMAPStore --transprotocol=smtp --transprotocolclass=com.sun.mail.smtp.SMTPTransport --password CHANGE_ME --auth true --property mail.smtp.user=heig.amt.project@gmail.com:mail.smtp.password=CHANGE_ME:mail.smtp.auth=true:mail.smtp.port=465:mail.smtp.socketFactory.port=465:mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory java/mail/swhp && \
	./bin/asadmin stop-domain domain1