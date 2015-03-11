<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 08/03/15
 * Time: 18:19
 */


include "../template/header.php" ?>
</head>
<body>
<?php
$_GET['page']='prof';
include "../template/menu.php"


?>

    <div id="navqw">
        <div align="center">
            <h3 align="center"><?php echo $user->login ?></h3>
            <img  src="../profile/user.png" class="img-circle">
            <h4>Level 1</h4>
            <div class="progress">

                <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 77%">77/100
                    <span class="sr-only">77% Complete (success)</span>
                </div>
            </div>
            <h4>ECo-Coins: <?php

                $response = file_get_contents('http://ecociclews.mybluemix.net/api/client/coins/'.$user->idCliente);

                //echo $response;
                $coins = json_decode($response);

                echo  (floatval($coins->coins)-floatval($coins->xp)); ?></h4>
            <h5>Xp: <?php

                echo $coins->coins ?></h5>
        </div>
    </div>

    <div id="section" ">

        <h4>Glass</h4>
        <div class="progress">

            <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">40/100
                <span class="sr-only">40% Complete (success)</span>
            </div> Level 1
        </div>

        <h4>Metal</h4>
        <div class="progress">
            <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">40/200
                <span class="sr-only">20% Complete</span>
            </div>Level 2
        </div>

        <h4>Plastic</h4>
        <div class="progress">
            <div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%"> 240/400
                <span class="sr-only">60% Complete (warning)</span>
            </div> Level 4
        </div>

        <h4>Paper</h4>
        <div class="progress">
            <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">180/200
                <span class="sr-only">80% Complete (danger)</span>Level 2
            </div>
        </div>

        <h4>Organic</h4>
        <div class="progress">
            <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">180/200
                <span class="sr-only">80% Complete (danger)</span>Level 2
            </div>
        </div>

    </div>
</body>
<?php include "../template/foot.php" ?>
