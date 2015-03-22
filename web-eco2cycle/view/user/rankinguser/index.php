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
$_GET['page']='rank';
$_GET['type']='user';

include "../../template/menu.php";

$response = file_get_contents('http://ecocicle.mybluemix.net/api/client/ranking');
$operations = json_decode($response);

?>
    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="text-center">
                        Ranking
                    </h1>
                    <h3 class="text-center">
                        Ranking of top recyclers
                    </h3>
                </div>
                <div id="no-more-tables">
                    <table class="col-md-12 table-bordered table-striped table-condensed cf">
                        <thead class="cf">
                        <tr>
                            <th>Position</th>
                            <th>Name</th>
                            <th>Points</th>
                        </tr>
                        </thead>

                        <tbody>
                        <?php
                            $position=1;
                            foreach ($operations as  $operation) {
                                if($operation->points==0){
                                    break;
                                }

                            ?>
                            <tr>
                                <td data-title="Code"><?php echo $position ?> </td>
                                <td data-title="Company"><?php echo  $operation->name ?></td>
                                <td data-title="Company"><?php echo  $operation->points ?></td>


                            </tr>
                        <?php
                                $position++;
                                if($position>10){
                                    break;
                                }
                            }?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</body>