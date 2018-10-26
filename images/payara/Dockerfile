FROM payara/server-full:5.183

MAINTAINER Ludovic Delafontaine <ludovic.delafontaine@heig-vd.ch>

ADD drivers/mysql-connector-java-5.1.39-bin.jar ./glassfish/lib/

RUN echo "AS_ADMIN_PASSWORD=admin" > /tmp/gfpwd.txt && \
    ./bin/asadmin start-domain domain1 && \
    ./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/gfpwd.txt create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=adminpw:DatabaseName=wp1:ServerName=db:port=3306 pool_wp1 && \
    ./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/gfpwd.txt create-jdbc-resource --connectionpoolid pool_wp1 jdbc/wp1 && \
    ./bin/asadmin stop-domain domain1