<?php

// $Id: index.php 1640 2010-11-24 17:50:28Z jberanek $

// Index is just a stub to redirect to the appropriate view
// as defined in config.inc.php using the variable $default_view
// If $default_room is defined in config.inc.php then this will
// be used to redirect to a particular room.

require_once "defaultincludes.inc";
require_once "mrbs_sql.inc";
require_once 'url.inc';

$url = formatURLFull($year, $month, $day, $area, $room);

switch ($default_view)
{
  case "month":
    $redirect_str = "month/$url";
    break;
  case "week":
    $redirect_str = "week/$url";
    break;
  default:
    $redirect_str = "day/$url";
}

header("Location: $redirect_str");
?>