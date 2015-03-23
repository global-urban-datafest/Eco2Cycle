<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 08/03/15
 * Time: 18:19
 */

?>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8" />
    <title>Eco2Cycle</title>
<!--    <link rel="stylesheet" href="http://eco2cycle.mybluemix.net/view/user/template/css/styles.css">-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <!--    css que faz a borda-->
    <link href="/view/css/bootstrap.min.css" rel="stylesheet">
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
        .navqw {
            line-height:30px;
            height:30%;
            width:250px;
            float:left;
            padding:5px;
        }
        .nave {
            line-height:30px;
            height:30%;
            width:250px;
            float:left;
            padding:5px;
        }
        .section {
            width:40%;
            float:left;
            padding:10px;
        }


    </style>
</head>
<body>
<?php
$_GET['page']='prof';
$_GET['type']='user';
include "../../template/menu.php";
//pega as informacoes do nivel do caboclo
$response = file_get_contents('http://ecocicle.mybluemix.net/api/client/profile/'.$user->idCliente);
$profile = json_decode($response);

if(!$logado){

}

?>
<div class="container">
    <div class="navqw">
        <div align="center">
            <h3 align="center"><?php echo $user->name ?></h3>
            <img  src="../profile/user.png" class="img-circle">
            <h4>Level <?php echo $profile->Level ?></h4>
            <div class="progress">

                <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo ($profile->xp*100)/$profile->next ?>%">
                    <?php echo $profile->xp." / ".$profile->next ?>
                    <span class="sr-only">90% Complete (success)</span>
                </div>
            </div>
            <h4>ECo-Coins: <?php

                $response = file_get_contents('http://ecocicle.mybluemix.net/api/client/coins/'.$user->idCliente);

                //echo $response;
                $coins = json_decode($response);

                echo  (floatval($coins->coins)-floatval($coins->xp)); ?></h4>
            <h5>Xp: <?php echo $coins->coins ?></h5>
        </div>
    </div>

    <div class="section">

        <h4>Glass </h4>
        <div class="progress">
            <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo ($profile->glass->xp*100)/$profile->glass->next?>%">
                <?php echo $profile->glass->xp."/".$profile->glass->next?>
                <span class="sr-only">40% Complete (success)</span>
            </div> Level <?php echo $profile->glass->Level ?>
        </div>

        <h4>Metal</h4>
        <div class="progress">
            <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width:  <?php echo ($profile->metal->xp*100)/$profile->metal->next?>%">
                <?php echo $profile->metal->xp."/".$profile->metal->next?>
                <span class="sr-only">20% Complete</span>
            </div> Level <?php echo $profile->metal->Level ?>
        </div>

        <h4>Plastic</h4>
        <div class="progress">
            <div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:  <?php echo ($profile->plastic->xp*100)/$profile->plastic->next?>%">
                <?php echo $profile->plastic->xp."/".$profile->plastic->next?>
                <span class="sr-only">60% Complete (warning)</span>
            </div> Level <?php echo $profile->plastic->Level ?>
        </div>

        <h4>Paper</h4>
        <div class="progress">
            <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width:  <?php echo ($profile->paper->xp*100)/$profile->paper->next?>%">
                <?php echo $profile->paper->xp."/".$profile->paper->next?>
                <span class="sr-only">80% Complete (danger)</span>
            </div> Level <?php echo $profile->paper->Level ?>
        </div>

        <h4>Organic</h4>
        <div class="progress">
            <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width:  <?php echo ($profile->organic->xp*100)/$profile->organic->next?>%">
                <?php echo $profile->organic->xp."/".$profile->organic->next?>
                <span class="sr-only">80% Complete (danger)</span>
            </div> Level <?php echo $profile->organic->Level ?>
        </div>

    </div>

</div>
</body>

