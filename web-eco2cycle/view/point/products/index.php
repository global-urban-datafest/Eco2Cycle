<?php include "../template/header.php" ?>

<meta charset="utf-8" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html"
      xmlns="http://www.w3.org/1999/html">

  <link rel="stylesheet" type="text/css" href="dist/css/bootstrap-select.css">
  <script type="text/javascript" src="dist/js/bootstrap-select.js"></script>

    <script>
    function pegaParada()
    {
        var e = document.getElementById("first-disabled").value;


        var eco = e.split(",");
        document.getElementById("idProd").value = eco[0];
        document.getElementById("ecocoin").value = eco[1];
        document.getElementById("price").value = eco[2];

        document.getElementById("result").innerHTML = eco[0];

    }

</script>

</head>
<body>

<?php
$_GET['page']='prod';
include "../template/menu.php" ?>

<?php
$response = file_get_contents('http://ecociclews.mybluemix.net/api/product/0');
$products = json_decode($response);



$url='http://ecociclews.mybluemix.net/api/productpoint/produtosponto/'.$_SESSION['point']->idPontoColeta;

$response2 = file_get_contents($url);
$pointProducts = json_decode($response2);

?>

    <div id="form-cad" style="margin-left: 20px">

        <select id="first-disabled" data-hide-disabled="true" onclick="pegaParada()">
            <?php foreach($products as $product){
                echo "<option value=\"$product->idProduto,$product->ecocoin,$product->price\">".$product->product."</option>";
            }?>

        </select>
        <form  class="form-control-static" method="post" action="../../../controller/point/productPoint.php">
            <div style="width: 400px; margin-top: 5px">
                <input type="hidden" id="idProd" name="idProd" value="0">
                <div class="input-group" ">
                    <span class="input-group-addon" id="sizing-addon2">Eco-Coin</span>
                    <input disabled="true" type="text" class="form-control" placeholder="Eco-Coin" aria-describedby="sizing-addon2" name="ecocoin" id="ecocoin">
                </div>
                </div>
                    <div style="width: 400px; margin-top: 5px">
                        <div class="input-group" ">
                         <span class="input-group-addon" id="sizing-addon2">Price</span>
                         <input type="text" class="form-control" placeholder="R$" aria-describedby="sizing-addon2" name="price" id="price">
                    </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit" value="Register"/>
                </div>
            </div>
        </form>
    </div>
    <h2 id="result">asd</h2>

    <div class="panel panel-default" style="margin-top: 30px">
      <div class="panel-heading">Products</div>

      <table class="table">
        <tr>
            <td>Material</td><td>Description</td><td>Price</td><td>Eco-Coin</td><td>Unity</td>
        </tr>
        <?php
            foreach ($pointProducts as $pp){
                $response3 = file_get_contents('http://ecociclews.mybluemix.net/api/product/'.$pp->productidProduto);

                $pro = json_decode($response3);
                echo "<tr>";
                    echo "<td>".$pro->material."</td>";
                    echo "<td>".$pro->product."</td>";
                    echo "<td>".$pro->price."</td>";
                    echo "<td>".$pro->ecocoin."</td>";
                    echo "<td>".$pro->unity."</td>";
                echo "</tr>";
            }?>
      </table>
    </div>


</body>
<?php include  "../template/foot.php";