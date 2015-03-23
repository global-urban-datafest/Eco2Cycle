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
 ?>
<!DOCTYPE html>
<html lang="pt-br">
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