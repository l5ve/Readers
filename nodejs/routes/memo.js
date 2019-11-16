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

router.post('/save', function (req, res) {
    console.log(req.body);
    var user_id = req.body.user_id;
    var toon_id = req.body.toon_id;
    var content = req.body.content;

    var exist_memo_sql = 'select * from memo where user_id = ? and toon_id = ?';
    var ins_memo_sql = 'insert into memo (user_id, toon_id, content, memo_date) values (?, ?, ?, NOW())';
    var mod_memo_sql = 'update memo set content = ?, memo_date = now() where user_id = ? and toon_id = ?';

    var ins_params = [user_id, toon_id, content];
    var mod_params = [content, user_id, toon_id];

    connection.query(exist_memo_sql, [user_id, toon_id], function (err, rows) {
        if(err) return res.sendStatus(400);

        if (rows.length === 0){ // 메모 추가
          connection.query(ins_memo_sql, ins_params, function(err, rows){
            if(err) return res.sendStatus(400);
            else return res.sendStatus(200);
          });
        }

        else {  // 메모 수정
          connection.query(mod_memo_sql, mod_params, function(err, rows){
            if(err) return res.sendStatus(400);
            else return res.sendStatus(200);
          });
        }
    });
});

router.post('/delete', function (req, res) {
    console.log(req.body);
    var user_id = req.body.user_id;
    var toon_id = req.body.toon_id;
    var exist_memo_sql = 'select * from memo where user_id = ? and toon_id = ?';
    var del_memo_sql = 'delete from memo where user_id = ? and toon_id = ?';
    var params = [user_id, toon_id];

    connection.query(exist_memo_sql, params, function (err, rows) {
      if(err) return res.sendStatus(400);

      if (rows.length !== 0){
        connection.query(del_memo_sql, params, function(err, rows){
          if(err) return res.sendStatus(400);
          else return res.sendStatus(200);
        });
      }

      else res.send('not exist');
    });
});

router.post('/list', function (req, res) {
    console.log(req.body);
    var user_id = req.body.user_id;
    var order_by = req.body.order_by;
    var desc_sql = 'select i.toon_id, i.toon_name, i.toon_site, i.wrt_name, i.toon_thumb_url, m.content, m.memo_date from toon_info as i, memo as m where m.user_id = ? and i.toon_id = m.toon_id order by memo_date desc';
    var asc_sql = 'select i.toon_id, i.toon_name, i.toon_site, i.wrt_name, i.toon_thumb_url, m.content, m.memo_date from toon_info as i, memo as m where m.user_id = ? and i.toon_id = m.toon_id order by memo_date asc';
    var params = [user_id, order_by];

    if (order_by == 'desc'){
      connection.query(desc_sql, params, function (err, rows) {
          if(err) return res.sendStatus(400);

          console.log("rows : " + JSON.stringify(rows));
          res.status(200).json(rows);
      });
    }

    else {
      connection.query(asc_sql, params, function (err, rows) {
          if(err) return res.sendStatus(400);

          console.log("rows : " + JSON.stringify(rows));
          res.status(200).json(rows);
      });
    }
});

module.exports = router;
