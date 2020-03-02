#!/usr/bin/env bash

set -e

java ${JVM} -Duser.Timezone=GMT -jar bank-account.jar
