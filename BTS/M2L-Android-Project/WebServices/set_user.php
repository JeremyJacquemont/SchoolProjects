<?php

/**
 * Function for  update informations for specific user
 * @param {String} $id id for $_POST
 * @param {String} $name name for $_POST
 * @param {String} $email email for $_POST
 * @param {String} $password password for $_POST
 * @return {String} response
 */


$response = array();

if(isset($_POST['id'])){
    require_once 'DB.php';
    $db = new DB();
    
    //Get user
    $id = $_POST['id'];
    $user = mysql_query("SELECT * FROM mrbs_users WHERE id = '$id'");    
    if(!empty($user)){
        if (mysql_num_rows($user) > 0) {
            $user = mysql_fetch_object($user);
        }
    }else{
        write_error(mysql_error());
        die();
    }
    
    //For save new name
    if(isset($_POST['name'])&& $_POST['name'] != null){
        $name = $_POST['name'];
        $query = mysql_query("SELECT * FROM mrbs_users WHERE name = '$name'");
        if(!$query){//Error with request
            write_error(mysql_error());
            die();
        }else{
            if(!empty($query) && mysql_num_rows($query) > 0){//Name is already use
                $response['success'] = 0;
                $response['message'] = "Le nom d'utilisateur est déjà utilisé";
                echo json_encode($response);
            }else{//Name not use and go to save
                $result = mysql_query("UPDATE mrbs_users SET name = '$name' WHERE id = '$id'");
                if(!$result){//Error with request
                    write_error(mysql_error());
                    die();
                }else{
                    $subResult = mysql_query("UPDATE mrbs_entry SET create_by = '$name' WHERE create_by = '$user->name'");
                    if(!$subResult){//Error with request
                        write_error(mysql_error());
                        die();
                    }else{//Success
                        $response['success'] = 1;
                        $response['message'] = "Changement du nom d'utilisateur effectué";
                        $response['value'] = $name;
                        echo json_encode($response);
                    }
                } 
            }
        }
    }
    
    //For save new email
    else if(isset($_POST['email']) && $_POST['email'] != null){
        $email = $_POST['email'];
        $result = mysql_query("UPDATE mrbs_users SET email = '$email' WHERE id = '$id'");
        if(!$result){//Error with request
            write_error(mysql_error());
            die();
        }else{
            $subResult = mysql_query("UPDATE mrbs_area SET area_admin_email = '$email' WHERE area_admin_email = '$user->email'");
            if(!$subResult){//Error with request
                write_error(mysql_error());
                die();
            }else{//Success
                $response['success'] = 1;
                $response['message'] = "Changement de l'email effectué";
                $response['value'] = $email;
                echo json_encode($response);
            }
        }
    }
    
    //For save new password
    else if(isset($_POST['old_password']) && $_POST['old_password'] != null &&
            isset($_POST['new_password']) && $_POST['new_password'] != null){
        $old_password = md5($_POST['old_password']);
        $new_password = md5($_POST['new_password']);
        $query = mysql_query("UPDATE mrbs_users SET password = '$new_password' WHERE id = '$id' AND password = '$old_password'");
         if(!$query){
                write_error(mysql_error());
                die();
            }else{
                $response['success'] = 1;
                $response['message'] = "Enregistrement password effectué";
                $response['value'] = "";
                echo json_encode($response);
            }
    }
    
    //For else...
    else{
        $response['success'] = 0;
        $response['message'] = "Une erreur est survenue";
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
