<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Eco2Cycle</title>
    <link rel="stylesheet" href="http://eco2cycle.mybluemix.net/view/user/template/css/styles.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/view/css/bootstrap.min.css">
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

    <meta charset="utf-8">

  <link rel="stylesheet" type="text/css" href="dist/css/bootstrap-select.css">
  <script type="text/javascript" src="dist/js/bootstrap-select.js"></script>

<script>
    function pegaParada()
    {
        var e = document.getElementById("first-disabled").value;


        var eco = e.split(",");
        document.getElementById("eco").value = eco[1];
        document.getElementById("price").value = eco[0];

        var qtd = document.getElementById("qtd").value;
        document.getElementById("amount").innerHTML = "Amount "+eco[2];
        document.getElementById("result").innerHTML = "Eco-Coin "+ (parseFloat(eco[1])*parseFloat(qtd))+" R$ "+ (parseFloat(eco[0])*parseFloat(qtd));
    }

</script>
</head>
<body>

<?php
$_GET['page']='sim';
$_GET['type']='user';
include "../../template/menu.php" ?>

<?php
$response = file_get_contents('http://ecocicle.mybluemix.net/api/product/torecycle/0');
$products = json_decode($response);

?>


<div class="container">


    <h2 align="center">Simulation</h2>

   <select id="first-disabled"  data-hide-disabled="true" data-live-search="true" onclick="pegaParada()">
    <optgroup disabled="disabled" label="disabled">
      <option>Hidden</option>
    </optgroup>

      <?php foreach($products as $product){
        echo "<option value=\" $product->price,$product->ecocoin,$product->unity\">".$product->product."</option>";
        }?>

  </select>
    <div style="width: 400px;alignment: ">
        <div class="input-group" style="bottom: 5px; top: 3px">
            <span class="input-group-addon" id="sizing-addon2">Eco-Coin</span>
            <input id="eco" type="text" class="form-control" placeholder="Eco-Coin" aria-describedby="sizing-addon2">
        </div>
    </div>
    <div style="width: 400px; top: 5px;">
        <div class="input-group" style="bottom: 5px; top: 3px">
            <span class="input-group-addon" id="sizing-addon2">Price</span>
            <input id="price" type="text" class="form-control" placeholder="Price" aria-describedby="sizing-addon2">
        </div>
    </div>

    <div style="width: 400px">
        <div class="input-group" style="bottom: 5px; top: 3px">
            <span class="input-group-addon" id="amount">Amount </span>
            <input id="qtd" type="text" class="form-control" placeholder="Amount" aria-describedby="sizing-addon2" value="0" onkeyup="pegaParada()">
        </div>
    </div>

<h1 id="result">Eco-Coin 0 R$ 0</h1>
    </div>

</div>
</body>
</html>
