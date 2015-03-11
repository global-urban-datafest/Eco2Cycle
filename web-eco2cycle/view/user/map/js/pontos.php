<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 08/03/15
 * Time: 18:33
 */

$response = file_get_contents('http://ecociclews.mybluemix.net/api/point/0');

echo $response;
?>