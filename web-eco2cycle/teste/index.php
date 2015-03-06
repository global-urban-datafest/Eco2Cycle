$response = file_get_contents('http://example.com/path/to/api/call?param1=5');
$response = json_decode($response);
echo $response