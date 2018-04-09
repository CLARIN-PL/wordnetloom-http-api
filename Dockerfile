FROM clarinpl/wildfly-mysql

# Maintainer
LABEL maintainer="Tomasz Naskręt, clarin-pl.eu" description="WordnetLoom HTTP API on WildFly 12 with MySql Drivers"

# Database
ENV DB_NAME wordnet
ENV DB_USER root
ENV DB_PASS password
ENV DB_URI 127.0.0.1:3306

# Configure Wildfly server
RUN echo "=> Starting WildFly server" && \
      bash -c '$WILDFLY_HOME/bin/standalone.sh -c standalone-ee8.xml &' && \
    echo "=> Waiting for the server to boot" && \
     sleep 3 && \
    echo "=> Creating a new datasource" && \
      $WILDFLY_CLI --connect --command="data-source add \
        --name=${DB_NAME}DS \
        --jndi-name=java:/jdbc/datasources/${DB_NAME}DS \
        --user-name=${DB_USER} \
        --password=${DB_PASS} \
        --driver-name=mysql \
        --connection-url=jdbc:mysql://${DB_URI}/${DB_NAME}?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC \
        --use-ccm=false \
        --max-pool-size=25 \
        --blocking-timeout-wait-millis=5000 \
        --enabled=true" && \
    echo "=> Shutting down WildFly and Cleaning up" && \
      $WILDFLY_CLI --connect --command=":shutdown" && \
      rm -rf $WILDFLY_HOME/standalone/configuration/standalone_xml_history/ $WILDFLY_HOME/standalone/log/* && \
      rm -f /tmp/*.jar

# Expose http and admin ports
EXPOSE 8080 9990

#echo "=> Restarting WildFly"
# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interfaces
## Deploying
COPY ./target/wordnetloom.war ${DEPLOYMENT_DIR}

ENTRYPOINT ${WILDFLY_HOME}/bin/standalone.sh -c standalone-ee8.xml -b=0.0.0.0 -bmanagment=0.0.0.0