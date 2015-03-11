
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

if ((isset($_SESSION['cliente']) && $_SESSION['cliente'] != '')) {

    $logado = true;
    $user = $_SESSION['cliente'];
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
            <a class="navbar-brand" href="/">E2C</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <?php if($logado){
                    echo "<li";?> <?php echo $page =='prof'?"class='active'":"" ?> <?php echo "><a href=\"../profile\"> Profile <span class=\"sr-only\">(current)</span></a></li>


                <li class=\"Profile\">
                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\"> <span class=\"caret\"></span></a>
                    <ul class=\"dropdown-menu\" role=\"menu\">
                        <li><a href=\"#\">"; echo  $user->name!="null"?$user->name:$user->login; echo "</a></li>
                        <li><a href=\"/view/user/login/newuser.php\"> edit my profile</a></li>
                        <li class=\"divider\"></li>
                        <li><a href=\"../extrato\">eco-Coins Statement </a></li>
                        <li><a href=\"#\">my exchanges</a></li>
                        <li class=\"divider\"></li>
                    </ul>
                </li>
                 "; }?>

                <li <?php echo $page =='map'?"class='active'":"" ?> ><a href="../map"> Map <span class="sr-only">(current)</span></a></li>
                <li <?php echo $page =='sim'?"class='active'":"" ?> ><a href="../simulation"> Simular reciclagem <span class="sr-only">(current)</span></a></li>
            </ul>


            <ul class="nav navbar-nav navbar-right">

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><?php echo $logado?$user->login:"Guest" ?> <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a <?php echo !$logado? "href=\"../login/\">Sign in":"href=\"../../../controller/logout.php\">Logout" ?>   </a></li>
                        <?php echo !$logado?"<li><a href=\"../login/newuser.php\" > SignUp</a></li>":"" ?>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
