<?php

/**
 * Function for create a new entry or update informations for specific entry
 * @param {String} $start_time start_time for $_POST
 * @param {String} $end_time end_time for $_POST
 * @param {String} $room_id room_id for $_POST
 * @param {String} $create_by create_by for $_POST
 * @param {String} $name name for $_POST
 * @param {String} $id id for $_POST
 * @param {String} $_POST['status'] status
 * @return {String} response
 */

$response = array();

if(isset($_POST['status'])){
    require_once 'DB.php';
    $db = new DB();
    
    //Get informations with $_POST
    $start_time = $_POST['start_time'];
    $end_time = $_POST['end_time'];
    $room_id = $_POST['room_id'];
    $create_by = $_POST['create_by'];
    $name = $_POST['name'];
    if($_POST['id'] != NULL){
        $id = $_POST['id'];
    }
   
    //Check if date start < date end
    if((int)$start_time < (int)$end_time){
        //For create new entry
        if($_POST['status'] == "ADD"){
            $query = "INSERT INTO mrbs_entry (start_time, end_time, room_id, create_by, name)
                         VALUES('$start_time', '$end_time', '$room_id', '$create_by', '$name');";
             if(!mysql_query($query)){//Error with request
                 write_error(mysql_error());
                 die();
             }else{//Success
                  $response['success'] = 1;
                  $response['message'] = "Enregistrement effectué";
                  $response['id'] = mysql_insert_id();
                  echo json_encode($response);
             }
        }
        //For update a entry
        else if($_POST['status'] == "UPDATE"){
            $query = "UPDATE mrbs_entry
                     SET start_time = '$start_time', end_time = '$end_time', room_id = '$room_id', create_by = '$create_by', name = '$name'
                     WHERE id = '$id'";
            if(!mysql_query($query)){//Error with request
                 write_error(mysql_error());
                 die();
             }else{//Success
                  $response['success'] = 1;
                  $response['message'] = "Mise à jour effectuée";
                  $response['id'] = $id;
                  echo json_encode($response);
             }
        }else{
             $response['success'] = 0;
             $response['message'] = "Une erreur est survenue";
             echo json_encode($response);
         }
    
    }else{//Error with dates
        $response['success'] = 0;
        $response['message'] = "La première date doit être inférieur à la deuxième";
        echo json_encode($response);
    }
    
}else{
    $response['success'] = 0;
    $response['message'] = "Champs manquants";
    echo json_encode($response);
}

function write_error($error) {
    $response['success'] = 0;
    $response['message'] = "Erreur SQL : $error";
    echo json_encode($response);
}

?>
