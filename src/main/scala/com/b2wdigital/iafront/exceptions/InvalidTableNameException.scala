package com.b2wdigital.iafront.exceptions

class InvalidTableNameException(tableName:String) extends Exception(s"Invalid table name $tableName")
