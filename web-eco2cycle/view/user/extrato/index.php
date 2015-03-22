<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8" />
    <title>Eco2Cycle</title>
<!--    <link rel="stylesheet" href="/view/css/styles.css">-->
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
<script type="text/css">
    @media only screen and (max-width: 800px) {

        /* Force table to not be like tables anymore */
        #no-more-tables table,
        #no-more-tables thead,
        #no-more-tables tbody,
        #no-more-tables th,
        #no-more-tables td,
        #no-more-tables tr {
            display: block;
        }

        /* Hide table headers (but not display: none;, for accessibility) */
        #no-more-tables thead tr {
            position: absolute;
            top: -9999px;
            left: -9999px;
        }

        #no-more-tables tr { border: 1px solid #ccc; }

        #no-more-tables td {
            /* Behave  like a "row" */
            border: none;
            border-bottom: 1px solid #eee;
            position: relative;
            padding-left: 50%;
            white-space: normal;
            text-align:left;
        }

        #no-more-tables td:before {
            /* Now like a table header */
            position: absolute;
            /* Top/left values mimic padding */
            top: 6px;
            left: 6px;
            width: 45%;
            padding-right: 10px;
            white-space: nowrap;
            text-align:left;
            font-weight: bold;
        }

        /*
        Label the data
        */
        #no-more-tables td:before { content: attr(data-title); }

    }


</script>

</head>
<body>
<?php
$_GET['page']='prof';
$_GET['type']='user';

include "../../template/menu.php";

$response = file_get_contents('http://ecocicle.mybluemix.net/api/client/operations/sell/'.$user->idCliente);
$operations = json_decode($response);

?>
<div class="container">
    <div class="navqw">
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
    <div class="section" >
        <div class="row">
            <div class="col-md-12">
                <h1 class="text-center">
                    Statement
                </h1>
                <h3 class="text-center">
                    see their eco won
                </h3>
            </div>
            <div id="no-more-tables">
                <table class="col-md-12 table-bordered table-striped table-condensed cf">
                    <thead class="cf">
                    <tr>
                        <th>Code</th>
                        <th>Point</th>
                        <th>Material</th>
                        <th>Product</th>
                        <th class="numeric">price</th>
                        <th class="numeric">Ecocoin</th>
                        <th class="numeric">date</th>
                    </tr>
                    </thead>

                    <tbody>
                    <?php foreach ($operations as  $operation) {


                        ?>
                        <tr>
                            <td data-title="Code"><?php echo $operation->idOperacao?> </td>
                            <td data-title="Company"><?php echo  $operation->point?></td>
                            <td data-title="Company"><?php echo  $operation->material?></td>
                            <td data-title="Company"><?php echo  $operation->product?></td>
                            <td data-title="Price" class="numeric">$<?php echo  $operation->price?></td>
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
</html>
