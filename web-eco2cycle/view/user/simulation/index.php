<?php include "../template/header.php" ?>

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
include "../template/menu.php" ?>

<?php
$response = file_get_contents('http://ecociclews.mybluemix.net/api/product/0');
$products = json_decode($response);

?>


<div class="container">

   <select id="first-disabled"  data-hide-disabled="true" data-live-search="true" onclick="pegaParada()">
    <optgroup disabled="disabled" label="disabled">
      <option>Hidden</option>
    </optgroup>

      <?php foreach($products as $product){
        echo "<option value=\" $product->price,$product->ecocoin,$product->unity\">".$product->product."</option>";
        }?>

  </select>
    <div style="width: 400px">
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

<h1 id="result">Eco-Coin 1235 R$ 10,23</h1>

</body>
</html>
