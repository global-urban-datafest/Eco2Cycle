<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 08/03/15
 * Time: 18:19
 */


if ((isset($_SESSION['point']) && $_SESSION['point'] != '')) {

    $logado = true;
    $user = $_SESSION['point'];
}

include "../template/header.php"?>


</head>
<body>
<?php
$_GET['page']='prof';
include "../template/menu.php";

//pega as informacoes do nivel do caboclo
$response = file_get_contents('http://ecocicle.mybluemix.net/api/point/profile/'.$point->idPontoColeta);
$profile = json_decode($response);
//echo $profile;
//echo $profile->Level;
?>


    <div class="navqw"; ">
        <div align="center">
            <h3 align="center"><?php echo $point->responsavel ?></h3>
            <img  src="../profile/user.png" class="img-circle">
            <h4>Level <?php echo $profile->Level?></h4>
            <div class="progress">

                <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo ($profile->xp*100)/$profile->next ?>%">
                    <?php echo $profile->xp?> / <?php echo $profile->next?>
                    <span class="sr-only">77% Complete (success)</span>
                </div>
            </div>
            <h4>E-Co: <?php echo $profile->xp?></h4>
        </div>
    </div>

<div class="section">

    <h4>Glass </h4>
    <div class="progress">
        <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: <?php echo ($profile->glass->xp*100)/$profile->glass->next?>%">
            <?php echo $profile->glass->xp."/".$profile->glass->next?>
            <span class="sr-only">40% Complete (success)</span>
        </div> Level <?php echo $profile->glass->Level ?>
    </div>

    <h4>Metal</h4>
    <div class="progress">
        <div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width:  <?php echo ($profile->metal->xp*100)/$profile->metal->next?>%">
            <?php echo $profile->metal->xp."/".$profile->metal->next?>
            <span class="sr-only">20% Complete</span>
        </div> Level <?php echo $profile->metal->Level ?>
    </div>

    <h4>Plastic</h4>
    <div class="progress">
        <div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:  <?php echo ($profile->plastic->xp*100)/$profile->plastic->next?>%">
            <?php echo $profile->plastic->xp."/".$profile->plastic->next?>
            <span class="sr-only">60% Complete (warning)</span>
        </div> Level <?php echo $profile->plastic->Level ?>
    </div>

    <h4>Paper</h4>
    <div class="progress">
        <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width:  <?php echo ($profile->paper->xp*100)/$profile->paper->next?>%">
            <?php echo $profile->paper->xp."/".$profile->paper->next?>
            <span class="sr-only">80% Complete (danger)</span>
        </div> Level <?php echo $profile->paper->Level ?>
    </div>

    <h4>Organic</h4>
    <div class="progress">
        <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width:  <?php echo ($profile->organic->xp*100)/$profile->organic->next?>%">
            <?php echo $profile->organic->xp."/".$profile->organic->next?>
            <span class="sr-only">80% Complete (danger)</span>
        </div> Level <?php echo $profile->organic->Level ?>
    </div>

</div>
</body>
<?php include "../template/foot.php" ?>
