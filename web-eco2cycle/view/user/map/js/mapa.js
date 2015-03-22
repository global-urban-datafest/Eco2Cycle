var map;
var idInfoBoxAberto;
var infoBox = [];
var markers = [];

function initialize() {	
	var latlng = new google.maps.LatLng(-23.580589, -46.649846);
	
    var options = {
        zoom: 9,
		center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("mapa"), options);
}

initialize();

function abrirInfoBox(id, marker) {
	if (typeof(idInfoBoxAberto) == 'number' && typeof(infoBox[idInfoBoxAberto]) == 'object') {
		infoBox[idInfoBoxAberto].close();
	}

	infoBox[id].open(map, marker);
	idInfoBoxAberto = id;
}

function carregarPontos() {
	
	$.getJSON('js/pontos.php', function(pontos) {

		var latlngbounds = new google.maps.LatLngBounds();

		$.each(pontos, function(index, ponto) {

			var marker = new google.maps.Marker({
				position: new google.maps.LatLng(parseFloat(ponto.latitude), parseFloat(ponto.longitude)),
				title: ponto.descricao,
                icon: ponto.tipo==0?'img/icon0.png':ponto.tipo==1?'img/icon1.png':'img/marcador.png'
			});

			var myOptions = {
				content: "<p>" + ponto.descricao+ "</p><p><a href='../points/?id="+ponto.idPontoColeta+"'>details</a>",
				pixelOffset: new google.maps.Size(-150, 0)
        	};

			infoBox[parseInt(ponto.idPontoColeta)] = new InfoBox(myOptions);
			infoBox[parseInt(ponto.idPontoColeta)].marker = marker;

			infoBox[parseInt(ponto.idPontoColeta)].listener = google.maps.event.addListener(marker, 'click', function (e) {
				abrirInfoBox(parseInt(ponto.idPontoColeta), marker);
			});

			markers.push(marker);

			latlngbounds.extend(marker.position);

		});

		var markerCluster = new MarkerClusterer(map, markers);

		map.fitBounds(latlngbounds);

	});

}

carregarPontos();