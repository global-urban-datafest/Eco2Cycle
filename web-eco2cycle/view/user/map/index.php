<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 08/03/15
 * Time: 17:46
 */

include "../template/header.php" ?>
</head>
<body>
<?php
$_GET['page']='map';
include "../template/menu.php" ?>


        <!-- aqui começa o mapa -->
    	<div id="mapa" style="width: 100%; height: 100%" > </div>
		
		<script src="js/jquery.min.js"></script>
 
        <!-- Maps API Javascript -->
        <script src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
        
        <!-- Caixa de informação -->
        <script src="js/infobox.js"></script>
		
        <!-- Agrupamento dos marcadores -->
		<script src="js/markerclusterer.js"></script>
 
        <!-- Arquivo de inicialização do mapa -->
		<script src="js/mapa.js"></script>
    </body>
</html>