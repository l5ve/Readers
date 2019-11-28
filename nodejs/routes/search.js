var express = require('express');
var router = express.Router();
var mysql = require('mysql');

var connection = mysql.createConnection({
  host     : "52.78.23.232",
  user     : "root",
  password : "readers7",
  port     : 3306,
  database : "readers"
});

router.post('/title', function(req, res){
  var word = req.body.word;
  var s_word = '%' + word + '%';
  var sql = 'select toon_id, toon_name, toon_site, wrt_name, toon_thumb_url, toon_desc from toon_info where toon_name like ?';

  connection.query(sql, s_word, function(err, rows){
    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

router.post('/writer', function(req, res){
  var word = req.body.word;
  var s_word = '%' + word + '%';
  var sql = 'select toon_id, toon_name, toon_site, wrt_name, toon_thumb_url, toon_desc from toon_info where wrt_name like ?';

  connection.query(sql, s_word, function(err, rows){
    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

router.post('/desc', function(req, res){
  var word = req.body.word;
  var s_word = '%' + word + '%';
  var sql = 'select toon_id, toon_name, toon_site, wrt_name, toon_thumb_url, toon_desc from toon_info where toon_desc like ?';

  connection.query(sql, s_word, function(err, rows){
    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

module.exports = router;
