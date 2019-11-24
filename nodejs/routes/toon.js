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

//toon/endlist // 완결 웹툰 불러오기
router.post('/endlist', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var is_end = req.body.is_end;
  var order_by = req.body.order_by; // name, site, update
  var params = [is_end, user_id];

  var update_sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, max(epi_date) as last_date from toon_info as t, epi_info as e where t.toon_id = e.toon_id and t.is_end = ? and t.toon_id not in (select toon_id from user_block where user_id = ?) group by t.toon_id order by last_date desc, t.toon_name';
  var name_sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, max(epi_date) as last_date from toon_info as t, epi_info as e where t.toon_id = e.toon_id and t.is_end = ? and t.toon_id not in (select toon_id from user_block where user_id = ?) group by t.toon_id order by t.toon_name';
  var site_sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, max(epi_date) as last_date from toon_info as t, epi_info as e where t.toon_id = e.toon_id and t.is_end = ? and t.toon_id not in (select toon_id from user_block where user_id = ?) group by t.toon_id order by t.toon_site, t.toon_name';

  if (order_by == 'name'){
    connection.query(name_sql, params, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
    });
  }

  else if (order_by == 'site'){
    connection.query(site_sql, params, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
    });
  }

  else {
    connection.query(update_sql, params, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
    });
  }
});

//toon/daylist // 요일 웹툰 쿼리 받아와서 불러오기
router.post('/daylist', function(req, res){
  var user_id = req.body.user_id;
  var toon_weekday = req.body.toon_weekday;
  var is_end = req.body.is_end;
  var order_by = req.body.order_by; // name, site, update

  var params = [toon_weekday, is_end, user_id];

  var update_sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, max(epi_date) as last_date from toon_info as t, epi_info as e, toon_weekday as w where t.toon_id = e.toon_id and t.toon_id = w.toon_id and w.toon_weekday = ? and t.is_end = ? and t.toon_id not in (select toon_id from user_block where user_id = ?) group by t.toon_id order by last_date desc, t.toon_name';
  var name_sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, max(epi_date) as last_date from toon_info as t, epi_info as e, toon_weekday as w where t.toon_id = e.toon_id and t.toon_id = w.toon_id and w.toon_weekday = ? and t.is_end = ? and t.toon_id not in (select toon_id from user_block where user_id = ?) group by t.toon_id order by t.toon_name';
  var site_sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, max(epi_date) as last_date from toon_info as t, epi_info as e, toon_weekday as w where t.toon_id = e.toon_id and t.toon_id = w.toon_id and w.toon_weekday = ? and t.is_end = ? and t.toon_id not in (select toon_id from user_block where user_id = ?) group by t.toon_id order by t.toon_site, t.toon_name';

  if (order_by == 'name'){
    connection.query(name_sql, params, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("name");
      res.status(200).json(rows);
    });
  }

  else if (order_by == 'site'){
    connection.query(site_sql, params, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("site");
      res.status(200).json(rows);
    });
  }

  else {
    connection.query(update_sql, params, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("update");
      res.status(200).json(rows);
    });
  }
});

//toon/genrelist
router.post('/genrelist', function(req, res){
  var user_id = req.body.user_id;
  var genre_name = req.body.genre_name;
  var order_by = req.body.order_by; // name, site, update
  var params = [genre_name, user_id];

  var name_sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, g.genre_name, max(epi_date) as last_date from toon_info as t, epi_info as e, toon_genre as g where t.toon_id = e.toon_id and t.toon_id = g.toon_id and genre_name = ? and t.toon_id not in (select toon_id from user_block where user_id = ?) group by t.toon_id order by t.toon_name';
  var site_sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, g.genre_name, max(epi_date) as last_date from toon_info as t, epi_info as e, toon_genre as g where t.toon_id = e.toon_id and t.toon_id = g.toon_id and genre_name = ? and t.toon_id not in (select toon_id from user_block where user_id = ?) group by t.toon_id order by t.toon_site, t.toon_name';
  var update_sql = 'select t.toon_id, t.toon_name, t.toon_site, t.wrt_name, t.toon_thumb_url, g.genre_name, max(epi_date) as last_date from toon_info as t, epi_info as e, toon_genre as g where t.toon_id = e.toon_id and t.toon_id = g.toon_id and genre_name = ? and t.toon_id not in (select toon_id from user_block where user_id = ?) group by t.toon_id order by last_date desc, t.toon_name';

  if (order_by == 'name'){
    connection.query(name_sql, params, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("name");
      res.status(200).json(rows);
    });
  }

  else if (order_by == 'site'){
    connection.query(site_sql, params, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("site");
      res.status(200).json(rows);
    });
  }

  else {
    connection.query(update_sql, params, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("update");
      res.status(200).json(rows);
    });
  }
});

// 웹툰 상세 페이지
router.post('/detailPage', function(req, res){
  var user_id = req.body.user_id;
  var toon_id = req.body.toon_id;

  var params = [toon_id, user_id, toon_id, user_id, toon_id, user_id, toon_id];
  var sql = 'select toon_id, toon_desc, (select content from memo where toon_id = ? and user_id = ?) as content, (select exists (select * from subscribe where toon_id = ? and user_id = ?)) as subs_flag, (select exists (select * from user_block where toon_id = ? and user_id = ?)) as block_flag from toon_info where toon_id = ?';

  connection.query(sql, params, function(err, rows){
    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

//toon/detailGenres // 웹툰 상세 페이지 - 장르 정보
router.post('/detailgenrelist', function(req, res){
  var toon_id = req.body.toon_id;

  var sql = 'select * from toon_genre where toon_id = ?';

  connection.query(sql, toon_id, function(err, rows){
    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

router.post('/episodelist', function(req, res){
  var toon_id = req.body.toon_id;
  var user_id = req.body.user_id;
  var params = [toon_id, user_id, toon_id];

  var sql = 'select toon_id, epi_name as curr_epi, epi_url, epi_thumb_url, epi_date, (select exists (select * from bookmark as b, epi_info as e where b.toon_id = e.toon_id and e.toon_id = ? and b.epi_name = curr_epi and b.user_id = ?)) as bm_flag from epi_info where toon_id = ? order by epi_date desc';

  connection.query(sql, params, function(err, rows){
    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

router.post('/search', function(req, res){
  var word = req.body.word;
  var s_word = '%' + word + '%';
  var params = [s_word, s_word];
  var sql = 'select toon_id, toon_name, toon_site, wrt_name, toon_thumb_url from toon_info where toon_name like ? or wrt_name like ?';

  connection.query(sql, params, function(err, rows){
    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

module.exports = router;
