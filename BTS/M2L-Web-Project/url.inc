<?php	


	/**
		Initialisation des variables
	**/

	function formatURLFull($year, $month, $day, $area, $room)
	{
            if($room != NULL){
                $string = "$year-$month-$day/area:$area-room:$room";
            }else{
                $string = "$year-$month-$day/area:$area";
            }
            return $string;
	}

        function formatURLDate($year, $month, $day)
	{
            $string = "$year-$month-$day";
            return $string;
	}
        
        function formatURLArea($area, $room)
        {
            $string = "area:$area";
            if($room != NULL)
                $string .= "-room:$room";
            return $string;
        }
        
        function formatURLPeriods($year, $month, $day, $area, $room, $period) {
            $string = "$year-$month-$day/area:$area-room:$room-period:$period";
            return $string;;
        }
        
        function formatURLTimes($year, $month, $day, $area, $room, $hour, $minute){
            if($room != NULL){
                $string = "$year-$month-$day-$hour-$minute/area:$area-room:$room";
            }else{
                $string = "$year-$month-$day-$hour-$minute/area:$area";
            }
            return $string;
        }
        
        function formatURLError($area, $error){
            if($error != NULL)
                $string = "area:$area-error:$error";
            else
                $string = formatURLArea($area, NULL);
            return $string;
        }
        
        function formatURLSearch($from_year, $from_month, $from_day, $search_str, $total, $search_pos) {
            $string = "$from_year-$from_month-$from_day/search_str:$search_str-total:$total";
            if($search_pos != NULL)
                $string .= "-search_pos:$search_pos";
            return $string;
        }

        function formatURLReport($from_day, $from_month, $from_year, $user) {
            $string = "$from_year-$from_month-$from_day(1-12-2030)/areamatch:-roommatch:-namematch:-descrmatch:-summarize:1-sortby:r-display:d-sumby:d-creatormatch:$user";
            return $string;
        }
        
        function formatURLReturnUrl($id, $returl, $day, $month, $year, $edit_type){
            if($edit_type && $day != NULL && $month != NULL && $year != NULL)
                $string = "$year-$month$day/id:$id-returl:$returl-edit_type:series";
            else
                $string = "id:$id-returl:$returl";
            return $string;
        }
?>