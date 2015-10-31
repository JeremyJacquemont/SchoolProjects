<?php

/**
 * Function for get all entries for user
 * @param {String} $name name for $_GET
 * @return {String} response
 */
$response = array();

if(isset($_GET['name'])){
    $name = $_GET['name'];
    
    require_once 'DB.php';
    $db = new DB();
    
    //Set query
    if($_GET['limit'] != null){
        $limit = $_GET['limit'];
        $result = mysql_query("SELECT id, start_time, end_time, name FROM mrbs_entry WHERE create_by = '$name' AND start_time > UNIX_TIMESTAMP() ORDER BY start_time DESC LIMIT 0, $limit");
    }else{
        $result = mysql_query("SELECT id, start_time, end_time, name FROM mrbs_entry WHERE create_by = '$name' ORDER BY start_time DESC");
    }
    
    //Get entries
    if(!empty($result) && mysql_num_rows($result) > 0) {//Success
        $rows = array();
        while ($row = mysql_fetch_object($result)) {
            $rows[] = $row;
        }
        $response['success'] = 1;
        $response['entry'] = $rows;
        echo json_encode($response);
        
    } else {//Error with request
        $response['success'] = 1;
        $response['message'] = "Aucune réservation trouvée";
        echo json_encode($response);
    }
}else{
    $response['success'] = 0;
    $response['message'] = "Champs manquants";
    echo json_encode($response);
}

?>
