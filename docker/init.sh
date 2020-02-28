#!/usr/bin/env bash

set -e

java ${JVM} -Duser.Timezone=America/Sao_Paulo -jar bank-account.jar
