
<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 08/03/15
 * Time: 17:46
 */

// Start the session
session_start();

$page =$_GET['page'];
$logado = false;

if ((isset($_SESSION['point']) && $_SESSION['point'] != '')) {

    $logado = true;
    $point = $_SESSION['point'];

}else{
    header("Location: ../login/");
    exit;
}

?>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><img alt="Brand" src="/view/img/logoCycle.png"></a>

        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <?php if($logado){
                    echo "<li";?> <?php echo $page =='prof'?"class='active'":"" ?> <?php echo "><a href=\"../profile\"> Profile <span class=\"sr-only\">(current)</span></a></li>


                <li class=\"Profile\">
                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\"> <span class=\"caret\"></span></a>
                    <ul class=\"dropdown-menu\" role=\"menu\">
                        <li><a href=\"#\">"; echo  $point->responsavel!="null"?$point->responsavel:$point->responsavel; echo"</a></li>
                        <li><a href=\"/view/user/login/newuser.php\"> edit my profile</a></li>
                        <li class=\"divider\"></li>
                        <li><a href=\"../products\">Products</a></li>
                        <li><a href=\"#\"></a></li>
                        <li class=\"divider\"></li>

                    </ul>
                </li>
                 "; }?>

                <li <?php echo $page =='sim'?"class='active'":"" ?> ><a href="../coleta"> Collect <span class="sr-only">(current)</span></a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><?php echo $logado?"$point->responsavel":"Guest" ?> <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a <?php echo !$logado? "href=\"../login/\">Sign in":"href=\"../../../controller/point/logout.php\">Logout" ?>   </a></li>
                        <?php echo !$logado?"<li><a href=\"../login/newuser.php\" > SignUp</a></li>":"" ?>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">