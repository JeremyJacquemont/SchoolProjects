<?php
    require_once 'defaultincludes.inc';
    
    /* Récupération des variables */
    static $NAME_DB = "mrbs_config";
    
    $name = $_GET['name'];
    $data = $_GET['data'];
    
    /* Update des résultats */
    $conn = connectToDatabase($NAME_DB);
    foreach ($data as $value) {
        if(isset($value['name']) && isset($value['value'])){
            updateSQL($name, $value['name'], addslashes($value['value']), $conn);
        }
        else if(is_array($value)){
            $sub = $value;
            foreach ($sub as $value){
                if(isset($value['name']) && isset($value['value']) && $value['name'] == "title"){
                    $name = $value['value'];
                }
                else if(isset($value['name']) && isset($value['value'])){
                    updateSQL($name, $value['name'], addslashes($value['value']), $conn);
                }
            }
        }
    }
    closeSQL($conn);
?>