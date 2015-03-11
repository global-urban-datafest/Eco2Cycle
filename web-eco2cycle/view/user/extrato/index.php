<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 08/03/15
 * Time: 18:19
 */


include "../template/header.php" ?>

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
include "../template/menu.php";

$response = file_get_contents('http://ecociclews.mybluemix.net/api/client/operations/'.$user->idCliente);
$operations = json_decode($response);

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
    <div id="section" class="container">
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


</body>
<?php include "../template/foot.php" ?>
