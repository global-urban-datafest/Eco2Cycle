<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 09/03/15
 * Time: 02:01
 */

session_start();
session_destroy();
header("Location: /view/user/map/");
exit;
?>