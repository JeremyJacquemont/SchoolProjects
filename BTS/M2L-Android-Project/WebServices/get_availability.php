<?php

/**
 * Function to get availability for room
 * @param {String} $room_id id of room for $_GET
 * @param {String} $start_time start_time for $_GET
 * @param {String} $end_time end_time for $_GET
 * @return {String} response
 */

$response = array();

if(isset($_GET['room_id']) && isset($_GET['start_time']) && isset($_GET['end_time'])){
    
    $room_id = $_GET['room_id'];
    $start_time  = $_GET['start_time'];
    $end_time  = $_GET['end_time'];
    
    require_once 'DB.php';
    $db = new DB();
    
    //Check if date start < date end
    if((int)$start_time < (int)$end_time){

        $result = mysql_query("SELECT e.id FROM mrbs_entry AS e WHERE e.room_id = '$room_id' AND 
            (
                ( '$start_time' BETWEEN e.start_time AND e.end_time ) AND ('$end_time'BETWEEN e.start_time AND e.end_time) OR
                ( '$start_time' NOT BETWEEN e.start_time AND e.end_time ) AND ('$end_time'BETWEEN e.start_time AND e.end_time) OR
                ( '$start_time' BETWEEN e.start_time AND e.end_time ) AND ('$end_time' NOT BETWEEN e.start_time AND e.end_time)
             )");
        if(!empty($result) && mysql_num_rows($result) > 0){//Room is unavailable
            /*$returnDate = 0;
            while ($row = mysql_fetch_object($result)) {
                $result = mysql_query("SELECT e.start_time FROM mrbs_entry AS e WHERE e.room_id = '$room_id' AND e.start_time > '$start_time'");
                $rows[] = $row;
            }*/
            $response['success'] = 1;
            $response['message'] = "La salle n'est pas libre";
            echo json_encode($response); 
        }else{//Room is available
            $response['success'] = 1;
            $response['message'] = "La salle est libre";
            echo json_encode($response); 
        }
    }else{//Error with dates
        $response['success'] = 0;
        $response['message'] = "La première date doit être inférieur à la deuxième";
        echo json_encode($response);
    }
    
}else{//Error with parameters
    $response['success'] = 0;
    $response['message'] = "Champs manquants";
    echo json_encode($response);
}

?>
