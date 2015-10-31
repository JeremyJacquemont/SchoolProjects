<?php
require_once 'defaultincludes.inc';

/*
 * Définition des variables Static
 */
static $NAME_DB = "mrbs_config";

static $DB = "db";
static $MISCELLANEOUS = "miscellaneous";
static $AUTHENTIFICATION = "auth";
static $MAIL = "mail";

$db = array("maxlength");
$miscellaneous = array("is_private_field");
$authentification = array("admin", "db_ext", "imap_php", "params",
    "session_cookie", "session_php", "smtp", "user");
//Penser à ajouter "_setitings"
$mail = array("mail", "sendmail", "smtp");

/* Récupération des contenus */
$conn = connectToDatabase($NAME_DB);
$name = $_GET['name'];
if($name == $MAIL)
    $res = NULL;
else
    $res = selectSQL ($name, $conn);

/* Génération du contenu principal */
if($res != null)
    generateContent($res);

/*Si le contenu cotient des sous-catégories, on les traite */
if($name == $DB){
    generateSubAccordion($name, $db, $conn);
}else if($name == $MISCELLANEOUS){
    generateSubAccordion($name, $miscellaneous, $conn);
}else if($name == $AUTHENTIFICATION){
    generateSubAccordion($name, $authentification, $conn);
}else if($name == $MAIL){
    generateSubAccordion($name, $mail, $conn);
}
closeSQL($conn);
    ?>
<script type="text/javascript">
$(function(){
      // Fonction Animation des Sous-catégories
      $('.subaccordion').accordion({
          heightStyle: "content",
          icons: { "header": "ui-icon-plus", "activeHeader": "ui-icon-minus" },
          active: false,
          collapsible: true 
      });  
    })
</script>
<?php
?>
<br><br>
<button type="submit" onclick="save('<?php echo $name; ?>');">Sauvegarder!</button>