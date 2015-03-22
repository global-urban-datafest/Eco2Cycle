<?php
/**
 * Created by PhpStorm.
 * User: guardezi
 * Date: 08/03/15
 * Time: 18:19
 */

?>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8" />
        <title>Eco2Cycle</title>
        <link rel="stylesheet" href="http://eco2cycle.mybluemix.net/view/user/template/css/styles.css">

        <link rel="stylesheet" href="/view/css/bootstrap.min.css">

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

        <script type="text/css">
            @media only screen and (max-width: 800px) {

                /* Force table to not be like tables anymore */
                #no-more-tables table,
                #no-more-tables thead,
                #no-more-tables tbody,
                #no-more-tables th,
                #no-more-tables td,
                #no-more-tables tr {
                    display: block;
                }

                /* Hide table headers (but not display: none;, for accessibility) */
                #no-more-tables thead tr {
                    position: absolute;
                    top: -9999px;
                    left: -9999px;
                }

                #no-more-tables tr { border: 1px solid #ccc; }

                #no-more-tables td {
                    /* Behave  like a "row" */
                    border: none;
                    border-bottom: 1px solid #eee;
                    position: relative;
                    padding-left: 50%;
                    white-space: normal;
                    text-align:left;
                }

                #no-more-tables td:before {
                    /* Now like a table header */
                    position: absolute;
                    /* Top/left values mimic padding */
                    top: 6px;
                    left: 6px;
                    width: 45%;
                    padding-right: 10px;
                    white-space: nowrap;
                    text-align:left;
                    font-weight: bold;
                }

                /*
                Label the data
                */
                #no-more-tables td:before { content: attr(data-title); }
            }


        </script>

</head>
<body>
    <?php
    $_GET['page']='prof';
    $_GET['type']='user';

    include "../../template/menu.php";


    $id=$_GET['id'];

    ?>

    <?php
    $url=file_get_contents('http://ecocicle.mybluemix.net/api/productpoint/produtosponto/'.$id);
    $products=json_decode($url);
    //echo $url;

    $urlPoint =file_get_contents('http://ecocicle.mybluemix.net/api/point/'.$id);
    $point = json_decode($urlPoint);

    ?>
    <div id="navqw">
        <div align="center">
            <h3 align="center"><?php echo $point->descricao ?></h3>
            <img  src="../profile/user.png" class="img-circle">
        <!--        <h4>Level 1</h4>-->
        <!--        <div class="progress">-->
        <!--            <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 77%">77/100-->
        <!--                <span class="sr-only">77% Complete (success)</span>-->
        <!--            </div>-->
        <!--        </div>-->
            <h3 align="center"><?php echo $point->endereco ?></h3>
        </div>
    </div>
    <div id="section" class="container">
        <div class="row">
            <div class="col-md-12">
                <h1 class="text-center">
                    Products
                </h1>
                <h3 class="text-center">
                    this are the products  that we recycle
                </h3>
            </div>
            <div id="no-more-tables">
                <table class="col-md-12 table-bordered table-striped table-condensed cf">
                    <thead class="cf">
                    <tr>
                        <th>Code</th>
                        <th>Material</th>
                        <th>Product</th>
                        <th class="numeric">price</th>
                        <th class="numeric">Ecocoin</th>
                    </tr>
                    </thead>

                    <tbody>
                    <?php foreach ($products as  $operation) {
                        $prod = file_get_contents('http://ecocicle.mybluemix.net/api/product/'.$operation->productidProduto);
                        $prod = json_decode($prod);
                        ?>
                        <tr>
                            <td data-title="Code"><?php echo $operation->productidProduto ?> </td>
                            <td data-title="Company"><?php echo  $prod->material ?></td>
                            <td data-title="Company"><?php echo  $prod->product ?></td>
                            <td data-title="Price" class="numeric">$<?php echo  $operation->price ?></td>
                            <td data-title="Change" class="numeric">$<?php echo  $operation->ecocoin ?></td>
                        </tr>
                    <?php }?>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

<?php include "../template/foot.php"?>