<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 09/03/15
 * Time: 12:40
 */
session_start();



$response = file_get_contents('http://ecociclews.mybluemix.net/api/product/0');

//echo $response;

echo $response;

?>