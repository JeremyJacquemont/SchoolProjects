<?php

/**
 * Function for delete a entry
 * @param {String} $id id for $_POST
 * @return {String} response
 */

$response = array();

if(isset($_POST['id'])){
    $id = $_POST['id'];
    
    require_once 'DB.php';
    $db = new DB();
    
    $query = "DELETE FROM mrbs_entry WHERE id='$id'";
    //Delete entry
    if(!mysql_query($query)){//Success
        $response['success'] = 0;
        $response['message'] = "Impossible de supprimer l'entry";
        echo json_encode($response);
    }else{//Error with request
        $response['success'] = 1;
        $response['message'] = "Entry supprimer";
        echo json_encode($response);
    }
    
}else{
    $response['success'] = 0;
    $response['message'] = "Champs manquants";
    echo json_encode($response);
}

?>
