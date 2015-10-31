<?php

// $Id: trailer.inc 1756 2011-01-26 17:17:35Z cimorrison $


if(!isset($year))
{
  $year = strftime("%Y");
}

if (!isset($month))
{
  $month = strftime("%m");
}

if (!isset($day))
{
  $day = strftime("%d");
}

if (empty($area))
{
  $params['area'] = NULL;
}
else
{
  $params['area'] = "$area";
}
if (empty($room))
{
  $params['room'] = NULL;
}else{
   $params['room'] = "$room";
}

$url = formatURLFull($year, $month, $day, $params['area'], $params['room']);
if ($simple_trailer) {
  echo "<div class=\"screenonly\" id=\"simple_trailer\">\n";
  echo "<div id=\"simple\">\n";
  echo "<a href=\"day/$url\">";
  echo get_vocab("viewday")."</a>|";
  echo "<a href=\"week/$url\">";
  echo get_vocab("viewweek")."</a>|";
  echo "<a href=\"month/$url\">";
  echo get_vocab("viewmonth")."</a>\n";
  echo "</div>\n";
  echo "</div>\n";
}

else
{
  echo "<div class=\"screenonly\" id=\"trailer\">\n";
  
  // DAYS
  echo "<div id=\"viewday\">\n";
  echo "<div class=\"trailer_label\">\n";
  echo "<span><a href=\"day/$url\">" . get_vocab("viewday") .":</a></span>\n";
  echo "</div>\n";
  
  echo "<div class=\"trailer_links\">\n";
  for ($i = -6; $i <= 7; $i++)
  {
    $ctime = mktime(0, 0, 0, $month, $day + $i, $year);
  
    $str = utf8_strftime($strftime_format['daymonth'], $ctime);
    $class = "";
  
    $cyear  = date("Y", $ctime);
    $cmonth = date("m", $ctime);
    $cday   = date("d", $ctime);
    
    // put in a separator, unless we are at the beginning
    if ($i != -6)
    {
      echo " | ";
    }
    
    // if it's a hidden day set the class; otherwise construct the anchor
    // (don't offer a link if the day is a hidden day)
    $url = formatURLFull($cyear, $cmonth, $cday, $params['area'], $params['room']);
    if (is_hidden_day(date("w", $ctime)))
    { 
      $class .= "hidden";      
    }
    else
    {
      $str = "<a href=\"day/$url\">" . $str . "</a>";
    }
    
    // and if it's the current day, add square brackets and set the class
    if ($i == 0)
    {
      $str = "[ " . $str . " ]";
      if (empty($class))
      {
        $class = "current";
      }
      else
      {
        $class .= " current";
      }
    }   
    echo "<span class=\"$class\">$str</span>\n";
  }
  echo "</div>\n";
  
  echo "</div>\n";
  
  
  // WEEKS
  $url = formatURLFull($year, $month, $day, $params['area'], $params['room']);
  echo "<div id=\"viewweek\">\n";
  echo "<div class=\"trailer_label\">\n";
  echo "  <span><a href=\"week/$url\">".get_vocab("viewweek").":</a></span>\n";
  echo "</div>\n";
 
  $ctime = mktime(0, 0, 0, $month, $day, $year);
  // How many days to skip back to first day of week:
  $skipback = (date("w", $ctime) - $weekstarts + 7) % 7;
  
  echo "<div class=\"trailer_links\">\n";
  for ($i = -4; $i <= 4; $i++)
  {
    $ctime = mktime(0, 0, 0, $month, $day + 7 * $i - $skipback, $year);
  
    $cweek  = date("W", $ctime);
    $cday   = date("d", $ctime);
    $cmonth = date("m", $ctime);
    $cyear  = date("Y", $ctime);
    
    // $htime gives you the d/m/y to use in the href link.    This is the
    // real day that we want to look at, whereas $ctime is the start of that week
    $htime = mktime(0, 0, 0, $month, $day + 7 * $i, $year);
    $hday   = date("d", $htime);
    $hmonth = date("m", $htime);
    $hyear  = date("Y", $htime);
    
    if ($i != -4)
    {
      echo " | ";
    }
  
    if ($view_week_number)
    {
      $str = $cweek;
    }
    else
    {
      $str = utf8_strftime($strftime_format['daymonth'], $ctime);
    }
    if ($i == 0)
    {
      echo "<span class=\"current\">\n[ ";
    }
    $url = formatURLFull($hyear, $hmonth, $hday, $params['area'], $params['room']);
    echo "  <a href=\"week/$url\">$str</a>\n";
    if ($i == 0)
    {
      echo "]</span>\n";
    }
  }
  echo "</div>\n";
  
  echo "</div>\n";
  
  
  // MONTHS
  $url = formatURLFull($year, $month, $day, $params['area'], $params['room']);
  echo "<div id=\"viewmonth\">\n";
  echo "<div class=\"trailer_label\">\n";
  echo "  <span><a href=\"month/$url\">".get_vocab("viewmonth").":</a></span>\n";
  echo "</div>\n";
  
  echo "<div class=\"trailer_links\">\n";
  for ($i = -2; $i <= 6; $i++)
  {
    $ctime = mktime(0, 0, 0, $month + $i, 1, $year);
    $str = utf8_strftime($strftime_format['monyear'], $ctime);
     
    $cmonth = date("m", $ctime);
    $cyear  = date("Y", $ctime);
    $cday   = $day;
    // decrement day until it's a valid one for the month, in case you're moving to a month with fewer
    // days than the current one
    while (!checkdate($cmonth, $cday, $cyear))
    {
      $cday--;
      if ($cday == 0)
      {
        $cday   = 1;
        break;
      }
    }
    if ($i != -2)
    {
      echo " | ";
    }
    if ($i == 0)
    {
      echo "<span class=\"current\">\n[ ";
    }
    $url = formatURLFull($cyear, $cmonth, $cday, $params['area'], $params['room']);
    echo "  <a href=\"month/$url\">$str</a>\n";
    if ($i == 0)
    {
      echo "]</span>\n";
    }
  }
  echo "</div>\n";  // end of links div
  echo "</div>\n";  // end of viewmonth
  echo "</div>\n";  // end of "classic" trailer
}

// Print footer
print_footer(FALSE);

?>