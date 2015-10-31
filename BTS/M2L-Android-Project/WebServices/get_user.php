<?php

/**
 * Function for get a user with specific name and password
 * @param {String} $name name for $_GET
 * @param {String} $pasword password for $_GET
 * @return {String} response
 */

$response = array();

if(isset($_GET['name']) && isset($_GET['password'])){
    $name = $_GET['name'];
    $password = $_GET['password'];
    
    require_once 'DB.php';
    $db = new DB();
    
    $password = md5($password);
    $result = mysql_query("SELECT * FROM mrbs_users WHERE name = '$name' AND password = '$password'");
    
    //Get user
    if(!empty($result)){
        if (mysql_num_rows($result) > 0) {//Success
            $result = mysql_fetch_object($result);
            $user = array();
            $user['id'] = $result->id;
            $user['name'] = $result->name;
            $user['password'] = $result->password;
            $user['email'] = $result->email;
            $user['level'] = $result->level;

            $response['success'] = 1;
            $response['user'] = array();
            array_push($response['user'], $user);
            echo json_encode($response);
        } else {//Error with request
            $response['success'] = 0;
            $response['message'] = "Utilisateur non trouvé";
            echo json_encode($response);
        }
    } else {
        $response['success'] = 0;
        $response['message'] = "Utilisateur non trouvé";
        echo json_encode($response);
    }
}else{
    $response['success'] = 0;
    $response['message'] = "Champs manquants";
    echo json_encode($response);
}

?>
