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

router.post('/add', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var toon_id = req.body.toon_id;
  var epi_name = req.body.epi_name;
  var params = [user_id, toon_id, epi_name];

  var exist_bm_sql = 'select * from bookmark where user_id = ? and toon_id = ? and epi_name = ?'
  var ins_bm_sql = 'insert into bookmark (user_id, toon_id, epi_name) values (?, ?, ?)';

  connection.query(exist_bm_sql, params, function(err, rows){
    if(err) {
      console.log('error!');
      return res.sendStatus(400);
    }

    if (rows.length === 0){ // 구독 추가
      connection.query(ins_bm_sql, params, function(err, rows){
        if(err) return res.sendStatus(400);
        else return res.sendStatus(200);
      });
    }

    else{
      res.send('duplicate bookmark');
    }
  });
});

router.post('/delete', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var toon_id = req.body.toon_id;
  var epi_name = req.body.epi_name;
  var params = [user_id, toon_id, epi_name];

  var exist_bm_sql = 'select * from bookmark where user_id = ? and toon_id = ? and epi_name = ?';
  var del_bm_sql = 'delete from bookmark where user_id = ? and toon_id = ? and epi_name = ?';

  connection.query(exist_bm_sql, params, function (err, rows) {
    if(err) return res.sendStatus(400);

    if (rows.length !== 0){
      connection.query(del_bm_sql, params, function(err, rows){
        if(err) return res.sendStatus(400);
        else return res.sendStatus(200);
      });
    }

    else res.send('not exist');
  });
});

router.post('/list', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var order_by = req.body.order_by; // desc, asc, site

  var desc_sql = 'select i.toon_id, i.toon_site, i.toon_name, e.epi_name, i.wrt_name, e.epi_date, e.epi_url, e.epi_thumb_url from toon_info as i, bookmark as b, epi_info as e where user_id = ? and i.toon_id = b.toon_id and b.toon_id = e.toon_id and b.epi_name = e.epi_name order by e.epi_date desc';
  var asc_sql = 'select i.toon_id, i.toon_site, i.toon_name, e.epi_name, i.wrt_name, e.epi_date, e.epi_url, e.epi_thumb_url from toon_info as i, bookmark as b, epi_info as e where user_id = ? and i.toon_id = b.toon_id and b.toon_id = e.toon_id and b.epi_name = e.epi_name order by e.epi_date asc';
  var site_sql = 'select i.toon_id, i.toon_site, i.toon_name, e.epi_name, i.wrt_name, e.epi_date, e.epi_url, e.epi_thumb_url from toon_info as i, bookmark as b, epi_info as e where user_id = ? and i.toon_id = b.toon_id and b.toon_id = e.toon_id and b.epi_name = e.epi_name order by toon_site, toon_name';

  if (order_by == 'desc'){
    connection.query(desc_sql, user_id, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
    });
  }

  else if (order_by == 'asc'){
    connection.query(asc_sql, user_id, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
    });
  }

  else {
    connection.query(site_sql, user_id, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
    });
  }
});

module.exports = router;
