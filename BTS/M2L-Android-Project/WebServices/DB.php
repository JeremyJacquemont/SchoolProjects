<?php

/**
 * DB class
 */

class DB {

    function __construct() {
        $this->connect();
    }
 
    function __destruct() {
        $this->close();
    }
 
    /**
     * Function to connect to database with SQL Define (cf. params.inc)
     */
    function connect() {
        require_once 'params.inc';
 
        $con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD) or die(mysql_error());
 
        $db = mysql_select_db(DB_DATABASE) or die(mysql_error());
        
        mysql_query("set names 'utf8'");
 
        return $con;
    }
 
    /**
     * Function to close db connection
     */
    function close() {
        mysql_close();
    }

}

?>