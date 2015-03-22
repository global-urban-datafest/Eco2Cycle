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
    <link rel="stylesheet" href="http://eco2cycle.mybluemix.net/view/user/template/css/styles.css">
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

    </style>
</head>
<body>
<?php
$_GET['page']='prof';
$_GET['type']='user';

include "../../template/menu.php";

$response = file_get_contents('http://ecocicle.mybluemix.net/api/client/operations/buy/'.$user->idCliente);
$operations = json_decode($response);

?>
<div class="container">
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

                $response = file_get_contents('http://ecocicle.mybluemix.net/api/client/coins/'.$user->idCliente);

                //echo $response;
                $coins = json_decode($response);

                echo  (floatval($coins->coins)-floatval($coins->xp)); ?></h4>
            <h5>Xp: <?php

                echo $coins->coins ?></h5>
        </div>
    </div>
    <div id="section" class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 class="text-center">
                    Purchases
                </h1>
                <h3 class="text-center">
                    purchases made with ECO-COIN
                </h3>
            </div>
            <div id="no-more-tables">
                <table class="col-md-12 table-bordered table-striped table-condensed cf">
                    <thead class="cf">
                    <tr>
                        <th>Code</th>
                        <th>Product</th>
                        <th class="numeric">Ecocoin</th>
                        <th class="numeric">date</th>
                    </tr>
                    </thead>

                    <tbody>
                    <?php foreach ($operations as  $operation) {


                        ?>
                        <tr>
                            <td data-title="Code"><?php echo $operation->idOperacao?> </td>
                            <td data-title="Company"><?php echo  $operation->product?></td>
                            <td data-title="Change" class="numeric"><?php echo  $operation->ecocoin?></td>
                            <td data-title="Change %" class="numeric"><?php echo  $operation->date?></td>

                        </tr>
                    <?php }?>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

</div>
</body>
