<?php

// $Id: version.inc 1784 2011-02-09 20:08:10Z jberanek $

// This should probably be a class, but you can only have protected
// class members in PHP 5, so we won't bother

function get_mrbs_version()
{
  // MRBS developers, make sure to update this string before each release
  //$mrbs_version = "MRBS 1.4.6";
  openConnectionSQL();
  $mrbs_version = getParameter("version", "mrbs_version");
  closeConnectionSQL();

  return $mrbs_version;
}

?>
