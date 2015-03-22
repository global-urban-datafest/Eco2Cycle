<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 12/03/15
 * Time: 14:02
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
</head>
<body>

<?php
$_GET['page']='prod';
$_GET['type']='user';

include "../../template/menu.php";


if ($logado){ //caboclo logadao


    $idProd = $_GET['idProd'];
    //echo $idProd;
    //API Url
    $url = 'http://ecocicle.mybluemix.net/api/operation/';


    //Initiate cURL.
    $ch = curl_init($url);

    //$prodPoint = file_get_contents('http://ecocicle.mybluemix.net/api/productpoint/'.$_GET['idProd']);

//    $prod  = file_get_contents('http://ecocicle.mybluemix.net/api/product/'.$prodPoint->);
//    $prod = json_decode($respo);

//    $jsonPonto = array("idPontoColeta"=>$point->idPontoColeta);
//    $jsonPonto = json_encode($jsonPonto);

    $prodPoint =json_decode(file_get_contents('http://ecocicle.mybluemix.net/api/productpoint/'.$idProd));
    //echo $prodPoint;

    $prod = file_get_contents('http://ecocicle.mybluemix.net/api/product/'.$prodPoint->productidProduto);

    $saldo = json_decode(file_get_contents('http://ecocicle.mybluemix.net/api/client/coins/'.$user->idCliente));


    $sai=json_decode( "{\"idProdutoPonto\":\"$idProd\"}");
//    echo "produtio ".$prodPoint;

    if((floatval($saldo->coins) - floatval($saldo->xp)) >= floatval($prodPoint->ecocoin)) {

        //The JSON data.
        $jsonData = array(
            "idOperacao" => "0",
            "price" => "0",
            "ecoCoin" => $prodPoint->ecocoin,
            "productPointidProdutoPonto" =>$sai,
            "buy" => "true",
            "clientidCliente" => $user
        );

        //Encode the array into JSON.
        $jsonDataEncoded = json_encode($jsonData);
        //echo $jsonDataEncoded;

        //Tell cURL that we want to send a POST request.
        curl_setopt($ch, CURLOPT_POST, 1);

        //Attach our encoded JSON string to the POST fields.
        curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);

        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        //Set the content type to application/json
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));

        //Execute the request
        $result = curl_exec($ch);
        //echo $result;
        $prod =json_decode(file_get_contents('http://ecocicle.mybluemix.net/api/product/'.$prodPoint->productidProduto));
        ?>

<div class="container">
        <div class="modal-body">

            <div class="jumbotron">
                <h1 align="center"> Sucesfull </h1>

                <h1 align="center"> the product: </h1>

                <h2 align="center"><?php echo $prod->product ?>  </h2>

                <h2 align="center">Eco Coin: <?php echo $prod->ecocoin ?> </h2>

                <h2 align="center">
                    will be delivered to your residence in 2 or 3 days
                    <img  src="<?php echo $prod->img ?>" width="400px" height="400px" class="img-responsive">
                </h2>

            </div>
        </div>
</div>
        <?php
    }else{?>

    <div class="container">
        <div class="modal-body">
            <div class="jumbotron">
                <h1 align="center"> ERROR </h1>

                <h1 align="center">You do not have sufficient ecocoins </h1>



            </div>
        </div>
</div>
    <?php }



}else {
    ?>

<div class="container">
    <div class="modal-body">
        <div class="jumbotron">

            <h1 align="center"> ERROR </h1>

            <h1 align="center"> you must be logged in to make purchases </h1>

        </div>
    </div>
    </div>
<?php }

include "../../template/foot.php" ?>
