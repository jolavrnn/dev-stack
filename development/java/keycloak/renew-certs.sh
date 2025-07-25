#!/bin/sh

# Initial generation
generate_certs() {
  # Generate new certs (5m validity)
  openssl req -x509 -newkey rsa:2048 -keyout /certs/keycloak.key -out /certs/keycloak.crt \
    -days 0 -minutes 5 -nodes -subj "/CN=keycloak"

  # Create PKCS12 keystore
  openssl pkcs12 -export -out /certs/keycloak.p12 \
    -inkey /certs/keycloak.key -in /certs/keycloak.crt \
    -name keycloak -password pass:changeit

  # Import cert into Java truststore
  keytool -import -trustcacerts -alias keycloak \
    -file /certs/keycloak.crt -keystore /truststore/truststore.jks \
    -storepass changeit -noprompt

  # Copy cert for Java app access
  cp /certs/keycloak.crt /truststore/keycloak.crt
}

# Initial generation
generate_certs

# Renewal loop
while true; do
  echo "Sleeping for $SLEEP_DURATION seconds..."
  sleep $SLEEP_DURATION
  echo "Renewing certificates..."
  generate_certs
  echo "New certificates generated!"
done