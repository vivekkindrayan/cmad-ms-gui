# Extend vert.x image
FROM vertx/vertx3

#                                                       
ENV VERTICLE_NAME com.mysocial.verticles.GUIVerticle
ENV VERTICLE_FILE target/cmad-ms-gui-1.0.0-SNAPSHOT.jar

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 9090

# Copy your verticle to the container                   
COPY $VERTICLE_FILE $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
