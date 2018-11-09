FROM payara/server-full:5.183

MAINTAINER Ludovic Delafontaine <ludovic.delafontaine@heig-vd.ch>

ADD *.war /opt/payara/deployments/
ADD drivers/mysql-connector-java-5.1.39-bin.jar ./glassfish/lib/

ARG DB_USERNAME
ARG DB_PASSWORD
ARG DB_HOSTNAME
ARG DB_PORT

ARG MAIL_USERNAME
ARG MAIL_PASSWORD
ARG HOST_NAME
ARG HOST_USER

ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_HOSTNAME=${DB_HOSTNAME}
ENV DB_PORT=${DB_PORT}

ENV MAIL_USERNAME=${MAIL_USERNAME}
ENV MAIL_PASSWORD=${MAIL_PASSWORD}
ENV HOST_NAME=${HOST_NAME}
ENV HOST_USER=${HOST_USER}

RUN echo "AS_ADMIN_PASSWORD=admin" > /tmp/gfpwd.txt && \
    ./bin/asadmin start-domain domain1 && \
    ./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/gfpwd.txt create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=adminpw:DatabaseName=wp1:ServerName=db:port=3306 pool_wp1 && \
    ./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/gfpwd.txt create-jdbc-resource --connectionpoolid pool_wp1 jdbc/wp1 && \
    ./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/gfpwd.txt create-javamail-resource --mailhost ${HOST_NAME} --mailuser ${HOST_USER} --fromaddress ${MAIL_USERNAME} --storeprotocol=imap --storeprotocolclass=com.sun.mail.imap.IMAPStore --transprotocol=smtp --transprotocolclass=com.sun.mail.smtp.SMTPTransport --password $MAIL_PASSWORD --auth true --property mail.smtp.user=$MAIL_USERNAME:mail.smtp.password=$MAIL_PASSWORD:mail.smtp.auth=true:mail.smtp.port=465:mail.smtp.socketFactory.port=465:mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory java/mail/swhp && \
	./bin/asadmin stop-domain domain1 
