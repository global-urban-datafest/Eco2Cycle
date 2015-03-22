<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 10/03/15
 * Time: 01:49
 */
session_start();


//$page =$_GET['page'];
$logado = false;

if ((isset($_SESSION['point']) && $_SESSION['point'] != '')) {

    $logado = true;
    $point = $_SESSION['point'];
    //header("Location: /view/user/map/");
    ////exit;
}



//API Url
$url = 'http://ecocicle.mybluemix.net/api/productpoint/';


//Initiate cURL.
$ch = curl_init($url);



$respo  = file_get_contents('http://ecocicle.mybluemix.net/api/product/'.$_POST['idProd']);
$prod = json_decode($respo);

$jsonPonto = array("idPontoColeta"=>$point->idPontoColeta);
$jsonPonto = json_encode($jsonPonto);
$jsonPonto = json_decode($jsonPonto);


//The JSON data.
$jsonData = array(
    "idProdutoPonto"=>"0",
    "pontoTrocaidPontoColeta"=> $jsonPonto,
    "productidProduto"=>$prod,
    "price"=> $_POST['price'],
    "ecocoin"=>$prod->ecocoin
);

//Encode the array into JSON.
$jsonDataEncoded = json_encode($jsonData);
echo $jsonDataEncoded;

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

    header("Location: ../../view/point/products/");
    exit;
//}



?>
