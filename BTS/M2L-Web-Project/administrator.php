<?php
require_once 'defaultincludes.inc';
require_once 'mincals.inc';
require_once 'functions.inc';

//Chech the user is authorized for this page
checkAuthorised();

//Print the header
print_header($day, $month, $year, $area, isset($room) ? $room : "");
//Get admin level of current user
$userLevel = getUserLvel(getUserName());
?>

<!-- Print Content -->
<h1>Paramètres du système MDL</h1>
<div class="accordion">
    <?php
    //Get all tables names in mrbs_config and check level
    $table = mysql_query("SHOW TABLES IN mrbs_config");
    while ($row = mysql_fetch_row($table)) {
        $sql = mysql_query("SELECT value FROM $row[0] WHERE name = 'Level'");
        $value = mysql_fetch_row($sql);
        if($value[0] <= $userLevel){
            createChamp($row[0]);
        }
    }
    
    //Funtion for draw champ
    function createChamp($name){
        ?>
        <h3 onclick="get('<?php echo $name; ?>', '#<?php echo $name; ?>')"><?php echo $name; ?></h3>
        <div id="<?php echo $name; ?>">
            <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
        </div>
        <?php
    }
    ?>
    <!--<h3 onclick="get('db', '#db')">Database</h3>
    <div id="db">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('administration', '#administration')">Administration</h3>
    <div id="administration">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('theme', '#theme')">Theme</h3>
    <div id="theme">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('calendar', '#calendar')">Calendar</h3>
    <div id="calendar">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('booking_policies', '#booking_policies')" >Booking policies</h3>
    <div id="booking_policies">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('display', '#display')">Display</h3>
    <div id="display">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('miscellaneous', '#miscellaneous')">Miscellaneous Settings</h3>
    <div id="miscellaneous">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('form_values', '#form_values')">Form Values</h3>
    <div id="form_values">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('auth', '#auth')">Authentication Settings</h3>
    <div id="auth">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('mail', '#mail')">Email Settings</h3>
    <div id="mail">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('language', '#language')">Language</h3>
    <div id="language">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('reports', '#reports')">Reports</h3>
    <div id="reports">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('entry_types', '#entry_types')">Entry Types</h3>
    <div id="entry_types">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    <h3 onclick="get('version', '#version')">Version</h3>
    <div id="version">
        <img src="images/ajax_loader.gif" style="width: 50px; height: 50px;" class="loader" />
    </div>
    -->
</div>

<script>
        /*
         * Fonction get (AJAX)
         * @parameter: table: String
         * @parameter: name: String
         * retrun: void
         */
        function get(table, name) {
            $(function() {
                $.ajax({
                    url: "getAdministration.php",
                    type: "GET",
                    data: {
                        name: table
                    },
                    dataType: "text",
                    success: function(data) {
                        $(name).find("#loader").remove();//css({'visibility': 'hidden'});
                        $(name).html(data);
                    },
                    error: function() {
                        $(name).find("#loader").remove();
                        $(name).html("<strong>Une erreur s'est produite!<strong>")
                    }
                });
            });
        }
        
        /*
         * Fonction save (AJAX)
         * @parameter: name: String
         * retrun: void
         */
        function save(name){
        //On parcourt tous les éléments non présents dans des sous-listes
           var Data = new Array();
            $('#'+name+' > .firstaccordion').find('input').each(function(){
                    var element = {
                        "name": $(this).prev().text(),
                        "value": $(this).val()
                    }
                    Data.push(element);
            });
            $('#'+name+' > .firstaccordion').find('select').each(function(){
                    var element = {
                        "name": $(this).prev().text(),
                        "value": $(this).val()
                    }
                    Data.push(element);
            });
            //On parcourt tous les éléments présents dans des sous-listes
            $('#'+name).find('.subaccordion').each(function(){
                 var SubData = new Array();
                 var title = {
                     "name": "title",
                     "value": $(this).attr("id")
                 }
                 SubData.push(title);
                 $(this).find('input').each(function(){
                    var element = {
                         "name": $(this).prev().text(),
                         "value": $(this).val()
                    };
                    SubData.push(element);
                });
               $(this).find('select').each(function(){
                    var element = {
                         "name": $(this).prev().text(),
                         "value": $(this).val()
                    };
                    SubData.push(element);
                });
                Data.push(SubData);
            });
           
           //On envoie la sauvegarde
           $.ajax({
                url: "setAdministration.php",
                type: "GET",
                data: {
                    name: name,
                    data: Data
                },
                beforeSend: function() {
                    if($('#'+name).find('span.result').val() !== null)
                        $('#'+name).find('span.result').remove();
                    $('#'+name).find('button').after('<img src="images/ajax_loader.gif" style="width: 15px; height: 15px;" class="loader" />');
                },
                success: function() {
                    $('#'+name).find('img').remove();
                    $('#'+name).find('button').after('<span class="result" style="color: green">Les données ont bien été enregistré!</span>');
                },
                error: function() {
                    $('#'+name).find('img').remove();
                    $('#'+name).find('button').after('<span class="result" style="color: red"><strong>Une erreur est survenue, veuillez réessayer!</strong></span>');
                }
            });
        }
</script>
<?php
require_once 'trailer.inc';
?>