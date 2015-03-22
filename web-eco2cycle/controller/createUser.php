<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 09/03/15
 * Time: 03:52
 */

// Start the session
session_start();

//$page =$_GET['page'];
$logado = false;

if ((isset($_SESSION['cliente']) && $_SESSION['cliente'] != '')) {

    $logado = true;
    $user = $_SESSION['cliente'];
    //header("Location: /view/user/map/");
    ////exit;
}



    //API Url
    $url = 'http://ecocicle.mybluemix.net/api/client/';


    //Initiate cURL.
    $ch = curl_init($url);

    //The JSON data.
   $jsonData = array(
       "idCliente"=>$_POST["idCliente"],
       "name"=>$_POST['name'],
       "rg" => $_POST['rg'],
       "cpf" => $_POST['cpf'],
       "email"=> $_POST['email'],
       "adress"=> $_POST['adress'],
       "login"=> $_POST['login'],
       "ativo"=> $_POST['ativo'],
       "pass"=>$_POST['password']
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

    $cliente = json_decode($result);

//    try {
//        if ($cliente->idCliente == "0") {
//            echo 'não logou';
//            session_destroy();
//        } else {
//            echo 'logou';
//            session_start();
//            $_SESSION['cliente'] = $cliente;
//            session_commit();
//            //echo session_status();
//
//        }
//    }catch(Exception $e){
//        session_destroy();
//    }
    if ($cliente->idCliente != "0") {
        header("Location: /view/user/map/");
        exit;
    }else{
        session_destroy();
        header("Location: /view/user/map/");
        exit;
    }



?>