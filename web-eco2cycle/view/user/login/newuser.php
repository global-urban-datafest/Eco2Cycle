<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 09/03/15
 * Time: 03:57
 */
?>

<?php
$logado = false;
if ((isset($_SESSION['cliente']) && $_SESSION['cliente'] != '')) {

    $logado = true;
    $user = $_SESSION['cliente'];

}

include "../template/header.php" ?>
</head>
<body>
<?php
$_GET['page']='prof';
include "../template/menu.php" ?>

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title"> <?php echo !$logado?"Please sign up for Eco2Cycle <small>It's free!</small>":"update your profile"?>  </h1>
            <div class="account-wall">
                <form class="form-signup" method="post" action="../../../controller/createUser.php">
                    <input type="hidden" class="form-control" placeholder="idCliente"  name="idCliente" value=<?php echo  $logado?$user->idCliente.">":"0>" ?>
                    <input type="text" class="form-control" placeholder="Name" required autofocus name="name" value=<?php echo  $logado?$user->name.">":">"?>
                    <input type="text" class="form-control" placeholder="rg" required autofocus name="rg"value=<?php echo  $logado?$user->rg.">":">"?>
                    <input type="text" class="form-control" placeholder="Cpf" required autofocus name="cpf"value=<?php echo  $logado?$user->cpf.">":">"?>
                    <input type="text" class="form-control" placeholder="Email" required autofocus name="email"value=<?php echo  $logado?$user->email.">":">"?>
                    <input type="text" class="form-control" placeholder="Adress" required autofocus name="adress"value=<?php echo  $logado?$user->adress.">":">"?>
                    <input type="text" class="form-control" placeholder="Login" required autofocus name="login"value=<?php echo  $logado?$user->login.">":">"?>
                    <input  type="hidden" class="form-control" placeholder="ativo" required autofocus name="ativo" value="0"value=<?php echo  $logado?$user->ativo.">":">"?>

                    <input type="password" class="form-control" placeholder="Password" required name="password">


                    <button class="btn btn-lg btn-primary btn-block" type="submit">
                        Confirm</button>
                    <a href="#" class="pull-right need-help">Need help? </a><span class="clearfix"></span>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
</body>
</html>