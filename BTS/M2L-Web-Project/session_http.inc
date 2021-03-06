<?php 
/*****************************************************************************\
*                                                                             *
*   File name       auth_http.inc                                             *
*                                                                             *
*   Description     Get user identity using the HTTP basic authentication.    *
*                                                                             *
*   Notes           To use this session scheme, set in config.inc.php:        *
*                       $auth["session"]  = "http";                           *
*                                                                             *
*   History                                                                   *
*    2003/11/12 JFL Created this file                                         *
*    Remaining history in ChangeLog and CVS logs                              *
*                                                                             *
\*****************************************************************************/

// $Id: session_http.inc 1640 2010-11-24 17:50:28Z jberanek $

/* authGet()
 * 
 * Request the user name/password
 * 
 * Returns: Nothing
 */
function authGet()
{
  global $auth;
  header("WWW-Authenticate: Basic realm=\"$auth[realm]\"");
  Header("HTTP/1.0 401 Unauthorized");
}

function getAuthPassword()
{
  global $PHP_AUTH_PW;

  if (isset($PHP_AUTH_PW))
  {
    return unslashes($PHP_AUTH_PW);
  }
  else
  {
    return null;
  }
}

function getUserName()
{
  global $PHP_AUTH_USER;
  if (isset($PHP_AUTH_USER))
  {
    $user = unslashes($PHP_AUTH_USER);

    if (authValidateUser($user,getAuthPassword()))
    {
      return $user;
    }
    else
    {
      return null;
    }
  }
  else
  {
    return null;
  }
}

// Print the logon entry on the top banner.
function PrintLogonBox()
{
  global $PHP_SELF, $QUERY_STRING, $user_list_link, $day, $month, $year;
    
  $TargetURL = basename($PHP_SELF);
  if (isset($url_base) && ($url_base != ""))
  {
    $TargetURL = $url_base . '/' . $TargetURL;
  }
  if (isset($QUERY_STRING))
  {
    $TargetURL = $TargetURL . "?" . $QUERY_STRING;
  }
  $TargetURL = "index";
  $user = getUserName();
  if (isset($user))
  {
    // words 'you are xxxx' becomes a link to the
    // report page with only entries created by xxx. Past entries are not
    // displayed but this can be changed
    $search_string = formatURLReport($day, $month, $year, $user);
?>


      <a name="logonBox" href="<?php echo "report/$search_string\" title=\""
         . get_vocab('show_my_entries') . "\">" . get_vocab('you_are')." "
         . htmlspecialchars($user); ?></a>
<?php
  }
  else
  {
?>
    <a name="logonBox" href=""><?php echo get_vocab('unknown_user'); ?></a>
    <form method="post" action="admin.php">
      <div>
        <input type="hidden" name="TargetURL" value="<?php echo htmlspecialchars($TargetURL) ?>">
        <input type="hidden" name="Action" value="QueryName">
        <input type="submit" value=" <?php echo get_vocab('login') ?> ">
      </div>
    </form>
<?php
  }
  if (isset($user_list_link))
  {
    print "<a href=\"$user_list_link\">" . get_vocab('user_list') . "</a>\n" ;
  } 
}
?>
