<?php

/**
 * Function for get all rooms
 * @return {String} response
 */

$response = array();
    
require_once 'DB.php';
$db = new DB();

$result = mysql_query("SELECT id, room_name AS name FROM mrbs_room WHERE disabled = 0") or die(mysql_error());

//Get all rooms
if(!empty($result) && mysql_num_rows($result) > 0) {//Sucess
    $rows = array();
    while ($row = mysql_fetch_object($result)) {
        $rows[] = $row;
    }
    $response['success'] = 1;
    $response['rooms'] = $rows;
    echo json_encode($response);

} else {//Error with request
    $response['success'] = 0;
    $response['message'] = "Aucune réservation trouvée";
    echo json_encode($response);
}

?>
