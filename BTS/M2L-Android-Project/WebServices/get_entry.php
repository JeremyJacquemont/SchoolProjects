<?php

/**
 * Function for return a entry with specific id
 * @param {String} $id id for $_GET
 * @return {String} response
 */

$response = array();

if(isset($_GET['id'])){
    $id = $_GET['id'];
    
    require_once 'DB.php';
    $db = new DB();
    
    $result = mysql_query("SELECT e.id, e.start_time, e.end_time, e.room_id, e.create_by, e.name, r.room_name FROM mrbs_entry AS e, mrbs_room AS r WHERE e.id = '$id' AND e.room_id = r.id");
    
    //Get entry    
    if(!empty($result) && mysql_num_rows($result) > 0) {//Success
        $result = mysql_fetch_object($result);
        $response['success'] = 1;
        $response['entry'] = $result;
        echo json_encode($response);
        
    } else {//Success but not entry found
        $response['success'] = 1;
        $response['message'] = "Aucune réservation trouvée";
        echo json_encode($response);
    }
}else{//Error with request
    $response['success'] = 0;
    $response['message'] = "Champs manquants";
    echo json_encode($response);
}

?>
