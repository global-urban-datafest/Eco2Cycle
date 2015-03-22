<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 08/03/15
 * Time: 17:46
 */
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Eco2Cycle</title>
    <link rel="stylesheet" href="http://eco2cycle.mybluemix.net/view/user/template/css/styles.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <style type="text/css">
        .bs-example{
            margin: 20px;
        }
        /* Fix alignment issue of label on extra small devices in Bootstrap 3.2 */
        .form-horizontal .control-label{
            padding-top: 7px;
        }
        body,html{
            height: 100%;
            width: 100%;
        }

    </style>
<link rel="stylesheet" href="css/estilo.css">
</head>
<body>
<?php
$_GET['page']='map';
$_GET['type']='user';

include "../../template/menu.php" ?>


        <!-- aqui começa o mapa -->
    	<div id="mapa" style="width: 100%; height: 100%" > </div>
		
		<script src="js/jquery.min.js"></script>

        <script type="text/javascript"
            src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCBVQwUdz8unNtIAB7E7lC8AIxOqBgvV2k&sensor=false">
        </script>
        
        <!-- Caixa de informação -->
        <script src="js/infobox.js"></script>
		
        <!-- Agrupamento dos marcadores -->
		<script src="js/markerclusterer.js"></script>
 
        <!-- Arquivo de inicialização do mapa -->
		<script src="js/mapa.js"></script>
    </body>
</html>