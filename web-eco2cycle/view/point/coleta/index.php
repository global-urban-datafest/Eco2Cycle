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
        document.getElementById("price").value = eco[2];

        var qtd = document.getElementById("qtd").value;
        var json = "{" +
        "\"idOperacao\":\"0\",\"price\":\""+ (parseFloat(eco[2])*parseFloat(qtd))+"\" ,\"ecoCoin\": \""+(parseFloat(eco[1])*parseFloat(qtd))+"\",\"productPointidProdutoPonto\":{\"idProdutoPonto\": \""+eco[0]+"\"},\"clientidCliente\":{\"idCliente\":\"201\"}}";
        document.getElementById("result").innerHTML = "json : "+json;
        document.getElementById("result").innerHTML ="Eco-Coin "+ (parseFloat(eco[1])*parseFloat(qtd))+" R$ "+ (parseFloat(eco[2])*parseFloat(qtd))+
        "<br><div align='center'> <iframe  width=\"310\" height=\"310\" frameborder=\"0\" src='http://api.qrserver.com/v1/create-qr-code/?size=300x300&data="+json+"'\"></iframe></div>";
    }

</script>
</head>
<body>

<?php
$_GET['page']='sim';
include "../template/menu.php" ?>

<?php
$response = file_get_contents('http://ecociclews.mybluemix.net/api/productpoint/produtosponto/'.$_SESSION['point']->idPontoColeta);
$products = json_decode($response);

?>
<div id="navqw">
<select id="first-disabled"  data-hide-disabled="true" data-live-search="true" onclick="pegaParada()">
    <optgroup disabled="disabled" label="disabled">
        <option>Hidden</option>
    </optgroup>

    <?php foreach($products as $pp){
        $response3 = file_get_contents('http://ecociclews.mybluemix.net/api/product/'.$pp->productidProduto);
        $pro = json_decode($response3);
        echo "<option value=\"$pp->idProdutoPonto,$pro->ecocoin,$pp->price\">".$pro->product."</option>";
    }?>

</select>
    <div style="width: 400px">
        <div class="input-group" style="bottom: 5px; top: 3px">
            <span class="input-group-addon" id="sizing-addon2">Eco-Coin</span>
            <input disabled="disabled" id="eco" type="text" class="form-control" placeholder="Eco-Coin" aria-describedby="sizing-addon2">
        </div>
    </div>
    <div style="width: 400px; top: 5px;">
        <div class="input-group" style="bottom: 5px; top: 3px">
            <span class="input-group-addon" id="sizing-addon2">R$</span>
            <input disabled="disabled" id="price" type="text" class="form-control" placeholder="Price" aria-describedby="sizing-addon2">
        </div>
    </div>

    <div style="width: 400px">
        <div class="input-group" style="bottom: 5px; top: 3px">
            <span class="input-group-addon" id="sizing-addon2">Quantidade </span>
            <input id="qtd" type="text" class="form-control" placeholder="Quantidade" aria-describedby="sizing-addon2" value="0" onkeyup="pegaParada()">
        </div>
    </div>
</div>

<div id="section">
    <h1 id="result">Eco-Coin 1235 R$ 10,23</h1>
    <h1 id="json">Json 1235 R$ 10,23</h1>
</div>

</body>
</html>

