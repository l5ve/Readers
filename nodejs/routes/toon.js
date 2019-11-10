var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var qs = require('querystring');
var connection = mysql.createConnection({
  host     : "52.78.23.232",
  user     : "root",
  password : "readers7",
  port     : 3306,
  database : "readers"
});

//toon/endlist // 완결 웹툰 불러오기
router.get('/endlist', function(req, res, next){
  var is_end = req.query.is_end;
  console.log("is_end : " + is_end);
  var sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, max(epi_date) as last_date from toon_info as t, epi_info as e where t.toon_id = e.toon_id and t.is_end = ? group by t.toon_id';

  connection.query(sql, is_end, function(err, rows){
    console.log("I'm in connection.query()");

    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

//toon/daylist // 요일 웹툰 쿼리 받아와서 불러오기
router.get('/daylist', function(req, res, next){
  var toon_weekday = req.query.toon_weekday;
  var is_end = req.query.is_end;

  var params = [toon_weekday, is_end];

  console.log("weekday : " + toon_weekday);
  console.log("is_end : " + is_end);

  var sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, max(epi_date) as last_date from toon_info as t, epi_info as e, toon_weekday as w where t.toon_id = e.toon_id and t.toon_id = w.toon_id and w.toon_weekday = ? and t.is_end = ? group by t.toon_id';

  connection.query(sql, params, function(err, rows){
    console.log("I'm in connection.query()");

    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

//toon/genrelist // 요일 웹툰 쿼리 받아와서 불러오기
router.get('/genrelist', function(req, res, next){
  var genre_name = req.query.genre_name;

  var sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, max(epi_date) as last_date from toon_info as t, epi_info as e, toon_genre as g where t.toon_id = e.toon_id and t.toon_id = g.toon_id and genre_name = ? group by t.toon_id;';

  connection.query(sql, genre_name, function(err, rows){
    console.log("I'm in connection.query()");

    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

module.exports = router;
