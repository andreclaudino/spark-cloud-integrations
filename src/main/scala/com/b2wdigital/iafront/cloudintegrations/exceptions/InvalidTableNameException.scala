package com.b2wdigital.iafront.cloudintegrations.exceptions

class InvalidTableNameException(tableName:String) extends Exception(s"Invalid table name $tableName")
