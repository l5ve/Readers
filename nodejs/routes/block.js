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
  var params = [user_id, toon_id];

  var exist_subs_sql = 'select * from subscribe where user_id = ? and toon_id = ?';
  var exist_block_sql = 'select * from user_block where user_id = ? and toon_id = ?';
  var ins_block_sql = 'insert into user_block (user_id, toon_id) values (?, ?)';


  connection.query(exist_block_sql, params, function(err, rows){
    if(err) {
      console.log('error!');
      return res.sendStatus(400);
    }

    if (rows.length === 0){ // 숨김 추가
      connection.query(ins_block_sql, params, function(err, rows){
        if(err) return res.sendStatus(400);
        else return res.sendStatus(200);
      });
    }

    else{
      res.send('duplicate block');
    }
  });
});

router.post('/delete', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var toon_id = req.body.toon_id;
  var params = [user_id, toon_id];

  var exist_block_sql = 'select * from user_block where user_id = ? and toon_id = ?';
  var del_block_sql = 'delete from user_block where user_id = ? and toon_id = ?';

  connection.query(exist_block_sql, params, function (err, rows) {
    if(err) return res.sendStatus(400);

    if (rows.length !== 0){
      connection.query(del_block_sql, params, function(err, rows){
        if(err) return res.sendStatus(400);
        else return res.sendStatus(200);
      });
    }

    else res.send('not exist');
  });
});

module.exports = router;
