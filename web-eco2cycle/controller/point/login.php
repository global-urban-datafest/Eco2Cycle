<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 09/03/15
 * Time: 14:27
 */

// Start the session
session_start();

//$page =$_GET['page'];
$logado = false;

if ((isset($_SESSION['point']) && $_SESSION['point'] != '')) {

    $logado = true;
    $user = $_SESSION['point'];
    header("Location: ../../view/point/");
    exit;
}



//API Url
$url = 'http://ecocicle.mybluemix.net/api/point/login/';


//Initiate cURL.
$ch = curl_init($url);

//The JSON data.
$jsonData = array(
    "idPontoColeta"=>"",
    "descricao"=>"",
    "responsavel"=>"",
    "endereco"=>"",
    "latitude"=>"",
    "longitude"=>"",
    "login"=>$_POST['username'],
    "password"=>$_POST['password'],
    "tipo"=>"",
    "ativo"=>"",
    "phone"=>"",
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
echo $result;

$point = json_decode($result);

try {
    if ($point->idPontoColeta == "0") {
        echo 'não logou';
        session_destroy();
    } else {
        echo 'logou '.$point->responsavel;

        //session_start();
        $_SESSION['point'] = $point;

        session_commit();
        header("Location: ../../view/point/profile/");
        exit;

    }
}catch(Exception $e){
    session_destroy();
}

?>