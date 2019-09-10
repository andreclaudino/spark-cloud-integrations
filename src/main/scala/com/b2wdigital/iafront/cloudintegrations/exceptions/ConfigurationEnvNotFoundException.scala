package com.b2wdigital.iafront.cloudintegrations.exceptions

class ConfigurationEnvNotFoundException(envName:String) extends Exception(s"Configuration environment variable '$envName' not found")