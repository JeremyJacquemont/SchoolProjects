<?php
/*
 * SQL Params
 */

/**
 * Dev
 */
/*define("DB_HOST", "localhost");
define("DB_USER", "root");
define("DB_PASSWORD", "");
define("DB_DATABASE", "mrbs");
*/

/**
 * Production
 * Hosted in AppFog (https://appfog.com/)
 */
$services_json = json_decode(getenv("VCAP_SERVICES"),true);
$mysql_config = $services_json["mysql-5.1"][0]["credentials"];
define("DB_HOST", $mysql_config["hostname"]);
define("DB_USER", $mysql_config["username"]);
define("DB_PASSWORD", $mysql_config["password"]);
define("DB_DATABASE", $mysql_config["name"]);

?>