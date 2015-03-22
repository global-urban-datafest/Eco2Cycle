<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 11/03/15
 * Time: 11:59
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
$_GET['page']='prod';
$_GET['type']='user';

include "../../template/menu.php";


?>

<!-- begin:product -->
<div class="container">
      <div id="catalogue">
            <div class="col-md-12">
                <h2>Products</h2>
                <h3>This are the products that you can buy with your EcoCoins</h3>
                <?php
                    $url='http://ecocicle.mybluemix.net/api/productpoint/produtosponto/sell/51';
                    $response2 = file_get_contents($url);
                    $pointProducts = json_decode($response2);
                    foreach ( $pointProducts as $pp){
                        $prod = json_decode(file_get_contents('http://ecocicle.mybluemix.net/api/product/'.$pp->productidProduto));?>

                        <ul class="thumbnails">
                            <li class="col-md-3 col-sm-3">
                                <div class="thumbnail">
                                    <img src="<?php echo $prod->img!=null?$prod->img:'user.png' ?>" class="img-responsive" alt="product dodolan manuk" />
                                    <div class="caption-details">
                                        <h3 align="center"> <?php echo $prod->product ?></h3>
                                        <h3 align="center">ecoCoin: <?php echo $pp->ecocoin?></h3>

                                        <!-- Button trigger modal -->
                                        <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal<?php echo $pp->productidProduto?>">
                                            BUY
                                        </button>

                                        <!-- Modal -->
                                        <div class="modal fade" id="myModal<?php echo $pp->productidProduto?>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title" id="myModalLabel"><?php echo $prod->product ?> </h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="jumbotron">
                                                            <h1><?php echo $prod->product ?></h1>
                                                            <p class="lead"><?php echo $prod->material ?></p>
                                                            <h2>Eco Coin: <?php echo $prod->ecocoin ?></h2>
                                                            <img  src="<?php echo $prod->img ?>" width="300px" height="300px" align="center" class="img-responsive">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                        <a href="detail.php?idProd=<?php echo $pp->idProdutoPonto?>">
                                                            <button type="button" class="btn btn-primary">Confirm</button>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    <?php }?>
                </div>
            </div>

    </div>
</div>

</body>
</html>
